/**
 * 
 */
package fr.deux.point.cinq.server.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Serge Prodhomme
 *
 */
public class HeaderEtFooter extends PdfPageEventHelper {

	/**
	 * 
	 */
	public HeaderEtFooter() {
		super();
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		super.onEndPage(writer, document);
		Paragraph header = new Paragraph("Header");
		try {
			writer.add(header);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	

}
