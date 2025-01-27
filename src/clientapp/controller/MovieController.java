/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author enzo
 */
public class MovieController {

    @FXML
    private TableColumn<MovieEntity, String> titleColumn;

    @FXML
    private TableColumn imgColumn;

    @FXML
    private TableColumn<MovieEntity, Integer> durationColumn;

    @FXML
    private TableColumn<MovieEntity, String> sinopsisColumn;

    @FXML
    private TableColumn<MovieEntity, Date> rDateColumn;

    @FXML
    private TableColumn<MovieEntity, MovieHour> movieHourClolumn;

    @FXML
    private TableColumn<MovieEntity, Long> providerColumn;

    @FXML
    private TableColumn<MovieEntity, ObservableList<CategoryEntity>> categoriesColumn;

    @FXML
    private TableView<MovieEntity> moviesTbv;

    @FXML
    private Button removeMovieBtn;

    @FXML
    private Button addMovieBtn;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfSinopsis;

    @FXML
    private TextField tfDuration;

    @FXML
    private TextField tfReleaseDate;

    @FXML
    private TextField tfMovieHour;

    @FXML
    private TextField tfCategories;

    @FXML
    private TextField tfProviders;

    private Stage stage;

    private IMovie movieManager;

    private ICategory categoryManager;

    private IProvider providerManager;

    private ObservableList<MovieEntity> movies;

    private List<CategoryEntity> availableCategories;

    private List<ProviderEntity> availableProviders;

