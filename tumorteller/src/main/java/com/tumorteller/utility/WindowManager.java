package com.tumorteller.utility;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * @author Sanushi Salgado
 *
 */
public class WindowManager {

	private static WindowManager windowManager;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	private WindowManager() {

	}

	public static WindowManager getInstance() {
		if (windowManager == null) // if there is no instance available... create new one
			windowManager = new WindowManager();
		return windowManager;
	}

	public void displayWindow(Stage stageToClose, Stage stageToOpen, String filename) {
		// Close the previous window
		stageToClose.close();

		// ((Stage)rootPane.getScene().getWindow()).close();

		try {
			log.info("Loading " + filename + " window");
			Parent root = FXMLLoader
					.load(getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + filename + ".fxml"));
			Scene scene = new Scene(root);

			stageToOpen = new Stage();
			stageToOpen.setScene(scene);
			stageToOpen.setTitle(ApplicationConstants.APPLICATION_NAME);
			stageToOpen.initModality(Modality.APPLICATION_MODAL);
			stageToOpen.setResizable(false);
			stageToOpen.getIcons().add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			stageToOpen.show();
		} catch (IOException e) {
			log.error("Failed to load " + filename + "  window");
		}
	}

	public void displayWindow(Stage stageToOpen, String filename) {
		try {
			log.info("Loading " + filename + " window");
			Parent root = FXMLLoader
					.load(getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + filename + ".fxml"));
			Scene scene = new Scene(root);

			stageToOpen = new Stage();
			stageToOpen.setScene(scene);
			stageToOpen.setTitle(ApplicationConstants.APPLICATION_NAME);
			stageToOpen.initModality(Modality.APPLICATION_MODAL);
			stageToOpen.setResizable(false);
			stageToOpen.getIcons().add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			stageToOpen.show();
		} catch (IOException e) {
			log.error("Failed to load " + filename + "  window");
		}
	}

	public void displayWindowWithParams(Stage stageToOpen, String filename, Map<String, String> map) {
		try {
			log.info("Loading " + filename + " window");
			final FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource(ApplicationConstants.VIEWS_DIRECTORY + filename + ".fxml"));
			for (Map.Entry<String, String> entry : map.entrySet())
				fxmlLoader.getNamespace().put(entry.getKey(), entry.getValue());

			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);

			stageToOpen = new Stage();
			stageToOpen.setScene(scene);
			stageToOpen.setTitle(ApplicationConstants.APPLICATION_NAME);
			stageToOpen.initModality(Modality.APPLICATION_MODAL);
			stageToOpen.setResizable(false);
			stageToOpen.getIcons().add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			stageToOpen.show();
		} catch (IOException e) {
			log.error("Failed to load " + filename + "  window");
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
		Thread thread = new Thread(() -> {
			try {
				// Wait for 5 secs
				Thread.sleep(1500);
				if (alert.isShowing()) {
					Platform.runLater(() -> alert.close());
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		});
		thread.setDaemon(true);
		thread.start();
		Optional<ButtonType> result = alert.showAndWait();
	}

}
