package com.tumorteller.model;

import javafx.beans.property.SimpleStringProperty;

public class MyDataModel {

	private final SimpleStringProperty idColumnProperty = new SimpleStringProperty("");

	public MyDataModel() {
		this("");
	}

	public MyDataModel(String id) {
		setIdColumn(id);
	}

	public String getIdColumn() {
		return idColumnProperty.get();
	}

	public void setIdColumn(String id) {
		idColumnProperty.set(id);
	}
}
