package com.tumorteller.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientDetails {
	private final StringProperty name;
	private final StringProperty prediction;
	private final StringProperty treatment;

	public PatientDetails(String name, String prediction, String treatment) {
		this.name = new SimpleStringProperty(name);
		this.prediction = new SimpleStringProperty(prediction);
		this.treatment = new SimpleStringProperty(treatment);
	}

	public String getName() {
		return name.get();
	}

	public String getPrediction() {
		return prediction.get();
	}
	
	public String getTreatment() {
		return treatment.get();
	}

	public void setName(String value) {
		name.set(value);
	}

	public void setPrediction(String value) {
		prediction.set(value);
	}
	
	public void setTreatment(String value) {
		treatment.set(value);
	}


	// Property values
	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty predictionProperty() {
		return prediction;
	}

	public StringProperty treatmentProperty() {
		return treatment;
	}

	
}
