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
    private ObservableList<TicketEntity> listTickets;
    private final int lastRowIndex;
    private Node titleCell;

    public InfoViewControllerTest() {
        this.ticketTableView = lookup("#ticketTableView").queryTableView();
        this.lastRowIndex = FXCollections.observableArrayList(
                TicketFactory.getITicket().findAll_XML(new GenericType<List<TicketEntity>>() {
                })).size();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainInfoView.class);
    }

    @Test
    public void testARead() {
        listTickets = FXCollections.observableArrayList(
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

        // Hacer doble clic en la celda "title" de la última fila
        titleCell = lookup("#ticketTableView .table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(1)
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
        String date = "20/02/2025";
        String people = "3";

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        interact(() -> {
            comboBoxNode = (ComboBox<?>) lookup(".combo-box-base").query();
            doubleClickOn(comboBoxNode);
            comboBoxNode.getSelectionModel().select(1);
        });
        type(KeyCode.ENTER);
        waitForFxEvents();
        
        Node datePickerCell = lookup("#ticketTableView .table-row-cell")
                .nth(lastRowIndex)
                .lookup(".table-cell").nth(2).query();

        doubleClickOn(datePickerCell);
        waitForFxEvents();
        clickOn(".date-picker .arrow-button");
        waitForFxEvents();
        clickOn("20");
        type(KeyCode.ENTER);
        waitForFxEvents();
        
        Node peopleCell = lookup("#ticketTableView .table-row-cell")
                .nth(lastRowIndex)
                .lookup(".table-cell").nth(5)
                .query();
        doubleClickOn(peopleCell);
        waitForFxEvents();
        write(people);
        type(KeyCode.ENTER);
        waitForFxEvents();

        TicketEntity updatedTicket = ticketTableView.getItems().get(lastRowIndex);
        String expectedTitle = (String) comboBoxNode.getSelectionModel().getSelectedItem();

        assertEquals("The ticket has not been updated correctly!!!", expectedTitle, updatedTicket.getMovie().getTitle());
        assertEquals("Buy date was not updated correctly.", date, df.format(updatedTicket.getBuyDate()));
        assertEquals("People was not updated correctly.", people, String.valueOf(updatedTicket.getNumPeople()));
    }

    @Test
    public void testDDelete() {
        int rowCount = ticketTableView.getItems().size();
        listTickets = ticketTableView.getItems();
        TicketEntity ticket;

        ticket = (TicketEntity) titleCell.getUserData();
        rightClickOn(titleCell);
        waitForFxEvents();

        clickOn("#removeMenuItem");
        waitForFxEvents();

        interact(() -> {
            DialogPane dialogPane = lookup(".dialog-pane").query();
            Button confirmButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            clickOn(confirmButton);
        });

        waitForFxEvents();

        int newRowCount = ticketTableView.getItems().size();
        assertEquals("The ticket has not been deleted correctly!!!", rowCount - 1, newRowCount);
        assertFalse(listTickets.contains(ticket));
    }

}
