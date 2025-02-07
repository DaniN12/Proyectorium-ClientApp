/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.factories.CategoryFactory;
import clientapp.interfaces.ICategory;
import clientapp.model.CategoryEntity;
import clientapp.model.Pegi;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controlador para la vista de categorías en la aplicación. Esta clase gestiona
 * la interacción con la interfaz gráfica de usuario para la visualización,
 * modificación, eliminación y creación de categorías, así como la impresión de
 * informes relacionados con ellas.
 *
 * @author 2dam
 */
public class CategoryController {

    private Stage stage;

    private static Logger logger = Logger.getLogger(SignInController.class.getName());

    @FXML
    private TableColumn<CategoryEntity, ImageView> tbcolIcon;
    @FXML
    private TableColumn<CategoryEntity, String> tbcolName;
    @FXML
    private TableColumn<CategoryEntity, String> tbcolDescription;
    @FXML
    private TableColumn<CategoryEntity, Date> tbcolCreationDate;
    @FXML
    private TableColumn<CategoryEntity, Pegi> tbcolPegi;
    @FXML
    private MenuItem filterDate;
    @FXML
    private MenuItem filterDescription;

    private ICategory categoryManager;

    private ObservableList<CategoryEntity> categories;

    @FXML
    private TableView<CategoryEntity> tbcategory;

