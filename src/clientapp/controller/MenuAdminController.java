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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller class for the admin menu view. It handles user interactions
 * with buttons in the admin menu (e.g., Movies, Providers, Categories, and Logout).
 * It is responsible for navigating between different views (e.g., Movie, Provider, Category views) and managing the application's state.
 * 
 * This controller also initializes the admin menu with images and button actions,
 * and handles the event for logging out and returning to the SignIn view.
 */
public class MenuAdminController {

    private Stage stage;

    @FXML
    private Button moviesBtn;

    @FXML
    private Button providersBtn;

    @FXML
    private Button categoriesBtn;

    @FXML
    private Button logoutBtn;
    
    @FXML
    private ImageView providersImageView;
    
    @FXML
    private ImageView moviesImageView;
    
    @FXML
    private ImageView categoriesImageView;

    /**
     * Initializes the admin menu window, setting up the stage, button actions,
     * and loading images for the buttons. It is called when the scene is created.
     *
     * @param root The root node for the scene.
     * @param user The user entity representing the currently logged-in user.
     */
    public void initialize(Parent root, UserEntity user) {

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Movie");
        stage.setResizable(false);
        moviesBtn.setOnAction(this::openMoviesWindow);
        providersBtn.setOnAction(this::openProvidersWindow);
        categoriesBtn.setOnAction(this::openCategoriesWindow);
        logoutBtn.setOnAction(this::backButtonAction);
        categoriesImageView.setImage(new Image(getClass().getResourceAsStream("/resources/iconCategory.png")));
        moviesImageView.setImage(new Image(getClass().getResourceAsStream("/resources/iconMovie.png")));
        providersImageView.setImage(new Image(getClass().getResourceAsStream("/resources/iconProvider.png")));

        stage.show();
    }

    /**
     * Opens the Movies window by loading the corresponding FXML view and controller.
     * 
     * @param event The action event triggered by the Movies button.
     */
    @FXML
    public void openMoviesWindow(ActionEvent event) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/MovieMainView.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            MovieController controller = (MovieController) loader.getController();
            // Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load Movies controller");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            // Initializes the controller with the loaded view
            controller.initialize(root);

        } catch (IOException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Opens the Providers window by loading the corresponding FXML view and controller.
     *
     * @param event The action event triggered by the Providers button.
     */
    @FXML
    public void openProvidersWindow(ActionEvent event) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/MainProviders.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            ProviderController controller = (ProviderController) loader.getController();
            // Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load Movies controller");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            // Initializes the controller with the loaded view
            controller.initialize(root);

        } catch (IOException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Opens the Categories window by loading the corresponding FXML view and controller.
     *
     * @param event The action event triggered by the Categories button.
     */
    @FXML
    public void openCategoriesWindow(ActionEvent event) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/MainCategory.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            CategoryController controller = (CategoryController) loader.getController();
            // Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load Movies controller");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            // Initializes the controller with the loaded view
            controller.initialize(root);

        } catch (IOException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Handles the event when the logout button is clicked. It returns the user to
     * the SignIn view.
     *
     * @param event The action event triggered by the logout button.
     */
    @FXML
    public void backButtonAction(ActionEvent event) {
        try {
            // Load DOM form FXML view
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = (Parent) loader.load();
            // Retrieve the controller associated with the view
            SignInController controller = (SignInController) loader.getController();
            // Check if there is a RuntimeException while opening the view
            if (controller == null) {
                throw new RuntimeException("Failed to load SignInController");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }
            controller.setStage(stage);
            // Initializes the controller with the loaded view
            controller.initialize(root);

        } catch (IOException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            // Logs the error and displays an alert message
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Returns the current stage of the application.
     *
     * @return The current {@link Stage}.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the current stage of the application.
     *
     * @param stage The {@link Stage} to be set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Returns the Movies button in the menu.
     *
     * @return The {@link Button} for movies.
     */
    public Button getMoviesBtn() {
        return moviesBtn;
    }

    /**
     * Sets the Movies button in the menu.
     *
     * @param moviesBtn The {@link Button} for movies.
     */
    public void setMoviesBtn(Button moviesBtn) {
        this.moviesBtn = moviesBtn;
    }

    /**
     * Returns the Providers button in the menu.
     *
     * @return The {@link Button} for providers.
     */
    public Button getProvidersBtn() {
        return providersBtn;
    }

    /**
     * Sets the Providers button in the menu.
     *
     * @param providersBtn The {@link Button} for providers.
     */
    public void setProvidersBtn(Button providersBtn) {
        this.providersBtn = providersBtn;
    }

    /**
     * Returns the Categories button in the menu.
     *
     * @return The {@link Button} for categories.
     */
    public Button getCategoriesBtn() {
        return categoriesBtn;
    }

    /**
     * Sets the Categories button in the menu.
     *
     * @param categoriesBtn The {@link Button} for categories.
     */
    public void setCategoriesBtn(Button categoriesBtn) {
        this.categoriesBtn = categoriesBtn;
    }
}
