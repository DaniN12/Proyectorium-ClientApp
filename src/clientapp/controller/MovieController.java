/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import clientapp.factories.MovieFactory;
import clientapp.interfaces.IMovie;
import clientapp.model.MovieEntity;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author enzo
 */
public class MovieController {

    @FXML
    private TableColumn titleColumn;

    @FXML
    private TableColumn imgColumn;

    @FXML
    private TableColumn durationColumn;

    @FXML
    private TableColumn sinopsisColumn;

    @FXML
    private TableColumn rDateColumn;

    @FXML
    private TableColumn movieHourClolumn;

    @FXML
    private TableColumn providerColumn;

    @FXML
    private TableColumn categoriesColumn;

    private Stage stage;

    private IMovie movieManager;

    @FXML
    private TableView moviesTbv;

    public void initialize(Parent root) {

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Movie");

        movieManager = MovieFactory.getIMovie();

        try {
            ObservableList<MovieEntity> movies = FXCollections.observableArrayList(movieManager.findAll(new GenericType<List<MovieEntity>>() {
            }));
            
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            sinopsisColumn.setCellValueFactory(new PropertyValueFactory<>("sinopsis"));
            rDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
            movieHourClolumn.setCellValueFactory(new PropertyValueFactory<>("movieHour"));
            // providerColumn.setCellValueFactory(new PropertyValueFactory<>("provider"));
            // categoriesColumn.setCellValueFactory(new PropertyValueFactory<>("categories"));

            moviesTbv.setItems(movies);

        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }

        stage.show();
    }
    
    public void handleUsersTableSelectionChange(ObservableValue observable, Object oldValue, Object newValue){
        
        if(newValue!= null){
            MovieEntity movie = (MovieEntity) newValue;
            
        }
    }

    public TableColumn getTitleColumn() {
        return titleColumn;
    }

    public void setTitleColumn(TableColumn titleColumn) {
        this.titleColumn = titleColumn;
    }

    public TableColumn getDurationColumn() {
        return durationColumn;
    }

    public void setDurationColumn(TableColumn durationColumn) {
        this.durationColumn = durationColumn;
    }

    public TableColumn getSinopsisColumn() {
        return sinopsisColumn;
    }

    public void setSinopsisColumn(TableColumn sinopsisColumn) {
        this.sinopsisColumn = sinopsisColumn;
    }

    public TableColumn getMovieHourClolumn() {
        return movieHourClolumn;
    }

    public void setMovieHourClolumn(TableColumn movieHourClolumn) {
        this.movieHourClolumn = movieHourClolumn;
    }

    public TableColumn getProviderColumn() {
        return providerColumn;
    }

    public void setProviderColumn(TableColumn providerColumn) {
        this.providerColumn = providerColumn;
    }

    public TableColumn getCategoriesColumn() {
        return categoriesColumn;
    }

    public void setCategoriesColumn(TableColumn categoriesColumn) {
        this.categoriesColumn = categoriesColumn;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public IMovie getMovieManager() {
        return movieManager;
    }

    public void setMovieManager(IMovie movieManager) {
        this.movieManager = movieManager;
    }

    public TableColumn getImgColumn() {
        return imgColumn;
    }

    public void setImgColumn(TableColumn imgColumn) {
        this.imgColumn = imgColumn;
    }

    public TableColumn getrDateColumn() {
        return rDateColumn;
    }

    public void setrDateColumn(TableColumn rDateColumn) {
        this.rDateColumn = rDateColumn;
    }

    public TableView getMoviesTbv() {
        return moviesTbv;
    }

    public void setMoviesTbv(TableView moviesTbv) {
        this.moviesTbv = moviesTbv;
    }

}
