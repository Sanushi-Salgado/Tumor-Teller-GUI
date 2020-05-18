package com.tumorteller.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.FindIterable;
import com.tumorteller.model.Doctor;
import com.tumorteller.model.DoctorDetails;
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

public class DoctorTableController {

	@FXML
	private TextField txtSearch;

	@FXML
	private  TableView<DoctorDetails> doctorTable = new TableView<>();

	@FXML
	private TableColumn<DoctorDetails, String> columnFirstName;

	@FXML
	private TableColumn<DoctorDetails, String> columnLastName;

	@FXML
	private TableColumn<DoctorDetails, String> columnUsername;

	protected static Stage doctorTableStage;

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

	private TableViewSelectionModel<DoctorDetails> selectionModel;

	private static ObservableList<DoctorDetails> masterData = FXCollections.observableArrayList();

	private static ObservableList<DoctorDetails> filteredData = FXCollections.observableArrayList();

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public DoctorTableController() {
		masterData.clear();
		filteredData.clear();
		
		txtSearch = new TextField();
		columnFirstName = new TableColumn<>();
		columnLastName = new TableColumn<>();
		columnUsername = new TableColumn<>();
		columnFirstName.setSortable(true);

		txtName = new TextField();
		txtUsername = new TextField();
		txtPassword = new PasswordField();

		btnEdit = new Button();
		btnAddDoctor = new Button();
		btnEditDoctor = new Button();

		addDoctorStage = new Stage();

		FindIterable<Document> cursor = Doctor.getAllDoctors();
		for (Document document : cursor) {
			// String id = cursor.first().getString("password");
			String fName = document.getString("first_name");
			String lName = document.getString("last_name");
			String username = document.getString("username");
			String password = document.getString("password");

			// Add all doctors to the list
			masterData.add(new DoctorDetails(fName, lName, username, password));
		}

		// Initially add all data to filtered data
		filteredData.addAll(masterData);

		// Listen for changes in master data.
		// Whenever the master data changes we must also update the filtered data.
		masterData.addListener(new ListChangeListener<DoctorDetails>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends DoctorDetails> change) {
				updateFilteredData();
			}
		});
	}

	@FXML
	private void initialize() {
		txtSearch.setFocusTraversable(false);
		filteredData.clear();
		masterData.clear();

		if (columnFirstName != null && columnLastName != null && columnUsername != null && doctorTable != null
				&& txtSearch != null) {
			// Initialize the doctor table
			columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
			columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
			columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

			// Get all registered doctors from the database
			FindIterable<Document> cursor = Doctor.getAllDoctors();
			for (Document document : cursor) {
				String fName = document.getString("first_name");
				String lName = document.getString("last_name");
				String username = document.getString("username");
				String password = document.getString("password");

				// Add all doctors to the list
				masterData.add(new DoctorDetails(fName, lName, username, password));
			}

			// Update table
			Platform.runLater(() -> { doctorTable.setItems(filteredData); } );

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

		for (DoctorDetails p : masterData) {
			if (matchesFilter(p)) {
				filteredData.add(p);
			}
		}

		// Must re-sort table after items changed
		reapplyTableSortOrder();
	}

	private boolean matchesFilter(DoctorDetails person) {
		String filterString = txtSearch.getText();
		if (filterString == null || filterString.isEmpty()) {
			// Initially add all records
			return true;
		}

		String lowerCaseFilterString = filterString.toLowerCase();

		// Filter doctors by their name
		if (person.getFirstName().toLowerCase().indexOf(lowerCaseFilterString) != -1)
			return true;
		else if (person.getLastName().toLowerCase().indexOf(lowerCaseFilterString) != -1)
			return true;

		return false; // Does not match
	}

	private void reapplyTableSortOrder() {
		ArrayList<TableColumn<DoctorDetails, ?>> sortOrder = new ArrayList<>(doctorTable.getSortOrder());
		doctorTable.getSortOrder().clear();
		doctorTable.getSortOrder().addAll(sortOrder);
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
		selectionModel = doctorTable.getSelectionModel();

		// Get Selected Rows
		ObservableList<DoctorDetails> selectedItems = selectionModel.getSelectedItems();
		if (selectedItems.isEmpty()) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please select a user to delete");

		} else if (!selectedItems.isEmpty()) {
			// Display a confirmation dialog
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this user from the system?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// If the user selects OK, then delete the user
				boolean flag = true;
				for (DoctorDetails item : selectedItems) {
					String uName = item.getUsername();
					flag = new Doctor().deleteDoctor(uName);
					if (!flag) {
						WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Failed to delete user");
						break;
					}
				}

				if (flag) {
					refreshTable();
					WindowManager.getInstance().displayAlert(AlertType.INFORMATION, "Information", "User deleted successfully");
				}
					
			} else {
				// If the user selects CANCEL
				alert.close();
			}
		}
	}


	@FXML
	private void openEditDoctorWindow() {
		selectionModel = doctorTable.getSelectionModel();

		// Get Selected Rows
		ObservableList<DoctorDetails> selectedItems = selectionModel.getSelectedItems();
		if (selectedItems.isEmpty()) {
			WindowManager.getInstance().displayAlert(AlertType.ERROR, "Error", "Please select a user to edit");

		} else if (!selectedItems.isEmpty()) {
			for (DoctorDetails item : selectedItems) {
				try {
					log.info("Loading add_edit_doctor window");
					final FXMLLoader fxmlLoader = new FXMLLoader(
							getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + "add_edit_doctor.fxml"));

					fxmlLoader.getNamespace().put("labelText", "Edit Doctor");
					fxmlLoader.getNamespace().put("fName", item.getFirstName());
					fxmlLoader.getNamespace().put("lName", item.getLastName());
					fxmlLoader.getNamespace().put("uName", item.getUsername());
					fxmlLoader.getNamespace().put("pwd", item.getPassword());
					fxmlLoader.getNamespace().put("isAddVisible", false);
					fxmlLoader.getNamespace().put("isEditVisible", true);
					fxmlLoader.getNamespace().put("isEditable", false);

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
					log.error("Failed to load add_edit_doctor window");
				}
			}
		}
	}


	void refreshTable() {
		masterData.clear();
		filteredData.clear();

		// Get doctors from database
		// Get all registered doctors from the database
		FindIterable<Document> cursor = Doctor.getAllDoctors();
		for (Document document : cursor) {
			String fName = document.getString("first_name");
			String lName = document.getString("last_name");
			String username = document.getString("username");
			String password = document.getString("password");

			// Add all doctors to the list
			masterData.add(new DoctorDetails(fName, lName, username, password));
		}

		// Update table
		Platform.runLater(() -> { doctorTable.setItems(filteredData); } );
	}
}
