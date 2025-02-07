/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.factories.DatePickerCellEditer;
import clientapp.factories.ProviderManagerFactory;
import clientapp.interfaces.IProvider;
import clientapp.model.ProviderEntity;
import clientapp.model.UserEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dani
 * @version 1.0
 * @see ProviderEntity
 * @see IProvider
 */
public class ProviderController {

    /**
     * Escenario (ventana) principal de la interfaz gráfica.
     */
    private Stage stage;

    /**
     * Logger para registrar eventos y errores.
     */
    private Logger logger = Logger.getLogger(SignInController.class.getName());

    /**
     * Columna de la tabla para el email del proveedor.
     */
    @FXML
    private TableColumn<ProviderEntity, String> tbcolumnEmail;

    /**
     * Columna de la tabla para el nombre del proveedor.
     */
    @FXML
    private TableColumn<ProviderEntity, String> tbcolumnName;

    /**
     * Columna de la tabla para el teléfono del proveedor.
     */
    @FXML
    private TableColumn<ProviderEntity, Integer> tbcolumnPhone;

    /**
     * Columna de la tabla para la fecha de inicio del contrato del proveedor.
     */
    @FXML
    private TableColumn<ProviderEntity, Date> tbcolumnConInit;

    /**
     * Columna de la tabla para la fecha de fin del contrato del proveedor.
     */
    @FXML
    private TableColumn<ProviderEntity, Date> tbcolumnConEnd;

    /**
     * Columna de la tabla para el precio del servicio del proveedor.
     */
    @FXML
    private TableColumn<ProviderEntity, Float> tbcolumnPrice;

    /**
     * Tabla que muestra la lista de proveedores.
     */
    @FXML
    private TableView tableProviders;

    /**
     * Lista de proveedores disponibles.
     */
    @FXML
    private List<ProviderEntity> availableProviders;

    /**
     * Botón para mostrar información adicional.
     */
    @FXML
    private Button btnInterrogation;

    /**
     * Lista observable de proveedores para la tabla.
     */
    private ObservableList<ProviderEntity> provider = null;

    /**
     * Interfaz para gestionar las operaciones de proveedores.
     */
    private IProvider iProvider;
    
