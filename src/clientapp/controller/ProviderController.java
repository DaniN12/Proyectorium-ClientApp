/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.factories.ProviderManagerFactory;
import clientapp.interfaces.IProvider;
import clientapp.model.ProviderEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class ProviderController {

    private Stage stage;

    private Logger logger = Logger.getLogger(SignInController.class.getName());

    @FXML
    private TableColumn tbcolumnEmail;

    @FXML
    private TableColumn tbcolumnName;

    @FXML
    private TableColumn tbcolumnPhone;

    @FXML
    private TableColumn tbcolumnConInit;

    @FXML
    private TableColumn tbcolumnConEnd;

    @FXML
    private TableColumn tbcolumnPrice;

    @FXML
    private TableView tableProviders;

    private ObservableList<ProviderEntity> provider = null;

    private IProvider iProvider;

    public void initialize(Parent root) {
        logger.info("Initializing Provider stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Providers");
        stage.setResizable(false);
        
        

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
//Establecer el modelo de datos de la tabla  
        tableProviders.setItems(provider);

        stage.show();
    }

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
