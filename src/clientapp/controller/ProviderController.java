/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import static clientapp.controller.MovieController.introducirCadena;
import clientapp.factories.ProviderManagerFactory;
import clientapp.interfaces.IProvider;
import clientapp.model.ProviderEntity;
import clientapp.model.UserEntity;
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

            provider = FXCollections.observableArrayList(iProvider.findAll(new GenericType<List<ProviderEntity>>() {
            }));

            tbcolumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tbcolumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcolumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            tbcolumnConInit.setCellValueFactory(new PropertyValueFactory<>("contractIni"));
            tbcolumnConEnd.setCellValueFactory(new PropertyValueFactory<>("contractEnd"));
            tbcolumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        } catch (Exception ex) {

        }

        tbcolumnEmail.setCellFactory(TextFieldTableCell.<ProviderEntity>forTableColumn());
        tbcolumnEmail.setOnEditCommit((CellEditEvent<ProviderEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue());
        });

        tbcolumnName.setCellFactory(TextFieldTableCell.<ProviderEntity>forTableColumn());
        tbcolumnName.setOnEditCommit((CellEditEvent<ProviderEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue());
        });

        tbcolumnConInit.setCellFactory(column -> new DatePickerCellEditer());
        tbcolumnConInit.setOnEditCommit(event -> {
            ProviderEntity movie = event.getRowValue();
            movie.setContractIni(event.getNewValue());
        });

        tbcolumnConEnd.setCellFactory(column -> new DatePickerCellEditer());
        tbcolumnConEnd.setOnEditCommit(event -> {
            ProviderEntity movie = event.getRowValue();
            movie.setContractEnd(event.getNewValue());
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
            ProviderEntity provider = t.getTableView().getItems().get(t.getTablePosition().getRow());
            provider.setPhone(t.getNewValue());
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
            ProviderEntity provider = t.getTableView().getItems().get(t.getTablePosition().getRow());
            provider.setPrice(t.getNewValue());
        });

        tableProviders.setItems(provider);

        stage.show();
    }

    public void handleRemoveAction(ActionEvent event) {

        ProviderEntity RmMovie = (ProviderEntity) tableProviders.getSelectionModel().getSelectedItem();
        iProvider.remove(String.valueOf(RmMovie.getId()));
        tableProviders.getItems().remove(RmMovie);
        tableProviders.refresh();
    }

    public void handleCreateAction(ActionEvent event) {
        // Crear una nueva instancia de MovieEntity
        ProviderEntity newProvider = new ProviderEntity();

        iProvider.create(newProvider);

        provider.add(newProvider);
        tableProviders.setItems(provider);
        tableProviders.refresh();
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
