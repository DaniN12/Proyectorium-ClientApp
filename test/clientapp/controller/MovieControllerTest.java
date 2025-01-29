/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import clientapp.model.MovieEntity;
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
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
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
    public void testA_createMovie() {

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

}
