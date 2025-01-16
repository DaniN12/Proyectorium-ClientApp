/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.model.UserEntity;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class MenuAdminController {

    private Stage stage;

    @FXML
    private Button moviesBtn;

    @FXML
    private Button providersBtn;

    @FXML
    private Button categoriesBtn;
    
    
    public void initialize(Parent root, UserEntity user) {

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Movie");
        stage.setResizable(false);

        stage.show();
    }

    @FXML
    public void openMoviesWindow(ActionEvent event, UserEntity user) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/MovieMainView.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            MovieController controller = (MovieController) loader.getController();
            //Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load Movies controller");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            //Initializes the controller with the loaded view
            controller.initialize(root, user);

        } catch (IOException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    @FXML
    public void openProvidersWindow(ActionEvent event, UserEntity user) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/MainProviders.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            ProviderController controller = (ProviderController) loader.getController();
            //Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load Movies controller");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            //Initializes the controller with the loaded view
            controller.initialize(root, user);

        } catch (IOException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /* 
     @FXML
    public void openCategoriesWindow(ActionEvent event, UserEntity user) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/MainCategory.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            CategoryController controller = (CategoryController) loader.getController();
            //Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load Movies controller");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            //Initializes the controller with the loaded view
            controller.initialize(root, user);

        } catch (IOException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert messsage
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
     */
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Button getMoviesBtn() {
        return moviesBtn;
    }

    public void setMoviesBtn(Button moviesBtn) {
        this.moviesBtn = moviesBtn;
    }

    public Button getProvidersBtn() {
        return providersBtn;
    }

    public void setProvidersBtn(Button providersBtn) {
        this.providersBtn = providersBtn;
    }

    public Button getCategoriesBtn() {
        return categoriesBtn;
    }

    public void setCategoriesBtn(Button categoriesBtn) {
        this.categoriesBtn = categoriesBtn;
    }

}
