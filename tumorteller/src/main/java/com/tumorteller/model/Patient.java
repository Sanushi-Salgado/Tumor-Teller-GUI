package com.tumorteller.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.tumorteller.controller.UserLoginController;
import com.tumorteller.utility.Database;

/**
 * @author Sanushi Salgado
 *
 */
public class Patient {

	private String name;
	private String age;
	private String gender;
	private Map<String, String> symptoms = new LinkedHashMap<String, String>();
	private String predictedRegion;
	private String predictedOrgan;
	private String treatment_notes;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	public Patient() {
		
	}

	public Patient(String name, String age, String gender, Map<String, String> symptoms, String predictedRegion, String predictedOrgan, String treatment_notes) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.symptoms = symptoms;
		this.predictedRegion = predictedRegion;
		this.predictedOrgan = predictedOrgan;
		this.treatment_notes = treatment_notes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Map<String, String> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Map<String, String> symptoms) {
		this.symptoms = symptoms;
	}

	public static MongoCollection<Document> getPatientCollection() {
		Database database = Database.getInstance();
		MongoClient mongoClient = database.getConnection();
		MongoDatabase db = mongoClient.getDatabase(Database.databaseName);
		MongoCollection<Document> patientCollection = db.getCollection("patients");
		return patientCollection;
	}

	
	public static FindIterable<Document> getAllPatients() {
		Document whereQuery = new Document();
		whereQuery.put("doctor_incharge", UserLoginController.doctorCurrentlyLoggedIn);
		return getPatientCollection().find(whereQuery);
	}
	
	public void addPatient() {
		Document patient = new Document();
		patient.append("doctor_incharge", UserLoginController.doctorCurrentlyLoggedIn);
		patient.append("name", this.name);
		patient.append("age", this.age);
		patient.append("gender", this.gender);
		patient.append("symptoms", this.symptoms);
		patient.append("predicted_region", this.predictedRegion);
		patient.append("predicted_organ", this.predictedOrgan);
		patient.append("treatment", this.treatment_notes);
	
		getPatientCollection().insertOne(patient);
		log.info("Patient added successfully to the database");
	}

	public boolean deletePatient(String name) {
		Document whereQuery = new Document();
		whereQuery.put("name", name);

		MongoCollection<Document> collection = getPatientCollection();
		BasicDBObject theQuery = new BasicDBObject();
		theQuery.put("name", name);
		DeleteResult result = collection.deleteMany(theQuery);
		if (result.getDeletedCount() > 0) {
			System.out.println("The Numbers of Deleted Document(s) : " + result.getDeletedCount());
			return true;
		} else
			return false;
	}
	
	
	public void viewAllPatients() {

	}
	
	

	
}
