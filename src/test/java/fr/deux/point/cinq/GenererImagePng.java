/**
 * 
 */
package fr.deux.point.cinq;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * @author Serge Prodhomme
 *
 */
public class GenererImagePng {

	private static final String WEB_PATH = "war/resources/";

	@Test
	public void tester() {
		try {
		      int width = 300, height = 300;

		      // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
		      // into integer pixels
		      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		      Graphics2D ig2 = bi.createGraphics();

		      Font font = new Font("Arial", Font.BOLD, 10);
		      ig2.setFont(font);
		      String message = "www.koom.org";
		      FontMetrics fontMetrics = ig2.getFontMetrics();
		      int stringWidth = fontMetrics.stringWidth(message);
		      int stringHeight = fontMetrics.getAscent();
		      ig2.setPaint(Color.BLUE);
		      ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);

		      ImageIO.write(bi, "PNG", new File(WEB_PATH + "image.PNG"));
		      ImageIO.write(bi, "JPEG", new File(WEB_PATH + "image.JPG"));
		      ImageIO.write(bi, "GIF", new File(WEB_PATH + "image.GIF"));
		      ImageIO.write(bi, "BMP", new File(WEB_PATH + "image.BMP"));
		      
		    } catch (IOException ie) {
		      ie.printStackTrace();
		    }
	}
	
}
