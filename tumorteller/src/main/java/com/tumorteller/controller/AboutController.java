package com.tumorteller.controller;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tumorteller.utility.ApplicationConstants;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AboutController implements Initializable {
	
	@FXML
	private static Label lblVersion;
	
	private static Stage aboutStage;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		aboutStage = new Stage();
		getApplicationDetails();
	}

	
	private void getApplicationDetails() {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model;
		String version = "";
		try {
			model = reader.read(new FileReader("pom.xml"));
			System.out.println(model.getId());
			System.out.println(model.getGroupId());
			System.out.println(model.getArtifactId());
			version = model.getVersion();
			log.info("Version: " + version);
//			lblVersion.setText("Version " + version);
//			lblVersion.setTextFill(Color.WHITE);
		} catch (IOException | XmlPullParserException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void displayAboutWindow() {
		try {
			log.info("Loading about window");	
			Parent root = FXMLLoader.load(getClass().getResource("/views/about.fxml"));
			Scene scene = new Scene(root);
			aboutStage.setScene(scene);
			aboutStage.setTitle("About " + ApplicationConstants.APPLICATION_NAME);
			aboutStage.initModality(Modality.APPLICATION_MODAL);
			aboutStage.setResizable(false);
			aboutStage.getIcons().add(new Image(this.getClass().getResourceAsStream(ApplicationConstants.LOGO_PATH)));
			aboutStage.show();
			
			EventHandler<MouseEvent> handler = e -> aboutStage.close();
			aboutStage.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
			
		} catch (IOException e) {
			log.error("Failed to load about window");
		}
	}
	
}
