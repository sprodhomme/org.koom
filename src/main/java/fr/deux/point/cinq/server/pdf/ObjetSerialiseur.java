/**
 * 
 */
package fr.deux.point.cinq.server.pdf;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Serge Prodhomme
 *
 */
public class ObjetSerialiseur {

	private static final String SEPARATOR1 = "/";
	private static final String SEPARATOR2 = ":";
	
	public static String fromJson(Map<String, String> json) {
		String resultatSerialise = "";
		for(Entry<String, String> entry : json.entrySet()) {
		    String cle = entry.getKey();
		    String valeur = entry.getValue();
		    resultatSerialise += cle + SEPARATOR2 + valeur + SEPARATOR1;
		}
		return resultatSerialise.substring(0, resultatSerialise.length()-1);
	}
	
	public static Map<String, String> toJson(String serialise) {
		
		System.out.println(serialise);
		
		Map<String, String> json = new HashMap<String, String>();
		String[] couples = serialise.split(SEPARATOR1);
		for(int i=0; i<couples.length; i++) {
			String[] cleValeur = couples[i].split(SEPARATOR2);
			json.put(cleValeur[0], cleValeur[1]);
		}
		return json;
	}
	
}
