package com.tumorteller.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController implements Initializable {


    @FXML
    private TextField txtUsername;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnRegister;
    
    static Stage registerStage;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		registerStage = new Stage();
	}    
	
	
	@FXML
    private void handleButtonAction(ActionEvent event) {
//		String username = txtUsername.getText().trim();
//		String password = txtPassword.getText().trim();
//		
//		if( username.isEmpty() || password.isEmpty() ) {
//			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please fill all the fields");
//			log.info("Received invalid username or password");
//		}
//		else {
//			log.info("Received valid username & password");
//			
//			Doctor user = new Doctor(username, password);
//			
//			// Check if the user is already registered
//			if( !(user.isRegisterd(username)) ) {
//				
//				// Add the new user to the database
//				new Doctor(username, password).addDoctor(); 
//				WindowManager.getInstance().displayWindow( registerStage, AdminLoginController.adminLoginStage, "login", "sign in" );
//			}
//			else {
//				WindowManager.getInstance().displayAlert( AlertType.ERROR, "Error", "Username already exists" );
//			}	
//		}
    }

	
}