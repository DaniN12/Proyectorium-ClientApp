/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

//import clientapp.model.SignableFactory;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import clientapp.exceptions.EmptyFieldException;
import clientapp.exceptions.IncorrectPasswordException;
import clientapp.exceptions.IncorrectPatternException;
import clientapp.factories.SignableFactory;
import clientapp.interfaces.Signable;
import clientapp.model.Customer;
//import exceptions.ConnectionErrorException;
//import exceptions.UserAlreadyExistException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ContextMenuEvent;
//import clientapp.model.Signable;
import clientapp.model.UserEntity;
import clientapp.model.UserType;

/**
 * FXML Controller class of the signUp window
 *
 * @author Kelian and Enzo
 */
public class SignUpViewController {

    @FXML
    private SplitPane splitPane;

    /**
     * TextField for email
     */
    @FXML
    private TextField emailTxf;

    /**
     * TextField for full name
     */
    @FXML
    private TextField fullNameTxf;

    /**
     * TextField for password (visible)
     */
    @FXML
    private TextField passwordTxf;

    /**
     * PasswordField for password (hidden)
     */
    @FXML
    private PasswordField passwordPwdf;

    /**
     * TextField for retry password (visible)
     */
    @FXML
    private TextField retryPasswordTxf;

    /**
     * PasswordField for retry password (hidden)
     */
    @FXML
    private PasswordField repeatPasswordPwdf;

    /**
     * TextField for street
     */
    @FXML
    private TextField streetTxf;

    /**
     * TextField for city
     */
    @FXML
    private TextField cityTxf;

    /**
     * TextField for zip
     */
    @FXML
    private TextField zipTxf;

    /**
     * Button to show password
     */
    @FXML
    private Button btnShowPasswd = new Button();

    /**
     * Button to show password in retry password
     */
    @FXML
    private Button retryButtonEye = new Button();

    /**
     * CheckBox to know if the user is active or not
     */
    @FXML
    private CheckBox checkActive;

    /**
     * Button to sign up
     */
    @FXML
    private Button signUpButton;

    /**
     * Button to return to the main window
     */
    @FXML
    private Button returnButton;

    /**
     * Image view to set the button eye
     */
    @FXML
    private ImageView buttonImgView;

    /**
     * Image view to set the second button eye
     */
    @FXML
    private ImageView repeatbuttonImgView;

    /**
     * Logger to show the steps of the application in the console
     */
    private Logger logger = Logger.getLogger(SignUpViewController.class.getName());

    /**
     * Stage for the view
     */
    private Stage stage;

    /**
     * Boolean for the password change
     */
    private boolean passwordVisible = false;

    /**
     * Boolean for the password change
     */
    private boolean repeatpasswordVisible = false;
    /**
     * Interface of the application
     */
    private Signable signable;

    /**
     * Context menu of the window
     */
    private ContextMenu contextMenu = new ContextMenu();

    /**
     * First menu Item of the menu
     */
    private MenuItem itemResetFields = new MenuItem("Reset fields");

    /**
     * Second menu Item of the menu
     */
    private MenuItem itemBack = new MenuItem("Go back");

    /**
     * Method that initializes the sign up view and sets the event handlers
     *
     * @param root the parent gotten from the previous window
     */
    public void initialize(Parent root) {
        splitPane = (SplitPane) root;
        splitPane.getDividers().forEach(divider -> divider.positionProperty().addListener((obs, oldPos, newPos) -> divider.setPosition(0.25))); // Fix divider position

        logger.info("Initializing SignUp stage.");

        // Setting up scene and stage properties
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.setResizable(false);

        // Register event handlers
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::onCloseRequest);
        btnShowPasswd.setOnAction(this::showPassword);
        itemResetFields.setOnAction(this::resetFields);
        itemBack.setOnAction(this::backButtonAction);
        root.setOnContextMenuRequested(this::manejarContextMenu);

