/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import clientapp.model.ProviderEntity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.TableViewMatchers.hasNumRows;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 *
 * @author 2dam
 */
public class ProviderControllerTest extends ApplicationTest {

    private TableView<ProviderEntity> tableProviders;

    public ProviderControllerTest() {
        this.tableProviders = lookup("#tableProviders").queryTableView();
    }

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @Test
    public void listProviders() {
        // Verificar que la tabla de proveedores está visible
        assertNotNull("Error loading providers", tableProviders);
        assertTrue("La tabla de proveedores no es visible", tableProviders.isVisible());

        // Verificar que todos los elementos en la tabla son instancias de ProviderEntity
        assertFalse("La tabla de proveedores está vacía", tableProviders.getItems().isEmpty());

        for (Object item : tableProviders.getItems()) {
            assertNotNull("Se encontró un elemento nulo en la tabla", item);
            assertTrue("Un elemento en la tabla no es instancia de ProviderEntity", item instanceof ProviderEntity);
        }
    }

    @Test
    public void testCreateProvider() {
        // Obtener el número de filas antes de agregar un proveedor
        int initialRowCount = tableProviders.getItems().size();

        // Hacer clic en el botón para agregar un proveedor
        clickOn("#btnAddProvider");
        waitForFxEvents();

        // Verificar que se ha agregado una nueva fila
        int newRowCount = tableProviders.getItems().size();
        assertEquals("No se ha agregado un nuevo proveedor.", initialRowCount + 1, newRowCount);

        // Obtener el último proveedor agregado
        ProviderEntity lastProvider = tableProviders.getItems().get(newRowCount - 1);
        assertNotNull("El proveedor creado es null.", lastProvider);

        // Verificar que es una instancia válida de ProviderEntity
        assertTrue("El objeto no es una instancia de ProviderEntity.", lastProvider instanceof ProviderEntity);

        // Crear un objeto de comparación sin ID
        ProviderEntity expectedProvider = new ProviderEntity();
        expectedProvider.setName(lastProvider.getName());
        expectedProvider.setEmail(lastProvider.getEmail());
        expectedProvider.setPhone(lastProvider.getPhone());
        expectedProvider.setContractIni(lastProvider.getContractIni());
        expectedProvider.setContractEnd(lastProvider.getContractEnd());
        expectedProvider.setPrice(lastProvider.getPrice());

        // Verificar que la lista de proveedores contiene un proveedor con los mismos datos (excepto el ID)
        assertTrue("El proveedor creado no se encuentra en la lista.",
                tableProviders.getItems().stream()
                        .anyMatch(provider -> provider instanceof ProviderEntity
                        && Objects.equals(provider.getName(), expectedProvider.getName())
                        && Objects.equals(provider.getEmail(), expectedProvider.getEmail())
                        && Objects.equals(provider.getPhone(), expectedProvider.getPhone())
                        && Objects.equals(provider.getContractIni(), expectedProvider.getContractIni())
                        && Objects.equals(provider.getContractEnd(), expectedProvider.getContractEnd())
                        && Objects.equals(provider.getPrice(), expectedProvider.getPrice())));
    }

