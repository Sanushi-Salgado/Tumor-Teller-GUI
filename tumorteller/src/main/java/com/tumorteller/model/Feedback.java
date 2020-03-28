package com.tumorteller.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sanushi Salgado
 *
 */
public class Feedback {

	private String title;
	private String description;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void sendFeedback(String title, String description) {

	}

}
