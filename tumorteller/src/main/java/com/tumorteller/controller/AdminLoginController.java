package com.tumorteller.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.utility.WindowManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class AdminLoginController implements Initializable {

	@FXML
	public static Label lblUser;
	
	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnLogin;

	protected static Stage adminLoginStage;
	
	@FXML
	private Button btnBack;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminLoginStage = new Stage();
	}

	@FXML
	private void handleLoginButton(ActionEvent event) {
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();
//		LoginController l = LoginController.getInstance();

		if ( username.isEmpty() || password.isEmpty() ) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please enter the username & password");
			log.info("Received invalid username or password");
		} 
		
		else if ( !username.equals("ADMIN") && !password.equals("ADMIN") ) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Invalid username or password");
			log.info("Received invalid username or password");
		}
		
		else if(username.equals("ADMIN") && password.equals("ADMIN")) {

			log.info("Received valid username & password");

//			User user = new User(username, password);

			// Check if the user is registered
//			if (user.isRegisterd(username)) {
				// Check if the entered password matches the original password
				// 1. Get the user's original password from the database
				// 2. Check if the db password is equal to the entered password
//				boolean isMatchingPassword = user.isCorrectPassword(password, user.getPasswordFromDb(username));
				
//				if (password.equals("ADMIN")) {
					log.info("User authenticated successfully");
					Stage stage = (Stage) btnLogin.getScene().getWindow(); 
					WindowManager.getInstance().displayWindow(stage, AdminPortalController.adminPortalStage, "admin_portal");
//				} else {
//					log.info("Failed to authenticate user");
//					windowManager.displayAlert(AlertType.ERROR, "Error", "Invalid password");
//				}

//			} else {
//				log.info("User does not exist");
//				WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Invalid username");
//			}

		}
	}

	@FXML
	private void handleCreateAccount() {
		WindowManager.getInstance().displayWindow(adminLoginStage, RegisterController.registerStage, "register");
	}

	
	@FXML
	private void handleBackButton() {
		// Get a handle to the stage
	    Stage stage = (Stage) btnBack.getScene().getWindow();
		WindowManager.getInstance().displayWindow(stage, MainLoginController.mainloginStage, "main_login");
	}

	
	
}
