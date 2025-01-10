/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controller;

import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class ProviderController {
     private Stage stage;

    private Logger logger = Logger.getLogger(SignInController.class.getName());
    
    private Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));
    private TableColumn tbcolEmail;
    private TableColumn tbcolName;
    private TableColumn tbcolPhone;
    private TableColumn tbcolContractIni;
    private TableColumn tbcolContractEnd;
    private TableColumn tbcolPrice;
    
    public void initialize(Parent root) {
        logger.info("Initializing SignIn stage.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.getIcons().add(icon);
        stage.setResizable(false);

        tbcolEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbcolPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tbcolContractIni.setCellValueFactory(new PropertyValueFactory<>("contractIni"));
        tbcolContractEnd.setCellValueFactory(new PropertyValueFactory<>("contractEnd"));
        tbcolPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        stage.show();
    }
    
    
}
