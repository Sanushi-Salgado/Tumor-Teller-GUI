package com.tumorteller.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DoctorDetails {
	private final StringProperty fistName;
	private final StringProperty lastName;
	private final StringProperty username;
	private final StringProperty password;

	public DoctorDetails(String fistName, String lastName, String username, String password) {
		this.fistName = new SimpleStringProperty(fistName);
		this.lastName = new SimpleStringProperty(lastName);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
	}

	public String getFirstName() {
		return fistName.get();
	}

	public String getLastName() {
		return lastName.get();
	}

	public String getUsername() {
		return username.get();
	}

	public String getPassword() {
		return password.get();
	}

	public void setFirstName(String value) {
		fistName.set(value);
	}

	public void setLastName(String value) {
		lastName.set(value);
	}

	public void setUsername(String value) {
		username.set(value);
	}

	public void setPassword(String value) {
		password.set(value);
	}

	// Property values
	public StringProperty firstNameProperty() {
		return fistName;
	}

	// Property values
	public StringProperty lastNameProperty() {
		return lastName;
	}

	public StringProperty usernameProperty() {
		return username;
	}

	public StringProperty passwordProperty() {
		return password;
	}

}
