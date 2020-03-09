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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class LoginController implements Initializable {

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnLogin;

	private static Stage loginStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

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
			
			// Check if the user is registered
			if( user.isRegisterd(username) ) {
				
				// Check if the entered password matches the original password
				// 1. Get the user's original password from the database
				// 2. Check if the db password is equal to the entered password
				boolean isMatchingPassword = user.isCorrectPassword(password, user.getPasswordFromDb(username));
				if( isMatchingPassword ) {
					log.info("User authenticated successfully");
					loginStage.close();
					new MainController().displayMainWindow();
				}
				else {
					log.info("Failed to authenticate user");
					new AlertManager().displayAlert(AlertType.ERROR, "Error", "Invalid password");
				}
				
			}
			else {
				new AlertManager().displayAlert(AlertType.ERROR, "Error", "Invalid username");
			}
						
		}
    }
	
	
	@FXML
	public void handleCreateAccount() {
		loginStage.close();
		new RegisterController().displaySignUp();
	}

	
	public void displaySignIn() {
		try {
			log.info("Loading sign up window");
			Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
			Scene scene = new Scene(root);
			loginStage = new Stage();
			loginStage.setScene(scene);
			loginStage.setTitle("Tumor Teller");
			loginStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/duck.jpg")));
			loginStage.show();
		} catch (IOException e) {
			log.error("Failed to load sign up window");
		}
	}

}
