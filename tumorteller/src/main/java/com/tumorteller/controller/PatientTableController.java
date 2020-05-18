package com.tumorteller.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.FindIterable;
import com.tumorteller.model.Patient;
import com.tumorteller.model.PatientDetails;
import com.tumorteller.utility.ApplicationConstants;
import com.tumorteller.utility.WindowManager;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PatientTableController {

	@FXML
	private TextField txtSearch;

	@FXML
	private  TableView<PatientDetails> patientTable = new TableView<>();

	@FXML
	private TableColumn<PatientDetails, String> columnName;

	@FXML
	private TableColumn<PatientDetails, String> columnPrediction;
	
	@FXML
	private TableColumn<PatientDetails, String> columnNotes;
	
	protected static Stage patientTableStage;

	protected static Stage addDoctorStage;

	@FXML
	private static TextField txtName;

	@FXML
	private static TextField txtUsername;

	@FXML
	private static PasswordField txtPassword;

	@FXML
	private Button btnAddDoctor;

	@FXML
	private Button btnEditDoctor;

	@FXML
	private Button btnEdit;

	private TableViewSelectionModel<PatientDetails> selectionModel;

	private static ObservableList<PatientDetails> masterData = FXCollections.observableArrayList();

	private static ObservableList<PatientDetails> filteredData = FXCollections.observableArrayList();

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public PatientTableController() {
		masterData.clear();
		filteredData.clear();
		
		txtSearch = new TextField();
		columnName = new TableColumn<>();
		columnPrediction = new TableColumn<>();

		txtName = new TextField();
		txtUsername = new TextField();
		txtPassword = new PasswordField();

		btnEdit = new Button();
		btnAddDoctor = new Button();
		btnEditDoctor = new Button();

		addDoctorStage = new Stage();

		FindIterable<Document> cursor = Patient.getAllPatients();
		for (Document document : cursor) {
			String fName = document.getString("name");
			String prediction = document.getString("predicted_organ");
			String treatment = document.getString("treatment");

			// Add all patient to the list
			masterData.add(new PatientDetails(fName, prediction, treatment));
		}

		// Initially add all data to filtered data
		filteredData.addAll(masterData);

		// Listen for changes in master data.
		// Whenever the master data changes we must also update the filtered data.
		masterData.addListener(new ListChangeListener<PatientDetails>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends PatientDetails> change) {
				updateFilteredData();
			}
		});
	}

	@FXML
	private void initialize() {
		txtSearch.setFocusTraversable(false);
		filteredData.clear();
		masterData.clear();

		if (columnName != null && columnPrediction != null && columnNotes != null && patientTable != null
				&& txtSearch != null) {
			// Initialize the doctor table
			columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnPrediction.setCellValueFactory(new PropertyValueFactory<>("prediction"));
			columnNotes.setCellValueFactory(new PropertyValueFactory<>("treatment"));

			// Get all registered doctors from the database
			FindIterable<Document> cursor = Patient.getAllPatients();
			for (Document document : cursor) {
				String fName = document.getString("name");
				String prediction = document.getString("predicted_organ");
				String treatment = document.getString("treatment");

				// Add all patient to the list
				masterData.add(new PatientDetails(fName, prediction, treatment));
			}

			// Update table
			Platform.runLater(() -> { patientTable.setItems(filteredData); } );

			// Listen for text changes in the filter text field
			txtSearch.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					updateFilteredData();
				}
			});
		}
	}
	

	private void updateFilteredData() {
		filteredData.clear();

		for (PatientDetails p : masterData) {
			if (matchesFilter(p)) {
				filteredData.add(p);
			}
		}

		// Must re-sort table after items changed
		reapplyTableSortOrder();
	}

	private boolean matchesFilter(PatientDetails p) {
		String filterString = txtSearch.getText();
		if (filterString == null || filterString.isEmpty()) {
			// Initially add all records
			return true;
		}

		String lowerCaseFilterString = filterString.toLowerCase();

		// Filter doctors by their name
		if (p.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1)
			return true;

		return false; // Does not match
	}

	private void reapplyTableSortOrder() {
		ArrayList<TableColumn<PatientDetails, ?>> sortOrder = new ArrayList<>(patientTable.getSortOrder());
		patientTable.getSortOrder().clear();
		patientTable.getSortOrder().addAll(sortOrder);
	}

	@FXML
	private void openAddDoctorWindow() {
		try {
			log.info("Loading add_doctor window");
			final FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + "add_edit_doctor.fxml"));

			fxmlLoader.getNamespace().put("labelText", "Add Doctor");
			fxmlLoader.getNamespace().put("name", "");
			fxmlLoader.getNamespace().put("username", "");
			fxmlLoader.getNamespace().put("password", "");
			fxmlLoader.getNamespace().put("isAddVisible", true);
			fxmlLoader.getNamespace().put("isEditVisible", false);
			fxmlLoader.getNamespace().put("isEditable", true);

			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);

			addDoctorStage = new Stage();
			addDoctorStage.setScene(scene);
			addDoctorStage.setTitle(ApplicationConstants.APPLICATION_NAME);
			addDoctorStage.initModality(Modality.APPLICATION_MODAL);
			addDoctorStage.setResizable(false);
			addDoctorStage.getIcons()
					.add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			addDoctorStage.show();
		} catch (IOException e) {
			log.error("Failed to load add_doctor window");
		}
	}


	@FXML
	private void deleteDoctor(ActionEvent event) {
		selectionModel = patientTable.getSelectionModel();

		// Get Selected Rows
		ObservableList<PatientDetails> selectedItems = selectionModel.getSelectedItems();
		if (selectedItems.isEmpty()) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please select a patient to delete");

		} else if (!selectedItems.isEmpty()) {
			// Display a confirmation dialog
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this patient from the system?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// If the user selects OK, then delete the user
				boolean flag = true;
				for (PatientDetails item : selectedItems) {
					String uName = item.getName();
					flag = new Patient().deletePatient(uName);
					if (!flag) {
						WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Failed to delete patient");
						break;
					}
				}

				if (flag) {
					refreshTable();
					WindowManager.getInstance().displayAlert(AlertType.INFORMATION, "Information", "Patient deleted successfully");
				}
					
			} else {
				// If the user selects CANCEL
				alert.close();
			}
		}
	}


