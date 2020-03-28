package com.tumorteller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sanushi Salgado
 *
 */
public class Patient {

	private String name;
	private int age;
	private Map<String, String> symptoms = new LinkedHashMap<String, String>();
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

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
