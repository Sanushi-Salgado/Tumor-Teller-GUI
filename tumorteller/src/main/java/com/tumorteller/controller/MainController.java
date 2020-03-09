package com.tumorteller.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class MainController {
	
	public static Stage mainStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	
	public MainController() {
		mainStage = new Stage();
	}


	public void displayMainWindow() {
		try {
			log.info("Loading sign in window");
			Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("Tumor Teller");
			mainStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/duck.jpg")));
			mainStage.show();
		} catch (IOException e) {
			log.error("Failed to load sign in window");
		}
	}

	
}
