package clientapp.factories;

import clientapp.model.TicketEntity;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * Custom TableCell implementation for editing Date values using a DatePicker in a TableView.
 * <p>
 * This class is designed to be used in a {@link TableView} for displaying and editing 
 * date values associated with {@link TicketEntity}. It provides a DatePicker as an editor 
 * when the cell is in edit mode, allowing users to select dates.
 * </p>
 */
public class TicketDatePickerTableCell extends TableCell<TicketEntity, Date> {

    /**
     * The DatePicker control used for selecting a date.
     */
    private DatePicker datePicker;

    /**
     * The TableView that this cell belongs to.
     */
    private TableView<TicketEntity> tableMovies;

    /**
     * Constructs a TicketDatePickerTableCell for the specified TableView.
     * 
     * @param tableMovies the TableView to which this cell belongs.
     */
    public TicketDatePickerTableCell(TableView<TicketEntity> tableMovies) {
        this.tableMovies = tableMovies;
    }

    /**
     * Default constructor.
     */
    public TicketDatePickerTableCell() {
    }

    /**
     * Starts the editing mode of the cell and displays the DatePicker.
     * 
     * This method is invoked when the user starts editing a cell. It creates and displays 
     * a DatePicker for the user to select a date.
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
     * Cancels the editing mode and restores the original text and graphic for the cell.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(formatDate(getItem()));
        setGraphic(null);
    }

    /**
     * Updates the cell with the current item. This method updates the cell's visual representation 
     * based on whether the cell is in editing mode or not.
     * 
     * @param item  the current value of the cell (Date).
     * @param empty whether the cell is empty.
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
     * Creates a DatePicker control for editing the date.
     * <p>
     * This method configures the DatePicker, sets the initial date value, disables 
     * future dates, and commits the selected date when the user finishes editing.
     * </p>
     */
    private void createDatePicker() {
        datePicker = new DatePicker(convertToLocalDate(getItem()));
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        // Disable future dates
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
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
     * Formats the given Date object into a string for display in the cell.
     * 
     * @param date the Date object to format.
     * @return a string representing the formatted date.
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
