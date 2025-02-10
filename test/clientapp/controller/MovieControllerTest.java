/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.Main;
import clientapp.factories.ProviderManagerFactory;
import clientapp.interfaces.IProvider;
import clientapp.model.MovieEntity;
import clientapp.model.MovieHour;
import clientapp.model.ProviderEntity;
import java.rmi.NotBoundException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javax.ws.rs.core.GenericType;
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
    private List<ProviderEntity> availableProviders;
    private IProvider providerManager;

    public MovieControllerTest() {
        providerManager = ProviderManagerFactory.getIProvider();
        availableProviders = providerManager.findAll_XML(new GenericType<List<ProviderEntity>>() {
        });
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

        List<MovieEntity> movies = table.getItems();
        Boolean isMovie = false;
        if (table.getItems().get(0) instanceof MovieEntity) {
            isMovie = true;
        }

        assertEquals("La lista de peliculas esta vacia", movies.isEmpty(), false);
        assertEquals("La pelicula es una pelicula", isMovie, true);

    }

    @Test
    public void testB_createMovie() {

        // Obtener el número de filas antes de agregar una nueva
        int rowCount = table.getItems().size();
        String movieTitle = "";
        String movieSinopsis = "";
        Integer movieDuration = 0;
        MovieHour hour = MovieHour.HOUR_16;
        ProviderEntity provider = null;

        // Hacer clic en el botón de agregar película
        clickOn("#addMovieBtn");

        // Esperar a que la fila se agregue
        waitForFxEvents();

        // Verificar que la fila se haya añadido
        assertEquals("The row has not been added!!!", rowCount + 1, table.getItems().size());

        // Comprobar que el título se ha guardado correctamente en la tabla
        List<MovieEntity> movies = table.getItems();
        MovieEntity newMovie = movies.get(rowCount);

        assertEquals("El titulo no se añadió correctamente", movieTitle, newMovie.getSinopsis());
        assertEquals("La sinopsis no se añadió correctamente", movieSinopsis, newMovie.getSinopsis());
        assertEquals("La duración no se añadió correctamente", movieDuration, newMovie.getDuration());
        assertEquals("La hora de reproducción no se añadió correctamente", hour, newMovie.getMovieHour());
        assertEquals("La fecha de estreno no se ha añadió corrrectamente", null, newMovie.getReleaseDate());
        assertEquals("El proveedor no se añadió correctamente", provider, newMovie.getProvider());
    }

    @Test
    public void testC_UpdateMovie() {

        int rowCount = table.getItems().size();
        String movieTitle = "Movie 1";
        String movieSinopsis = "sinopsis 1";
        Integer movieDuration = 90;
        MovieHour hour = MovieHour.HOUR_20;
        ProviderEntity provider = availableProviders.get(0);
        Date newDate = Date.from(Instant.parse("2025-02-10T00:00:00.Z"));
        newDate.setHours(0);
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
        write(movieTitle);  // Escribir la sinopsis

        // Confirmar la edición (ENTER)
        type(javafx.scene.input.KeyCode.ENTER);
        waitForFxEvents();

        // Hacer doble clic en la celda "title" de la última fila
        Node sinopsisCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(2) // Suponiendo que la columna "title" es la segunda columna
                .query();
        doubleClickOn(sinopsisCell);
        waitForFxEvents(); // Esperar a que la celda entre en modo edición

        // Buscar el TextField dentro de la celda en modo edición
        Node textField2 = lookup(".text-field").query();
        clickOn(textField2); // Asegurar que el foco esté en el campo de edición
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

        Node dateCell = lookup(".table-row-cell").nth(lastRowIndex).lookup(".table-cell").nth(3).query();
        clickOn(dateCell).doubleClickOn(dateCell);
        waitForFxEvents();
        clickOn(".date-picker .arrow-button"); // Abre el DatePicker
        waitForFxEvents();
        clickOn("10");
        waitForFxEvents();
        type(KeyCode.ENTER);

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

// Seleccionar la celda de la tabla donde está el ComboBox (columna específica)
        Node comboBoxCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(5) // Reemplaza COLUMN_INDEX con el índice de la columna del ComboBox
                .query();

// Hacer doble clic para entrar en modo edición (si no se activa automáticamente)
        doubleClickOn(comboBoxCell);
        waitForFxEvents();

// Ahora buscar el ComboBox dentro de la celda activa
        Node comboBoxProvider = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".combo-box") // Buscar el ComboBox dentro de la celda
                .query();

        clickOn(provider.getName()); // Abrir el ComboBox
        waitForFxEvents();

// Seleccionar la celda de la tabla donde está el ComboBox (columna específica)
        Node movieHourCell = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".table-cell").nth(7) // Suponiendo que la columna del ComboBox es la séptima
                .query();

// Hacer doble clic para entrar en modo edición
        doubleClickOn(movieHourCell);
        waitForFxEvents();

// Buscar el ComboBox dentro de la celda en edición
        Node comboBox = lookup(".table-row-cell").nth(lastRowIndex)
                .lookup(".combo-box") // Buscar el ComboBox dentro de la celda activa
                .query();

// Seleccionar el valor correcto en el ComboBox
        clickOn(hour.name());
        type(KeyCode.ENTER);
        waitForFxEvents();

        List<MovieEntity> movie = table.getItems();
        MovieEntity updatedMovie = movie.get(lastRowIndex);

        assertEquals("El titulo no se actualizó correctamente", movieTitle, updatedMovie.getTitle());
        assertEquals("La sinopsis no se actualizó correctamente", movieSinopsis, updatedMovie.getSinopsis());
        assertEquals("La duración no se actualizó correctamente", movieDuration, updatedMovie.getDuration());
        assertEquals("La hora de la pelicula no se actualizó correctamente", hour, updatedMovie.getMovieHour());
        assertEquals("La fecha no se actualizo correctamente", newDate, updatedMovie.getReleaseDate());
        assertEquals("El proveedor no se actualizó correctamente", provider, updatedMovie.getProvider());

    }

    @Test
    public void testD_deleteMovie() throws NotBoundException {
        // Obtener el número de filas antes de eliminar
        int rowCount = table.getItems().size();
        List<MovieEntity> movie = table.getItems();
        MovieEntity deletedMovie = movie.get(rowCount - 1);

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
        assertEquals("The movie has not been deleted", movie.contains(deletedMovie), false);
    }
}
