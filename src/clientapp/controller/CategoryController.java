/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.factories.CategoryFactory;
import clientapp.interfaces.ICategory;
import clientapp.model.CategoryEntity;
import clientapp.model.MovieEntity;
import clientapp.model.Pegi;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private TableColumn<CategoryEntity, String> tbcolName;
    @FXML
    private TableColumn<CategoryEntity, String> tbcolDescription;
    @FXML
    private TableColumn<CategoryEntity, Date> tbcolCreationDate;
    @FXML
    private TableColumn tbcolPegi;

    private ICategory categoryManager;

    private ObservableList<CategoryEntity> categories;

    @FXML
    private TableView tbcategory;

    public void initialize(Parent root) {
        logger.info("Initializing InfoView stage.");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Category");
        stage.setResizable(false);
        tbcategory.setEditable(true);

        categoryManager = CategoryFactory.getICategory();
        try {

            categories = FXCollections.observableArrayList(categoryManager.findAll(new GenericType<List<CategoryEntity>>() {
            }));

            tbcolIcon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CategoryEntity, ImageView>, ObservableValue<ImageView>>() {
                @Override
                public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<CategoryEntity, ImageView> param) {
                    CategoryEntity category = param.getValue();
                    byte[] iconBytes = category.getIcon();

                    // Convertir el array de bytes a una imagen
                    Image image = new Image("/resources/iconCategory.jpg");

                    // Crear un ImageView y establecer la imagen
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);  // Ajustar el tamaño de la imagen
                    imageView.setFitHeight(100); // Ajustar el tamaño de la imagen
                    return new SimpleObjectProperty<>(imageView);
                }
            });

            // tbcolIcon.setCellValueFactory(new PropertyValueFactory<>("icon"));
            tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tbcolCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
            tbcolPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
            // Configurar la ComboBox para la columna "pegi"
            ObservableList<Pegi> pegiOptions = FXCollections.observableArrayList(Pegi.values());
            tbcolPegi.setCellFactory(ComboBoxTableCell.forTableColumn(pegiOptions));

            tbcategory.setItems(categories);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha podido abrir la ventana: " + e.getMessage(), ButtonType.OK);
        }

        tbcolName.setCellFactory(TextFieldTableCell.<CategoryEntity>forTableColumn());
        tbcolName.setOnEditCommit((CellEditEvent<CategoryEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue());
        });

        tbcolDescription.setCellFactory(TextFieldTableCell.<CategoryEntity>forTableColumn());
        tbcolDescription.setOnEditCommit((CellEditEvent<CategoryEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
        });

        tbcolCreationDate.setCellFactory(column -> new DatePickerCellEditer());
        tbcolCreationDate.setOnEditCommit(event -> {
            CategoryEntity category = event.getRowValue();
            category.setCreationDate(event.getNewValue());
        });

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

    public void removeCategory() {
        tbcategory.getItems().remove(tbcategory.getSelectionModel().getSelectedItem());
        tbcategory.refresh();
    }

    public void handleRemoveAction(ActionEvent event) {
        CategoryEntity removeCategory = (CategoryEntity) tbcategory.getSelectionModel().getSelectedItem();
        categoryManager.remove(String.valueOf(removeCategory.getId()));
        tbcategory.getItems().remove(removeCategory);
        tbcategory.refresh();
    }

    public void handleCreateAction(ActionEvent event) {
        CategoryEntity newCategory = new CategoryEntity();
        categoryManager.create(newCategory);
        categories.add(newCategory);
        tbcategory.setItems(categories);
        tbcategory.refresh();
    }

}
