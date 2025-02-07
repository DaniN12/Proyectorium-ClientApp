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
import java.util.Locale.Category;
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
import javafx.scene.control.TableColumn.CellEditEvent;
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
 *
 * @author 2dam
 */
public class CategoryController {

    private Stage stage;

    private static Logger logger = Logger.getLogger(SignInController.class.getName());

    @FXML
    private TableColumn tbcolIcon;
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
    private TableView tbcategory;

    private Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));

    public void initialize(Parent root) {
        logger.info("Initializing Category View.");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Category");
        stage.setResizable(false);
        tbcategory.setEditable(true);
        stage.getIcons().add(icon);

        categoryManager = CategoryFactory.getICategory();
        try {

            categories = FXCollections.observableArrayList(categoryManager.findAll_XML(new GenericType<List<CategoryEntity>>() {
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

        tbcolName.setCellFactory(TextFieldTableCell.<CategoryEntity>forTableColumn());
        tbcolName.setOnEditCommit((CellEditEvent<CategoryEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue());
            CategoryEntity name = t.getRowValue();
            name.setName(t.getNewValue());
            categoryManager.edit(name, String.valueOf(name.getId()));
        });

        tbcolDescription.setCellFactory(TextFieldTableCell.<CategoryEntity>forTableColumn());
        tbcolDescription.setOnEditCommit((CellEditEvent<CategoryEntity, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
            CategoryEntity description = t.getRowValue();
            description.setDescription(t.getNewValue());
            categoryManager.edit(description, String.valueOf(description.getId()));
        });

        tbcolCreationDate.setCellFactory(column -> new DatePickerCellEditer());
        tbcolCreationDate.setOnEditCommit(event -> {
            CategoryEntity creationDate = event.getRowValue();
            creationDate.setCreationDate(event.getNewValue());
            categoryManager.edit(creationDate, String.valueOf(creationDate.getId()));
        });

        ObservableList<Pegi> pegiOptions = FXCollections.observableArrayList(Pegi.values());

        tbcolPegi.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPegi()));

        tbcolPegi.setCellFactory(column -> {
            return new ComboBoxTableCell<>(new StringConverter<Pegi>() {
                @Override
                public String toString(Pegi age) {
                    return age != null ? age.toString() : "";
                }

                @Override
                public Pegi fromString(String string) {
                    return Pegi.valueOf(string);
                }
            }, pegiOptions);
        });

        tbcolPegi.setOnEditCommit(event -> {
            CategoryEntity pegiAge = event.getRowValue();
            pegiAge.setPegi(event.getNewValue()); // Guarda la hora seleccionada
            categoryManager.edit(pegiAge, String.valueOf(pegiAge.getId()));
        });

        tbcategory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        filterDate.setOnAction(this::listCategoriesbyCreationDate);
        filterDescription.setOnAction(this::listCategoriesByDescriptionAndPegi18);
        setupContextMenu();
    }

    private void refreshTable() {
        // Limpiar la lista actual de tickets
        categories.clear();
        // Obtener todos los tickets y filtrar solo los que pertenecen al usuario logueado
        categories.addAll(
                categoryManager.findAll_XML(new GenericType<List<CategoryEntity>>() {
                }));/*
                        .stream()
                        .filter(ticket -> ticket.getUser().getId() == user.getId()) // Filtrar por el ID del usuario
                        .collect(Collectors.toList()) // Convertir el resultado en una lista estándar
        );*/
    }

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

    public void handleCreateAction(ActionEvent event) {
        CategoryEntity newCategory = new CategoryEntity();
        categoryManager.create(newCategory);
        categories = FXCollections.observableArrayList(categoryManager.findAll_XML(new GenericType<List<CategoryEntity>>() {
        }));
        tbcategory.setItems(categories);

    }

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

    @FXML
    private void handlePrintAction(ActionEvent event) {
        try {
            JasperReport report   = JasperCompileManager.compileReport(getClass().getResourceAsStream("/clientapp/reports/CategoryReport.jrxml"));
            JRBeanCollectionDataSource dataItems   = new JRBeanCollectionDataSource((Collection<Category>) this.tbcategory.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception error) {
            //Logger.log(Level.SEVERE, "AccountController(handlePrintReport): Exception while creating the report {0}", error.getMessage());
        }
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