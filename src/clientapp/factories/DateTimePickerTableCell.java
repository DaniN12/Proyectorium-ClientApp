package clientapp.factories;

import clientapp.model.MovieHour;
import clientapp.model.TicketEntity;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateTimePickerTableCell extends TableCell<TicketEntity, LocalDateTime> {

    private final DatePicker datePicker;
    private final ComboBox<String> timePicker;
    private final HBox container;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public DateTimePickerTableCell() {
        // Inicializamos los componentes
        datePicker = new DatePicker();
        timePicker = new ComboBox<>();
        container = new HBox(5, datePicker, timePicker);

        // Configuración de DatePicker
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.format(DATE_FORMATTER) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, DATE_FORMATTER) : null;
            }
        });

        // Configuración de TimePicker con opciones generadas desde MovieHour
        timePicker.getItems().addAll(generateTimeOptions());
        timePicker.setEditable(false);
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            LocalDateTime currentValue = getItem();

            // Configura los valores actuales
            if (currentValue != null) {
                datePicker.setValue(currentValue.toLocalDate());
                timePicker.setValue(currentValue.toLocalTime().format(TIME_FORMATTER));
            } else {
                datePicker.setValue(LocalDate.now());
                timePicker.setValue(MovieHour.HOUR_16.getHour()); // Valor predeterminado
            }

            setGraphic(container);
            setText(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(formatDateTime(getItem()));
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalDateTime item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (item != null) {
                    datePicker.setValue(item.toLocalDate());
                    timePicker.setValue(item.toLocalTime().format(TIME_FORMATTER));
                }
                setGraphic(container);
                setText(null);
            } else {
                setText(formatDateTime(item));
                setGraphic(null);
            }
        }
    }

    @Override
    public void commitEdit(LocalDateTime newValue) {
        if (newValue != null) {
            // Convierte LocalDateTime a String y guarda el valor en la celda correspondiente
            TicketEntity ticket = getTableView().getItems().get(getIndex());
            ticket.setBuyDateFromLocalDateTime(newValue);
            super.commitEdit(newValue);
        }
    }

    /**
     * Obtiene y valida el valor editado del DatePicker y TimePicker.
     */
    private LocalDateTime getEditedValue() {
        LocalDate date = datePicker.getValue();
        String time = timePicker.getValue();

        if (date != null && time != null) {
            try {
                LocalTime parsedTime = LocalTime.parse(time, TIME_FORMATTER);
                return LocalDateTime.of(date, parsedTime);
            } catch (Exception e) {
                // Manejo de errores en caso de un valor de tiempo no válido
                System.err.println("Invalid time format: " + time);
            }
        }
        return null;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : "";
    }

    private List<String> generateTimeOptions() {
        List<String> options = new ArrayList<>();
        // Usamos las horas definidas en el enum MovieHour
        for (MovieHour hour : MovieHour.values()) {
            options.add(hour.getHour());
        }
        return options;
    }
}
