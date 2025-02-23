package com.tumorteller.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	public static String databaseName;
	
	private static Database databaseInstance;

	private static MongoClient mongoClient;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	

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
		InputStream in = getClass().getResourceAsStream("/conf/database.properties"); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try {
//			reader = new FileReader( new File( getClass().getClassLoader().getResource("database.properties").getFile() ));
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


}