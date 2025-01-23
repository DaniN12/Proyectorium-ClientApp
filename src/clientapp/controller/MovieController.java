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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private TableColumn<MovieEntity, String> durationColumn;

    @FXML
    private TableColumn<MovieEntity, String> sinopsisColumn;

    @FXML
    private TableColumn<MovieEntity, Date> rDateColumn;

    @FXML
    private TableColumn movieHourClolumn;

    @FXML
    private TableColumn providerColumn;

    @FXML
    private TableColumn<MovieEntity, List<CategoryEntity>> categoriesColumn;

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
            movies = FXCollections.observableArrayList(movieManager.findAll(new GenericType<List<MovieEntity>>() {
            }));
            availableCategories = categoryManager.findAll(new GenericType<List<CategoryEntity>>() {
            });

            imgColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MovieEntity, ImageView>, ObservableValue<ImageView>>() {
                @Override
                public ObservableValue<ImageView> call(CellDataFeatures<MovieEntity, ImageView> param) {
                    MovieEntity ticket = param.getValue();
                    byte[] movieImageBytes = ticket.getMovieImage();

                    // Convertir el array de bytes a una imagen
                    Image image = new Image(new ByteArrayInputStream(movieImageBytes));

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
            // Configurar cómo obtener los datos de la película para la columna0
            categoriesColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCategories()));

            moviesTbv.setItems(movies);

        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }

        titleColumn.setCellFactory(TextFieldTableCell.<MovieEntity>forTableColumn());
        titleColumn.setOnEditCommit((CellEditEvent<MovieEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setTitle(t.getNewValue());
        });
        /*
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
         */

        sinopsisColumn.setCellFactory(TextFieldTableCell.<MovieEntity>forTableColumn());
        sinopsisColumn.setOnEditCommit((CellEditEvent<MovieEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSinopsis(t.getNewValue());
        });

        rDateColumn.setCellFactory(column -> new DatePickerCellEditer());
        rDateColumn.setOnEditCommit(event -> {
            MovieEntity movie = event.getRowValue();
            movie.setReleaseDate(event.getNewValue());
        });

        stage.show();
    }

    public void handleRemoveAction(ActionEvent event) {

        MovieEntity RmMovie = (MovieEntity) moviesTbv.getSelectionModel().getSelectedItem();
        movieManager.remove(String.valueOf(RmMovie.getId()));
        moviesTbv.getItems().remove(RmMovie);
        moviesTbv.refresh();
    }

    public void handleCreateAction(ActionEvent event) {
        // Crear una nueva instancia de MovieEntity
        MovieEntity newMovie = new MovieEntity();

        movieManager.create(newMovie);

        movies.add(newMovie);
        moviesTbv.setItems(movies);
        moviesTbv.refresh();
    }

    /*
    private ProviderEntity findOrCreateProvider(String providerName) {
        // Intentar buscar el proveedor en la base de datos
        ProviderEntity provider = providerManager.find(providerName); // Suponiendo que tienes un manager para los proveedores
        if (provider == null) {
            // Si no existe, crear uno nuevo
            provider = new ProviderEntity();
            provider.setName(providerName);
            providerManager.create(provider); // Crear y guardar el proveedor en la base de datos
        }
        return provider;
    }
     */
    public static String fechaToString(Date fecha) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(fecha);
    }

    public static Date stringToDate(String fechaStr) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formateador.parse(fechaStr);
        } catch (ParseException e) {
            System.out.println("Error: Formato de fecha incorrecto. Usa dd/MM/yyyy.");
            return null; // Retorna null si la conversión falla
        }
    }

    public static Date leerFechaDMA() {
        boolean error;
        LocalDate localDate = null;
        String dateString;
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            error = false;
            dateString = introducirCadena();  // Asumo que este método obtiene la entrada del usuario
            try {
                localDate = LocalDate.parse(dateString, formateador);
            } catch (DateTimeParseException e) {
                System.out.println("Error, introduce una fecha en formato dd/MM/yyyy ");
                error = true;
            }
        } while (error);

        // Convertimos LocalDate a Date
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String introducirCadena() {
        String cadena = "";
        boolean error;
        InputStreamReader entrada = new InputStreamReader(System.in);
        BufferedReader teclado = new BufferedReader(entrada);
        do {
            error = false;
            try {
                cadena = teclado.readLine();
            } catch (IOException e) {
                System.out.println("Error en la entrada de datos");
                error = true;
            }
        } while (error);
        return cadena;
    }

    /*
    private List<CategoryEntity> obtenerCategoriasDesdeTexto(String categoriasTexto) {
        List<CategoryEntity> categories = new ArrayList<>();

        // Suponiendo que las categorías vienen separadas por comas en el TextField
        String[] nombresCategorias = categoriasTexto.split(",");

        for (String nombre : nombresCategorias) {
            nombre = nombre.trim(); // Eliminar espacios en blanco

            // Buscar la categoría en la base de datos o en una lista
            CategoryEntity categoria = categoryManager.find(nombre);

            if (categoria != null) {
                categories.add(categoria);
            } else {
                System.out.println("Advertencia: Categoría '" + nombre + "' no encontrada.");
            }
        }

        return categories;
    }
     */
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
