package clientapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class MenuController {

    @FXML
    private MenuItem logOutMItem;

    @FXML
    private MenuItem providerMItem;

    @FXML
    private MenuItem movieMItem;

    @FXML
    private MenuItem categoryMItem;

    private Stage primaryStage;


    public void initialize() {
        logOutMItem.setOnAction(e -> logOut());
        providerMItem.setOnAction(e -> showProviders());
        movieMItem.setOnAction(e -> showMovies());
        categoryMItem.setOnAction(e -> showCategories());
    }

    private void logOut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = loader.load();
            SignInController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initialize(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMovies() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MovieMainView.fxml"));
            Parent root = loader.load();
            MovieController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initialize(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProviders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MainProviders.fxml"));
            Parent root = loader.load();
            ProviderController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initialize(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCategories() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MainCategory.fxml"));
            Parent root = loader.load();
            CategoryController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initialize(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
