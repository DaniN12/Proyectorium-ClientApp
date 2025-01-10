/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import java.util.Optional;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author 2dam
 */
public class CategoryController {
    

       private Stage stage;
    
       private Logger logger = Logger.getLogger(SignInController.class.getName());
       
       private TableColumn tbcolIcon;
       private TableColumn tbcolName;
       private TableColumn tbcolDescription;
       private TableColumn tbcolCreationDate;
       private TableColumn tbcolPegi;
       
       
       public void initialize(Parent root) {
        logger.info("Initializing InfoView stage.");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User info");
        stage.setResizable(false);
        
         tbcolIcon.setCellValueFactory(new PropertyValueFactory<>("icon"));
         tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbcolCreationDate.setCellValueFactory(new PropertyValueFactory<>("creation date"));
        tbcolPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
        
        stage.show();
    }

}
