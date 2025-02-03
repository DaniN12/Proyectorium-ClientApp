/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

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
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class ProviderController {

    private Stage stage;

    private Logger logger = Logger.getLogger(SignInController.class.getName());

    @FXML
    private TableColumn<ProviderEntity, String> tbcolumnEmail;

    @FXML
    private TableColumn<ProviderEntity, String> tbcolumnName;

    @FXML
    private TableColumn<ProviderEntity, Integer> tbcolumnPhone;

    @FXML
    private TableColumn<ProviderEntity, Date> tbcolumnConInit;

    @FXML
    private TableColumn<ProviderEntity, Date> tbcolumnConEnd;

    @FXML
    private TableColumn<ProviderEntity, Float> tbcolumnPrice;

    @FXML
    private TableView tableProviders;

    @FXML
    private Button btnInterrogation;

    private ObservableList<ProviderEntity> provider = null;

    private IProvider iProvider;

    public void initialize(Parent root) {
        logger.info("Initializing Provider stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Providers");
        stage.setResizable(false);
        tableProviders.setEditable(true);

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

        }

        tbcolumnEmail.setCellFactory(TextFieldTableCell.<ProviderEntity>forTableColumn());
        tbcolumnEmail.setOnEditCommit((CellEditEvent<ProviderEntity, String> t) -> {
            ProviderEntity provider = t.getRowValue();
            provider.setEmail(t.getNewValue());
            iProvider.edit_XML(provider, String.valueOf(provider.getId()));
        });

        tbcolumnName.setCellFactory(TextFieldTableCell.<ProviderEntity>forTableColumn());
        tbcolumnName.setOnEditCommit((CellEditEvent<ProviderEntity, String> t) -> {
            ProviderEntity provider = t.getRowValue();
            provider.setName(t.getNewValue());
            iProvider.edit_XML(provider, String.valueOf(provider.getId()));
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

        tbcolumnPhone.setOnEditCommit((CellEditEvent<ProviderEntity, Integer> t) -> {
            ProviderEntity provider = t.getRowValue();
            provider.setPhone(t.getNewValue());
            iProvider.edit_XML(provider, String.valueOf(provider.getId()));
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

        stage.show();


    }

    public void handleRemoveAction(ActionEvent event) {
        ProviderEntity RmProvider = (ProviderEntity) tableProviders.getSelectionModel().getSelectedItem();
        if (RmProvider != null && RmProvider.getId() != null) {
            System.out.println("ID del proveedor a eliminar: " + RmProvider.getId());
            iProvider.remove(String.valueOf(RmProvider.getId()));
            tableProviders.getItems().remove(RmProvider);
            tableProviders.refresh();
        } else {
            System.out.println("No se puede eliminar un proveedor sin ID.");
        }
    }

    public void handleCreateAction(ActionEvent event) {
        ProviderEntity newProvider = new ProviderEntity();

        iProvider.create_XML(newProvider);
        provider = FXCollections.observableArrayList(iProvider.findAll_XML(new GenericType<List<ProviderEntity>>() {
            }));
        tableProviders.setItems(provider);
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

    public static String fechaToString(Date fecha) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(fecha);
    }

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
    
    @FXML
    public void handleFilterByContractInit (ActionEvent event){
        provider = FXCollections.observableArrayList(iProvider.listByContractInit_XML(new GenericType<List<ProviderEntity>>() {
        }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
        tableProviders.setItems(provider);
        tableProviders.refresh();
    }
    
    @FXML
    public void handleFilterByContractEnd (ActionEvent event){
        provider = FXCollections.observableArrayList(iProvider.listByContractEnd_XML(new GenericType<List<ProviderEntity>>() {
        }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
        tableProviders.setItems(provider);
        tableProviders.refresh();
    }
    
    @FXML
    public void handleFilterByPrice (ActionEvent event){
        provider = FXCollections.observableArrayList(iProvider.listByPrice_XML(new GenericType<List<ProviderEntity>>() {
        }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
        tableProviders.setItems(provider);
        tableProviders.refresh();
    }
    

//Establecer el modelo de datos de la tabla  
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
