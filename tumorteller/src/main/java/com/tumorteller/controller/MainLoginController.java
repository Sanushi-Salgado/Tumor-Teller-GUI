package com.tumorteller.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.utility.ApplicationConstants;
import com.tumorteller.utility.WindowManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class MainLoginController extends Application implements Initializable {

	
	@FXML
	private Button btnAdmin;
	
	@FXML
	private Button btnDoctor;

	protected static Stage mainloginStage;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainloginStage = new Stage();
	}

	@FXML
	private void handleAdminLogin(ActionEvent event) {
		Stage stage = (Stage) btnAdmin.getScene().getWindow(); 
		WindowManager.getInstance().displayWindow( stage, AdminLoginController.adminLoginStage, "admin_login" );		
	}
	
	
	@FXML
	private void handleUserLogin(ActionEvent event) {
		Stage stage = (Stage) btnDoctor.getScene().getWindow(); 
		WindowManager.getInstance().displayWindow( stage, UserLoginController.userLoginStage, "user_login" );		
	}

	
	@Override
	public void start(Stage arg0) throws Exception {
		log.info("Application started");
		
		try {
			log.info("Loading main login window");
			Parent root = FXMLLoader.load(getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + "main_login.fxml"));
			Scene scene = new Scene(root);
			mainloginStage = new Stage();
			mainloginStage.setScene(scene);
			mainloginStage.setTitle(ApplicationConstants.APPLICATION_NAME);
			mainloginStage.initModality(Modality.APPLICATION_MODAL);
			mainloginStage.setResizable(false);
			mainloginStage.getIcons().add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			mainloginStage.show();
		} catch (IOException e) {
			log.error("Failed to load main login window");
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	
}
