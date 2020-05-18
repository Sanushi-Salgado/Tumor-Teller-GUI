package com.tumorteller.model;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.tumorteller.utility.Database;


/**
 * @author Sanushi Salgado
 *
 */
public class Doctor {

	private UserRoles userRole;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public Doctor() {

	}

	public Doctor(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Doctor(String firstName, String lastName, String username, String password) {
		// this.id = id;
		userRole = UserRoles.USER;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		MongoDatabase db = mongoClient.getDatabase(Database.databaseName);
		MongoCollection<Document> userCollection = db.getCollection("users");
		return userCollection;
	}

	public boolean deleteDoctor(String username) {
		Document whereQuery = new Document();
		whereQuery.put("username", username);

		MongoCollection<Document> collection = getUserCollection();
		BasicDBObject theQuery = new BasicDBObject();
		theQuery.put("username", username);
		DeleteResult result = collection.deleteMany(theQuery);
		if (result.getDeletedCount() > 0) {
			System.out.println("The Numbers of Deleted Document(s) : " + result.getDeletedCount());
			return true;
		} else
			return false;
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
	public void addDoctor() {
		Document user = new Document();
		user.append("user_role", this.userRole.toString());
		user.append("first_name", this.firstName);
		user.append("last_name", this.lastName);
		user.append("username", this.username);
		user.append("password", hashPassword(this.password));
		getUserCollection().insertOne(user);
		log.info("Doctor added successfully to the database");
	}

	
	public static FindIterable<Document> getAllDoctors() {
		Document whereQuery = new Document();
		whereQuery.put("user_role", UserRoles.USER.toString());
		return getUserCollection().find(whereQuery);
	}


	public void editDoctor(String fname, String lname, String username, String password) {
        MongoCollection<Document> collection = getUserCollection();
        Document query = new Document();
        query.append("username", username);
        
        Document setData = new Document();
        setData.append("first_name", fname).append("last_name", lname).append("password", hashPassword(password));
        
        Document update = new Document();
        update.append("$set", setData);
        //To update single Document  
        collection.updateOne(query, update);
		
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
