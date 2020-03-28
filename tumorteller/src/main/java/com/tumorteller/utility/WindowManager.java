package com.tumorteller.utility;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class WindowManager {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	
	public void displayWindow(Stage stage, String filename, String windowName) {
		try {
			log.info("Loading " + windowName + " window");
			Parent root = FXMLLoader.load(getClass().getResource("/views/" + filename + ".fxml"));
			Scene scene = new Scene(root);
			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(ApplicationConstants.APPLICATION_NAME);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			stage.show();
		} catch (IOException e) {
			log.error("Failed to load " + windowName + "  window");
		}
	}

	
	public void displayAlert(AlertType alertType, String title, String message) {
		log.info("Displaying alert");
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setResizable(false);
		alert.showAndWait();
	}

	
}
