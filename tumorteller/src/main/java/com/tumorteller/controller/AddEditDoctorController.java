package com.tumorteller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.model.Doctor;
import com.tumorteller.utility.WindowManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddEditDoctorController {

	@FXML
	private TextField txtFirstName;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnAddDoctor;

	@FXML
	private Button btnEditDoctor;

	protected Stage addDoctorStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public AddEditDoctorController() {
		addDoctorStage = new Stage();
		txtFirstName = new TextField();
		txtLastName = new TextField();
		txtUsername = new TextField();
		txtPassword = new PasswordField();
		btnAddDoctor = new Button();
		btnEditDoctor = new Button();
	}

	@FXML
	private void addDoctor(ActionEvent event) {
		String fName = txtFirstName.getText().trim();
		String lName = txtLastName.getText().trim();
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();

		// Display an error message if any field is empty
		if (fName.isEmpty() || lName.isEmpty() || username.isEmpty() || password.isEmpty()) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please fill all fields");
			log.info("Received incomplete registration details");
		}

		else {
			log.info("Received valid details");
			Doctor user = new Doctor(fName, lName, username, password);

			// Check if the doctor is registered before
			if (user.isRegisterd(username)) {
				WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "User already exists");
			} else {
				// Add the doctor to the database
				user.addDoctor();

				// Check if the record is added successfully added to the database

				// Close the window
				Stage stage = (Stage) btnAddDoctor.getScene().getWindow();
				stage.close();

				// If the record is added successfully
				// Add the new doctor to the doctors table - update the table
				new DoctorTableController().refreshTable();
				WindowManager.getInstance().displayAlert(AlertType.INFORMATION, "Information",
						"User added successfully");

				// Clear all text fields
				resetFields();
			}
		}
	}

	@FXML
	private void editDoctor(ActionEvent event) {
		// Update doctor details
		String fName = txtFirstName.getText().trim();
		String lName = txtLastName.getText().trim();
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();

		// Display an error message if any field is empty
		if (username.isEmpty() || lName.isEmpty() || username.isEmpty() || password.isEmpty()) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please fill all fields");
			log.info("Received incomplete registration details");
		}

		else {
			log.info("Received valid details");
			Doctor user = new Doctor(fName, lName, username, password);

			// Update doctor details in the database
			user.editDoctor(fName, lName, username, password);

			// Check if the record is updated successfully to the database

			// Close the window
			Stage stage = (Stage) btnEditDoctor.getScene().getWindow();
			stage.close();

			// If the record is edited successfully
			// Add the new details of the doctor to the doctors table - update the table
			new DoctorTableController().refreshTable();
			WindowManager.getInstance().displayAlert(AlertType.INFORMATION, "Information",
					"User details edited successfully");
			log.info("User details edited successfully");

			// Clear all text fields
			resetFields();
		}
	}
	

	@FXML
	private void resetFields() {
		txtFirstName.clear();
		txtLastName.clear();
		txtUsername.clear();
		txtPassword.clear();
	}

}
