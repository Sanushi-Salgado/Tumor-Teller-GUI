package com.tumorteller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.model.Doctor;
import com.tumorteller.utility.WindowManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class UserLoginController {
	@FXML
	public static Label lblUser;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnBack;

	public static Stage userLoginStage;

	public static AdminLoginController loginController;
	
	public static String doctorCurrentlyLoggedIn;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public UserLoginController() {
		btnLogin = new Button();
	}

	@FXML
	private void handleLoginButton(ActionEvent event) {
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();

		if (username.isEmpty() || password.isEmpty()) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please enter the username & password");
			log.info("Received invalid username or password");
		}

		else {
			log.info("Received valid username & password");
			Doctor user = new Doctor(username, password);

			// Check if the user is registered
			if (user.isRegisterd(username)) {
				// Check if the entered password matches the original password
				// 1. Get the user's original password from the database
				// 2. Check if the db password is equal to the entered password
				boolean isMatchingPassword = user.isCorrectPassword(password, user.getPasswordFromDb(username));
				if (isMatchingPassword) {
					log.info("User authenticated successfully");
					Stage stage = (Stage) btnLogin.getScene().getWindow(); 
					stage.close();
					doctorCurrentlyLoggedIn = username;
					WindowManager.getInstance().displayWindow(
							DoctorPortalController.doctorPortalStage, "doctor_portal");
				} else {
					log.info("Failed to authenticate user");
					WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Invalid username or password");
				}

			} else {
				log.info("User does not exist");
				WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Invalid username");
			}
		}

	}

	@FXML
	private void handleCreateAccount() {
		WindowManager.getInstance().displayWindow(MainLoginController.mainloginStage, RegisterController.registerStage,
				"register");
	}

	@FXML
	private void handleBackButton() {
		// Get a handle to the stage
		Stage stage = (Stage) btnBack.getScene().getWindow();
		WindowManager.getInstance().displayWindow(stage, MainLoginController.mainloginStage, "main_login");
	}

}
