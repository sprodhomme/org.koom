package org.koom;

/**
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.junit.Test;
import org.koom.geocoder.SPGeocoder;

/**
 * @author Serge Prodhomme
 * 
 */
public class ReverseGeocoderTest {

	String fichier = "src/test/resources/adresses.xls";
	static Sheet wrksheet;
	static Workbook wrkbook = null;
	static Hashtable<String, Integer> dict = new Hashtable<String, Integer>();

	@Test
	public void testerLaClasse() {
		// Juste verifier que l'adresse est arrivee a Google Maps Geocoder
		Map<String, Double> coordonnees = SPGeocoder.geocoder("84 Quai de Jemmapes, 75010, Paris, France");
		Assert.assertTrue(SPGeocoder.test(coordonnees));

		// Avec le detail des champs de l'adresse
		Map<String, String> champsAdresse = new HashMap<String, String>();
		champsAdresse.put(SPGeocoder.ADRESSE, "84 Quai de Jemmapes");
		champsAdresse.put(SPGeocoder.ZIPCODE, "75010");
		champsAdresse.put(SPGeocoder.VILLE, "Paris");
		champsAdresse.put(SPGeocoder.PAYS, "France");
		Assert.assertTrue(SPGeocoder.test(SPGeocoder.geocoder(champsAdresse)));
		
		// Encore un cas de test
		Map<String, Double> coordonnees2 = SPGeocoder.geocoder("5 Place de l'Hotel de ville, 75004 Paris, France");
		Assert.assertTrue(SPGeocoder.test(coordonnees2));
//		System.out.println(SPGeocoder.fromLatLng(coordonnees2));
		Assert.assertTrue(coordonnees2.get(SPGeocoder.LAT).equals(new Double(48.85663719999999)));
		Assert.assertTrue(coordonnees2.get(SPGeocoder.LNG).equals(new Double(2.3509291)));
	}

	@Test
	public void jxl() throws BiffException, IOException {

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("ISO-8859-1");
		ws.setLocale(Locale.FRANCE);

		// Initialize
		wrkbook = Workbook.getWorkbook(new File(fichier), ws);
		// For Demo purpose the excel sheet path is hardcoded, but not
		// recommended :)
		wrksheet = wrkbook.getSheet(0);

		for (int ligne = 1; ligne < RowCount(); ligne++) {
			String province = ReadCell(0, ligne);
			String adresse = ReadCell(1, ligne);
			String zipcode = ReadCell(2, ligne);
			String ville = ReadCell(3, ligne);
			String pays = ReadCell(4, ligne);
			String telephone = ReadCell(5, ligne);
			// lat : 6
			// lng : 7

			// System.out.println("Ligne : " + ligne + " | adresse : " + adresse
			// + " zipcode : " + zipcode + " ville : " + ville);

			String adresseComplete = adresse + "," + zipcode.trim() + "," + ville + "," + pays;
//			System.out.println("adresseComplete : " + adresseComplete);

			Map<String, Double> coordonnees = SPGeocoder.geocoder(adresseComplete);

//			System.out.println(SPGeocoder.fromLatLng(coordonnees));

			String adresseFormattee = "";
			adresseFormattee += province;
			adresseFormattee += "\t";
			adresseFormattee += adresse;
			adresseFormattee += "\t";
			adresseFormattee += zipcode;
			adresseFormattee += "\t";
			adresseFormattee += ville;
			adresseFormattee += "\t";
			adresseFormattee += pays;
			adresseFormattee += "\t";
			adresseFormattee += telephone;
			adresseFormattee += "\t";
			adresseFormattee += coordonnees.get(SPGeocoder.LAT);
			adresseFormattee += "\t";
			adresseFormattee += coordonnees.get(SPGeocoder.LNG);
			
			System.out.println(adresseFormattee);
			
			Assert.assertTrue(SPGeocoder.test(coordonnees));
		}

		// fermeture du workbook
		wrkbook.close();
	}

	// Returns the Number of Rows
	public static int RowCount() {
		return wrksheet.getRows();
	}

