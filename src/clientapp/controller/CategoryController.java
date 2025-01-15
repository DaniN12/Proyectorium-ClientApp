/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.factories.CategoryFactory;
import clientapp.interfaces.ICategory;
import clientapp.model.CategoryEntity;
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
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class CategoryController {

    private Stage stage;

    private Logger logger = Logger.getLogger(SignInController.class.getName());

    @FXML
    private TableColumn tbcolIcon;
    @FXML
    private TableColumn tbcolName;
    @FXML
    private TableColumn tbcolDescription;
    @FXML
    private TableColumn tbcolCreationDate;
    @FXML
    private TableColumn tbcolPegi;

    private ICategory categoryManager;

    @FXML
    private TableView tbcategory;

    public void initialize(Parent root) {
        logger.info("Initializing InfoView stage.");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User info");
        stage.setResizable(false);

        categoryManager = CategoryFactory.getICategory();
        try {

            ObservableList<CategoryEntity> category = FXCollections.observableArrayList(categoryManager.findAll(new GenericType<List<CategoryEntity>>() {
            }));

            // tbcolIcon.setCellValueFactory(new PropertyValueFactory<>("icon"));
            tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tbcolCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
            tbcolPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));

            tbcategory.setItems(category);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha podido abrir la ventana: " + e.getMessage(), ButtonType.OK);
        }
        ;
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

    public TableColumn getTbcolIcon() {
        return tbcolIcon;
    }

    public void setTbcolIcon(TableColumn tbcolIcon) {
        this.tbcolIcon = tbcolIcon;
    }

    public TableColumn getTbcolName() {
        return tbcolName;
    }

    public void setTbcolName(TableColumn tbcolName) {
        this.tbcolName = tbcolName;
    }

    public TableColumn getTbcolDescription() {
        return tbcolDescription;
    }

    public void setTbcolDescription(TableColumn tbcolDescription) {
        this.tbcolDescription = tbcolDescription;
    }

    public TableColumn getTbcolCreationDate() {
        return tbcolCreationDate;
    }

    public void setTbcolCreationDate(TableColumn tbcolCreationDate) {
        this.tbcolCreationDate = tbcolCreationDate;
    }

    public TableColumn getTbcolPegi() {
        return tbcolPegi;
    }

    public void setTbcolPegi(TableColumn tbcolPegi) {
        this.tbcolPegi = tbcolPegi;
    }

    public ICategory getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(ICategory categoryManager) {
        this.categoryManager = categoryManager;
    }

}