    private Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));

    /**
     * Inicializa la vista de categorías y configura la tabla, las columnas y
     * las acciones de filtro y contexto.
     *
     * @param root El contenedor raíz de la vista para la escena.
     */
    public void initialize(Parent root) {
        logger.info("Initializing Category View.");

        // Configura la escena y la ventana
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Category");
        stage.setResizable(false);
        tbcategory.setEditable(true);
        stage.getIcons().add(icon);

        // Obtiene el administrador de categorías
        categoryManager = CategoryFactory.getICategory();
        try {
            categories = FXCollections.observableArrayList(categoryManager.findAll_XML(new GenericType<List<CategoryEntity>>() {
            }));

            // Configura las columnas de la tabla
            tbcolIcon.setCellValueFactory(param -> {
                CategoryEntity category = param.getValue();
                byte[] iconBytes = category.getIcon();
                Image image = new Image("/resources/iconCategory.jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                return new SimpleObjectProperty<>(imageView);
            });

            tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tbcolCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
            tbcolPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));

            // Configura el ComboBox para la columna "pegi"
            ObservableList<Pegi> pegiOptions = FXCollections.observableArrayList(Pegi.values());
            tbcolPegi.setCellFactory(ComboBoxTableCell.forTableColumn(pegiOptions));

            tbcategory.setItems(categories);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha podido abrir la ventana: " + e.getMessage(), ButtonType.OK);
        }

        // Permite editar las celdas de la tabla
        tbcolName.setCellFactory(TextFieldTableCell.<CategoryEntity>forTableColumn());
        tbcolName.setOnEditCommit(t -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue());
            categoryManager.edit(t.getTableView().getItems().get(t.getTablePosition().getRow()), String.valueOf(t.getTableView().getItems().get(t.getTablePosition().getRow()).getId()));
        });

        tbcolDescription.setCellFactory(TextFieldTableCell.<CategoryEntity>forTableColumn());
        tbcolDescription.setOnEditCommit(t -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
            categoryManager.edit(t.getTableView().getItems().get(t.getTablePosition().getRow()), String.valueOf(t.getTableView().getItems().get(t.getTablePosition().getRow()).getId()));
        });

        tbcolCreationDate.setCellFactory(column -> new DatePickerCellEditer());
        tbcolCreationDate.setOnEditCommit(event -> {
            CategoryEntity category = event.getRowValue();
            category.setCreationDate(event.getNewValue());
            categoryManager.edit(category, String.valueOf(category.getId()));
        });

        // Establece las opciones de filtro
        filterDate.setOnAction(this::listCategoriesbyCreationDate);
        filterDescription.setOnAction(this::listCategoriesByDescriptionAndPegi18);

        setupContextMenu();

        stage.show();
    }

    /**
     * Refresca la tabla de categorías con los datos actualizados.
     */
    private void refreshTable() {
        categories.clear();
        categories.addAll(categoryManager.findAll_XML(new GenericType<List<CategoryEntity>>() {
        }));
    }

    /**
     * Maneja la acción de eliminar una categoría seleccionada.
     *
     * @param event El evento de acción de eliminación.
     */
    public void handleRemoveAction(ActionEvent event) {
        List<CategoryEntity> removeCategory = tbcategory.getSelectionModel().getSelectedItems();
        if (removeCategory != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove confirmation");
            alert.setHeaderText("¿Are you sure you want to remove this category?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (removeCategory.size() > 1) {
                        for (CategoryEntity categ : removeCategory) {
                            categoryManager.remove(String.valueOf(categ.getId()));
                            tbcategory.getItems().remove(removeCategory);
                        }
                    } else {
                        categoryManager.remove(String.valueOf(removeCategory.get(0).getId()));
                        tbcategory.getItems().remove(removeCategory);
                    }
                    refreshTable();
                }
            });
        }
    }

    /**
     * Maneja la acción de crear una nueva categoría.
     *
     * @param event El evento de acción de creación.
     */
    public void handleCreateAction(ActionEvent event) {
        CategoryEntity newCategory = new CategoryEntity();
        categoryManager.create(newCategory);
        categories = FXCollections.observableArrayList(categoryManager.findAll_XML(new GenericType<List<CategoryEntity>>() {
        }));
        tbcategory.setItems(categories);
    }

    /**
     * Filtra las categorías por fecha de creación.
     *
     * @param event El evento de filtro por fecha.
     */
    public void listCategoriesbyCreationDate(ActionEvent event) {
        try {
            categories = FXCollections.observableArrayList(categoryManager.listCategoriesbyCreationDate_XML(new GenericType<List<CategoryEntity>>() {
            }));
            tbcategory.setItems(categories);
            tbcategory.refresh();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al filtrar las categorías: " + e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    /**
     * Filtra las categorías por descripción y por el rango de PEGI 18.
     *
     * @param event El evento de filtro por descripción y PEGI.
     */
    public void listCategoriesByDescriptionAndPegi18(ActionEvent event) {
        try {
            categories = FXCollections.observableArrayList(categoryManager.listCategoriesByDescriptionAndPegi18_XML(new GenericType<List<CategoryEntity>>() {
            }));
            tbcategory.setItems(categories);
            tbcategory.refresh();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al filtrar las categorías: " + e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    /**
     * Configura el menú contextual de la tabla de categorías.
     */
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem add = new MenuItem("Add ");
        MenuItem remove = new MenuItem("Remove");
        MenuItem print = new MenuItem("Print table");

        add.setOnAction(this::handleCreateAction);
        remove.setOnAction(this::handleRemoveAction);
        print.setOnAction(this::handlePrintAction);

        contextMenu.getItems().add(add);
        contextMenu.getItems().add(remove);

        tbcategory.setContextMenu(contextMenu);
    }

    /**
     * Maneja la acción de imprimir la tabla de categorías.
     *
     * @param event El evento de impresión de la tabla.
     */
    @FXML
    private void handlePrintAction(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/clientapp/reports/CategoryReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<CategoryEntity>) this.tbcategory.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception error) {
            logger.log(Level.SEVERE, "Error al generar el reporte: " + error.getMessage());
        }
    }

    // Métodos getter y setter para las propiedades de la clase
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

    public TableColumn<CategoryEntity, ImageView> getTbcolIcon() {
        return tbcolIcon;
    }

    public void setTbcolIcon(TableColumn<CategoryEntity, ImageView> tbcolIcon) {
        this.tbcolIcon = tbcolIcon;
    }

    public TableColumn<CategoryEntity, String> getTbcolName() {
        return tbcolName;
    }

    public void setTbcolName(TableColumn<CategoryEntity, String> tbcolName) {
        this.tbcolName = tbcolName;
    }

    public TableColumn<CategoryEntity, String> getTbcolDescription() {
        return tbcolDescription;
    }

    public void setTbcolDescription(TableColumn<CategoryEntity, String> tbcolDescription) {
        this.tbcolDescription = tbcolDescription;
    }

    public TableColumn<CategoryEntity, Date> getTbcolCreationDate() {
        return tbcolCreationDate;
    }

    public void setTbcolCreationDate(TableColumn<CategoryEntity, Date> tbcolCreationDate) {
        this.tbcolCreationDate = tbcolCreationDate;
    }

    public TableColumn<CategoryEntity, Pegi> getTbcolPegi() {
        return tbcolPegi;
    }

    public void setTbcolPegi(TableColumn<CategoryEntity, Pegi> tbcolPegi) {
        this.tbcolPegi = tbcolPegi;

    }
}
