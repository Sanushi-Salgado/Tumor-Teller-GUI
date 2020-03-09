package com.tumorteller.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Sanushi Salgado
 *
 */
public class WelcomeController extends Application implements Initializable {

	@FXML
	private ProgressIndicator progressIndicator;

	private static Stage welcomeStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
	public void start(Stage stage) {
		log.info("Application started");
		try {
			log.info("Loading welcome window");
			Parent root = FXMLLoader.load(getClass().getResource("/views/welcome.fxml"));
			Scene scene = new Scene(root);
			welcomeStage = stage;
			welcomeStage.setScene(scene);
			welcomeStage.setTitle("Tumor Teller");
			welcomeStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/duck.jpg")));
			welcomeStage.show();
		} catch (IOException e) {
			log.error("Failed to load welcome window");
		}

		WelcomeController main = new WelcomeController();
		main.displayNextWindow(welcomeStage);
	}

	public void displayNextWindow(Stage stageToClose) {
		progressIndicator = new ProgressIndicator();
		PauseTransition delay = new PauseTransition(Duration.seconds(5));
		delay.setOnFinished(event -> {
//			final Float[] values = new Float[] { -1.0f, 0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f,
//					1.0f };
			final Float[] values = new Float[] { -1.0f, 0f, 0.1f, 0.2f};
			for (int i = 0; i < values.length; i++)
				// Set valus to the progress indicator
				progressIndicator.setProgress(values[i]);

			stageToClose.close();
			LoginController loginController = new LoginController();
			loginController.displaySignIn();
		});
		delay.play();
	}

	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
}
