package com.tumorteller.controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.utility.ApplicationConstants;
import com.tumorteller.utility.WindowManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class MainController implements Initializable {

	@FXML
	private MenuItem exitMenu;

	@FXML
	private MenuItem aboutMenu;

	@FXML
	private MenuItem manualsMenu;

	protected static Stage mainStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainStage = new Stage();
	}

	@FXML
	private void handleAddButton(ActionEvent event) {
		new WindowManager().displayWindow(AddPatientController.addPatientStage, "add", "add patient");
	}

	@FXML
	private void handleViewButton(ActionEvent event) {
		// new AddPatientController().displayAddPatientWindow();
		log.info("View button clicked");
	}

	@FXML
	private void handleExportButton(ActionEvent event) {
		// new AddPatientController().displayAddPatientWindow();
		log.info("Export button clicked");
	}

	@FXML
	private void handleExitMenu() {
		Platform.exit();
	}

	@FXML
	private void handleAboutMenu() {
		new AboutController().displayAboutWindow();
	}

	@FXML
	private void handleManualsMenu() {
		try {
			File pdfFile = new File( getClass().getClassLoader().getResource(ApplicationConstants.USER_MANUAL_PATH).toURI() );
			if (pdfFile.exists()) {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
					log.info("File opened successfully");
				} else {
					log.error("Awt Desktop is not supported!");
				}
			} else {
				log.error("File does not exist!");
			}
		} catch (Exception ex) {
			log.error("Failed to load file");
		}
	}
	
	
}
