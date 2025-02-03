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
 *
 * @author 2dam
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

    public void initialize() {

        logOutMItem.setOnAction(this::logOut);
        providerMItem.setOnAction(this::showProviders);
        movieMItem.setOnAction(this::showMovies);
        categoryMItem.setOnAction(this::showCategories);

    }

    @FXML
    public void logOut(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = loader.load();
            SignInController controller = (SignInController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Close the current stage (the one with the menu)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading signIn window: " + ex);
        }
    }

    @FXML
    public void showMovies(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MovieMainView.fxml"));
            Parent root = loader.load();
            MovieController controller = (MovieController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Close the current stage (the one with the menu)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading movies window: " + ex);
        }
    }

    @FXML
    public void showCategories(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MainCategory.fxml"));
            Parent root = loader.load();
            CategoryController controller = (CategoryController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Close the current stage (the one with the menu)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading categories window: " + ex);
        }
    }

    @FXML
    public void showProviders(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MainProviders.fxml"));
            Parent root = loader.load();
            ProviderController controller = (ProviderController) loader.getController();
            Stage loginStage = new Stage();
            controller.setStage(loginStage);
            controller.initialize(root);
            // Close the current stage (the one with the menu)
            Stage currentStage = (Stage) menuBar.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            logger.severe("Error loading providers window: " + ex);
        }
    }
}
