package clientapp.controller;

import clientapp.MainInfoView;
import clientapp.factories.TicketFactory;
import clientapp.model.TicketEntity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javax.ws.rs.core.GenericType;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 * Test class for InfoViewController.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InfoViewControllerTest extends ApplicationTest {

    private TableView<TicketEntity> ticketTableView;
    private ComboBox<?> comboBoxNode;

    public InfoViewControllerTest() {
        this.ticketTableView = lookup("#ticketTableView").queryTableView();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainInfoView.class);
    }

    @Test
    public void testARead() {
        ObservableList<TicketEntity> listTickets = FXCollections.observableArrayList(
                TicketFactory.getITicket().findAll_XML(new GenericType<List<TicketEntity>>() {
                }));
        List<TicketEntity> newListTickets = ticketTableView.getItems();
        assertEquals("The table hasn´t the same tickets as database!!!", listTickets, newListTickets);
    }

    @Test
    public void testBCreate() {
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
        String expectedTitle = (String) comboBoxNode.getSelectionModel().getSelectedItem();

        // Verificar que la fila se haya añadido
        assertEquals("The ticket has not been added!!!", rowCount + 1, ticketTableView.getItems().size());
        assertEquals("The ticket has not been added correctly!!!", expectedTitle, ticketTableView.getItems().get(lastRowIndex).getMovie().getTitle());
    }

    @Test
    public void testCUpdate() {
        String title = "UpdateTitle";
        String date = "15/02/2025"; 
        String people="3";
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        int lastRowIndex = ticketTableView.getItems().size() - 1;

        interact(() -> {
            comboBoxNode = (ComboBox<?>) lookup(".combo-box").query();
            comboBoxNode.getSelectionModel().select(1); // Cambiar la selección al segundo elemento
        });

        // Confirmar la edición (ENTER) dentro del hilo de JavaFX
        interact(() -> {
            type(javafx.scene.input.KeyCode.ENTER);
        });
        
        waitForFxEvents();
        
        
        // *** Editar el campo "title" ***
        Node titleCell = lookup(".table-row-cell").nth(lastRowIndex).lookup(".table-cell").nth(0).query();
        clickOn(titleCell).doubleClickOn(titleCell);
        waitForFxEvents();
        write(title);
        type(KeyCode.ENTER);
        waitForFxEvents();
        
        
        // *** Editar el campo "Date" usando DatePicker ***
        Node dateCell = lookup(".table-row-cell").nth(lastRowIndex).lookup(".table-cell").nth(2).query();
        clickOn(dateCell).doubleClickOn(dateCell);
        waitForFxEvents();
        clickOn(".date-picker .arrow-button"); // Abre el DatePicker
        waitForFxEvents();
        clickOn("15"); // Selecciona el día 15
        waitForFxEvents();
        type(KeyCode.ENTER);
        
         // *** Editar el campo "People" ***
        Node peopleCell = lookup(".table-row-cell").nth(lastRowIndex).lookup(".table-cell").nth(5).query();
        clickOn(peopleCell).doubleClickOn(peopleCell);
        waitForFxEvents();
        write(people);
        type(KeyCode.ENTER);
        waitForFxEvents();
        
        

        
        TicketEntity updatedTicket = ticketTableView.getItems().get(lastRowIndex);
        String expectedTitle = (String) comboBoxNode.getSelectionModel().getSelectedItem();
        assertEquals("The ticket has not been updated correctly!!!", expectedTitle, ticketTableView.getItems().get(lastRowIndex).getMovie().getTitle());
        assertEquals("Provider name was not updated correctly.", title, updatedTicket.getMovieTitle());
        assertEquals("People  was not updated correctly.", people, updatedTicket.getNumPeople().toString());
        assertEquals("Buy date was not updated correctly.", date, df.format(updatedTicket.getBuyDate()));
    }

    @Test
    public void testDDelete() {
        int rowCount = ticketTableView.getItems().size();
        int lastRowIndex = ticketTableView.getItems().size() - 1;

        interact(() -> {
            Node titleCell = lookup("#ticketTableView .table-row-cell").nth(lastRowIndex)
                    .lookup(".table-cell").nth(1) // Suponiendo que la columna "title" es la segunda columna
                    .query();
            rightClickOn(titleCell);
        });
        waitForFxEvents();

        interact(() -> {
            clickOn("#removeMenuItem");
        });
        waitForFxEvents();

        interact(() -> {
            DialogPane dialogPane = lookup(".dialog-pane").query();
            Button confirmButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            clickOn(confirmButton);
        });

        waitForFxEvents();

        int newRowCount = ticketTableView.getItems().size();
        assertEquals("The ticket has not been deleted correctly!!!", rowCount - 1, newRowCount);
    }

}
