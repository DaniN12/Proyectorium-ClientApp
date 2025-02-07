package clientapp.controller;

import clientapp.model.MovieEntity;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * A custom {@link TableCell} implementation that allows editing of Date values
 * in a {@link TableView} using a {@link DatePicker}. This class enables the
 * user to select a date from a calendar view, with additional functionality to
 * disable future dates from being selected.
 *
 * @param <S> The type of the object in the {@link TableView} (e.g., MovieEntity).
 */
public class DatePickerCellEditer<S> extends TableCell<S, Date> {

    private DatePicker datePicker;
    private TableView<MovieEntity> tableMovies;

    /**
     * Creates a new instance of DatePickerCellEditer for the specified table.
     *
     * @param tableMovies The {@link TableView} that contains the date column
     *                    for which this editor is used.
     */
    public DatePickerCellEditer(TableView<MovieEntity> tableMovies) {
        this.tableMovies = tableMovies;
    }

    /**
     * Default constructor for DatePickerCellEditer.
     */
    public DatePickerCellEditer() {
    }

    /**
     * Initializes the editor, creating a {@link DatePicker} and displaying it
     * for the user to choose a date when the cell is clicked for editing.
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    /**
     * Cancels the editing process, restoring the text representation of the date.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(formatDate(getItem()));
        setGraphic(null);
    }

    /**
     * Updates the cell display with either the selected {@link Date} or the
     * {@link DatePicker} for editing.
     *
     * @param item  The current date to display in the cell.
     * @param empty Whether the cell is empty.
     */
    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(convertToLocalDate(item));
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(formatDate(item));
                setGraphic(null);
            }
        }
    }

    /**
     * Creates a {@link DatePicker} with a custom day cell factory to disable
     * future dates, and sets up listeners to commit the selected date when the
     * user interacts with the picker.
     */
    private void createDatePicker() {
        datePicker = new DatePicker(convertToLocalDate(getItem()));
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        // Disable future dates in the DatePicker
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Style for disabled dates
                        }
                    }
                };
            }
        };

        datePicker.setDayCellFactory(dayCellFactory);

        // Commit the value when focus is lost or a date is selected
        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(convertToDate(datePicker.getValue()));
            }
        });

        datePicker.setOnAction(e -> commitEdit(convertToDate(datePicker.getValue())));
    }

    /**
     * Formats the {@link Date} into a string representation for display in the cell.
     *
     * @param date The {@link Date} to format.
     * @return The formatted date string.
     */
    private String formatDate(Date date) {
        return date == null ? "" : java.text.DateFormat.getDateInstance().format(date);
    }

    /**
     * Converts a {@link Date} to a {@link LocalDate} for use in the {@link DatePicker}.
     *
     * @param date The {@link Date} to convert.
     * @return The corresponding {@link LocalDate}.
     */
    private LocalDate convertToLocalDate(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Converts a {@link LocalDate} to a {@link Date} for storing in the entity.
     *
     * @param localDate The {@link LocalDate} to convert.
     * @return The corresponding {@link Date}.
     */
    private Date convertToDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
