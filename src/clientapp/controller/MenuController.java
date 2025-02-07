package clientapp.controller;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Controlador para la barra de menú de la aplicación. Esta clase maneja las interacciones
 * del usuario con los elementos de la barra de menú, como las opciones para cerrar sesión,
 * mostrar las vistas de películas, categorías y proveedores.
 * 
 * La clase se encarga de cargar las vistas correspondientes cuando el usuario selecciona
 * alguna de las opciones del menú y cerrar la ventana actual.
 */
public class MenuController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem logOutMItem;

    @FXML
    private MenuItem providerMItem;

    @FXML
    private MenuItem movieMItem;

    @FXML
    private MenuItem categoryMItem;

    private Logger logger = Logger.getLogger(InfoViewController.class.getName());

    /**
     * Inicializa la barra de menú, configurando las acciones de cada elemento del menú
     * para que correspondan con las funciones adecuadas (cerrar sesión, mostrar películas,
     * categorías y proveedores).
     */
    public void initialize() {
        logOutMItem.setOnAction(this::logOut);
        providerMItem.setOnAction(this::showProviders);
        movieMItem.setOnAction(this::showMovies);
        categoryMItem.setOnAction(this::showCategories);
    }

    /**
     * Cierra la sesión del usuario, abriendo la ventana de inicio de sesión y cerrando
     * la ventana actual.
     *
     * @param event El evento que dispara la acción (clic en el menú de cerrar sesión).
     */
    @FXML
    public void logOut(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = loader.load();
            SignInController controller = (SignInController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Cierra la ventana actual (la que contiene el menú)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading signIn window: " + ex);
        }
    }

    /**
     * Muestra la ventana de películas cargando la vista correspondiente y cerrando
     * la ventana actual.
     *
     * @param event El evento que dispara la acción (clic en el menú de películas).
     */
    @FXML
    public void showMovies(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MovieMainView.fxml"));
            Parent root = loader.load();
            MovieController controller = (MovieController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Cierra la ventana actual (la que contiene el menú)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading movies window: " + ex);
        }
    }

    /**
     * Muestra la ventana de categorías cargando la vista correspondiente y cerrando
     * la ventana actual.
     *
     * @param event El evento que dispara la acción (clic en el menú de categorías).
     */
    @FXML
    public void showCategories(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MainCategory.fxml"));
            Parent root = loader.load();
            CategoryController controller = (CategoryController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Cierra la ventana actual (la que contiene el menú)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading categories window: " + ex);
        }
    }

    /**
     * Muestra la ventana de proveedores cargando la vista correspondiente y cerrando
     * la ventana actual.
     *
     * @param event El evento que dispara la acción (clic en el menú de proveedores).
     */
    @FXML
    public void showProviders(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MainProviders.fxml"));
            Parent root = loader.load();
            ProviderController controller = (ProviderController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Cierra la ventana actual (la que contiene el menú)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading providers window: " + ex);
        }
    }
}