        // Show the stage
        stage.show();
    }

    /**
     * Handles the context menu action triggered by a click on the window.
     * Displays the context menu at the click position.
     *
     * @param event The event triggered by the right-click
     */
    private void manejarContextMenu(ContextMenuEvent event) {
        contextMenu.show(splitPane, event.getScreenX(), event.getScreenY());
    }

    /**
     * Initializes the visibility and behavior of password fields when the
     * window is shown. Also sets up the password text field bindings and
     * context menu items.
     *
     * @param event The event triggered when the window is shown
     */
    public void handleWindowShowing(WindowEvent event) {
        logger.info("Beginning event handler::handleWindowShowing");

        // Hide the visible password fields and bind them to the corresponding password fields
        passwordTxf.setVisible(false);
        passwordTxf.setManaged(false);
        retryPasswordTxf.setVisible(false);
        retryPasswordTxf.setManaged(false);
        passwordTxf.textProperty().bindBidirectional(passwordPwdf.textProperty());
        retryPasswordTxf.textProperty().bindBidirectional(repeatPasswordPwdf.textProperty());

        // Set the initial images for password visibility
        buttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
        btnShowPasswd.setOnAction(this::showPassword);

        returnButton.setOnAction(this::backButtonAction);

        // Context menu setup
        contextMenu.getItems().addAll(itemResetFields, itemBack);

        // Initialize the signable factory
        signable = SignableFactory.getSignable();
    }

    /**
     * Handles the sign-up action when the user clicks the sign-up button. It
     * validates the input fields, checks for errors, and creates a new user.
     *
     * @param event The event triggered by the sign-up button click
     */
    @FXML
    public void handleButtonAction(ActionEvent event) {
        try {
            // Check if the fields are empty
            if (emailTxf.getText().isEmpty() || fullNameTxf.getText().isEmpty() || passwordTxf.getText().isEmpty()
                    || passwordPwdf.getText().isEmpty() || retryPasswordTxf.getText().isEmpty()
                    || repeatPasswordPwdf.getText().isEmpty() || streetTxf.getText().isEmpty() || cityTxf.getText().isEmpty()) {

                throw new EmptyFieldException("Fields are empty, all fields need to be filled");
            } // Check if passwords match
            else if (!passwordTxf.getText().equalsIgnoreCase(retryPasswordTxf.getText()) && !passwordPwdf.getText().equalsIgnoreCase(repeatPasswordPwdf.getText())) {

                throw new IncorrectPasswordException("The password fields do not match");
            } // Check if email matches the correct pattern
            else if (!emailTxf.getText().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {

                throw new IncorrectPatternException("The email has to have a valid email format, e.g., @gmail.com");
            } // Check if zip code is numeric
            else if (!zipTxf.getText().matches("\\d+")) {

                throw new IncorrectPatternException("The zip has to be an Integer");
            } // Create user if all validations are passed
            else {
                UserEntity user = new UserEntity();
                user.setEmail(emailTxf.getText());
                user.setFullName(fullNameTxf.getText());
                user.setPassword(passwordTxf.getText());
                user.setStreet(streetTxf.getText());
                user.setCity(cityTxf.getText());
                user.setZip(Integer.parseInt(zipTxf.getText()));
                user.setUserType(UserType.CUSTOMER);

                signable.create(user);

                if (user != null) {
                    // Return to the sign-in window if registration is successful
                    backButtonAction(event);
                }
            }
        } catch (IncorrectPasswordException | IncorrectPatternException | EmptyFieldException ex) {
            // Log and display the error message
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.WARNING, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Navigates back to the sign-in window when the back button is clicked.
     *
     * @param event The event triggered by the back button click
     */
    @FXML
    public void backButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = loader.load();
            SignInController controller = loader.getController();
            controller.setStage(stage);
            controller.initialize(root);
        } catch (IOException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Handles the close request for the application. Confirms with the user
     * before exiting.
     *
     * @param event The event triggered by the window close action
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        alert.setContentText("Are you sure you want to close the application?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * Toggles the visibility of the password fields when the show password
     * button is clicked.
     *
     * @param event The event triggered by the password visibility toggle button
     * click
     */
    public void showPassword(ActionEvent event) {
        if (!passwordVisible) {
            buttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png")));
            passwordPwdf.setVisible(false);
            passwordPwdf.setManaged(false);
            passwordTxf.setVisible(true);
            passwordTxf.setManaged(true);
            repeatPasswordPwdf.setVisible(false);
            repeatPasswordPwdf.setManaged(false);
            retryPasswordTxf.setVisible(true);
            retryPasswordTxf.setManaged(true);
            passwordVisible = true;
        } else {
            buttonImgView.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
            passwordTxf.setVisible(false);
            passwordTxf.setManaged(false);
            passwordPwdf.setVisible(true);
            passwordPwdf.setManaged(true);
            retryPasswordTxf.setVisible(false);
            retryPasswordTxf.setManaged(false);
            repeatPasswordPwdf.setVisible(true);
            repeatPasswordPwdf.setManaged(true);
            passwordVisible = false;
        }
    }

    /**
     * Resets all the fields in the registration form to their default state.
     *
     * @param event The event triggered by the reset fields context menu item
     * click
     */
    public void resetFields(ActionEvent event) {
        emailTxf.clear();
        fullNameTxf.clear();
        passwordTxf.clear();
        passwordPwdf.clear();
        retryPasswordTxf.clear();
        repeatPasswordPwdf.clear();
        streetTxf.clear();
        cityTxf.clear();
        zipTxf.clear();
    }

    /**
     * Gets the stage for the current window.
     *
     * @return the stage associated with the current window
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage for the current window.
     *
     * @param stage the stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
