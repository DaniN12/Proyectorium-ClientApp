/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import clientapp.model.MovieEntity;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieControllerTest extends ApplicationTest {

    private TableView table = (TableView) lookup("#moviesTbv").queryTableView();

    public MovieControllerTest() {
    }

    /*
    @Override
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        new Main().start(stage);
    }
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @Test
    public void testA_listMovie() {

        assertNotNull("Error loading movies", isVisible());

    }

    @Test
    public void testB_createMovie() {

        // Obtener el número de filas antes de agregar una nueva
        int rowCount = table.getItems().size();

        // Generar un título aleatorio para la nueva película
        String movieTitle = "Movie" + new Random().nextInt();

        // Hacer clic en el botón de agregar película
        clickOn("#addMovieBtn");

        // Esperar a que la fila se agregue
        waitForFxEvents();

        // Seleccionar la última fila de la tabla (la recién añadida)
        int lastRowIndex = table.getItems().size() - 1;
        interact(() -> table.getSelectionModel().select(lastRowIndex)); // Selecciona la fila programáticamente
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
        write(movieTitle);  // Escribir el título

        // Confirmar la edición (ENTER)
        type(javafx.scene.input.KeyCode.ENTER);
        waitForFxEvents();

        // Verificar que la fila se haya añadido
        assertEquals("The row has not been added!!!", rowCount + 1, table.getItems().size());

        // Comprobar que el título se ha guardado correctamente en la tabla
        List<MovieEntity> movies = table.getItems();
        assertEquals("The movie has not been added correctly!!!",
                movies.stream().filter(m -> m.getTitle().equals(movieTitle)).count(), 1);
    }

    @Test
    public void testC_UpdateMovie() {

        int rowCount = table.getItems().size();
        String movieSinopsis = "sinopsis 1";
        String movieDate = "31/01/2025";
        Integer movieDuration = 90;

        waitForFxEvents();

        // Seleccionar la última fila de la tabla (la recién añadida)
        int lastRowIndex = table.getItems().size() - 1;
        interact(() -> table.getSelectionModel().select(lastRowIndex)); // Selecciona la fila programáticamente
        waitForFxEvents();

        // Hacer doble clic en la celda "title" de la última fila
        Node titleCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(2) // Suponiendo que la columna "title" es la segunda columna
                .query();
        doubleClickOn(titleCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        // Buscar el TextField dentro de la celda en modo edición
        Node textField = lookup(".text-field").query();
        clickOn(textField); // Asegurar que el foco esté en el campo de edición
        write(movieSinopsis);  // Escribir la sinopsis

        // Confirmar la edición (ENTER)
        type(javafx.scene.input.KeyCode.ENTER);
        waitForFxEvents();
        
        // Hacer doble clic en la celda "title" de la última fila
        Node DateCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(3) // Suponiendo que la columna "title" es la segunda columna
                .query();
        doubleClickOn(DateCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        // Buscar el TextField dentro de la celda en modo edición
        Node DatePicker = lookup(".text-field").query();
        clickOn(DatePicker); // Asegurar que el foco esté en el campo de edición
        write(movieDate);  // Escribir la sinopsis

        // Confirmar la edición (ENTER)
        type(javafx.scene.input.KeyCode.ENTER);
        waitForFxEvents();
        
        // Hacer doble clic en la celda "title" de la última fila
        Node DurationCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(4) // Suponiendo que la columna "title" es la segunda columna
                .query();
        doubleClickOn(DurationCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        // Buscar el TextField dentro de la celda en modo edición
        Node IntegerField = lookup(".text-field").query();
        clickOn(IntegerField); // Asegurar que el foco esté en el campo de edición
        write(String.valueOf(movieDuration));  // Escribir la sinopsis

        // Confirmar la edición (ENTER)
        type(javafx.scene.input.KeyCode.ENTER);
        waitForFxEvents();

        // Verificar que la fila se haya añadido
        assertEquals("The row has not been added!!!", rowCount + 1, table.getItems().size());

        // Comprobar que el título se ha guardado correctamente en la tabla
        List<MovieEntity> movies = table.getItems();
        assertEquals("The movie has not been added correctly!!!",
                movies.stream().filter(m -> m.getSinopsis().equals(movieSinopsis)).count(), 1);

    }
    
    @Test
    public void testD_deleteMovie() throws NotBoundException {
        // Obtener el número de filas antes de eliminar
        int rowCount = table.getItems().size();

        // Verificar que la tabla tiene datos
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        // Seleccionar la última fila en la tabla
        int lastRowIndex = rowCount - 1;
        Node row = lookup(".table-row-cell").nth(lastRowIndex).query();
        assertNotNull("Row is null: table has not that row.", row);
        clickOn(row);
        // Hacer clic en el botón de eliminar
        clickOn("#removeMovieBtn");

        // Verificar que aparece el cuadro de diálogo de confirmación
        verifyThat("¿Are you sure you want to remove this Movie?\nTickets associated to the movie will be automatically deleted", isVisible());
        // Confirmar la eliminación haciendo clic en el botón predeterminado (OK)
        clickOn("Aceptar");

        // Verificar que la fila ha sido eliminada
        assertEquals("The row has not been deleted!!!", rowCount - 1, table.getItems().size());
    }
}