    private final Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));

    /**
     * Inicializa la interfaz gráfica y configura la tabla de proveedores.
     *
     * @param root Nodo raíz de la interfaz gráfica.
     */
    public void initialize(Parent root) {
        logger.info("Initializing Provider stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Providers");
        stage.setResizable(false);
        tableProviders.setEditable(true);
        stage.getIcons().add(icon);

        try {
            iProvider = ProviderManagerFactory.getIProvider();

            provider = FXCollections.observableArrayList(iProvider.findAll_XML(new GenericType<List<ProviderEntity>>() {
            }));

            tbcolumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tbcolumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcolumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            tbcolumnConInit.setCellValueFactory(new PropertyValueFactory<>("contractIni"));
            tbcolumnConEnd.setCellValueFactory(new PropertyValueFactory<>("contractEnd"));
            tbcolumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            tableProviders.setItems(provider);
        } catch (Exception ex) {
            logger.severe("Error initializing provider data: " + ex.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading providers", ButtonType.OK).showAndWait();
        }

        tbcolumnEmail.setCellFactory(TextFieldTableCell.<ProviderEntity>forTableColumn());
        tbcolumnEmail.setOnEditCommit(t -> {
            ProviderEntity provider = t.getRowValue();
            String newEmail = t.getNewValue();
            if (!newEmail.endsWith("@gmail.com")) {
                showAlert("Invalid Email", "Email must end with @gmail.com.");
                tableProviders.refresh();
            } else if (newEmail.length() > 50) {
                showAlert("Email Too Long", "Email cannot exceed 50 characters.");
                tableProviders.refresh();
            } else {
                provider.setEmail(newEmail);
                iProvider.edit_XML(provider, String.valueOf(provider.getId()));
            }
        });

        tbcolumnName.setCellFactory(TextFieldTableCell.<ProviderEntity>forTableColumn());
        tbcolumnName.setOnEditCommit(t -> {
            ProviderEntity provider = t.getRowValue();
            String newName = t.getNewValue();
            if (newName.length() > 50) {
                showAlert("Name Too Long", "Name cannot exceed 50 characters.");
                tableProviders.refresh();
            } else {
                provider.setName(newName);
                iProvider.edit_XML(provider, String.valueOf(provider.getId()));
            }
        });

        tbcolumnConInit.setCellFactory(column -> new DatePickerCellEditer());
        tbcolumnConInit.setOnEditCommit(event -> {
            ProviderEntity provider = event.getRowValue();
            provider.setContractIni(event.getNewValue());
            iProvider.edit_XML(provider, String.valueOf(provider.getId()));
        });

        tbcolumnConEnd.setCellFactory(column -> new DatePickerCellEditer());
        tbcolumnConEnd.setOnEditCommit(event -> {
            ProviderEntity provider = event.getRowValue();
            provider.setContractEnd(event.getNewValue());
            iProvider.edit_XML(provider, String.valueOf(provider.getId()));
        });

        tbcolumnPhone.setCellFactory(TextFieldTableCell.<ProviderEntity, Integer>forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    // Manejo de error para valores no válidos
                    return null;
                }
            }
        }));

        tbcolumnPhone.setOnEditCommit(t -> {
            ProviderEntity provider = t.getRowValue();
            Integer newPhone = t.getNewValue();
            if (newPhone == null || String.valueOf(newPhone).length() != 9) {
                showAlert("Invalid Phone Number", "The phone number must have exactly 9 digits.");
                tableProviders.refresh();
            } else {
                provider.setPhone(newPhone);
                iProvider.edit_XML(provider, String.valueOf(provider.getId()));
            }
        });

        tbcolumnPrice.setCellFactory(TextFieldTableCell.<ProviderEntity, Float>forTableColumn(new StringConverter<Float>() {
            @Override
            public String toString(Float object) {
                return object == null ? "" : String.format("%.2f", object); // Formatea a 2 decimales
            }

            @Override
            public Float fromString(String string) {
                try {
                    return Float.parseFloat(string);
                } catch (NumberFormatException e) {
                    // Manejo de error para valores no válidos
                    return null;
                }
            }
        }));

        tbcolumnPrice.setOnEditCommit((CellEditEvent<ProviderEntity, Float> t) -> {
            ProviderEntity provider = t.getRowValue();
            provider.setPrice(t.getNewValue());
            iProvider.edit_XML(provider, String.valueOf(provider.getId()));
        });

        setupContextMenu();
        tableProviders.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        stage.show();

    }

    /**
     * Muestra una alerta con un mensaje de advertencia.
     *
     * @param title Título de la alerta.
     * @param message Mensaje de la alerta.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Actualiza la tabla de proveedores con los datos más recientes.
     */
    private void refreshTable() {
        // Limpiar la lista actual de providers
        provider.clear();

        provider.addAll(
                iProvider.findAll_XML(new GenericType<List<ProviderEntity>>() {
                }));
    }

    /**
     * Maneja la acción de eliminar proveedores seleccionados.
     *
     * @param event Evento de acción.
     */
    public void handleRemoveAction(ActionEvent event) {
        List<ProviderEntity> removeProvider = tableProviders.getSelectionModel().getSelectedItems();
        if (removeProvider != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove confirmation");
            alert.setHeaderText("¿Are you sure you want to remove this provider?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (removeProvider.size() > 1) {
                        for (ProviderEntity categ : removeProvider) {
                            iProvider.remove(String.valueOf(categ.getId()));
                            tableProviders.getItems().remove(removeProvider);
                        }
                    } else {
                        iProvider.remove(String.valueOf(removeProvider.get(0).getId()));
                        tableProviders.getItems().remove(removeProvider);
                    }
                    refreshTable();
                }
            });
        }
    }

    /**
     * Maneja la acción de crear un nuevo proveedor.
     *
     * @param event Evento de acción.
     */
    public void handleCreateAction(ActionEvent event) {
        ProviderEntity newProvider = new ProviderEntity();

        iProvider.create_XML(newProvider);
        provider = FXCollections.observableArrayList(iProvider.findAll_XML(new GenericType<List<ProviderEntity>>() {
        }));
        tableProviders.setItems(provider);
    }

    /**
     * Maneja la acción de imprimir un informe de proveedores.
     *
     * @param event Evento de acción.
     */
    @FXML
    private void handleImprimirAction(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/clientapp/reports/ProvidersReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<ProviderEntity>) this.tableProviders.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception error) {
            //Logger.log(Level.SEVERE, "AccountController(handlePrintReport): Exception while creating the report {0}", error.getMessage());
        }

    }

    public static String introducirCadena() {
        String cadena = "";
        boolean error;
        InputStreamReader entrada = new InputStreamReader(System.in);
        BufferedReader teclado = new BufferedReader(entrada);
        do {
            error = false;
            try {
                cadena = teclado.readLine();
            } catch (IOException e) {
                System.out.println("Error en la entrada de datos");
                error = true;
            }
        } while (error);
        return cadena;
    }

    /**
     * Convierte una fecha a una cadena en formato "dd/MM/yyyy".
     *
     * @param fecha Fecha a convertir.
     * @return Cadena que representa la fecha.
     */
    public static String fechaToString(Date fecha) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(fecha);
    }

    /**
     * Convierte una cadena en formato "dd/MM/yyyy" a una fecha.
     *
     * @param fechaStr Cadena que representa la fecha.
     * @return Fecha convertida.
     */
    public static Date stringToDate(String fechaStr) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formateador.parse(fechaStr);
        } catch (ParseException e) {
            System.out.println("Error: Formato de fecha incorrecto. Usa dd/MM/yyyy.");
            return null; // Retorna null si la conversión falla
        }
    }

    public static Date leerFechaDMA() {
        boolean error;
        LocalDate localDate = null;
        String dateString;
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            error = false;
            dateString = introducirCadena();  // Asumo que este método obtiene la entrada del usuario
            try {
                localDate = LocalDate.parse(dateString, formateador);
            } catch (DateTimeParseException e) {
                System.out.println("Error, introduce una fecha en formato dd/MM/yyyy ");
                error = true;
            }
        } while (error);

        // Convertimos LocalDate a Date
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Configura el menú contextual para la tabla de proveedores.
     */
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addProvider = new MenuItem("Add provider");
        MenuItem removeProvider = new MenuItem("Removde provider");
        MenuItem print = new MenuItem("Print table");

        addProvider.setOnAction(this::handleCreateAction);
        removeProvider.setOnAction(this::handleRemoveAction);

        contextMenu.getItems().add(addProvider);
        contextMenu.getItems().add(removeProvider);
        contextMenu.getItems().add(print);

        tableProviders.setContextMenu(contextMenu);
    }

    /**
     * Filtra los proveedores por fecha de inicio de contrato.
     *
     * @param event Evento de acción.
     */
    @FXML
    public void handleFilterByContractInit(ActionEvent event) {
        provider = FXCollections.observableArrayList(iProvider.listByContractInit_XML(new GenericType<List<ProviderEntity>>() {
        }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
        tableProviders.setItems(provider);
        tableProviders.refresh();
    }

    /**
     * Filtra los proveedores por fecha de fin de contrato.
     *
     * @param event Evento de acción.
     */
    @FXML
    public void handleFilterByContractEnd(ActionEvent event) {
        provider = FXCollections.observableArrayList(iProvider.listByContractEnd_XML(new GenericType<List<ProviderEntity>>() {
        }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
        tableProviders.setItems(provider);
        tableProviders.refresh();
    }

    /**
     * Filtra los proveedores por precio.
     *
     * @param event Evento de acción.
     */
    @FXML
    public void handleFilterByPrice(ActionEvent event) {
        provider = FXCollections.observableArrayList(iProvider.listByPrice_XML(new GenericType<List<ProviderEntity>>() {
        }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
        tableProviders.setItems(provider);
        tableProviders.refresh();
    }

// Getters y Setters 
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public TableColumn gettbcolumnEmail() {
        return tbcolumnEmail;
    }

    public void settbcolumnEmail(TableColumn tbcolumnEmail) {
        this.tbcolumnEmail = tbcolumnEmail;
    }

    public TableColumn gettbcolumnName() {
        return tbcolumnName;
    }

    public void settbcolumnName(TableColumn tbcolumnName) {
        this.tbcolumnName = tbcolumnName;
    }

    public TableColumn gettbcolumnPhone() {
        return tbcolumnPhone;
    }

    public void settbcolumnPhone(TableColumn tbcolumnPhone) {
        this.tbcolumnPhone = tbcolumnPhone;
    }

    public TableColumn gettbcolumnConInit() {
        return tbcolumnConInit;
    }

    public void settbcolumnConInit(TableColumn tbcolumnConInit) {
        this.tbcolumnConInit = tbcolumnConInit;
    }

    public TableColumn gettbcolumnConEnd() {
        return tbcolumnConEnd;
    }

    public void settbcolumnConEnd(TableColumn tbcolumnConEnd) {
        this.tbcolumnConEnd = tbcolumnConEnd;
    }

    public TableColumn getTbcolPrice() {
        return tbcolumnPrice;
    }

    public void settbcolumnPrice(TableColumn tbcolumnPrice) {
        this.tbcolumnPrice = tbcolumnPrice;
    }

    public IProvider getiProvider() {
        return iProvider;
    }

    public void setiProvider(IProvider iProvider) {
        this.iProvider = iProvider;
    }

}