package com.tumorteller.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

/**
 * @author Sanushi Salgado
 *
 */
public class Database {
	
	
	private static String databaseHost;

	private static int databasePort;

	private static String databaseName;
	
	private static Database databaseInstance;

	private static MongoClient mongoClient;

	private static Logger log = LoggerFactory.getLogger("Database");
	

	private Database() {
		
	}
	
	public Database(String databaseHost, int databasePort, String databaseName, MongoClient mongoClient) {
		this.databaseHost = databaseHost;
		this.databasePort = databasePort;
		this.databaseName = databaseName;
		mongoClient = mongoClient;
	}

	public static Database getInstance(){
        if (databaseInstance == null)  //if there is no instance available... create new one
        	databaseInstance = new Database();
        
        return databaseInstance;
    }
	
	
	public MongoClient getConnection() {
		FileReader reader;

		try {
			reader = new FileReader(new File(getClass().getClassLoader().getResource("database.properties").getFile()));
			Properties properties = new Properties();
			properties.load(reader);
			databaseHost = properties.getProperty("db.hostName");
			databasePort = Integer.parseInt(properties.getProperty("db.portNumber"));
			databaseName = properties.getProperty("db.name");

		} catch (FileNotFoundException e1) {
			log.error("");
			e1.printStackTrace();
		} catch (IOException e) {
			log.error("");
			e.printStackTrace();
		}
		
		// Creating a Mongo client
		mongoClient = new MongoClient(databaseHost, databasePort);

		// Creating Credentials
		MongoCredential credential = MongoCredential.createCredential("sampleUser", databaseName, "password".toCharArray());
		log.info("Credentials ::" + credential);

		try {
			mongoClient.getAddress();
			log.info("Connected to the database successfully");
		} catch (Exception e) {
			log.error("Failed to connect to the database");
			mongoClient.close();
		}

		return mongoClient;
	}

	// public final static MongoDatabase getDatabase(String dbName) {
	// // Accessing the database
	// MongoDatabase database = mongoClient.getDatabase(dbName);
	// return database;
	// }
	//
	//// public static void main(String[] args) {
	//// new Database().getConnection();
	//// }
	//
	//
	// public static MongoCollection<Document> getCollection(String collectionName)
	// {
	// return database.getCollection(collectionName);
	// }
	//
	//

}