//	@FXML
//	private void openEditDoctorWindow() {
//		selectionModel = doctorTable.getSelectionModel();
//
//		// Get Selected Rows
//		ObservableList<PatientDetails> selectedItems = selectionModel.getSelectedItems();
//		if (selectedItems.isEmpty()) {
//			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please select a user to edit");
//
//		} else if (!selectedItems.isEmpty()) {
//			for (PatientDetails item : selectedItems) {
//				try {
//					log.info("Loading add_edit_doctor window");
//					final FXMLLoader fxmlLoader = new FXMLLoader(
//							getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + "add_edit_doctor.fxml"));
//
//					fxmlLoader.getNamespace().put("labelText", "Edit Doctor");
//					fxmlLoader.getNamespace().put("fName", item.getFirstName());
//					fxmlLoader.getNamespace().put("lName", item.getLastName());
//					fxmlLoader.getNamespace().put("uName", item.getUsername());
//					fxmlLoader.getNamespace().put("pwd", item.getPassword());
//					fxmlLoader.getNamespace().put("isAddVisible", false);
//					fxmlLoader.getNamespace().put("isEditVisible", true);
//					fxmlLoader.getNamespace().put("isEditable", false);
//
//					Parent root = fxmlLoader.load();
//					Scene scene = new Scene(root);
//
//					addDoctorStage = new Stage();
//					addDoctorStage.setScene(scene);
//					addDoctorStage.setTitle(ApplicationConstants.APPLICATION_NAME);
//					addDoctorStage.initModality(Modality.APPLICATION_MODAL);
//					addDoctorStage.setResizable(false);
//					addDoctorStage.getIcons()
//							.add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
//					addDoctorStage.show();
//				} catch (IOException e) {
//					log.error("Failed to load add_edit_doctor window");
//				}
//			}
//		}
//	}

	
	@FXML
	private void openEditDoctorWindow() {
		
	}

	void refreshTable() {
		masterData.clear();
		filteredData.clear();

		// Get doctors from database
		// Get all registered doctors from the database
		FindIterable<Document> cursor = Patient.getAllPatients();
		for (Document document : cursor) {
			String fName = document.getString("name");
			String prediction = document.getString("predicted_organ");
			String treatment = document.getString("treatment");

			// Add all patient to the list
			masterData.add(new PatientDetails(fName, prediction, treatment));
		}

		// Update table
		Platform.runLater(() -> { patientTable.setItems(filteredData); } );
	}
	
	
	@FXML
	private void openAddPatientWindow() {
		WindowManager.getInstance().displayWindow(AddEditPatientController.addPatientStage, "add");
	}
}
