/**
 * 
 */
package org.koom.geocoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Serge Prodhomme
 * Special thanks to MKyong!
 */
public class SPGeocoder {

	static String URL_GOOGLE_MAPS = "http://maps.googleapis.com" + "/maps/api/geocode/json?sensor=false" + "&address=";
//	static String URL_GOOGLE_MAPS = "https://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=";
	
	/**
	 * Les clefs de la map d'adresse
	 */
	public static final String ADRESSE = "address";
	public static final String ZIPCODE = "zipcode";
	public static final String VILLE = "city";
	public static final String PAYS = "country";
	
	/**
	 * Le nom des clefs de la map de retour de coordonnees
	 */
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	
	/**
	 * 
	 * @param champsAdresse : map avec les clefs "address", "zipcode", "city", "country"
	 * @return
	 */
	public static Map<String, Double> geocoder(Map<String, String> champsAdresse) {
		
		String address = champsAdresse.get(ADRESSE); //.replaceAll("-", "&ndash;");
		String zipcode = champsAdresse.get(ZIPCODE);
		String city = champsAdresse.get(VILLE);
		String country = champsAdresse.get(PAYS);
		
		String adresse = address + "," + zipcode + "," + city + "," + country;
		
		Map<String, Double> coordonnees = new HashMap<String, Double>();

		URL url = null;
		try {
			url = new URL(URL_GOOGLE_MAPS + adresse.replaceAll(" ", "+"));

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Probleme : [code d'erreur HTTP : " + conn.getResponseCode() + "]");
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String reponseComplete = "";

			String output;
			while ((output = br.readLine()) != null) {
				reponseComplete += output;
			}

			/*
			 *  Parsing du resultat
			 *  chemins :
			 *    status : "OK"
			 *    results[0].geometry.location.lat/lng
			*/
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(reponseComplete);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray results = (JSONArray) jsonObject.get("results");

			if(!jsonObject.get("status").equals("OK")) {
				coordonnees.put(LAT, 0.0);
				coordonnees.put(LNG, 0.0);
				System.out.println("STATUS : " + jsonObject.get("status"));
				return coordonnees;
			}

			JSONObject mesresults = (JSONObject) parser.parse(((JSONObject) results.get(0)).toJSONString());
			JSONObject geometry = (JSONObject) mesresults.get("geometry");
			JSONObject location = (JSONObject) geometry.get("location");

			Double lat = (Double) location.get(LAT);
			Double lng = (Double) location.get(LNG);

			coordonnees.put(LAT, lat);
			coordonnees.put(LNG, lng);

			conn.disconnect();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			System.err.println("Format de l'URL non conforme : " + url);
		} catch (IOException e2) {
			System.out.println("Impossible de lire le fichier");
			e2.printStackTrace();
		} catch (ParseException e3) {
			System.err.println("Parsing de l'URL non conforme : " + url);
			e3.printStackTrace();
		}

		return coordonnees;
	}
	
	/**
	 *   
	 * @param adresse : l'adresse de l'emplacement, en toutes lettres et chiffres, champs separes par des virgules
	 * @return Map<String, Double> avec les deux coordonnees :
	 * 		map.get("lat") // latitude
	 * 		et map.get("lng") // longitude 
	 */
	public static Map<String, Double> geocoder(String adresse) {
		
		Map<String, Double> coordonnees = new HashMap<String, Double>();

		URL url;
		try {
			url = new URL(URL_GOOGLE_MAPS + adresse.replaceAll(" ", "+"));

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Probleme : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			StringBuffer reponseComplete = new StringBuffer();
			
			String output;
			while ((output = br.readLine()) != null) {
				reponseComplete.append(output);
			}

			/*
			 *  Parsing du resultat
			 *  chemins :
			 *    status : "OK"
			 *    results[0].geometry.location.lat/lng
			*/
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(reponseComplete.toString());
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray results = (JSONArray) jsonObject.get("results");

			if(!jsonObject.get("status").equals("OK")) {
				coordonnees.put(LAT, 0.0);
				coordonnees.put(LNG, 0.0);
				return coordonnees;
			}

			JSONObject mesresults = (JSONObject) parser.parse(((JSONObject) results.get(0)).toJSONString());
			JSONObject geometry = (JSONObject) mesresults.get("geometry");
			JSONObject location = (JSONObject) geometry.get("location");

			Double lat = (Double) location.get(LAT);
			Double lng = (Double) location.get(LNG);

			coordonnees.put(LAT, lat);
			coordonnees.put(LNG, lng);

			conn.disconnect();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return coordonnees;
	}
	
	public static boolean test(Map<String, Double> coordonnees) {
		return !(coordonnees.get(LAT).equals(new Double(0.0)) && coordonnees.get(LNG).equals(new Double(0.0)));
	}
	
	public static String fromLatLng(Map<String, Double> coordonnees) {
		return SPGeocoder.LAT + "=" + coordonnees.get(SPGeocoder.LAT) + "\t" + SPGeocoder.LNG + "=" + coordonnees.get(SPGeocoder.LNG);
	}
	
}
