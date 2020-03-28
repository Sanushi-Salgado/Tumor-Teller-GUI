package com.tumorteller.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Sanushi Salgado
 *
 */
public class AddPatientController implements Initializable  {
	
	 
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtAge;
	
	public static Stage addPatientStage;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	@FXML
	private void handleAddPatientButton(ActionEvent event) {
		String name = txtName.getText().trim();
		int age = Integer.parseInt(txtAge.getText().trim());
		
		log.info("Name " + name);
		log.info("Age " + age);
	}
	
	 
	
}