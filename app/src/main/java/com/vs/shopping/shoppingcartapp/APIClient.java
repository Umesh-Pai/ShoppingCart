package com.vs.shopping.shoppingcartapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class APIClient {
	
	//Override this method for POST/PUT/DELETE if required
	public String invokeAPI(String endpoint, String method) {
		
		//String response = null;
		URL url = null;
		HttpURLConnection conn = null;
		String output = null;
		StringBuilder response = new StringBuilder();
		
		try {
			Log.i("productDetails::", method);
			url = new URL(endpoint);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			while ((output = br.readLine()) != null) {
				//System.out.println("output::" + output);
				response.append(output);
			}

			Log.i("response::", response.toString());

			conn.disconnect();	
		} catch (Exception e) {
			System.out.println("Exception::" + e);
		}
		
		return response.toString();
	}

}
