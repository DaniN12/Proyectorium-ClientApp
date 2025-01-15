/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import clientapp.controller.CategoryController;
import clientapp.factories.CategoryFactory;
import clientapp.model.CategoryEntity;
import java.util.List;
import clientapp.controller.MovieController;
import clientapp.controller.SignInController;
import clientapp.factories.MovieFactory;
import clientapp.model.MovieEntity;
import clientapp.controller.ProviderController;
import clientapp.factories.ProviderManagerFactory;
import clientapp.model.ProviderEntity;
import java.util.List;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class Main extends Application {

    /**
     * Method to open the main window, in this case the signIn window
     *
     * @param stage the main window
     * @throws Exception when the view cannot be found
     */
    @Override
    public void start(Stage stage) throws Exception {

        // Load DOM form FXML view
        FXMLLoader loader = new FXMLLoader(
                /*getClass().getResource("/clientapp/view/MainCategory.fxml"));
        Parent root = (Parent) loader.load();
        // Retrieve the controller associated with the view
        CategoryController controller = (CategoryController) loader.getController();*/
               /* getClass().getResource("/clientapp/view/MovieMainView.fxml"));
        Parent root = (Parent) loader.load();
        // Retrieve the controller associated with the view
        MovieController controller = (MovieController) loader.getController();*/
                getClass().getResource("/clientapp/view/MainProviders.fxml"));
        Parent root = (Parent) loader.load();
        // Retrieve the controller associated with the view
        ProviderController  controller = (ProviderController) loader.getController();
        controller.setStage(stage);
        //Initializes the controller with the loaded view
        controller.initialize(root);

    }

    /**
     * Launches the application
     *
     * @param args the command line arguments
     */
   public static void main(String[] args) {
        launch(args);
    }
}
