package com.tumorteller.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tumorteller.utility.WindowManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminPortalController implements Initializable {

	@FXML
	private Button btnManageDoctors;

	@FXML
	private Button btnManagePatients;
	
	@FXML
	private Button btnLogOut;

	protected static Stage adminPortalStage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminPortalStage = new Stage();
		DoctorController.manageDoctorsStage = new Stage();
	}

	@FXML
	private void manageDoctors() {
		WindowManager.getInstance().displayWindow( DoctorTableController.doctorTableStage, "table" );
	}

	@FXML
	private void handleLogOut() {
		Stage stage = (Stage) btnLogOut.getScene().getWindow(); 
		WindowManager.getInstance().displayWindow( stage, MainLoginController.mainloginStage, "main_login" );
	}

}