	// Returns the Cell value by taking row and Column values as argument
	public static String ReadCell(int column, int row) {
		return wrksheet.getCell(column, row).getContents();
	}

	// Create Column Dictionary to hold all the Column Names
	public static void ColumnDictionary() {
		// Iterate through all the columns in the Excel sheet and store the
		// value in Hashtable
		for (int col = 0; col < wrksheet.getColumns(); col++) {
			dict.put(ReadCell(col, 0), col);
		}
	}

	// Read Column Names
	public static int GetCell(String colName) {
		try {
			int value;
			value = ((Integer) dict.get(colName)).intValue();
			return value;
		} catch (NullPointerException e) {
			return (0);
		}
	}

	@Test
	public void paris() {
		String adresse = "1 rue de Rivoli, 75004, Paris, France";
		Map<String, Double> coordonnees = SPGeocoder.geocoder(adresse);
		Assert.assertTrue(SPGeocoder.test(coordonnees));
		System.out.println(adresse + "\n" + SPGeocoder.fromLatLng(coordonnees));
	}

//	private Map<String, Double> geocoder(String adresse) {
//
//		Map<String, Double> coordonnees = new HashMap<String, Double>();
//
//		URL url = null;
//		try {
//			url = new URL("http://maps.googleapis.com" + "/maps/api/geocode/json?sensor=false" + "&address=" + adresse.replaceAll(" ", "+"));
//
////			 System.out.println(url.toString());
//			
//			HttpURLConnection conn;
//			conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Accept", "application/json");
//
//			if (conn.getResponseCode() != 200) {
//				throw new RuntimeException("Probleme : HTTP error code : " + conn.getResponseCode());
//			}
//
//			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//
//			String reponseComplete = "";
//			
//			// Pour garantir le retour des appels au reseau
//			Thread.sleep(100);
//
//			String output;
//			// System.out.println("Resultat de Google Maps ReverseGeocoderTest ....");
//			while ((output = br.readLine()) != null) {
//				reponseComplete += output;
//			}
//
//			JSONParser parser = new JSONParser();
//
//			Object obj = parser.parse(reponseComplete);
//
//			JSONObject jsonObject = (JSONObject) obj;
//
//			JSONArray results = (JSONArray) jsonObject.get("results");
//
//			if (!jsonObject.get("status").equals("OK")) {
//				coordonnees.put(SPGeocoder.LAT, 0.0);
//				coordonnees.put(SPGeocoder.LNG, 0.0);
//				return coordonnees;
//			}
//
//			JSONObject mesresults = (JSONObject) parser.parse(((JSONObject) results.get(0)).toJSONString());
//			JSONObject geometry = (JSONObject) mesresults.get("geometry");
//			JSONObject location = (JSONObject) geometry.get("location");
//
//			Double lat = (Double) location.get(SPGeocoder.LAT);
//			Double lng = (Double) location.get(SPGeocoder.LNG);
//
//			coordonnees.put(SPGeocoder.LAT, lat);
//			coordonnees.put(SPGeocoder.LNG, lng);
//
//			conn.disconnect();
//		} catch (MalformedURLException e1) {
//			e1.printStackTrace();
//			System.err.println("Format de l'URL non conforme : " + url);
//		} catch (IOException e2) {
//			System.out.println("Impossible de lire le fichier : " + fichier);
//			e2.printStackTrace();
//		} catch (ParseException e3) {
//			System.err.println("Parsing de l'URL non conforme : " + url);
//			e3.printStackTrace();
//		} catch (InterruptedException e4) {
//			System.err.println("Reseau encombre");
//			e4.printStackTrace();
//		}
//
//		return coordonnees;
//	}
	
	
//	@Test
//	public void testParis() {
//		Geocoder geocoder = new Geocoder();
//		GeocoderRequest geocoderRequest = new
//				GeocoderRequestBuilder().setAddress("Paris, France").setLanguage("fr").getGeocoderRequest();
//		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
//	}
}
