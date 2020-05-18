package com.tumorteller.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.FindIterable;
import com.tumorteller.model.Doctor;
import com.tumorteller.utility.ApplicationConstants;
import com.tumorteller.utility.WindowManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DoctorController implements Initializable {

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

//	@FXML
//	private TableView tableview;
//
//	@FXML
//	private TableColumn<String, Doctor> doctorIdCol;
//
//	@FXML
//	private TableColumn<String, Doctor> nameCol;
//
//	@FXML
//	private TableColumn<String, Doctor> usernameCol;
//
//	@FXML
//	private TableColumn<String, Doctor> passwordCol;

	@FXML
	private Button btnManageDoctors;

	@FXML
	private Button btnManagePatients;

	@FXML
	private Button btnDelete;
	
	
	@FXML
	private Button btnAdd;
	
//	ObservableList<DoctorTableModel> data;

	private TableViewSelectionModel selectionModel;

	protected static Stage manageDoctorsStage;

	protected static Stage addDoctorStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		manageDoctorsStage = new Stage();
		addDoctorStage = new Stage();
		btnDelete = new Button();
		btnAdd = new Button();
		
//		selectionModel = new TableViewSelectionModel();
		
//		if (tableview.getItems().isEmpty())
//			btnDelete.setDisable(true);
//		else
//			btnDelete.setDisable(false);
		
		
	}

	@FXML
	private void openAddDoctorWindow(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + "add_doctor.fxml"));
			Scene scene = new Scene(root);
			
			// Close the previous window
			addDoctorStage.setScene(scene);
			addDoctorStage.setTitle(ApplicationConstants.APPLICATION_NAME);
			addDoctorStage.initModality(Modality.APPLICATION_MODAL);
			addDoctorStage.setResizable(false);
			addDoctorStage.getIcons()
					.add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			addDoctorStage.show();
		} catch (IOException e) {
			log.error("Failed to load adddoctor window");
		}
	}
	

	
	
	static void populateTable() {
		final ObservableList<Doctor> allDoctors = FXCollections.observableArrayList();

		// Get all registered doctors from the database
		FindIterable<Document> cursor = Doctor.getAllDoctors();
		for (Document document : cursor) {
			// String id = cursor.first().getString("password");
			String fName = document.getString("first_name");
			String lName = document.getString("last_name");
			String username = document.getString("username");
			String password = document.getString("password");

			// Add all doctors to the list
			allDoctors.add(new Doctor(fName, lName, username, password));
		}
		
//		return allDoctors;
//		DoctorTableController.table.setItems(allDoctors);
	}

	


	@FXML
	private void deleteDoctor(ActionEvent event) {
		 ObservableList selectedItems = null;
		 
		// Get Selected Rows
		if(selectionModel != null) {
			selectedItems = selectionModel.getSelectedItems();

		// http://tutorials.jenkov.com/javafx/tableview.html
//			if (selectedItems.isEmpty()) {
//				
//			}
		}
		else
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please select the users to delete");
	}
	
	
	@FXML
	private void searchDoctor() {
		
	}
	

	private void updateTable() {

	}

}
