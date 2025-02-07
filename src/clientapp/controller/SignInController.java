package clientapp.controller;

import clientapp.exceptions.EmptyFieldException;
import clientapp.exceptions.IncorrectPatternException;
import clientapp.factories.SignableFactory;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import clientapp.model.UserEntity;
import clientapp.interfaces.Signable;
import clientapp.model.UserType;
import java.util.Optional;
import javax.ws.rs.core.GenericType;

/**
 * Controlador para la vista de inicio de sesión (SignIn). Maneja las interacciones
 * del usuario con los campos de entrada, validaciones de inicio de sesión y la
 * navegación entre vistas.
 *
 * Esta clase gestiona la validación de los campos de inicio de sesión, el manejo
 * de errores, el cambio de visibilidad de la contraseña y la apertura de ventanas
 * dependiendo del tipo de usuario (administrador o cliente).
 *
 * @author Dani
 */
public class SignInController {

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private TextField txtFieldPassword;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button btnShowPasswd = new Button();

    @FXML
    private Button btnSignIn = new Button();

    @FXML
    private Hyperlink HyperLinkRegistered;

    @FXML
    private Label label;

    @FXML
    private boolean passwordVisible = false;

    @FXML
    private ImageView ImageViewEye = new ImageView();

    @FXML
    private Button btnSigIn;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    private Signable signable;

    private Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));

    private Stage stage = new Stage();

    private Logger logger = Logger.getLogger(SignInController.class.getName());

    /**
     * Inicializa la vista de inicio de sesión configurando la escena, el icono
     * de la ventana y estableciendo las propiedades de los elementos de la interfaz.
     * 
     * @param root El nodo raíz del gráfico de la escena.
     */
    public void initialize(Parent root) {
        logger.info("Initializing SignIn stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.getIcons().add(icon);
        stage.setResizable(false);

        txtFieldPassword.setVisible(false);
        txtFieldPassword.textProperty().bindBidirectional(PasswordField.textProperty());

        ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));

        HyperLinkRegistered.setOnAction(this::handleHyperLinkAction);

        signable = SignableFactory.getSignable();

        btnShowPasswd.setOnAction(this::showPassword);
        
        stage.show();
    }

    /**
     * Maneja el proceso de inicio de sesión cuando el usuario hace clic en el botón de inicio de sesión.
     * Realiza validaciones de los campos y verifica las credenciales. Dependiendo del tipo de usuario
     * (administrador o cliente), abre la ventana correspondiente.
     *
     * @param event El evento de acción que dispara el inicio de sesión.
     */
    @FXML
    protected void handleSignIn(ActionEvent event) {
        String email = txtFieldEmail.getText();
        String password = txtFieldPassword.getText();

        UserEntity credentials = new UserEntity();
        credentials.setEmail(email);
        credentials.setPassword(password);

        try {

            // Comprobación de credenciales predeterminadas
            if (txtFieldEmail.getText().equals("admin") && PasswordField.getText().equals("admin")) {
                openAdminWindow(event, new UserEntity());
            } else if (txtFieldEmail.getText().equals("customer") && PasswordField.getText().equals("customer")) {
                openMainWindow(event, new UserEntity());
            } else {
                // Validaciones de campos vacíos y formato de correo electrónico
                if (email.isEmpty() || password.isEmpty() || txtFieldPassword.getText().isEmpty()) {
                    throw new EmptyFieldException("Fields are empty, all fields need to be filled");
                }

                if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$")) {
                    throw new IncorrectPatternException("The email is not well written or is incorrect");
                }
                
                // Intento de inicio de sesión con las credenciales proporcionadas
                UserEntity signedInUser = signable.signIn(credentials, new GenericType<UserEntity>() {});

                if (signedInUser.getUserType() != UserType.ADMIN) {
                    openMainWindow(event, signedInUser);
                } else {
                    openAdminWindow(event, signedInUser);
                }
            }

        } catch (EmptyFieldException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
        } catch (IncorrectPatternException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            showAlert("Error", "The email has to have a valid format, don't forget the @.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Abre la ventana principal para el usuario cliente.
     *
     * @param event El evento que dispara la acción de abrir la ventana principal.
     * @param user El usuario que ha iniciado sesión.
     */
    @FXML
    public void openMainWindow(ActionEvent event, UserEntity user) {
        try {
            // Cargar vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/InfoView.fxml"));
            Parent root = loader.load();
            InfoViewController controller = (InfoViewController) loader.getController();

            // Inicializa el controlador y la vista
            if (controller == null) {
                throw new RuntimeException("Failed to load InfoViewController");
            }

            controller.setStage(stage);
            controller.initialize(root, user);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading InfoView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Abre la ventana principal para el usuario administrador.
     *
     * @param event El evento que dispara la acción de abrir la ventana del administrador.
     * @param user El usuario que ha iniciado sesión.
     */
    @FXML
    public void openAdminWindow(ActionEvent event, UserEntity user) {
        try {
            // Cargar vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/MainAdmin.fxml"));
            Parent root = loader.load();
            MenuAdminController controller = (MenuAdminController) loader.getController();

            // Inicializa el controlador y la vista
            if (controller == null) {
                throw new RuntimeException("Failed to load MenuAdminController");
            }

            controller.setStage(stage);
            controller.initialize(root, user);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading MainAdmin.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, "Exception occurred", ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Obtiene la etapa asociada con este controlador.
     *
     * @return La etapa.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Establece la etapa para este controlador.
     *
     * @param stage La etapa a establecer.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Muestra una alerta al usuario con el título, mensaje y tipo de alerta especificados.
     *
     * @param title El título de la alerta.
     * @param message El mensaje a mostrar en la alerta.
     * @param alertType El tipo de alerta (ERROR, INFORMATION, etc.).
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Maneja el evento de cierre de la ventana, mostrando un cuadro de confirmación.
     *
     * @param event El evento de la ventana.
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
     * Abre la vista de registro cuando el usuario hace clic en el enlace de registro.
     *
     * @param event El evento de acción disparado por el clic en el enlace.
     */
    @FXML
    private void handleHyperLinkAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignUpView.fxml"));
            Parent root = loader.load();

            SignUpViewController controller = (SignUpViewController) loader.getController();
            if (controller == null || stage == null) {
                throw new RuntimeException("Failed to load SignUpController or stage not initialized.");
            }

            controller.setStage(stage);
            controller.initialize(root);
            controller.handleWindowShowing(new WindowEvent(stage, WindowEvent.WINDOW_SHOWING));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            showAlert("Error", "Error loading SignUpView.fxml", Alert.AlertType.ERROR);
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Cambia la visibilidad de la contraseña cuando el usuario hace clic en el botón
     * de mostrar/ocultar contraseña.
     *
     * @param event El evento de acción disparado por el clic en el botón de mostrar/ocultar contraseña.
     */
    public void showPassword(ActionEvent event) {
        if (!passwordVisible) {
            ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/ViendoContraseña.png")));
            PasswordField.setVisible(false);
            txtFieldPassword.setVisible(true);
            passwordVisible = true;
        } else {
            ImageViewEye.setImage(new Image(getClass().getResourceAsStream("/resources/SinVerContraseña.png")));
            txtFieldPassword.setVisible(false);
            PasswordField.setVisible(true);
            passwordVisible = false;
        }
    }
}
