package clientapp.controller;

import clientapp.factories.MovieFactory;
import clientapp.factories.TicketDatePickerTableCell;
import clientapp.factories.TicketFactory;
import clientapp.interfaces.ITicket;
import clientapp.model.MovieEntity;
import clientapp.model.TicketEntity;
import clientapp.model.UserEntity;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controller class for the InfoView. Manages the user information display,
 * ticket listing, and filtering options.
 */
public class InfoViewController {

    @FXML
    private TextField emailTextF;
    @FXML
    private TextField userNameTextF;
    @FXML
    private TextField cityTextF;
    @FXML
    private ImageView profileImageView = new ImageView();
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
    private MenuItem addMenuItem;
    @FXML
    private MenuItem removeMenuItem;
    @FXML
    private MenuItem movieFilter;
    @FXML
    private MenuItem priceFilter;
    @FXML
    private MenuItem buyDateFilter;
    @FXML
    private MenuItem printMenuItem;
    @FXML
    private MenuItem logOutMenuItem;
    @FXML
    private TableView<TicketEntity> ticketTableView;
    @FXML
    private TableColumn<TicketEntity, ImageView> movieImageColumn;
    @FXML
    private TableColumn<TicketEntity, String> movieTitleColumn;
    @FXML
    private TableColumn<TicketEntity, Date> dateColumn;
    @FXML
    private TableColumn<TicketEntity, String> hourColumn;
    @FXML
    private TableColumn<TicketEntity, String> durationColumn;
    @FXML
    private TableColumn<TicketEntity, String> priceColumn;
    @FXML
    private TableColumn<TicketEntity, Integer> peopleColumn;
    @FXML
    private Button addTicketButton;

