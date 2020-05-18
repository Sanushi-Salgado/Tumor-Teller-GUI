package com.tumorteller.predictions;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlePredictions {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public String predictRegion(Map<String, Object> params) throws IOException {
		URL url = null;
		try {
			url = new URL("http://127.0.0.1:5000/predict_region");
		} catch (MalformedURLException e) {
			log.error("Incorrect url endpoint");
		}

		log.info("Received params: " + params);
		for (Map.Entry<String, Object> param : params.entrySet()) {
			System.out.println(param.getKey() + " " + param.getValue());
		}

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output;
		log.info("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			return output;
		}

		return output;
	}

	
	
	public String predictOrgan(Map<String, Object> params, String predictedRegion) throws IOException {
		URL url = null;
		try {
			url = new URL("http://127.0.0.1:5000/predict_organ");
		} catch (MalformedURLException e) {
			log.error("Incorrect url endpoint");
		}
		
		log.info("Received params: " + params);
		for (Map.Entry<String, Object> param : params.entrySet()) {
			System.out.println(param.getKey() + " " + param.getValue());
		}

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output;
		log.info("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			return output;
		}

		return output;
	}
	
	
	
}