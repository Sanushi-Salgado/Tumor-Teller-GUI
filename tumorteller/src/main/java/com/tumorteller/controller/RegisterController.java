package com.tumorteller.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.model.User;
import com.tumorteller.utility.AlertManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RegisterController implements Initializable {


    @FXML
    private TextField txtUsername;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnRegister;
    
    private static Stage registerStage;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 
	}    
	
	
	@FXML
    private void handleButtonAction(ActionEvent event) {
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();
		
		if( username.isEmpty() || password.isEmpty() ) {
			new AlertManager().displayAlert(AlertType.ERROR, "Error", "Please fill all the fields");
			log.info("Received invalid username or password");
		}
		else {
			log.info("Received valid username & password");
			
			User user = new User(username, password);
			
			// Check if the user is already registered
			if( !(user.isRegisterd(username)) ) {
				
				// Add the new user to the database
				new User(username, password).addUser(); 
				
				registerStage.close();
				new LoginController().displaySignIn();
			}
			else {
				new AlertManager().displayAlert(AlertType.ERROR, "Error", "Username already exists");
			}	
		}
    }

	
	public void displaySignUp() {
		try {
			log.info("Loading sign up window");
			Parent root = FXMLLoader.load(getClass().getResource("/views/register.fxml"));
			Scene scene = new Scene(root);
			registerStage = new Stage();
			registerStage.setScene(scene);
			registerStage.setTitle("Tumor Teller");
			registerStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/duck.jpg")));
			registerStage.show();
		} catch (IOException e) {
			log.error("Failed to load sign up window");
		}
	}
}