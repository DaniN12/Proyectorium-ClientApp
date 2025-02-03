/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import java.rmi.NotBoundException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    /**
     * Test that movie is deleted when the OK button is clicked in the
     * confirmation dialog.
     */
    @Test
    public void testB_deleteMovie() throws NotBoundException {
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

}
