package com.tumorteller.model;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.tumorteller.utility.Database;

/**
 * @author Sanushi Salgado
 *
 */
public class User {

	private String username;
	private String password;

	private static Logger log = LoggerFactory.getLogger("User");

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static MongoCollection<Document> getUserCollection() {
		Database database = Database.getInstance();
		MongoClient mongoClient = database.getConnection();
		MongoDatabase db = mongoClient.getDatabase("tumorteller");
		MongoCollection<Document> userCollection = db.getCollection("users");
		return userCollection;
	}

	
	// Check if the user is already registered
	public boolean isRegisterd(String username) {
		Document whereQuery = new Document();
		whereQuery.put("username", username);

		FindIterable<Document> cursor = getUserCollection().find(whereQuery);
		if (cursor.first() != null && cursor.first().containsValue(username)) {
			log.info("User already exists");
			return true;
		} else {
			log.info("User does not exist");
			return false;
		}
	}

	
	// Add a new user to the database
	public void addUser() {
		Document user = new Document();
		user.append("username", this.username);
		user.append("password", hashPassword(this.password));
		getUserCollection().insertOne(user);
		log.info("Record added successfully to the database");
	}

	
	public String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	
	public boolean isCorrectPassword(String enteredPassword, String hashedPassword) {
		if (BCrypt.checkpw(enteredPassword, hashedPassword)) {
			log.info("Received matching password");
			return true;
		} else {
			log.error("Password does not match");
			return false;
		}
	}

	// Get the password of a user from the database
	public String getPasswordFromDb(String username) {
		Document whereQuery = new Document();
		whereQuery.put("username", username);
		String pwdInDb = "";
		FindIterable<Document> cursor = getUserCollection().find(whereQuery);
		if (cursor.first() != null) {
			pwdInDb = cursor.first().getString("password");
			return pwdInDb;
		}
		return pwdInDb;  
	}

}
