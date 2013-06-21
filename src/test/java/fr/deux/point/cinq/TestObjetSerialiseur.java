/**
 * 
 */
package fr.deux.point.cinq;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import fr.deux.point.cinq.server.pdf.ObjetSerialiseur;

/**
 * @author Serge Prodhomme
 *
 */
public class TestObjetSerialiseur {

	@Test
	public void testerFromJson() {
		Map<String, String> json = new HashMap<String, String>();
		json.put("cle1", "valeur1");
		json.put("cle2", "valeur2");
		json.put("cle3", "valeur3");
		Assert.assertEquals(ObjetSerialiseur.fromJson(json), "cle3:valeur3/cle2:valeur2/cle1:valeur1");
	}
	
	@Test
	public void testerToJson() {
		String serialise = "cle3:valeur3/cle2:valeur2/cle1:valeur1";
		Map<String, String> json = ObjetSerialiseur.toJson(serialise);
		Assert.assertTrue(json.containsKey("cle1"));
		Assert.assertTrue(json.containsKey("cle2"));
		Assert.assertTrue(json.containsKey("cle3"));
		Assert.assertEquals(json.get("cle1"), "valeur1");
		Assert.assertEquals(json.get("cle2"), "valeur2");
		Assert.assertEquals(json.get("cle3"), "valeur3");
	}
	
}
