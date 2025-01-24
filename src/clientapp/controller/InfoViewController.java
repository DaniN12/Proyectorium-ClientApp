package clientapp.controller;

import clientapp.factories.DateTimePickerTableCell;
import clientapp.factories.MovieFactory;
import clientapp.factories.TicketFactory;
import clientapp.interfaces.ITicket;
import clientapp.model.MovieEntity;
import clientapp.model.TicketEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 */
public class InfoViewController {

    @FXML
    private TextField emailTextF;
    @FXML
    private TextField userNameTextF;
    @FXML
    private TextField cityTextF;
    @FXML
    private ImageView profileImageView;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private ContextMenu tableContextMenu;
    @FXML
    private MenuButton filterMenuButton;
    @FXML
    private MenuItem optionMordecay;
    @FXML
    private MenuItem optionCj;
    @FXML
    private MenuItem optionRigby;
    @FXML
    private TableView<TicketEntity> ticketTableView;
    @FXML
    private TableColumn<TicketEntity, ImageView> movieImageColumn;
    @FXML
    private TableColumn<TicketEntity, String> movieTitleColumn;
    @FXML
    private TableColumn<TicketEntity, LocalDateTime> dateHourColumn;
    @FXML
    private TableColumn<TicketEntity, String> durationColumn;
    @FXML
    private TableColumn<TicketEntity, String> priceColumn;
    @FXML
    private TableColumn<TicketEntity, Integer> peopleColumn;
    @FXML
    private Button addTicketButton;

    private ITicket iTicket;
    private ObservableList<TicketEntity> listTickets;
    private ObservableList<MovieEntity> listMovies;
    private final Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));
    private final Logger logger = Logger.getLogger(InfoViewController.class.getName());
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    public void initialize(Parent root) {
        try {
            logger.info("Initializing InfoView stage.");

            if (stage == null) {
                stage = new Stage();
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("User info");
            stage.getIcons().add(icon);
            stage.setResizable(false);

            stage.setOnCloseRequest(this::onCloseRequest);

            optionMordecay.setOnAction(this::onOptionMordecay);
            optionCj.setOnAction(this::onOptionCj);
            optionRigby.setOnAction(this::onOptionRigby);

            loadTickets();

            stage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initializing InfoView: {0}", e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Initialization Error", "Error initializing the view.");
        }
    }

    private void loadTickets() {
        try {
            iTicket = TicketFactory.getITicket(); // Obtener la implementación de ITicket
            listMovies = FXCollections.observableArrayList(MovieFactory.getIMovie().findAll(new GenericType<List<MovieEntity>>() {}));
            listTickets = FXCollections.observableArrayList(iTicket.findAll(new GenericType<List<TicketEntity>>() {}));
            setupTicketTable();
            ticketTableView.setItems(listTickets);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading tickets: {0}", e.getMessage());
        }
    }

    private void setupTicketTable() {
        // Configurar columna de imagen de la película
        movieImageColumn.setCellValueFactory(param -> {
            TicketEntity ticket = param.getValue();
            if (ticket.getMovie().getId() != null) {
                MovieEntity movie = listMovies.stream()
                        .filter(m -> m.getId().equals(ticket.getMovie().getId()))
                        .findFirst()
                        .orElse(null);

                if (movie != null && movie.getMovieImage() != null) {
                    Image image = new Image(new ByteArrayInputStream(movie.getMovieImage()));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    return new SimpleObjectProperty<>(imageView);
                }
            }
            return null;
        });

        movieTitleColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMovie().getTitle()));

        movieTitleColumn.setCellFactory(ComboBoxTableCell.forTableColumn(/*
                new StringConverter<String>() {
                    @Override
                    public String toString(String movieId) {
                        MovieEntity movie = listMovies.stream()
                                .filter(m -> m.getId().equals(movieId))
                                .findFirst()
                                .orElse(null);
                        return movie != null ? movie.getTitle() : "";
                    }

                    @Override
                    public String fromString(String title) {
                        MovieEntity movie = listMovies.stream()
                                .filter(m -> m.getTitle().equals(title))
                                .findFirst()
                                .orElse(null);
                        return movie != null ? String.valueOf(movie.getId()) : null;
                    }
                },
                FXCollections.observableArrayList(listMovies.stream()
                        .map(MovieEntity::getId)
                        .collect(Collectors.toList()))
      */  ));

        movieTitleColumn.setOnEditCommit(event -> {
            TicketEntity ticket = event.getRowValue();
            String newMovieId = event.getNewValue();

            if (newMovieId != null) {
                ticket.getMovie().setId(Integer.parseInt(newMovieId));

                try {
                    iTicket.edit(ticket, String.valueOf(ticket.getMovie().getId()));
                    refreshTickets();
                } catch (WebApplicationException e) {
                    logger.log(Level.SEVERE, "Error updating ticket via REST: {0}", e.getMessage());
                }
            }
        });

        durationColumn.setCellValueFactory(new PropertyValueFactory<>("movieDuration"));
        dateHourColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getBuyDateAsLocalDateTime()));
        peopleColumn.setCellValueFactory(new PropertyValueFactory<>("numPeople"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("calculatedPrice"));
        dateHourColumn.setCellFactory(column -> new DateTimePickerTableCell());
        peopleColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    private void refreshTickets() {
        listTickets.clear();
        listTickets.addAll(iTicket.findAll(new GenericType<List<TicketEntity>>() {}));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onOptionMordecay(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/mordecay.png")));
    }

    @FXML
    private void onOptionCj(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/cj.png")));
    }

    @FXML
    private void onOptionRigby(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/rigby.png")));
    }

    @FXML
    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            if (ticketTableView.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                tableContextMenu.show(ticketTableView, event.getSceneX(), event.getSceneY());
            } else {
                contextMenu.show(profileImageView, event.getSceneX(), event.getSceneY());
            }
        }
    }

    @FXML
    public void logOutButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientapp/view/SignInView.fxml"));
            Parent root = loader.load();

            SignInController controller = loader.getController();
            controller.setStage(stage);
            controller.initialize(root);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading SignInView.fxml: {0}", ex.getMessage());
            showAlert(Alert.AlertType.ERROR, "Loading Error", "Error loading SignInView.fxml.");
        }
    }

    @FXML
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to close the application?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.isPresent() && answer.get() != ButtonType.OK) {
            event.consume();
        } else {
            Platform.exit();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
