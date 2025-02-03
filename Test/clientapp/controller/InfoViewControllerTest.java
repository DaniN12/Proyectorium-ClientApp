/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import clientapp.model.TicketEntity;
import java.util.concurrent.TimeoutException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 *
 * @author kbilb
 */
public class InfoViewControllerTest extends ApplicationTest {

    private TableView<TicketEntity> ticketTableView;
    private ComboBox<?> comboBoxNode;

    public InfoViewControllerTest() {
        this.ticketTableView = lookup("#ticketTableView").queryTableView();
    }

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @Test
    public void testCreate() {
        // Obtener el número de filas antes de agregar una nueva
        int rowCount = ticketTableView.getItems().size();

        // Hacer clic en el botón de agregar película
        clickOn("#addTicketButton");

        // Esperar a que la fila se agregue
        waitForFxEvents();

        // Seleccionar la última fila de la tabla (la recién añadida)
        int lastRowIndex = ticketTableView.getItems().size() - 1;

        // Hacer scroll hacia la fila seleccionada
        ticketTableView.scrollTo(lastRowIndex);

        // Hacer doble clic en la celda "title" de la última fila
        Node titleCell = lookup("#ticketTableView .table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(1) // Suponiendo que la columna "title" es la segunda columna
                .query();
        doubleClickOn(titleCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        // Obtener el valor seleccionado del ComboBox
        comboBoxNode = (ComboBox<?>) lookup(".combo-box-base").query();

        // Verificar que el título del ticket se haya actualizado correctamente
        String expectedTitle = (String) ((ComboBox<?>) comboBoxNode).getSelectionModel().getSelectedItem();

        // Verificar que la fila se haya añadido
        assertEquals("The ticket has not been added!!!", rowCount + 1, ticketTableView.getItems().size());

        assertEquals("The ticket has not been added correctly!!!", expectedTitle, ticketTableView.getItems().get(lastRowIndex).getMovie().getTitle());
    }

    @Test
    public void testUpdate() {
        // Seleccionar la última fila de la tabla
        int lastRowIndex = ticketTableView.getItems().size() - 1;

        // Obtener el valor seleccionado del ComboBox y cambiarlo dentro del hilo de JavaFX
        interact(() -> {
            comboBoxNode = (ComboBox<?>) lookup(".combo-box-base").query();
            comboBoxNode.getSelectionModel().select(1); // Cambiar la selección al segundo elemento
        });

        // Confirmar la edición (ENTER) dentro del hilo de JavaFX
        interact(() -> {
            type(javafx.scene.input.KeyCode.ENTER);
        });
        waitForFxEvents(); // Esperar que los eventos se procesen

        // Verificar que el título del ticket se haya actualizado correctamente
        String expectedTitle = (String) ((ComboBox<?>) comboBoxNode).getSelectionModel().getSelectedItem();

        // Hacer la verificación de que el ticket se haya actualizado correctamente
        assertEquals("The ticket has not been updated correctly!!!", expectedTitle, ticketTableView.getItems().get(lastRowIndex).getMovie().getTitle());
    }
    
    @Test
    public void testDelete() {
        // Obtener el número de filas antes de agregar una nueva
        int rowCount = ticketTableView.getItems().size();
        
        // Seleccionar la última fila de la tabla
        int lastRowIndex = ticketTableView.getItems().size() - 1;

        // Hacer doble clic en la celda "title" de la última fila
        Node titleCell = lookup("#ticketTableView .table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(1) // Suponiendo que la columna "title" es la segunda columna
                .query();
        doubleClickOn(titleCell);
        waitForFxEvents();
        
        // Obtener el valor seleccionado del ComboBox y cambiarlo dentro del hilo de JavaFX
        interact(() -> {
            rightClickOn(titleCell);
            clickOn("#removeMenuItem");
            clickOn("#button_ok");
        });
        waitForFxEvents(); // Esperar que los eventos se procesen

        // Obtener el número de filas antes de agregar una nueva
        int newRowCount = ticketTableView.getItems().size();
        
        // Hacer la verificación de que el ticket se haya actualizado correctamente
        assertEquals("The ticket has not been updated correctly!!!", rowCount, newRowCount - 1);
    }

}
