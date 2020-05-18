package com.tumorteller.controller;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.model.Patient;
import com.tumorteller.predictions.HandlePredictions;
import com.tumorteller.utility.ApplicationConstants;
import com.tumorteller.utility.WindowManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class AddEditPatientController {

	@FXML
	private ToggleGroup sex;

	@FXML
	private ToggleGroup age;

	@FXML
	private ToggleGroup histo_type;

	@FXML
	private ToggleGroup degree;

	@FXML
	private ToggleGroup bone;

	@FXML
	private ToggleGroup bone_marrow;

	@FXML
	private ToggleGroup lung;

	@FXML
	private ToggleGroup pleura;

	@FXML
	private ToggleGroup peritoneum;

	@FXML
	private ToggleGroup liver;

	@FXML
	private ToggleGroup brain;

	@FXML
	private ToggleGroup skin;

	@FXML
	private ToggleGroup neck;

	@FXML
	private ToggleGroup supraclavicular;

	@FXML
	private ToggleGroup axillar;

	@FXML
	private ToggleGroup mediastinum;

	@FXML
	private ToggleGroup abdominal;

	@FXML
	private TextField txtName;

	@FXML
	private TextArea txtNotes;

	@FXML
	private Button btnAddPatient;

	@FXML
	private Button btnGetPredictions;

	@FXML
	private Button btnEditDoctor;

	protected static Stage addPatientStage;

	private static Stage predictionResultsStage;

	private String name;
	private String treatment_notes;
	private String gender;
	private String ageGroup;
	private String histologic_type;
	private String degree_of_differece;
	private String bone_symptoms;
	private String bone_marrow_symptoms;
	private String lung_symptoms;
	private String pleura_symptoms;
	private String peritoneum_symptoms;
	private String liver_symptoms;
	private String brain_symptoms;
	private String skin_symptoms;
	private String neck_symptoms;
	private String supraclavicular_symptoms;
	private String axillar_symptoms;
	private String mediastinum_symptoms;
	private String abdominal_symptoms;

	private String regionName;
	private String organName;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public AddEditPatientController() {
		addPatientStage = new Stage();
		predictionResultsStage = new Stage();

		txtName = new TextField();
		txtNotes = new TextArea();

		btnAddPatient = new Button();
		btnGetPredictions = new Button();

		btnEditDoctor = new Button();

		// Initialize Toggle Groups
		sex = new ToggleGroup();
		age = new ToggleGroup();
		histo_type = new ToggleGroup();
		degree = new ToggleGroup();
		bone = new ToggleGroup();
		bone_marrow = new ToggleGroup();
		lung = new ToggleGroup();
		pleura = new ToggleGroup();
		peritoneum = new ToggleGroup();
		liver = new ToggleGroup();
		brain = new ToggleGroup();
		skin = new ToggleGroup();
		neck = new ToggleGroup();
		supraclavicular = new ToggleGroup();
		axillar = new ToggleGroup();
		mediastinum = new ToggleGroup();
		abdominal = new ToggleGroup();

		// if ( sex.getSelectedToggle() == null || age.getSelectedToggle() == null
		// || histo_type.getSelectedToggle() == null || degree.getSelectedToggle() ==
		// null
		// || bone.getSelectedToggle() == null || bone_marrow.getSelectedToggle() ==
		// null
		// || lung.getSelectedToggle() == null || pleura.getSelectedToggle() == null
		// || peritoneum.getSelectedToggle() == null || liver.getSelectedToggle() ==
		// null
		// || brain.getSelectedToggle() == null || skin.getSelectedToggle() == null
		// || neck.getSelectedToggle() == null || supraclavicular.getSelectedToggle() ==
		// null
		// || axillar.getSelectedToggle() == null || mediastinum.getSelectedToggle() ==
		// null
		// || abdominal.getSelectedToggle() == null)
		//
		// {
		// Platform.runLater(() -> {
		// btnAddPatient.setDisable(true);
		// btnGetPredictions.setDisable(true);
		// });
		// }
		// else if ( sex.getSelectedToggle() != null || age.getSelectedToggle() != null
		// || histo_type.getSelectedToggle() != null || degree.getSelectedToggle() !=
		// null
		// || bone.getSelectedToggle() != null || bone_marrow.getSelectedToggle() !=
		// null
		// || lung.getSelectedToggle() != null || pleura.getSelectedToggle() != null
		// || peritoneum.getSelectedToggle() != null || liver.getSelectedToggle() !=
		// null
		// || brain.getSelectedToggle() != null || skin.getSelectedToggle() != null
		// || neck.getSelectedToggle() != null || supraclavicular.getSelectedToggle() !=
		// null
		// || axillar.getSelectedToggle() != null || mediastinum.getSelectedToggle() !=
		// null
		// || abdominal.getSelectedToggle() != null)
		//
		// {
		// Platform.runLater(() -> {
		// btnAddPatient.setDisable(false);
		// btnGetPredictions.setDisable(false);
		// });
		// }

		// if(sex.getSelectedToggle() == null) {
		// btnAddPatient.setDisable(true);
		// }
		// else if(sex.getSelectedToggle() != null){
		// btnAddPatient.setDisable(false);
		// }

	}

	/**
	 * Collects all entered data from the patient form
	 */
	private void getPatientData() {
		name = txtName.getText().trim();
		treatment_notes = txtNotes.getText().trim();

		gender = ((RadioButton) sex.getSelectedToggle()).getText();
		log.info("Selected gender - " + gender);

		ageGroup = ((RadioButton) age.getSelectedToggle()).getText();
		log.info("Selected age - " + ageGroup);

		histologic_type = ((RadioButton) histo_type.getSelectedToggle()).getText();
		log.info("Selected histo type - " + histologic_type);

		degree_of_differece = ((RadioButton) degree.getSelectedToggle()).getText();
		log.info("Selected degree - " + degree_of_differece);

		bone_symptoms = ((RadioButton) bone.getSelectedToggle()).getText();
		log.info("Selected bone - " + bone_symptoms);

		bone_marrow_symptoms = ((RadioButton) bone_marrow.getSelectedToggle()).getText();
		log.info("Selected degree - " + bone_marrow_symptoms);

		lung_symptoms = ((RadioButton) lung.getSelectedToggle()).getText();
		log.info("Selected degree - " + lung_symptoms);

		pleura_symptoms = ((RadioButton) pleura.getSelectedToggle()).getText();
		log.info("Selected pleura - " + pleura_symptoms);

		peritoneum_symptoms = ((RadioButton) peritoneum.getSelectedToggle()).getText();
		liver_symptoms = ((RadioButton) liver.getSelectedToggle()).getText();
		brain_symptoms = ((RadioButton) brain.getSelectedToggle()).getText();
		skin_symptoms = ((RadioButton) skin.getSelectedToggle()).getText();
		neck_symptoms = ((RadioButton) neck.getSelectedToggle()).getText();
		supraclavicular_symptoms = ((RadioButton) supraclavicular.getSelectedToggle()).getText();
		axillar_symptoms = ((RadioButton) axillar.getSelectedToggle()).getText();
		mediastinum_symptoms = ((RadioButton) mediastinum.getSelectedToggle()).getText();

		abdominal_symptoms = ((RadioButton) abdominal.getSelectedToggle()).getText();
		log.info("Selected abdominal - " + abdominal_symptoms);

	}

	@FXML
	private void addPatient(ActionEvent event) throws IOException {
		getPatientData();

		// Add all the symptoms to a map
		Map<String, String> symptoms = new LinkedHashMap<String, String>();
		symptoms.put("histologic_type", histologic_type);
		symptoms.put("degree_of_differece", degree_of_differece);
		symptoms.put("bone", bone_symptoms);
		symptoms.put("bone_marrow", bone_marrow_symptoms);
		symptoms.put("lung", lung_symptoms);
		symptoms.put("pleura", pleura_symptoms);
		symptoms.put("peritoneum", peritoneum_symptoms);
		symptoms.put("liver", liver_symptoms);
		symptoms.put("brain", brain_symptoms);
		symptoms.put("skin", skin_symptoms);
		symptoms.put("neck", neck_symptoms);
		symptoms.put("supraclavicular", supraclavicular_symptoms);
		symptoms.put("axillar", axillar_symptoms);
		symptoms.put("mediastinum", mediastinum_symptoms);
		symptoms.put("abdominal", abdominal_symptoms);

		// Add the patient's details to the database
		Patient newPatient = new Patient(name, ageGroup, gender, symptoms, regionName, organName, treatment_notes);
		newPatient.addPatient();

		// Check if the record is added successfully added to the database

		// Close the window
		Stage stage = (Stage) btnAddPatient.getScene().getWindow();
		stage.close();

		new PatientTableController().refreshTable();

		// If the record is added successfully
		// Add the new doctor to the doctors table - update the table
		// new DoctorTableController().refreshTable();
		WindowManager.getInstance().displayAlert(AlertType.INFORMATION, "Information", "Patient added successfully");
	}

	@FXML
	private void getPredictions() throws IOException {
		// Handle exceptional cases
		// if() {
		//
		// }

		getPatientData();

		Map<String, Object> params = new LinkedHashMap<>();

		if (ageGroup.equals("< 30"))
			params.put("age", 1);
		else if (ageGroup.equals("30-59"))
			params.put("age", 2);
		else if (ageGroup.equals(">= 60"))
			params.put("age", 3);

		if (degree_of_differece.equals("Well"))
			params.put("degree-of-diffe", 1);
		else if (degree_of_differece.equals("Fairly"))
			params.put("degree-of-diffe", 2);
		else if (degree_of_differece.equals("Poorly"))
			params.put("degree-of-diffe", 3);

		if (gender.equals("Female"))
			params.put("sex_2", 2);
		else if (gender.equals("Male"))
			params.put("sex_2", 1);

		if (bone_symptoms.equals("No"))
			params.put("bone_2", 2);
		else if (bone_symptoms.equals("Yes"))
			params.put("bone_2", 1);

		if (bone_marrow_symptoms.equals("No"))
			params.put("bone-marrow_2", 2);
		else if (bone_marrow_symptoms.equals("Yes"))
			params.put("bone-marrow_2", 1);

		if (lung_symptoms.equals("No"))
			params.put("lung_2", 2);
		else if (lung_symptoms.equals("Yes"))
			params.put("lung_2", 1);

		if (pleura_symptoms.equals("No"))
			params.put("pleura_2", 2);
		else if (pleura_symptoms.equals("Yes"))
			params.put("pleura_2", 1);

		if (peritoneum_symptoms.equals("No"))
			params.put("peritoneum_2", 2);
		else if (peritoneum_symptoms.equals("Yes"))
			params.put("peritoneum_2", 1);

		if (liver_symptoms.equals("No"))
			params.put("liver_2", 2);
		else if (liver_symptoms.equals("Yes"))
			params.put("liver_2", 1);

		if (brain_symptoms.equals("No"))
			params.put("brain_2", 2);
		else if (brain_symptoms.equals("Yes"))
			params.put("brain_2", 1);

		if (skin_symptoms.equals("No"))
			params.put("skin_2", 2);
		else if (skin_symptoms.equals("Yes"))
			params.put("skin_2", 1);

		if (neck_symptoms.equals("No"))
			params.put("neck_2", 2);
		else if (neck_symptoms.equals("Yes"))
			params.put("neck_2", 1);

		if (supraclavicular_symptoms.equals("No"))
			params.put("supraclavicular_2", 2);
		else if (supraclavicular_symptoms.equals("Yes"))
			params.put("supraclavicular_2", 1);

		if (axillar_symptoms.equals("No"))
			params.put("axillar_2", 2);
		else if (axillar_symptoms.equals("Yes"))
			params.put("axillar_2", 1);

		if (mediastinum_symptoms.equals("No"))
			params.put("mediastinum_2", 2);
		else if (mediastinum_symptoms.equals("Yes"))
			params.put("mediastinum_2", 1);

		if (abdominal_symptoms.equals("No"))
			params.put("abdominal_2", 2);
		else if (abdominal_symptoms.equals("Yes"))
			params.put("abdominal_2", 1);

		if (histologic_type.equals("Epidermoid")) {
			params.put("histologic-type", 1);
		} else if (histologic_type.equals("Adeno")) {
			params.put("histologic-type", 2);
		} else if (histologic_type.equals("Anaplastic")) {
			params.put("histologic-type", 3);
		}

		params.put("small-intestine", 2);

		
		// Display an alert if all the symptoms are No
		if (bone_symptoms.equals("No") && bone_marrow_symptoms.equals("No") && lung_symptoms.equals("No")
				&& pleura_symptoms.equals("No") && peritoneum_symptoms.equals("No") && liver_symptoms.equals("No")
				&& brain_symptoms.equals("No") && skin_symptoms.equals("No") && neck_symptoms.equals("No")
				&& supraclavicular_symptoms.equals("No") && axillar_symptoms.equals("No")
				&& mediastinum_symptoms.equals("No") && abdominal_symptoms.equals("No")) {
			WindowManager.getInstance().displayAlert(AlertType.WARNING, "Warning",
					"Unable to predict tumor location! Please provide valid details.");
		} else {
			// Get the prediction results for the region
			String predictedRegion = new HandlePredictions().predictRegion(params);
			log.info("Predicted Region: " + predictedRegion);

			// Get the prediction results for the specific organ based on the region
			params.put("region", predictedRegion);
			String predictedOrgan = new HandlePredictions().predictOrgan(params, predictedRegion);
			log.info("Predicted Organ: " + predictedOrgan);

			// well, fairly, poorly - 1, 2, 3
			// epidermoid, adeno, anaplastic - 1, 2, 3
			// male, female - 1, 2
			// <30, 30-59, >=60 - 1, 2, 3

			// Display the prediction results in a separate window
			regionName = predictedRegion;
			switch (regionName) {
			case "1":
				regionName = "Upper Region";
				break;
			case "2":
				regionName = "Thoracic Region";
				break;
			case "3":
				regionName = "Inraperitoneal Region";
				break;
			case "4":
				regionName = "Exraperitoneal Region";
				break;
			}

			organName = predictedOrgan;
			switch (organName) {
			case "1":
				organName = "Lung";
				break;
			case "2":
				organName = "Head & neck";
				break;
			case "3":
				organName = "Esophagus";
				break;
			case "4":
				organName = "Thyroid";
				break;
			case "5":
				organName = "Stomach";
				break;
			case "6":
				organName = "Duodenum & small intestines";
				break;
			case "7":
				organName = "Colon";
				break;
			case "8":
				organName = "Rectum";
				break;
			case "10":
				organName = "Salivary Glands";
				break;
			case "11":
				organName = "Pancreas";
				break;
			case "12":
				organName = "Gall Bladder";
				break;
			case "13":
				organName = "Liver";
				break;
			case "14":
				organName = "Kidneys";
				break;
			case "15":
				organName = "Urinary Bladder";
				break;
			case "16":
				organName = "Testis";
				break;
			case "17":
				organName = "Prostate";
				break;
			case "18":
				organName = "Ovary";
				break;
			case "19":
				organName = "Corpus Uteri";
				break;
			case "20":
				organName = "Cervix Uteri ";
				break;
			case "21":
				organName = "Vagina ";
				break;
			case "22":
				organName = "Breast";
				break;
			}

			try {
				log.info("Loading prediction_results window");
				final FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + "prediction_results.fxml"));

				fxmlLoader.getNamespace().put("predicted_region", regionName);
				fxmlLoader.getNamespace().put("predicted_organ", organName);

				Parent root = fxmlLoader.load();
				Scene scene = new Scene(root);

				predictionResultsStage = new Stage();
				predictionResultsStage.setScene(scene);
				predictionResultsStage.setTitle(ApplicationConstants.APPLICATION_NAME);
				predictionResultsStage.initModality(Modality.APPLICATION_MODAL);
				predictionResultsStage.setResizable(false);
				predictionResultsStage.getIcons()
						.add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
				predictionResultsStage.show();
			} catch (IOException e) {
				log.error("Failed to load prediction_results window");
			}

		}

	}

	@FXML
	private void resetFields() {
		txtName.clear();
	}

}
