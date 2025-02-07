
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import clientapp.model.CategoryEntity;
import clientapp.model.Pegi;
import java.rmi.NotBoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryControllerTest extends ApplicationTest {

    private TableView tbcategory = (TableView) lookup("#tbcategory").queryTableView();
     private ComboBox<?> comboBoxNode;

    public CategoryControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @Test
    public void testSomeMethod() {
    }

//      /**
//     * Starts application to be tested.
//     * @param stage Primary Stage object
//     * @throws Exception If there is any error
//     */
//    @Test
//    public void delete(Stage stage) throws Exception {
//         int rowCount=table.getItems().size();
//        assertNotEquals("Table has no data: Cannot test.",
//                        rowCount,0);
//        //look for 1st row in table view and click it
//        Node row=lookup(".table-row-cell").nth(0).query();
//        assertNotNull("Row is null: table has not that row. ",row);
//        clickOn(row);
//        verifyThat("#eliminar", isEnabled());//note that id is used instead of fx:id
//        clickOn("#eliminar");
//        verifyThat("¿Borrar la fila seleccionada?\n"
//                                    + "Esta operación no se puede deshacer.",
//                    isVisible());    
//        clickOn(isDefaultButton());
//        assertEquals("The row has not been deleted!!!",
//                    rowCount-1,table.getItems().size());
//        verifyThat(tfLogin,  (TextField t) -> t.isFocused());
//    }
    @Test
    public void listCategory() {
        assertNotNull("Error loading movies", isVisible());
    }

    @Test
    public void createCategory() {

        // Obtener el número de filas antes de agregar una nueva
        int rowCount = tbcategory.getItems().size();

        // Generar un nombre aleatorio para la nueva película
        String categoryName = "Category" + new Random().nextInt();

        // Hacer clic en el botón de agregar película
        clickOn("#addBtn");

        // Esperar a que la fila se agregue
        waitForFxEvents();

        // Seleccionar la última fila de la tabla (la recién añadida)
        int lastRowIndex = tbcategory.getItems().size() - 1;
        interact(() -> tbcategory.getSelectionModel().select(lastRowIndex)); // Selecciona la fila programáticamente
        waitForFxEvents();

        // Hacer doble clic en la celda "title" de la última fila
        Node titleCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(1) // Suponiendo que la columna "title" es la segunda columna
                .query();
        doubleClickOn(titleCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        // Buscar el TextField dentro de la celda en modo edición
        Node textField = lookup(".text-field").query();
        clickOn(textField); // Asegurar que el foco esté en el campo de edición
        write(categoryName);  // Escribir el título

        // Confirmar la edición (ENTER)
        type(javafx.scene.input.KeyCode.ENTER);
        waitForFxEvents();

        // Verificar que la fila se haya añadido
        assertEquals("The row has not been added!!!", rowCount + 1, tbcategory.getItems().size());

        // Comprobar que el título se ha guardado correctamente en la tabla
        List<CategoryEntity> cate = tbcategory.getItems();
        assertEquals("The movie has not been added correctly!!!",
                cate.stream().filter(m -> m.getName().equals(categoryName)).count(), 1);
    }

    /**
     * Test that movie is deleted when the OK button is clicked in the
     * confirmation dialog.
     */
    @Test
    public void deleteCategory() throws NotBoundException {
        // Obtener el número de filas antes de eliminar
        int rowCount = tbcategory.getItems().size();

        // Verificar que la tabla tiene datos
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        // Seleccionar la última fila en la tabla
        int lastRowIndex = rowCount - 1;
        Node row = lookup(".table-row-cell").nth(lastRowIndex).query();
        assertNotNull("Row is null: table has not that row.", row);
        clickOn(row);
        // Hacer clic en el botón de eliminar
        clickOn("#removeBtn");

        // Verificar que aparece el cuadro de diálogo de confirmación
        verifyThat("¿Are you sure you want to remove this category?", isVisible());
        // Confirmar la eliminación haciendo clic en el botón predeterminado (OK)
        clickOn("Aceptar");

        // Verificar que la fila ha sido eliminada
        assertEquals("The row has not been deleted!!!", rowCount - 1, tbcategory.getItems().size());
    }

    @Test
    public void testUpdateCategory() {
        // Contar el número inicial de filas en la tabla
        int rowCount = tbcategory.getItems().size();

        // Definir los valores que vamos a actualizar en la fila
        String categoryName = "Test";
        String categoryDescription = "probando test update";
        String categoryDate = "05/02/2025";
        Pegi categoryPegi = Pegi.PEGI_7; // Enum correcto
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        waitForFxEvents();

        // Seleccionar la última fila de la tabla
        int lastRowIndex = tbcategory.getItems().size() - 1;
        interact(() -> tbcategory.getSelectionModel().select(lastRowIndex)); // Selecciona la fila programáticamente
        waitForFxEvents();

        // =================== EDITAR EL NOMBRE ===================
        Node nameCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(1) // Suponiendo que la columna "name" es la segunda columna
                .query();
        clickOn(nameCell).doubleClickOn(nameCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        Node textField = lookup(".text-field").query();
        clickOn(textField).doubleClickOn(textField); // Asegurar que el foco esté en el campo de edición
        write(categoryName);  // Escribir el nuevo nombre

        // Confirmar la edición (ENTER)
        type(KeyCode.ENTER);
        waitForFxEvents();

        // =================== EDITAR LA DESCRIPCIÓN ===================
        Node descriptionCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(2) // Suponiendo que la columna "description" es la tercera columna
                .query();
        clickOn(descriptionCell).doubleClickOn(descriptionCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        Node descriptionField = lookup(".text-field").query();
        clickOn(descriptionField).doubleClickOn(descriptionField); // Asegurar que el foco esté en el campo de edición
        write(categoryDescription);  // Escribir la nueva descripción

        // Confirmar la edición (ENTER)
        type(KeyCode.ENTER);
        waitForFxEvents();

        // =================== EDITAR LA FECHA ===================
        Node dateCell = lookup(".table-row-cell").nth(lastRowIndex).lookup(".table-cell").nth(3).query();
        clickOn(dateCell).doubleClickOn(dateCell);
        waitForFxEvents();

        // Buscar y hacer clic en el botón del DatePicker para abrir el calendario
        Node datePickerButton = lookup(".date-picker .arrow-button").query();
        clickOn(datePickerButton);
        waitForFxEvents();

        // Buscar el nodo exacto del día dentro del calendario
        Node dayToSelect = lookup(".date-picker-popup .day-cell").lookup("5").query(); // Ajusta "5" según el día necesario
        clickOn(dayToSelect);
        waitForFxEvents();

        // Confirmar la edición con ENTER
        type(KeyCode.ENTER);
        waitForFxEvents();

        // =================== EDITAR EL PEGI (COMBOBOX) ===================
        Node pegiCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(4) // Suponiendo que la columna "pegi" es la quinta columna
                .query();
        doubleClickOn(pegiCell); // Activar el ComboBox
        waitForFxEvents();

// Buscar y seleccionar la opción correcta en la lista desplegable
        interact(() -> {
             comboBoxNode = (ComboBox<?>) lookup(".combo-box-base").query();
            comboBoxNode.getSelectionModel().select(1); // Cambiar la selección al segundo elemento
        });
// Confirmar la selección (ENTER)
        type(KeyCode.ENTER);
        waitForFxEvents();

        // =================== VERIFICAR QUE LOS CAMBIOS SE HAN GUARDADO ===================
        List<CategoryEntity> categories = tbcategory.getItems();
        CategoryEntity updatedCategory = categories.get(lastRowIndex);

        assertEquals("El nombre no se actualizó correctamente", categoryName, updatedCategory.getName());
        assertEquals("La descripción no se actualizó correctamente", categoryDescription, updatedCategory.getDescription());
        assertEquals("La fecha no se actualizó correctamente", categoryDate, df.format(updatedCategory.getCreationDate()));
        assertEquals("El Pegi no se actualizó correctamente", categoryPegi, updatedCategory.getPegi());
    }
}