    private UserEntity user;
    private ITicket iTicket = TicketFactory.getITicket();
    private ObservableList<TicketEntity> listTickets;
    private ObservableList<MovieEntity> listMovies;
    private final Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));
    private final Logger logger = Logger.getLogger(InfoViewController.class.getName());
    private Stage stage;

    /**
     * Initializes the controller with the given root node and user entity.
     *
     * @param root the root node of the view
     * @param user the user entity containing user information
     */
    public void initialize(Parent root, UserEntity user) {
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
            addMenuItem.setOnAction(this::handleCreateAction);
            removeMenuItem.setOnAction(this::handleRemoveAction);
            printMenuItem.setOnAction(this::handlePrintAction);
            logOutMenuItem.setOnAction(this::logOutButtonAction);

            addTicketButton.setOnAction(this::handleCreateAction);

            priceFilter.setOnAction(this::handleFilterByPriceAction);
            buyDateFilter.setOnAction(this::handleFilterByBuyDateAction);
            movieFilter.setOnAction(this::handleFilterByMovieAction);

            emailTextF.setText(user.getEmail());
            cityTextF.setText(user.getCity());
            userNameTextF.setText(user.getFullName());

            loadTickets();

            stage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initializing InfoView: {0}", e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Initialization Error", "Error initializing the view.", "/resources/WarningAlert.png");
        }
    }

    /**
     * Loads the user's tickets from the database and populates the table view.
     */
    private void loadTickets() {
        try {
            iTicket = TicketFactory.getITicket();
            listTickets = FXCollections.observableArrayList(
                    iTicket.findAll_XML(new GenericType<List<TicketEntity>>() {
                    }));/*
                            .stream()
                            .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                            .collect(Collectors.toList()) // Recoger en una lista estándar
            );*/
            listMovies = FXCollections.observableArrayList(MovieFactory.getIMovie().findAll_XML(new GenericType<List<MovieEntity>>() {
            }));
            setupTicketTable();
            ticketTableView.setItems(listTickets);
        } catch (WebApplicationException e) {
            logger.log(Level.SEVERE, "Error loading tickets: {0}", e.getMessage());
        }
    }

    /**
     * Configures the ticket table with appropriate cell factories and event
     * handlers.
     */
    private void setupTicketTable() {
        movieImageColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TicketEntity, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(CellDataFeatures<TicketEntity, ImageView> param) {
                TicketEntity ticket = param.getValue();
                byte[] movieImageBytes = ticket.getMovie().getMovieImage();

                // Si la imagen es nula o vacía, usar una imagen predeterminada
                // Cargar la imagen predeterminada o la imagen desde los bytes del BLOB
                Image image = (movieImageBytes == null || movieImageBytes.length == 0)
                        ? new Image(getClass().getResource("/resources/icon.png").toExternalForm()) // Predeterminada
                        : new Image(new ByteArrayInputStream(movieImageBytes)); // Desde los bytes

                // Crear un ImageView y establecer la imagen
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);  // Ajustar el tamaño de la imagen
                imageView.setFitHeight(100); // Ajustar el tamaño de la imagen
                return new SimpleObjectProperty<>(imageView);
            }
        });
        movieTitleColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMovie().getTitle()));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("movieDuration"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
        hourColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMovie().getMovieHour().getHour()));
        peopleColumn.setCellValueFactory(new PropertyValueFactory<>("numPeople"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("calculatedPrice"));

        movieTitleColumn.setCellFactory(column -> {
            // Crear una instancia de ComboBoxTableCell con los títulos de las películas
            ComboBoxTableCell<TicketEntity, String> cell = new ComboBoxTableCell<>(
                    FXCollections.observableArrayList(
                            listMovies.stream()
                                    .map(MovieEntity::getTitle) // Obtener la lista de títulos
                                    .collect(Collectors.toList())
                    )
            );

            // Configurar el convertidor manualmente para que funcione con Java 8
            cell.setConverter(new StringConverter<String>() {
                @Override
                public String toString(String movieTitle) {
                    return movieTitle != null ? movieTitle : ""; // Devuelve el título directamente
                }

                @Override
                public String fromString(String title) {
                    return title; // Devuelve el título directamente (ya que trabajamos con títulos)
                }
            });

            return cell;
        });
        dateColumn.setCellFactory(column -> new TicketDatePickerTableCell());
        peopleColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        movieTitleColumn.setOnEditCommit(event -> {
            TicketEntity ticket = event.getRowValue();
            String selectedTitle = event.getNewValue();

            // Buscar la película seleccionada basada en el título
            MovieEntity selectedMovie = listMovies.stream()
                    .filter(movie -> movie.getTitle().equals(selectedTitle))
                    .findFirst()
                    .orElse(null);

            if (selectedMovie != null) {
                ticket.setMovie(selectedMovie); // Asignar la película al ticket
                editDatabase(ticket);
            }
        });

        dateColumn.setOnEditCommit(event -> {
            TicketEntity ticket = event.getRowValue();
            Date newDate = event.getNewValue();

            // Definir el formato esperado
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);  // No permite fechas ambiguas

            // Convertir la nueva fecha a un formato String y validarla
            String formattedDate = formatter.format(newDate);

            try {
                // Intentar parsear la fecha para validar el formato
                Date parsedDate = formatter.parse(formattedDate);

                // Si la fecha es válida, la guardamos en el ticket
                ticket.setBuyDate(new Date(parsedDate.getTime()));  // Asignamos el valor
                editDatabase(ticket);
            } catch (ParseException e) {
                // Si el formato es incorrecto, mostrar un mensaje de error
                showAlert(Alert.AlertType.ERROR, "Input Error", "Date has to be dd/MM/yyyy.", "/resources/WarningAlert.png");
            }
        });

        peopleColumn.setOnEditCommit(event -> {
            Integer newValue = event.getNewValue(); // Obtener el nuevo valor de la celda
            TicketEntity ticket = event.getRowValue();  // Obtener el ticket editado

            try {
                // Validar que el nuevo valor sea mayor o igual a 0
                if (newValue >= 0) {
                    // Actualizar la propiedad correspondiente del ticket
                    ticket.setNumPeople(newValue);

                    // Llamar al método para actualizar la base de datos
                    editDatabase(ticket);
                } else {
                    // Mostrar un mensaje o tomar otra acción si el valor no es válido
                    showAlert(Alert.AlertType.ERROR, "Input Error", "It´s not a number avobe 0.", "/resources/WarningAlert.png");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "It´s not a number.", "/resources/WarningAlert.png");
            }
        });

        ticketTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeMenuItem.visibleProperty().bind(ticketTableView.getSelectionModel().selectedItemProperty().isNotNull());
    }

    /**
     * Updates a ticket in the database.
     *
     * @param ticket the ticket entity to update
     */
    private void editDatabase(TicketEntity ticket) {
        try {
            // Llamar al servicio REST para actualizar el ticket
            iTicket.edit_XML(ticket, String.valueOf(ticket.getId()));
            refreshTickets(); // Actualizar la tabla después de realizar cambios
        } catch (WebApplicationException e) {
            logger.log(Level.SEVERE, "Error al actualizar el ticket mediante REST: {0}", e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Updating Error", "Error updating ticket.", "/resources/WarningAlert.png");
        }
    }

    /**
     * Refreshes the ticket list from the database.
     */
    private void refreshTickets() {
        // Limpiar la lista actual de tickets
        listTickets.clear();

        // Obtener todos los tickets y filtrar solo los que pertenecen al usuario logueado
        listTickets.addAll(
                iTicket.findAll_XML(new GenericType<List<TicketEntity>>() {

                }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
    }

    /**
     * Displays an alert dialog with a specified message and icon.
     *
     * @param type the type of alert
     * @param title the title of the alert
     * @param message the message to display
     * @param imagePath the path to the icon image
     * @return true if confirmed, false otherwise
     */
    private boolean showAlert(Alert.AlertType type, String title, String message, String imagePath) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (imagePath != null && !imagePath.isEmpty()) {
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            alert.setGraphic(imageView);
        }

        if (type == Alert.AlertType.CONFIRMATION) {
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }

        alert.showAndWait();
        return true;
    }

    /**
     * Sets the profile image to Mordecay.
     *
     * @param event the action event triggering the change
     */
    private void onOptionMordecay(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/mordecay.png")));
    }

    /**
     * Sets the profile image to CJ.
     *
     * @param event the action event triggering the change
     */
    private void onOptionCj(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/cj.png")));
    }

    /**
     * Sets the profile image to Rigby.
     *
     * @param event the action event triggering the change
     */
    private void onOptionRigby(ActionEvent event) {
        profileImageView.setImage(new Image(getClass().getResourceAsStream("/resources/rigby.png")));
    }

    /**
     * Handles the removal of selected tickets.
     *
     * @param event the action event triggering the removal
     */
    public void handleRemoveAction(ActionEvent event) {
        List<TicketEntity> removeTicket = ticketTableView.getSelectionModel().getSelectedItems();
        if (showAlert(Alert.AlertType.CONFIRMATION, "Confirm", "Are you sure you want to delete?", "/resources/DeleteAlert.png")) {
            for (TicketEntity ticket : removeTicket) {
                iTicket.remove(String.valueOf(ticket.getId()));
                ticketTableView.getItems().remove(removeTicket);
            }
        }

        refreshTickets();
    }

    /**
     * Handles the creation of a new ticket.
     *
     * @param event the action event triggering the creation
     */
    public void handleCreateAction(ActionEvent event) {
        TicketEntity newTicket = new TicketEntity(listMovies);
        iTicket.create_XML(newTicket);
        listTickets.add(newTicket);
        ticketTableView.setItems(listTickets);
        refreshTickets();
    }

    /**
     * Filters the ticket list by movie title in ascending order.
     *
     * @param event the action event triggering the filter
     */
    public void handleFilterByMovieAction(ActionEvent event) {
        listTickets = FXCollections.observableArrayList(iTicket.listByMovieASC_XML(new GenericType<List<TicketEntity>>() {
        }));/*
                .stream()
                .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
        ticketTableView.setItems(listTickets);
        ticketTableView.refresh();
    }

    /**
     * Handles the action to filter tickets by price in ascending order.
     * Retrieves the list of tickets, filters them by the logged-in user's ID,
     * and updates the TableView.
     *
     * @param event The event triggered by the user.
     */
    @FXML
    public void handleFilterByPriceAction(ActionEvent event) {
        listTickets = FXCollections.observableArrayList(iTicket.listByPriceASC_XML(new GenericType<List<TicketEntity>>() {
        }));/*
                .stream()
                .filter(ticket -> ticket.getUser().getId() == user.getId())
                .collect(Collectors.toList())
        );*/
        ticketTableView.setItems(listTickets);
        ticketTableView.refresh();
    }

    /**
     * Handles the action to filter tickets by purchase date in ascending order.
     * Retrieves the list of tickets, filters them by the logged-in user's ID,
     * and updates the TableView.
     *
     * @param event The event triggered by the user.
     */
    @FXML
    public void handleFilterByBuyDateAction(ActionEvent event) {
        listTickets = FXCollections.observableArrayList(iTicket.listByBuyDateASC_XML(new GenericType<List<TicketEntity>>() {
        }));/*
                .stream()
                .filter(ticket -> ticket.getUser().getId() == user.getId())
                .collect(Collectors.toList())
        );*/
        ticketTableView.setItems(listTickets);
        ticketTableView.refresh();
    }

    /**
     * Handles the action to generate and display a report of the tickets. Uses
     * JasperReports to compile, fill, and visualize the report.
     *
     * @param event The event triggered by the user.
     */
    public void handlePrintAction(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/clientapp/reports/TicketReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<TicketEntity>) ticketTableView.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading report: {0}", e.getMessage());
            showAlert(AlertType.ERROR, "Error de Reporte", "Ocurrió un error al generar el reporte.", "/resources/WarningAlert.png");
        }
    }

    /**
     * Handles the logout action by loading the SignInView.fxml and setting up
     * the stage for the new scene.
     *
     * @param event The event triggered by the user.
     */
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
            showAlert(Alert.AlertType.ERROR, "Loading Error", "Error loading SignInView.fxml.", "/resources/Flecha.png");
        }
    }

    /**
     * Handles the window close request event. Shows a confirmation dialog and
     * prevents closing if the user cancels.
     *
     * @param event The window close request event.
     */
    @FXML
    public void onCloseRequest(WindowEvent event) {
        if (!showAlert(Alert.AlertType.CONFIRMATION, "Exit", "Are you sure you want to close the application?", "/resources/CloseAlert.png")) {
            event.consume();
        } else {
            Platform.exit();
        }
    }

    /**
     * Sets the stage for this controller.
     *
     * @param stage The primary stage of the application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
