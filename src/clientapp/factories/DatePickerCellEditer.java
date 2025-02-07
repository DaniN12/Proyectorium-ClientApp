package clientapp.factories;

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
 * A custom TableCell implementation for editing Date values with a DatePicker.
 * <p>
 * This class allows for the editing of Date values in a TableView with a DatePicker
 * control. When a user starts editing a cell, a DatePicker is displayed for them 
 * to choose a date. The cell also handles formatting and validation, such as disabling 
 * past dates in the DatePicker.
 * </p>
 * 
 * @param <S> The type of the TableView row (MovieEntity in this case).
 */
public class DatePickerCellEditer<S> extends TableCell<S, Date> {

    /**
     * The DatePicker used for editing the date.
     */
    private DatePicker datePicker;

    /**
     * The TableView containing the MovieEntity data.
     */
    private TableView<MovieEntity> tableMovies;

    /**
     * Constructor that initializes the table of movies.
     * 
     * @param tableMovies the TableView to be associated with the DatePicker editor.
     */
    public DatePickerCellEditer(TableView<MovieEntity> tableMovies) {
        this.tableMovies = tableMovies;
    }

    /**
     * Default constructor.
     */
    public DatePickerCellEditer() {}

    /**
     * Starts the editing process by creating a DatePicker and setting it in the cell.
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
     * Cancels the editing process and restores the original text and graphic.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(formatDate(getItem()));
        setGraphic(null);
    }

    /**
     * Updates the cell's text and graphic based on the current item (date).
     * If the cell is in edit mode, it shows the DatePicker. Otherwise, it formats the date as text.
     *
     * @param item the Date value to display in the cell.
     * @param empty indicates if the item is empty or not.
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
     * Creates and initializes the DatePicker for editing the date.
     * <p>
     * The DatePicker is configured to disable past dates and commit the value when
     * the focus is lost or a date is selected.
     * </p>
     */
    private void createDatePicker() {
        datePicker = new DatePicker(convertToLocalDate(getItem()));
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) { // Disable past dates
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Pink background for disabled dates
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
     * Formats a Date object into a String for display in the cell.
     * 
     * @param date the Date object to format.
     * @return a formatted String representing the date.
     */
    private String formatDate(Date date) {
        return date == null ? "" : java.text.DateFormat.getDateInstance().format(date);
    }

    /**
     * Converts a Date object to a LocalDate object.
     * 
     * @param date the Date object to convert.
     * @return the corresponding LocalDate object.
     */
    private LocalDate convertToLocalDate(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Converts a LocalDate object to a Date object.
     * 
     * @param localDate the LocalDate object to convert.
     * @return the corresponding Date object.
     */
    private Date convertToDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