    @Test
    public void testUpdateProvider() {
        // Obtener el número de filas antes de agregar una nueva
        int rowCount = tableProviders.getItems().size();

        // Datos de prueba para el nuevo proveedor
        String providerName = "UpdateProvider";
        String providerEmail = "updateProvider@gmail.com";
        String providerPhone = "987654321";
        String contractInitDate = "20/02/2025"; // Ajusta el formato según tu configuración regional
        String contractEndDate = "23/02/2025"; // Ajusta el formato según tu configuración regional
        Float providerPrice = 300.0f;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        // Seleccionar la primera fila de la tabla (proveedor a actualizar)
        int firstRowIndex = 0;
        interact(() -> tableProviders.getSelectionModel().select(firstRowIndex));
        waitForFxEvents();

        // *** Editar el campo "Name" ***
        Node nameCell = lookup(".table-row-cell").nth(firstRowIndex).lookup(".table-cell").nth(0).query();
        clickOn(nameCell).doubleClickOn(nameCell);
        waitForFxEvents();
        write(providerName);
        type(KeyCode.ENTER);
        waitForFxEvents();

        // *** Editar el campo "Email" ***
        Node emailCell = lookup(".table-row-cell").nth(firstRowIndex).lookup(".table-cell").nth(1).query();
        clickOn(emailCell).doubleClickOn(emailCell);
        waitForFxEvents();
        write(providerEmail);
        type(KeyCode.ENTER);
        waitForFxEvents();

        // *** Editar el campo "Phone" ***
        Node phoneCell = lookup(".table-row-cell").nth(firstRowIndex).lookup(".table-cell").nth(2).query();
        clickOn(phoneCell).doubleClickOn(phoneCell);
        waitForFxEvents();
        write(providerPhone);
        type(KeyCode.ENTER);
        waitForFxEvents();

        // *** Editar el campo "Contract Init Date" usando DatePicker ***
        Node contractInitCell = lookup(".table-row-cell").nth(firstRowIndex).lookup(".table-cell").nth(3).query();
        clickOn(contractInitCell).doubleClickOn(contractInitCell);
        waitForFxEvents();
        clickOn(".date-picker .arrow-button"); // Abre el DatePicker
        waitForFxEvents();
        clickOn("20"); // Selecciona el día 20
        waitForFxEvents();
        type(KeyCode.ENTER);

        // *** Editar el campo "Contract End Date" usando DatePicker ***
        Node contractEndCell = lookup(".table-row-cell").nth(firstRowIndex).lookup(".table-cell").nth(4).query();
        clickOn(contractEndCell).doubleClickOn(contractEndCell);
        waitForFxEvents();
        clickOn(".date-picker .arrow-button"); // Abre el DatePicker
        waitForFxEvents();
        clickOn("23"); // Selecciona el día 23
        waitForFxEvents();
        type(KeyCode.ENTER);

        // *** Editar el campo "Price" ***
        Node priceCell = lookup(".table-row-cell").nth(firstRowIndex).lookup(".table-cell").nth(5).query();
        clickOn(priceCell).doubleClickOn(priceCell);
        waitForFxEvents();
        write(providerPrice.toString());
        type(KeyCode.ENTER);
        waitForFxEvents();

        // *** Verificar que los valores se han actualizado correctamente ***
        ProviderEntity updatedProvider = tableProviders.getItems().get(firstRowIndex);
        assertEquals("Provider name was not updated correctly.", providerName, updatedProvider.getName());
        assertEquals("Provider email was not updated correctly.", providerEmail, updatedProvider.getEmail());
        assertEquals("Provider phone was not updated correctly.", providerPhone, updatedProvider.getPhone().toString());
        assertEquals("Contract Init date was not updated correctly.", contractInitDate, df.format(updatedProvider.getContractIni()));
        assertEquals("Contract End date was not updated correctly.", contractEndDate, df.format(updatedProvider.getContractEnd()));
        assertEquals("Provider price was not updated correctly.", providerPrice, updatedProvider.getPrice());

        // Confirmar la edición (ENTER)
        type(javafx.scene.input.KeyCode.ENTER);
        waitForFxEvents();
    }

    @Test
    public void testRemoveProvider() {
        // Obtener el número de filas antes de eliminar
        int rowCount = tableProviders.getItems().size();

        // Verificar que la tabla tiene datos
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        // Seleccionar la última fila en la tabla
        int lastRowIndex = rowCount - 1;
        Node row = lookup(".table-row-cell").nth(lastRowIndex).query();
        assertNotNull("Row is null: table has not that row.", row);
        clickOn(row);

        // Capturar el proveedor seleccionado antes de eliminarlo
        ProviderEntity selectedProvider = tableProviders.getItems().get(lastRowIndex);

        // Hacer clic en el botón de eliminar
        clickOn("#btnRemoveProvider");

        // Verificar que aparece el cuadro de diálogo de confirmación
        verifyThat("¿Are you sure you want to remove this provider?", isVisible());

        // Confirmar la eliminación haciendo clic en el botón predeterminado (OK)
        clickOn("Aceptar");

        // Verificar que la fila ha sido eliminada
        assertEquals("The row has not been deleted!!!", rowCount - 1, tableProviders.getItems().size());

        // Verificar que el proveedor eliminado ya no está en la tabla
        assertFalse("The provider is still in the table after deletion.", tableProviders.getItems().contains(selectedProvider));
    }
}
