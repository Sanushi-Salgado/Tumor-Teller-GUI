package com.tumorteller.controller;

import com.tumorteller.utility.WindowManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DoctorPortalController {
	
	@FXML
	private Button btnLogOut;
	
	protected static Stage doctorPortalStage;
	
	public DoctorPortalController() {
		doctorPortalStage = new Stage();
	}

	@FXML
	private void managePatients() {
		PatientTableController.patientTableStage = new Stage();
		WindowManager.getInstance().displayWindow( PatientTableController.patientTableStage, "patient_table" );
	}
	
	@FXML
	private void handleLogOut() {
		Stage stage = (Stage) btnLogOut.getScene().getWindow(); 
		WindowManager.getInstance().displayWindow( stage, MainLoginController.mainloginStage, "main_login" );
	}
}
