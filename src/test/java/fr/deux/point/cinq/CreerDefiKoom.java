/**
 * 
 */
package fr.deux.point.cinq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;

import fr.deux.point.cinq.server.pdf.DocumentKoom;
import fr.deux.point.cinq.server.pdf.GenerateurDefi;

/**
 * @author serge
 *
 */
public class CreerDefiKoom {

	@Test
	public void utiliserGloriaHallelujah() throws DocumentException, IOException {
		BaseFont base = BaseFont.createFont("war/resources/gloriahallelujah.ttf", BaseFont.WINANSI, true);
		Font font = new Font(base, 18f, Font.BOLD);
		DocumentKoom document = new DocumentKoom("test/results/document-police.pdf");
		document.add(new Paragraph(new Chunk("Ceci a été écrit avec succès en police Gloria Hallelujah !", font)));
		font.setColor(DocumentKoom.bleuKoom);
		document.add(new Chunk(" et ceci en bleu !", font));
		document.close();
	}
	
	@Test
	public void creerDefi() throws DocumentException, IOException {
		Map<String, String> parametres = new HashMap<String, String>();
		parametres.put("nomKoomer", "Serge Prod'homme");
		parametres.put("intituleDefi", "Si 1 500 Koomers s'engagent à recycler leurs anciennes ampoules, alors votre entreprise s'engage à mettre des bacs de recyclage à disposition des employés.");
		GenerateurDefi.genererDefi("war/", "affiche-defi.pdf", parametres);
	}
	
}
