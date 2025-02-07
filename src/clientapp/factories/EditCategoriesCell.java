package clientapp.factories;

import clientapp.model.CategoryEntity;
import clientapp.model.MovieEntity;
import java.util.Collections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import java.util.List;
import javafx.collections.FXCollections;

/**
 * A custom TableCell implementation for editing categories of movies using a ChoiceBox.
 * <p>
 * This class allows users to edit the category of a movie in a TableView by using a
 * ChoiceBox that presents a list of available categories. The selected category is
 * then applied to the movie entity in the table.
 * </p>
 */
public class EditCategoriesCell extends TableCell<MovieEntity, CategoryEntity> {

    /**
     * The ChoiceBox used for selecting a category.
     */
    private final ChoiceBox<CategoryEntity> categoriesCellBox = new ChoiceBox<>();

    /**
     * Constructor to create a new EditCategoriesCell with the list of available categories.
     * <p>
     * The ChoiceBox is populated with the provided list of categories, and an action handler
     * is attached to update the category of the movie entity when a new category is selected.
     * </p>
     * 
     * @param availableCategories a list of available categories to populate the ChoiceBox.
     */
    public EditCategoriesCell(List<CategoryEntity> availableCategories) {
        categoriesCellBox.setItems(FXCollections.observableArrayList(availableCategories));
        categoriesCellBox.setOnAction(event -> {
            MovieEntity movie = getTableView().getItems().get(getIndex());
            movie.setCategories(Collections.singletonList(categoriesCellBox.getValue()));
        });
    }

    /**
     * Starts the editing process by showing the ChoiceBox with the current category of the movie.
     * If a category is already selected, it will be shown in the ChoiceBox.
     */
    @Override
    public void startEdit() {
        super.startEdit();
        CategoryEntity currentValue = getItem();

        if (currentValue != null) {
            categoriesCellBox.setValue(currentValue); // Set the current category as the selected value
        }

        setGraphic(categoriesCellBox);
        setText(null);
    }

    /**
     * Cancels the editing process and restores the text of the cell to show the current category.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        updateText();
    }

    /**
     * Commits the edit when a category is selected, applying the selected value and restoring the text.
     * 
     * @param value the new selected category to commit to the movie.
     */
    @Override
    public void commitEdit(CategoryEntity value) {
        super.commitEdit(value);
        updateText();
    }

    /**
     * Updates the item shown in the cell based on the current state.
     * If the item is empty or null, the cell is cleared. Otherwise, it displays the category name.
     *
     * @param item the current category entity to be displayed.
     * @param empty indicates whether the item is empty.
     */
    @Override
    protected void updateItem(CategoryEntity item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            updateText();
        }
    }

    /**
     * Updates the text of the cell to display the name of the current category.
     * If the category is null, the text will be set to null.
     */
    private void updateText() {
        CategoryEntity item = getItem();
        setText((item != null) ? item.getName() : null);
        setGraphic(null);
    }
}
