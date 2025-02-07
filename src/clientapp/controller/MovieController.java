/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.factories.DatePickerCellEditer;
import clientapp.factories.CategoryFactory;
import clientapp.factories.MovieFactory;
import clientapp.factories.ProviderManagerFactory;
import clientapp.interfaces.ICategory;
import clientapp.interfaces.IMovie;
import clientapp.interfaces.IProvider;
import clientapp.model.CategoryEntity;
import clientapp.model.MovieEntity;
import clientapp.model.MovieHour;
import clientapp.model.ProviderEntity;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Controlador para la gestión de películas en la aplicación. Esta clase maneja
 * la lógica de la interfaz de usuario relacionada con la visualización,
 * edición, eliminación y filtrado de películas.
 *
 * @author enzo
 */
public class MovieController {

    /**
     * Columna de la tabla que muestra el título de la película.
     */
    @FXML
    private TableColumn<MovieEntity, String> titleColumn;

    /**
     * Columna de la tabla que muestra la imagen de la película.
     */
    @FXML
    private TableColumn imgColumn;

    /**
     * Columna de la tabla que muestra la duración de la película.
     */
    @FXML
    private TableColumn<MovieEntity, Integer> durationColumn;

    /**
     * Columna de la tabla que muestra la sinopsis de la película.
     */
    @FXML
    private TableColumn<MovieEntity, String> sinopsisColumn;

    /**
     * Columna de la tabla que muestra la fecha de lanzamiento de la película.
     */
    @FXML
    private TableColumn<MovieEntity, Date> rDateColumn;

    /**
     * Columna de la tabla que muestra la hora de la película.
     */
    @FXML
    private TableColumn<MovieEntity, MovieHour> movieHourClolumn;

    /**
     * Columna de la tabla que muestra el proveedor de la película.
     */
    @FXML
    private TableColumn<MovieEntity, Long> providerColumn;

    /**
     * Columna de la tabla que muestra las categorías de la película.
     */
    @FXML
    private TableColumn<MovieEntity, ObservableList<CategoryEntity>> categoriesColumn;

    /**
     * Tabla que muestra la lista de películas.
     */
    @FXML
    private TableView<MovieEntity> moviesTbv;

    /**
     * Botón para eliminar una película seleccionada.
     */
    @FXML
    private Button removeMovieBtn;

    /**
     * Botón para agregar una nueva película.
     */
    @FXML
    private Button addMovieBtn;

    /**
     * Menú para filtrar películas por fecha de lanzamiento.
     */
    @FXML
    private MenuItem releaseDateMButton;

    /**
     * Menú para filtrar películas por hora.
     */
    @FXML
    private MenuItem movieHourMButton;

    /**
     * Menú para filtrar películas por proveedor.
     */
    @FXML
    private MenuItem providerMButton;

    /**
     * ComboBox para seleccionar un proveedor o una hora de película.
     */
    @FXML
    private ComboBox findProveedorCbox;

    /**
     * Ventana principal de la aplicación.
     */
    private Stage stage;

    /**
     * Interfaz para gestionar las películas.
     */
    private IMovie movieManager;

    /**
     * Interfaz para gestionar las categorías.
     */
    private ICategory categoryManager;

    /**
     * Interfaz para gestionar los proveedores.
     */
    private IProvider providerManager;

    /**
     * Lista observable de películas.
     */
    private ObservableList<MovieEntity> movies;

    /**
     * Lista de categorías disponibles.
     */
    private List<CategoryEntity> availableCategories;

    /**
     * Lista de proveedores disponibles.
     */
    private List<ProviderEntity> availableProviders;

    /**
     * Logger para registrar eventos y errores.
     */
    private Logger logger = Logger.getLogger(InfoViewController.class.getName());

