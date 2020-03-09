package com.tumorteller.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

/**
 * @author Sanushi Salgado
 *
 */
public class AlertManager {

	
	public void displayAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();

	}
}
