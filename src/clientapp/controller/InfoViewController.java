package clientapp.controller;

import clientapp.factories.TicketFactory;
import clientapp.interfaces.ITicket;
import clientapp.model.TicketEntity;
import clientapp.model.UserEntity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author ruth
 */
public class InfoViewController {

    /**
     * TextField to show user email
     */
    @FXML
    private TextField emailTextF;

    /**
     * TextField to show user name
     */
    @FXML
    private TextField userNameTextF;

    /**
     * TextField to show user city
     */
    @FXML
    private TextField cityTextF;

    /**
     * ImageView for the profile photo
     */
    @FXML
    private ImageView profileImageView = new ImageView();

    /**
     * ContextMenu for the menu
     */
    @FXML
    private ContextMenu contextMenu;

    /**
     * ContextMenu for the menu
     */
    @FXML
    private ContextMenu tableContextMenu;

    /**
     * MenuItm for Mordecay option
     */
    @FXML
    private MenuItem optionMordecay;

    /**
     * MenuItm for Cj option
     */
    @FXML
    private MenuItem optionCj;

    /**
     * MenuItm for Rigby option
     */
    @FXML
    private MenuItem optionRigby;

    /**
     * ImageView for Rigby image
     */
    @FXML
    private ImageView profileImageRigby;

    @FXML
    private TableView ticketTableView;

    @FXML
    private TableColumn movieImageColumn;

    @FXML
    private TableColumn movieTitleColumn;

    @FXML
    private TableColumn dateHourColumn;

    @FXML
    private TableColumn durationColumn;

    @FXML
    private TableColumn priceColumn;

    @FXML
    private TableColumn peopleColumn;

    ObservableList<TicketEntity> listTickets;

    private Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));

    private Logger logger = Logger.getLogger(InfoViewController.class.getName());

    private Stage stage;

    /**
     * Method that initializes the controller class.
     */
    public void initialize(Parent root, UserEntity user) {
        try {
            logger.info("Initializing InfoView stage.");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("User info");
            stage.getIcons().add(icon);
            stage.setResizable(false);

            profileImageView.setOnMouseClicked(this::showContextMenu);
            stage.addEventHandler(WindowEvent.WINDOW_SHOWN, this::handleWindowShowing);
            stage.setOnCloseRequest(this::onCloseRequest);

            //movieImageColumn.setCellValueFactory(new PropertyValueFactory<>("movie.image"));
            //movieTitleColumn.setCellValueFactory(new PropertyValueFactory<>("movie.title"));
            dateHourColumn.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
            //durationColumn.setCellValueFactory(new PropertyValueFactory<>("movie.duration"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            peopleColumn.setCellValueFactory(new PropertyValueFactory<>("people"));

            ticketTableView.setItems(FXCollections.observableArrayList(TicketFactory.getITicket().findAll(new GenericType<List<TicketEntity>>() {
            })));

            stage.show();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /**
     * Method that handles the events that occur before the window opens
     *
     * @param event triggers an action, in this case a window opening
     */
    public void handleWindowShowing(WindowEvent event) {
        UserEntity user = fetchUserData();
        emailTextF.setText(user.getEmail());
        userNameTextF.setText(user.getFullName());
        cityTextF.setText(user.getCity());
        optionMordecay.setOnAction(this::onOptionMordecay);
        optionCj.setOnAction(this::onOptionCj);
        optionRigby.setOnAction(this::onOptionRigby);

    }

    /**
     * Handles the action event for the "Option Mordecay" option in the UI.
     * Displays the image associated with the Mordecay profile
     *
     * @param event action event triggered by the UI component associated with
     * this method.
     */
    @FXML
    private void onOptionMordecay(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/mordecay.png")));
    }

    /**
     * Handles the action event for the "Option Cj" option in the UI. Displays
     * the image associated with the Cj profile
     *
     * @param event action event triggered by the UI component associated with
     * this method.
     */
    @FXML
    private void onOptionCj(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/cj.png")));
    }

    /**
     * Handles the action event for the "Option Rigby" option in the UI.
     * Displays the image associated with the Rigby profile
     *
     * @param event action event triggered by the UI component associated with
     * this method.
     */
    @FXML
    private void onOptionRigby(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/rigby.png")));
    }

    /**
     * Displays the context menu when a right-click is detected on the
     * component.
     *
     * @param event the mouse event that triggers the method
     */
    @FXML
    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            if (!ticketTableView.contains(event.getScreenX(), event.getScreenY())) {
                contextMenu.show(profileImageView, event.getScreenX(), event.getScreenY());
            } else {
                tableContextMenu.show(ticketTableView, event.getScreenX(), event.getScreenY());
            }
        }
    }

    /**
     * Handles the action event for logging out
     *
     * @param event the action event triggered by pressing the logout button
     * @throws RuntimeException if the SignInController or the stage is not
     * properly initialized
     * @throws IOException if an error occurs while loading the
     * "SignInView.fxml" file
     */
    @FXML
    public void logOutButtonActtion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = loader.load();

            SignInController controller = loader.getController();
            if (controller == null) {
                throw new RuntimeException("Failed to load SignInController");
            }

            if (stage == null) {
                throw new RuntimeException("Stage is not initialized");
            }

            controller.setStage(stage);
            controller.initialize(root);

        } catch (IOException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading SignInView.fxml", ButtonType.OK).showAndWait();
        } catch (RuntimeException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * This method handles the close request for the application
     *
     * @param event triggers an action, in this case a close request when the
     * user attemps to close the window
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        alert.setContentText("Are you sure you want to close the application?");

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * Sets the stage for this instance.
     *
     * @param stage the stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Fetches the user data.
     *
     * @return A User object containing the user data. This will be an empty
     */
    private UserEntity fetchUserData() {
        return new UserEntity();
    }
}
