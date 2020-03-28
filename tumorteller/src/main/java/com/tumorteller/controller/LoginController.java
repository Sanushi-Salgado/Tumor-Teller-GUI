package com.tumorteller.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.model.User;
import com.tumorteller.utility.WindowManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class LoginController extends Application implements Initializable {

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnLogin;

	public static Stage loginStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	private static WindowManager windowManager;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	@FXML
	private void handleButtonAction(ActionEvent event) {
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();

		if (username.isEmpty() || password.isEmpty()) {
			windowManager.displayAlert(AlertType.ERROR, "Error", "Please fill all the fields");
			log.info("Received invalid username or password");
		} else {

			log.info("Received valid username & password");

			User user = new User(username, password);

			// Check if the user is registered
			if (user.isRegisterd(username)) {

				// Check if the entered password matches the original password
				// 1. Get the user's original password from the database
				// 2. Check if the db password is equal to the entered password
				boolean isMatchingPassword = user.isCorrectPassword(password, user.getPasswordFromDb(username));
				if (isMatchingPassword) {
					log.info("User authenticated successfully");
					loginStage.close();
					windowManager.displayWindow(MainController.mainStage, "main", "main");
				} else {
					log.info("Failed to authenticate user");
					windowManager.displayAlert(AlertType.ERROR, "Error", "Invalid password");
				}

			} else {
				windowManager.displayAlert(AlertType.ERROR, "Error", "Invalid username");
			}

		}
	}

	@FXML
	private void handleCreateAccount() {
		loginStage.close();
		windowManager.displayWindow(RegisterController.registerStage, "register", "sign up");
	}


	@Override
	public void start(Stage arg0) throws Exception {
		log.info("Application started");
		loginStage = new Stage();
		windowManager = new WindowManager();
		RegisterController.registerStage = new Stage();
		windowManager.displayWindow(LoginController.loginStage, "login", "sign in");
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	
}