    /**
     * Icono de la aplicación.
     */
    private Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));

    /**
     * Inicializa la ventana y configura los componentes de la interfaz de
     * usuario.
     *
     * @param root El nodo raíz de la interfaz de usuario.
     */
    public void initialize(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Movie");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        moviesTbv.setEditable(true);
        imgColumn.setEditable(false);
        removeMovieBtn.setOnAction(this::handleRemoveAction);
        stage.setOnCloseRequest(this::onCloseRequest);
        releaseDateMButton.setOnAction(this::filterByReleaseDate);
        movieHourMButton.setOnAction(this::filterByMovieHour);
        providerMButton.setOnAction(this::filterProvider);

        loadMovies();
        setupContextMenu();

        stage.show();
    }

    /**
     * Carga las películas desde el servidor y las muestra en la tabla.
     */
    public void loadMovies() {
        try {
            movieManager = MovieFactory.getIMovie();
            categoryManager = CategoryFactory.getICategory();
            providerManager = ProviderManagerFactory.getIProvider();

            availableCategories = categoryManager.findAll_XML(new GenericType<List<CategoryEntity>>() {
            });

            availableProviders = providerManager.findAll_XML(new GenericType<List<ProviderEntity>>() {
            });

            movies = FXCollections.observableArrayList(movieManager.findAll_XML(new GenericType<List<MovieEntity>>() {
            }));

            setUpMovies();

        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading movies", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Configura las columnas de la tabla y asigna los valores de las películas.
     */
    public void setUpMovies() {
        try {
            imgColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MovieEntity, ImageView>, ObservableValue<ImageView>>() {
                @Override
                public ObservableValue<ImageView> call(CellDataFeatures<MovieEntity, ImageView> param) {
                    MovieEntity movie = param.getValue();
                    byte[] movieImageBytes = movie.getMovieImage();

                    // Si la imagen es nula o vacía, usar una imagen predeterminada
                    Image image;
                    if (movieImageBytes == null || movieImageBytes.length == 0) {
                        // Usar una imagen predeterminada si no hay imagen
                        image = new Image(getClass().getResource("/resources/icon.png").toExternalForm());
                    } else {
                        // Convertir el array de bytes a una imagen
                        image = new Image(new ByteArrayInputStream(movieImageBytes));
                    }

                    // Crear un ImageView y establecer la imagen
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);  // Ajustar el tamaño de la imagen
                    imageView.setFitHeight(100); // Ajustar el tamaño de la imagen
                    return new SimpleObjectProperty<>(imageView);
                }
            });

            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            sinopsisColumn.setCellValueFactory(new PropertyValueFactory<>("sinopsis"));
            rDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
            movieHourClolumn.setCellValueFactory(new PropertyValueFactory<>("movieHour"));
            providerColumn.setCellValueFactory(new PropertyValueFactory<>("provider"));
            categoriesColumn.setCellValueFactory(new PropertyValueFactory<>("categories"));

            moviesTbv.setItems(movies);
            setUpEditableTable();

        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading movies", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Configura la tabla para permitir la edición de los valores de las
     * películas.
     */
    public void setUpEditableTable() {
        try {
            titleColumn.setCellFactory(TextFieldTableCell.<MovieEntity>forTableColumn());
            titleColumn.setOnEditCommit((CellEditEvent<MovieEntity, String> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setTitle(t.getNewValue());
                MovieEntity movie = t.getRowValue();
                movie.setTitle(t.getNewValue());
                movieManager.edit_XML(movie, String.valueOf(movie.getId()));
            });

            durationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
                @Override
                public String toString(Integer object) {
                    return object == null ? "" : object.toString();  // Convierte el Integer a String
                }

                @Override
                public Integer fromString(String string) {
                    try {
                        return Integer.parseInt(string);  // Convierte el String a Integer
                    } catch (NumberFormatException e) {
                        new Alert(Alert.AlertType.ERROR, "The number has to have a number format", ButtonType.OK).showAndWait();
                        return null;  // Si no es un número válido, devuelve null
                    }
                }
            }));
            durationColumn.setOnEditCommit((CellEditEvent<MovieEntity, Integer> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setDuration(t.getNewValue());
                MovieEntity movie = t.getRowValue();
                movie.setDuration(t.getNewValue());
                movieManager.edit_XML(movie, String.valueOf(movie.getId()));
            });

            sinopsisColumn.setCellFactory(TextFieldTableCell.<MovieEntity>forTableColumn());
            sinopsisColumn.setOnEditCommit((CellEditEvent<MovieEntity, String> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setSinopsis(t.getNewValue());
                MovieEntity movie = t.getRowValue();
                movie.setSinopsis(t.getNewValue());
                movieManager.edit_XML(movie, String.valueOf(movie.getId()));
            });

            rDateColumn.setCellFactory(column -> new DatePickerCellEditer());
            rDateColumn.setOnEditCommit(event -> {
                MovieEntity movie = event.getRowValue();
                movie.setReleaseDate(event.getNewValue());
                movieManager.edit_XML(movie, String.valueOf(movie.getId()));
            });

            ObservableList<MovieHour> availableHours = FXCollections.observableArrayList(MovieHour.values());

            movieHourClolumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMovieHour()));

            movieHourClolumn.setCellFactory(column -> {
                return new ComboBoxTableCell<>(new StringConverter<MovieHour>() {
                    @Override
                    public String toString(MovieHour hour) {
                        return hour != null ? hour.toString() : "";
                    }

                    @Override
                    public MovieHour fromString(String string) {
                        return MovieHour.valueOf(string);
                    }
                }, availableHours);
            });

            movieHourClolumn.setOnEditCommit(event -> {
                MovieEntity movie = event.getRowValue();
                movie.setMovieHour(event.getNewValue()); // Guarda la hora seleccionada
                movieManager.edit_XML(movie, String.valueOf(movie.getId()));
            });

            providerColumn.setCellValueFactory(cellData -> {
                MovieEntity movie = cellData.getValue();
                if (movie != null && movie.getProvider() != null) {
                    return new SimpleObjectProperty<>(movie.getProvider().getId());
                } else {
                    // Manejar el caso en el que no haya proveedor o película
                    return new SimpleObjectProperty<>(null);  // o un valor por defecto si prefieres
                }
            });

            providerColumn.setCellFactory(column -> {
                ComboBoxTableCell<MovieEntity, Long> cell = new ComboBoxTableCell<>(FXCollections.observableArrayList(availableProviders.stream().map(ProviderEntity::getId).collect(Collectors.toList())));
                // Configurar el convertidor
                cell.setConverter(new StringConverter<Long>() {
                    @Override
                    public String toString(Long providerId) {
                        return availableProviders.stream()
                                .filter(provider -> provider.getId().equals(providerId))
                                .map(ProviderEntity::getName)
                                .findFirst()
                                .orElse("");
                    }

                    @Override
                    public Long fromString(String name) {
                        return availableProviders.stream()
                                .filter(provider -> provider.getName().equals(name))
                                .map(ProviderEntity::getId)
                                .findFirst()
                                .orElse(null);
                    }
                });
                return cell;
            });

            providerColumn.setOnEditCommit(event -> {
                int rowIndex = event.getTablePosition().getRow();
                if (rowIndex >= 0 && rowIndex < movies.size()) {
                    MovieEntity movie = event.getRowValue();
                    Long selectedProviderId = event.getNewValue();
                    if (selectedProviderId != null) {
                        ProviderEntity selectedProvider = availableProviders.stream()
                                .filter(provider -> provider.getId().equals(selectedProviderId))
                                .findFirst()
                                .orElse(null);
                        if (selectedProvider != null) {
                            movie.setProvider(selectedProvider);
                            // Llamar al servicio REST para actualizar la película
                            movieManager.edit_XML(movie, String.valueOf(movie.getId()));
                            Platform.runLater(() -> {
                                movies.set(rowIndex, movie); // Actualizar la fila directamente
                            });

                        }
                    }
                }
            });

        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error triying to update movies", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Maneja la acción de eliminar una película seleccionada.
     *
     * @param event El evento de acción que desencadena la eliminación.
     */
    public void handleRemoveAction(ActionEvent event) {
        try {
            MovieEntity RmMovie = (MovieEntity) moviesTbv.getSelectionModel().getSelectedItem();
            if (RmMovie != null && RmMovie.getId() != null) {
                System.out.println("ID de la película a eliminar: " + RmMovie.getId());
                movieManager.remove(String.valueOf(RmMovie.getId()));
                moviesTbv.getItems().remove(RmMovie);
                moviesTbv.refresh();
            } else {
                logger.info("The ID cannot be null");
            }

        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error trying to remove movies", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Maneja la acción de agregar una nueva película.
     *
     * @param event El evento de acción que desencadena la creación.
     */
    public void handleCreateAction(ActionEvent event) {

        try {
            MovieEntity newMovie = new MovieEntity();

            movieManager.create_XML(newMovie);
            movies = FXCollections.observableArrayList(movieManager.findAll_XML(new GenericType<List<MovieEntity>>() {
            }));
            moviesTbv.setItems(movies);

        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error trying to add movies", ButtonType.OK).showAndWait();
        }

    }

    /**
     * Configura el menú contextual para la tabla de películas.
     */
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addCategoryMenuItem = new MenuItem("Add category");
        MenuItem addMovie = new MenuItem("Add movie");
        MenuItem removeMovie = new MenuItem("RemoveMovie");
        MenuItem print = new MenuItem("Print table");

        addCategoryMenuItem.setOnAction(event -> {
            MovieEntity selectedCategory = moviesTbv.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                showCategorySelectionDialog(selectedCategory);
            }
        });

        addMovie.setOnAction(this::handleCreateAction);
        removeMovie.setOnAction(this::handleRemoveAction);

        contextMenu.getItems().add(addCategoryMenuItem);
        contextMenu.getItems().add(addMovie);
        contextMenu.getItems().add(removeMovie);
        contextMenu.getItems().add(print);

        moviesTbv.setContextMenu(contextMenu);
    }

    /**
     * Muestra un diálogo para seleccionar categorías para una película.
     *
     * @param movie La película a la que se le agregarán las categorías.
     */
    private void showCategorySelectionDialog(MovieEntity movie) {
        Stage categoryStage = new Stage();
        categoryStage.setTitle("Select Categories");

        ListView<CategoryEntity> categoryListView = new ListView<>();
        ObservableList<CategoryEntity> selectedCategories = FXCollections.observableArrayList(); // Lista de elementos seleccionados manualmente

        try {
            List<CategoryEntity> categories = availableCategories;

            // Agregar las categorías a la lista de categorías disponibles
            categoryListView.setItems(FXCollections.observableArrayList(categories));

            // Agregar manejador de clics personalizados
            categoryListView.setCellFactory(lv -> {
                ListCell<CategoryEntity> cell = new ListCell<CategoryEntity>() {
                    @Override
                    protected void updateItem(CategoryEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName()); // Mostrar el nombre de la categoría
                        } else {
                            setText(null);
                        }
                    }
                };

                cell.setOnMouseClicked(event -> {
                    if (cell.getItem() != null) {
                        CategoryEntity clickedCategory = cell.getItem();
                        if (selectedCategories.contains(clickedCategory)) {
                            selectedCategories.remove(clickedCategory); // Deseleccionar si ya está seleccionado
                            cell.setStyle(""); // Restablecer estilo
                        } else {
                            selectedCategories.add(clickedCategory); // Seleccionar si no está seleccionado
                            cell.setStyle("-fx-background-color: lightblue;"); // Cambiar el estilo del elemento seleccionado
                        }
                    }
                });

                return cell;
            });

            Button confirmButton = new Button("Confirmar");
            confirmButton.setOnAction(e -> {
                if (!selectedCategories.isEmpty()) {
                    try {
                        // Actualizar la lista de categorías seleccionadas en la película
                        movie.setCategories(selectedCategories);

                        movieManager.edit_XML(movie, String.valueOf(movie.getId()));
                        categoryStage.close();
                        moviesTbv.refresh();

                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Error adding categories to the movie", ex);
                        new Alert(Alert.AlertType.ERROR, "Error loading categories", ButtonType.OK).showAndWait();
                    }
                }
            });

            VBox layout = new VBox(10);
            layout.getStyleClass().add("jfx-popup-container");
            layout.setPadding(new javafx.geometry.Insets(10));
            layout.getChildren().addAll(categoryListView, confirmButton);

            Scene scene = new Scene(layout);
            categoryStage.setScene(scene);
            categoryStage.setWidth(300);
            categoryStage.setHeight(300);

            categoryStage.show();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error loading categories", ex);
            new Alert(Alert.AlertType.ERROR, "Error loading categories", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Filtra la lista de películas por fecha de estreno y actualiza la tabla.
     *
     * @param event Evento de acción que activa el filtro.
     */
    @FXML
    private void filterByReleaseDate(ActionEvent event) {
        movies = FXCollections.observableArrayList(movieManager.listByReleaseDate_XML(new GenericType<List<MovieEntity>>() {
        }));
        moviesTbv.setItems(movies);
        moviesTbv.refresh();
    }

    /**
     * Configura el filtro de películas por horario de emisión, cargando las
     * opciones disponibles en un ComboBox.
     *
     * @param event Evento de acción que activa el filtro.
     */
    @FXML
    private void filterByMovieHour(ActionEvent event) {
        ObservableList<MovieHour> availableHours = FXCollections.observableArrayList(MovieHour.values());
        findProveedorCbox.setItems(availableHours);
        findProveedorCbox.setOnAction(e -> applyMovieHourFilter());
    }

    /**
     * Aplica el filtro de películas según la hora seleccionada en el ComboBox.
     */
    private void applyMovieHourFilter() {
        MovieHour selectedHour = (MovieHour) findProveedorCbox.getValue();

        if (selectedHour != null) {
            try {
                movies = FXCollections.observableArrayList(movieManager.listByMovieHour_XML(new GenericType<List<MovieEntity>>() {
                }, String.valueOf(selectedHour)));

                moviesTbv.setItems(movies);
                moviesTbv.refresh();
            } catch (WebApplicationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Configura el filtro de películas por proveedor, cargando las opciones
     * disponibles en un ComboBox.
     *
     * @param event Evento de acción que activa el filtro.
     */
    @FXML
    private void filterProvider(ActionEvent event) {
        ObservableList<ProviderEntity> providersList = FXCollections.observableArrayList(availableProviders);
        findProveedorCbox.setItems(providersList);
        findProveedorCbox.setOnAction(e -> applyProviderFilter());
    }

    /**
     * Aplica el filtro de películas según el proveedor seleccionado en el
     * ComboBox.
     */
    private void applyProviderFilter() {
        ProviderEntity selectedProvider = (ProviderEntity) findProveedorCbox.getValue();

        if (selectedProvider != null) {
            try {
                movies = FXCollections.observableArrayList(movieManager.listByProvider_XML(new GenericType<List<MovieEntity>>() {
                }, String.valueOf(selectedProvider)));

                moviesTbv.setItems(movies);
                moviesTbv.refresh();
            } catch (WebApplicationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Maneja el evento de cierre de la aplicación mostrando un cuadro de
     * diálogo de confirmación. Si el usuario confirma, la aplicación se cierra;
     * de lo contrario, la acción se cancela.
     *
     * @param event Evento de cierre de la ventana.
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        alert.setContentText("Are you sure you want to close the application?");

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * Obtiene la columna del título de la película en la tabla.
     *
     * @return La columna del título.
     */
    public TableColumn getTitleColumn() {
        return titleColumn;
    }

    /**
     * Establece la columna del título de la película en la tabla.
     *
     * @param titleColumn La nueva columna del título.
     */
    public void setTitleColumn(TableColumn titleColumn) {
        this.titleColumn = titleColumn;
    }

    /**
     * Obtiene la columna de la duración de la película en la tabla.
     *
     * @return La columna de duración.
     */
    public TableColumn getDurationColumn() {
        return durationColumn;
    }

    /**
     * Establece la columna de la duración de la película en la tabla.
     *
     * @param durationColumn La nueva columna de duración.
     */
    public void setDurationColumn(TableColumn durationColumn) {
        this.durationColumn = durationColumn;
    }

    /**
     * Obtiene la columna de la sinopsis de la película en la tabla.
     *
     * @return La columna de sinopsis.
     */
    public TableColumn getSinopsisColumn() {
        return sinopsisColumn;
    }

    /**
     * Establece la columna de la sinopsis de la película en la tabla.
     *
     * @param sinopsisColumn La nueva columna de sinopsis.
     */
    public void setSinopsisColumn(TableColumn sinopsisColumn) {
        this.sinopsisColumn = sinopsisColumn;
    }

    /**
     * Obtiene la columna de la hora de emisión de la película en la tabla.
     *
     * @return La columna de la hora de emisión.
     */
    public TableColumn getMovieHourClolumn() {
        return movieHourClolumn;
    }

    /**
     * Establece la columna de la hora de emisión de la película en la tabla.
     *
     * @param movieHourClolumn La nueva columna de la hora de emisión.
     */
    public void setMovieHourClolumn(TableColumn movieHourClolumn) {
        this.movieHourClolumn = movieHourClolumn;
    }

    /**
     * Obtiene la columna del proveedor de la película en la tabla.
     *
     * @return La columna del proveedor.
     */
    public TableColumn getProviderColumn() {
        return providerColumn;
    }

    /**
     * Establece la columna del proveedor de la película en la tabla.
     *
     * @param providerColumn La nueva columna del proveedor.
     */
    public void setProviderColumn(TableColumn providerColumn) {
        this.providerColumn = providerColumn;
    }

    /**
     * Obtiene la columna de las categorías de la película en la tabla.
     *
     * @return La columna de categorías.
     */
    public TableColumn getCategoriesColumn() {
        return categoriesColumn;
    }

    /**
     * Establece la columna de las categorías de la película en la tabla.
     *
     * @param categoriesColumn La nueva columna de categorías.
     */
    public void setCategoriesColumn(TableColumn categoriesColumn) {
        this.categoriesColumn = categoriesColumn;
    }

    /**
     * Obtiene la ventana (Stage) actual de la aplicación.
     *
     * @return La ventana de la aplicación.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Establece la ventana (Stage) de la aplicación.
     *
     * @param stage La nueva ventana.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Obtiene el administrador de películas.
     *
     * @return El administrador de películas.
     */
    public IMovie getMovieManager() {
        return movieManager;
    }

    /**
     * Establece el administrador de películas.
     *
     * @param movieManager El nuevo administrador de películas.
     */
    public void setMovieManager(IMovie movieManager) {
        this.movieManager = movieManager;
    }

    /**
     * Obtiene la columna de la imagen de la película en la tabla.
     *
     * @return La columna de la imagen.
     */
    public TableColumn getImgColumn() {
        return imgColumn;
    }

    /**
     * Establece la columna de la imagen de la película en la tabla.
     *
     * @param imgColumn La nueva columna de la imagen.
     */
    public void setImgColumn(TableColumn imgColumn) {
        this.imgColumn = imgColumn;
    }

    /**
     * Obtiene la columna de la fecha de estreno de la película en la tabla.
     *
     * @return La columna de la fecha de estreno.
     */
    public TableColumn getrDateColumn() {
        return rDateColumn;
    }

    /**
     * Establece la columna de la fecha de estreno de la película en la tabla.
     *
     * @param rDateColumn La nueva columna de la fecha de estreno.
     */
    public void setrDateColumn(TableColumn rDateColumn) {
        this.rDateColumn = rDateColumn;
    }

    /**
     * Obtiene la tabla que contiene la lista de películas.
     *
     * @return La tabla de películas.
     */
    public TableView getMoviesTbv() {
        return moviesTbv;
    }

    /**
     * Establece la tabla que contiene la lista de películas.
     *
     * @param moviesTbv La nueva tabla de películas.
     */
    public void setMoviesTbv(TableView moviesTbv) {
        this.moviesTbv = moviesTbv;
    }

    /**
     * Obtiene el botón para eliminar una película.
     *
     * @return El botón de eliminar película.
     */
    public Button getRemoveMovieBtn() {
        return removeMovieBtn;
    }

    /**
     * Establece el botón para eliminar una película.
     *
     * @param removeMovieBtn El nuevo botón de eliminar película.
     */
    public void setRemoveMovieBtn(Button removeMovieBtn) {
        this.removeMovieBtn = removeMovieBtn;
    }

    /**
     * Obtiene el botón para agregar una nueva película.
     *
     * @return El botón de agregar película.
     */
    public Button getAddMovieBtn() {
        return addMovieBtn;
    }

    /**
     * Establece el botón para agregar una nueva película.
     *
     * @param addMovieBtn El nuevo botón de agregar película.
     */
    public void setAddMovieBtn(Button addMovieBtn) {
        this.addMovieBtn = addMovieBtn;
    }
}
