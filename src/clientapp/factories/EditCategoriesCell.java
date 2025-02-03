package clientapp.factories;

import clientapp.model.CategoryEntity;
import clientapp.model.MovieEntity;
import java.util.Collections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import java.util.List;
import javafx.collections.FXCollections;

/**
 * Custom TableCell for editing categories using a ChoiceBox.
 */
public class EditCategoriesCell extends TableCell<MovieEntity, CategoryEntity> {

    private final ChoiceBox<CategoryEntity> categoriesCellBox = new ChoiceBox<>();

    public EditCategoriesCell(List<CategoryEntity> availableCategories) {
        categoriesCellBox.setItems(FXCollections.observableArrayList(availableCategories));
        categoriesCellBox.setOnAction(event -> {
            MovieEntity movie = getTableView().getItems().get(getIndex());
            movie.setCategories(Collections.singletonList(categoriesCellBox.getValue()));

        });
    }

    @Override
    public void startEdit() {
        super.startEdit();
        CategoryEntity currentValue = getItem();

        if (currentValue != null) {
            categoriesCellBox.setValue(currentValue); // Selecciona la categoría actual
        }

        setGraphic(categoriesCellBox);
        setText(null);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        updateText();
    }

    @Override
    public void commitEdit(CategoryEntity value) {
        super.commitEdit(value);
        updateText();
    }

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

    private void updateText() {
        CategoryEntity item = getItem();
        setText((item != null) ? item.getName() : null);
        setGraphic(null);
    }
}