    public void initialize(Parent root) {

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Movie");
        stage.setResizable(false);
        moviesTbv.setEditable(true);
        imgColumn.setEditable(false);
        removeMovieBtn.setOnAction(this::handleRemoveAction);

        movieManager = MovieFactory.getIMovie();
        categoryManager = CategoryFactory.getICategory();
        providerManager = ProviderManagerFactory.getIProvider();

        try {
            availableCategories = categoryManager.findAll(new GenericType<List<CategoryEntity>>() {
            });

            availableProviders = providerManager.findAll(new GenericType<List<ProviderEntity>>() {
            });

            movies = FXCollections.observableArrayList(movieManager.findAll(new GenericType<List<MovieEntity>>() {
            }));

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

        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }

        titleColumn.setCellFactory(TextFieldTableCell.<MovieEntity>forTableColumn());
        titleColumn.setOnEditCommit((CellEditEvent<MovieEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setTitle(t.getNewValue());
            MovieEntity movie = t.getRowValue();
            movie.setTitle(t.getNewValue());
            movieManager.edit(movie, String.valueOf(movie.getId()));
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
                    return null;  // Si no es un número válido, devuelve null
                }
            }
        }));
        durationColumn.setOnEditCommit((CellEditEvent<MovieEntity, Integer> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setDuration(t.getNewValue());
            MovieEntity movie = t.getRowValue();
            movie.setDuration(t.getNewValue());
            movieManager.edit(movie, String.valueOf(movie.getId()));
        });

        sinopsisColumn.setCellFactory(TextFieldTableCell.<MovieEntity>forTableColumn());
        sinopsisColumn.setOnEditCommit((CellEditEvent<MovieEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSinopsis(t.getNewValue());
            MovieEntity movie = t.getRowValue();
            movie.setSinopsis(t.getNewValue());
            movieManager.edit(movie, String.valueOf(movie.getId()));
        });

        rDateColumn.setCellFactory(column -> new DatePickerCellEditer());
        rDateColumn.setOnEditCommit(event -> {
            MovieEntity movie = event.getRowValue();
            movie.setReleaseDate(event.getNewValue());
            movieManager.edit(movie, String.valueOf(movie.getId()));
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
            movieManager.edit(movie, String.valueOf(movie.getId()));
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
                        try {
                            // Llamar al servicio REST para actualizar la película
                            movieManager.edit(movie, String.valueOf(movie.getId()));
                            Platform.runLater(() -> {
                                movies.set(rowIndex, movie); // Actualizar la fila directamente
                            });
                        } catch (WebApplicationException e) {
                            // Manejar el error si es necesario
                        }
                    }
                }
            }
        });

        setupContextMenu();

        stage.show();
    }

    public void handleRemoveAction(ActionEvent event) {
        MovieEntity RmMovie = (MovieEntity) moviesTbv.getSelectionModel().getSelectedItem();
        if (RmMovie != null && RmMovie.getId() != null) {
            System.out.println("ID de la película a eliminar: " + RmMovie.getId());
            movieManager.remove(String.valueOf(RmMovie.getId()));
            moviesTbv.getItems().remove(RmMovie);
            moviesTbv.refresh();
        } else {
            System.out.println("No se puede eliminar una película sin ID.");
        }
    }

    public void handleCreateAction(ActionEvent event) {
        // Crear una nueva instancia de MovieEntity
        MovieEntity newMovie = new MovieEntity();

        movieManager.create(newMovie);
        movies = FXCollections.observableArrayList(movieManager.findAll(new GenericType<List<MovieEntity>>() {
        }));
        moviesTbv.setItems(movies);

    }

    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addCategoryMenuItem = new MenuItem("Añadir categoria");

        addCategoryMenuItem.setOnAction(event -> {
            MovieEntity selectedCategory = moviesTbv.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                showCategorySelectionDialog(selectedCategory);
            }
        });

        contextMenu.getItems().add(addCategoryMenuItem);

        moviesTbv.setContextMenu(contextMenu);
    }

    private void showCategorySelectionDialog(MovieEntity movie) {
        Stage categoryStage = new Stage();
        categoryStage.setTitle("Seleccionar Categorías");

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

                        movieManager.edit(movie, String.valueOf(movie.getId()));
                        categoryStage.close();
                        moviesTbv.refresh();
                        //     logger.info("Categorías seleccionadas para la película: " + movie.getName());
                    } catch (Exception ex) {
                        // Loguear error si algo sale mal
                        //   logger.log(Level.SEVERE, "Error al añadir categorías a la película", ex);
                        //showAlert("Error", "No se pudieron añadir las categorías a la película");
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
            // logger.log(Level.SEVERE, "Error al cargar las categorías", ex);
            //showAlert("Error", "No se pudieron cargar las categorías");
        }
    }

    public TableColumn getTitleColumn() {
        return titleColumn;
    }

    public void setTitleColumn(TableColumn titleColumn) {
        this.titleColumn = titleColumn;
    }

    public TableColumn getDurationColumn() {
        return durationColumn;
    }

    public void setDurationColumn(TableColumn durationColumn) {
        this.durationColumn = durationColumn;
    }

    public TableColumn getSinopsisColumn() {
        return sinopsisColumn;
    }

    public void setSinopsisColumn(TableColumn sinopsisColumn) {
        this.sinopsisColumn = sinopsisColumn;
    }

    public TableColumn getMovieHourClolumn() {
        return movieHourClolumn;
    }

    public void setMovieHourClolumn(TableColumn movieHourClolumn) {
        this.movieHourClolumn = movieHourClolumn;
    }

    public TableColumn getProviderColumn() {
        return providerColumn;
    }

    public void setProviderColumn(TableColumn providerColumn) {
        this.providerColumn = providerColumn;
    }

    public TableColumn getCategoriesColumn() {
        return categoriesColumn;
    }

    public void setCategoriesColumn(TableColumn categoriesColumn) {
        this.categoriesColumn = categoriesColumn;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public IMovie getMovieManager() {
        return movieManager;
    }

    public void setMovieManager(IMovie movieManager) {
        this.movieManager = movieManager;
    }

    public TableColumn getImgColumn() {
        return imgColumn;
    }

    public void setImgColumn(TableColumn imgColumn) {
        this.imgColumn = imgColumn;
    }

    public TableColumn getrDateColumn() {
        return rDateColumn;
    }

    public void setrDateColumn(TableColumn rDateColumn) {
        this.rDateColumn = rDateColumn;
    }

    public TableView getMoviesTbv() {
        return moviesTbv;
    }

    public void setMoviesTbv(TableView moviesTbv) {
        this.moviesTbv = moviesTbv;
    }

    public Button getRemoveMovieBtn() {
        return removeMovieBtn;
    }

    public void setRemoveMovieBtn(Button removeMovieBtn) {
        this.removeMovieBtn = removeMovieBtn;
    }

    public Button getAddMovieBtn() {
        return addMovieBtn;
    }

    public void setAddMovieBtn(Button addMovieBtn) {
        this.addMovieBtn = addMovieBtn;
    }

    public TextField getTfTitle() {
        return tfTitle;
    }

    public void setTfTitle(TextField tfTitle) {
        this.tfTitle = tfTitle;
    }

    public TextField getTfSinopsis() {
        return tfSinopsis;
    }

    public void setTfSinopsis(TextField tfSinopsis) {
        this.tfSinopsis = tfSinopsis;
    }

    public TextField getTfDuration() {
        return tfDuration;
    }

    public void setTfDuration(TextField tfDuration) {
        this.tfDuration = tfDuration;
    }

    public TextField getTfReleaseDate() {
        return tfReleaseDate;
    }

    public void setTfReleaseDate(TextField tfReleaseDate) {
        this.tfReleaseDate = tfReleaseDate;
    }

    public TextField getTfMovieHour() {
        return tfMovieHour;
    }

    public void setTfMovieHour(TextField tfMovieHour) {
        this.tfMovieHour = tfMovieHour;
    }

    public TextField getTfCategories() {
        return tfCategories;
    }

    public void setTfCategories(TextField tfCategories) {
        this.tfCategories = tfCategories;
    }

    public TextField getTfProviders() {
        return tfProviders;
    }

    public void setTfProviders(TextField tfProviders) {
        this.tfProviders = tfProviders;
    }

}