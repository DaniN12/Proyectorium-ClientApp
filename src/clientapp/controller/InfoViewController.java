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
import java.util.HashMap;
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
import javafx.collections.ObservableMap;
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
    private ObservableMap<Integer, String> movieMap;
    private final Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));
    private final Logger logger = Logger.getLogger(InfoViewController.class.getName());
    private Stage stage;

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
            iTicket = TicketFactory.getITicket();
            listTickets = FXCollections.observableArrayList(iTicket.findAll(new GenericType<List<TicketEntity>>() {
            }));
            setupTicketTable();
            ticketTableView.setItems(listTickets);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading tickets: {0}", e.getMessage());
        }
    }

    private void setupTicketTable() {
        movieImageColumn.setCellValueFactory(param -> {
            TicketEntity ticket = param.getValue();
            if (ticket.getMovie() != null && ticket.getMovie().getMovieImage() != null) {
                Image image = new Image(new ByteArrayInputStream(ticket.getMovie().getMovieImage()));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                return new SimpleObjectProperty<>(imageView);
            }
            return null;
        });

        movieTitleColumn.setCellValueFactory(new PropertyValueFactory<>("movie.title"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("movieDuration"));
        dateHourColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getBuyDateAsLocalDateTime()));
        peopleColumn.setCellValueFactory(new PropertyValueFactory<>("numPeople"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("calculatedPrice"));

        // Configurar ComboBoxTableCell para la columna movieTitleColumn
        listMovies = FXCollections.observableArrayList(MovieFactory.getIMovie().findAll(new GenericType<List<MovieEntity>>() {
        }));
        for (int i = 0; i < listMovies.size(); i++) {
            movieMap.put(listMovies.get(i).getId(), listMovies.get(i).getTitle());
        }
        movieTitleColumn.setCellFactory(ComboBoxTableCell.forTableColumn(/*String, listMovies*/));

        movieTitleColumn.setOnEditCommit(event -> {
            TicketEntity ticket = event.getRowValue();  // Obtener el ticket de la fila editada
            String selectedMovieTitle = event.getNewValue(); // Obtener la película seleccionada del ComboBox

            Optional<MovieEntity> selectedMovie = listMovies.stream()
                    .filter(movie -> movie.getTitle().equalsIgnoreCase(selectedMovieTitle))
                    .findFirst();

            selectedMovie.ifPresent(movie -> ticket.setMovie(movie));  // Actualizar la película en el TicketEntity

            try {
                Integer ticketId = ticket.getId();  // Obtener el ID del ticket
                iTicket.edit(ticket, String.valueOf(ticketId));  // Actualizar el ticket en la base de datos mediante el servicio REST
            } catch (WebApplicationException e) {
                logger.log(Level.SEVERE, "Error saving ticket changes via REST: {0}", e.getMessage());
            }
        });

        dateHourColumn.setCellFactory(column -> new DateTimePickerTableCell());
        peopleColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    public ObservableList<MovieEntity> getAvailableMovies() {
        List<MovieEntity> movies = MovieFactory.getIMovie().findAll(new GenericType<List<MovieEntity>>() {
        });
        return FXCollections.observableArrayList(movies);
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
