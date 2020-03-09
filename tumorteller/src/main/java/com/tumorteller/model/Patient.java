package com.tumorteller.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Sanushi Salgado
 *
 */
public class Patient {

	private String name;
	private int age;
	private Map<String, String> symptoms = new LinkedHashMap<String, String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Map<String, String> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Map<String, String> symptoms) {
		this.symptoms = symptoms;
	}

	public void addPatient() {

	}

	public void deletePatient() {

	}
	
	public void viewAllPatients() {

	}
	
	public void getPatientReport() {
		
	}
	
	public void exportPatientReport() {
		
	}

}
