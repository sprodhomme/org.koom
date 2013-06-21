/**
 * 
 */
package fr.deux.point.cinq.server.pdf;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Serge Prodhomme
 * 
 */
public class DocumentKoom extends Document {
	
	public static final BaseColor bleuKoom = new BaseColor(4, 168, 210);
	public static final BaseColor orangeKoom = new BaseColor(247, 140, 70);
	public static final BaseColor vertKoom = new BaseColor(133, 206, 68);
	public static final BaseColor grisKoom = new BaseColor(200, 200, 200);
	
	
	private int indexTitre1 = 0;
	private int indexTitre2 = 0;
	private int indexTitre3 = 0;

	/**
	 * Page de garde
	 */
	private static final Font policeTitrePrincipal = new Font(Font.FontFamily.HELVETICA, 28, Font.NORMAL);
	private static final Font policeSousTitreBleu = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL); //, new BaseColor(4, 168, 210));
	private static final Font policeSousTitreOrange = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL); //, new BaseColor(239, 165, 36));
	private static final Font policeSousTitreVert = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL); //, new BaseColor(239, 165, 36));

	/**
	 * Polices requises dans la suite du document
	 */
	public static Font policeGloriaHallelujah;// = new Font(BaseFont.createFont("war/resources/gloriahallelujah.ttf", BaseFont.WINANSI, true));
	private static final Font policeTitre = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static final Font policeTexte = new Font(Font.FontFamily.HELVETICA);
	private static Font policeOrange = new Font(Font.FontFamily.HELVETICA);
	
	public PdfWriter writer;
	
	/**
	 * 
	 * Ne pas oublier de fermer le document a la fin !
	 * 
	 * 
	 * <code>document.close();</code>
	 * @param document
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public DocumentKoom(String document) throws DocumentException, IOException {
		super(PageSize.A4, 50, 50, 50, 50);

		// Initialisation des polices
		initPolices();

		this.writer = PdfWriter.getInstance(this, new FileOutputStream(document));
		this.open();
		this.ajouterTexte(""); // pour ne pas avoir un document vide
		// Ne pas oublier de fermer le document à la fin !
	}
	
	/**
	 * Initialisation du document Koom en precisant les marges
	 * @param document
	 * @param margeG
	 * @param margeD
	 * @param margeH
	 * @param margeB
	 * @throws DocumentException
	 * @throws IOException
	 */
	public DocumentKoom(String document, int margeG, int margeD, int margeH, int margeB) throws DocumentException, IOException {
		super(PageSize.A4, margeG, margeD, margeH, margeB);

		// Initialisation des polices
		initPolices();

		this.writer = PdfWriter.getInstance(this, new FileOutputStream(document));
		this.open();
		this.ajouterTexte(""); // pour ne pas avoir un document vide
		// Ne pas oublier de fermer le document à la fin !
	}

	private void initPolices() throws DocumentException, IOException {
		policeTitre.setColor(bleuKoom);
		policeOrange.setColor(orangeKoom);
		policeTitrePrincipal.setColor(orangeKoom);
		policeSousTitreOrange.setColor(orangeKoom);
		policeSousTitreBleu.setColor(bleuKoom);
		policeSousTitreVert.setColor(vertKoom);
		
		// Tester l'existence du fichier a la racine de l'application, sinon de l'application web
		// donc dans war/.
		File f = new File("resources/gloriahallelujah.ttf");
		if (f.exists())
			policeGloriaHallelujah = new Font(BaseFont.createFont("resources/gloriahallelujah.ttf", BaseFont.WINANSI, true));
		else 
			policeGloriaHallelujah = new Font(BaseFont.createFont("war/resources/gloriahallelujah.ttf", BaseFont.WINANSI, true));
	}
	
	/*
	 * Page de garde 
	 */
	
	/**
	 * Generation du titre
	 * @param titre
	 * @throws DocumentException
	 */
	public void ajouterTitrePrincipal(String titre) throws DocumentException {
		Chunk chunk = new Chunk(titre, policeTitrePrincipal);
		Paragraph titrePrincipal = new Paragraph(chunk);
		titrePrincipal.setAlignment(Element.ALIGN_CENTER);
		titrePrincipal.setSpacingBefore(30f);
		titrePrincipal.setSpacingAfter(30f);
		this.add(titrePrincipal);
	}
	
	public void ajouterSousTitrePrincipal(String soustitre) throws DocumentException {
		Chunk chunk = new Chunk(soustitre, policeSousTitreBleu);
		Paragraph titrePrincipal = new Paragraph(chunk);
		titrePrincipal.setAlignment(Element.ALIGN_CENTER);
		titrePrincipal.setSpacingBefore(25f);
		this.add(titrePrincipal);
	}

	public void ajouterSousTitrePrincipalOrange(String soustitre) throws DocumentException {
		Chunk chunk = new Chunk(soustitre, policeSousTitreOrange);
		Paragraph titrePrincipal = new Paragraph(chunk);
		titrePrincipal.setAlignment(Element.ALIGN_CENTER);
		this.add(titrePrincipal);
	}

	public void ajouterSousTitrePrincipalEntreprise(String soustitre) throws DocumentException {
		Chunk chunk = new Chunk(soustitre, policeSousTitreOrange);
		Paragraph titrePrincipal = new Paragraph(chunk);
		titrePrincipal.setAlignment(Element.ALIGN_CENTER);
		this.add(titrePrincipal);
	}
	
	public void ajouterSousTitrePrincipalVert(String soustitre) throws DocumentException {
		Chunk chunk = new Chunk(soustitre, policeSousTitreVert);
		Paragraph titrePrincipal = new Paragraph(chunk);
		titrePrincipal.setAlignment(Element.ALIGN_CENTER);
		titrePrincipal.setSpacingBefore(30f);
		titrePrincipal.setIndentationLeft(15f);
		titrePrincipal.setIndentationRight(15f);
		this.add(titrePrincipal);		
	}
	
	/*
	 * Document en tant que tel
	 */
	
	/**
	 * Ajouter un titre 1
	 * @param titre
	 * @throws DocumentException
	 */
	public void ajouterTitre(String titre) throws DocumentException {
		this.indexTitre1++;
		this.indexTitre2 = 0;
		this.indexTitre3 = 0;
		this.ajouterSautDePage();
		Paragraph titreP = new Paragraph(new Chunk(this.indexTitre1 + " - " + titre, policeTitre));
		titreP.setSpacingBefore(10f);
		this.add(titreP);
	}
	
	/**
	 * Ajouter un titre 1 qui ne possede pas de numerotation
	 * @param titre
	 * @throws DocumentException
	 */
	public void ajouterTitreIndependant(String titre) throws DocumentException {
		this.ajouterSautDePage();
		Paragraph titreIndependant = new Paragraph(new Chunk(titre, policeTitre));
		titreIndependant.setSpacingBefore(10f);
		this.add(titreIndependant);
	}

	/**
	 * Ajouter un titre 2 (avec une numerotation)
	 * @param soustitre
	 * @throws DocumentException
	 */
	public void ajouterSoustitre(String soustitre) throws DocumentException {
		this.indexTitre2++;
		this.indexTitre3 = 0;
		this.add(new Paragraph(new Chunk(this.indexTitre1 + "." + this.indexTitre2 + " - " + soustitre, policeSousTitreOrange)));
	}
	
	/**
	 * Ajouter un titre 3 avec une numerotation
	 * @param soussoustitre
	 * @throws DocumentException
	 */
	public void ajouterSoussoustitre(String soussoustitre) throws DocumentException {
		this.indexTitre3++;
		this.add(new Paragraph(new Chunk(this.indexTitre1 + "." + this.indexTitre2 + "." + this.indexTitre3 + " - " + soussoustitre, policeSousTitreVert)));
	}
	
	/**
	 * Ajouter un pragraphe justifie
	 * @param texte
	 * @throws DocumentException
	 */
	public void ajouterParagraphe(String texte) throws DocumentException {
		this.add(new Paragraph(new Chunk(texte, policeTexte)));
	}

	/**
	 * Ajouter une image presente dans un dossier ou sous-dossier a partir
	 * de la racine de l'application, web ou non
	 * @param image
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void ajouterImage(String image) throws IOException,
			DocumentException {
		this.add(Image.getInstance(
				Toolkit.getDefaultToolkit().createImage(image), null));
	}
	
	/**
	 * Ajout d'une image a une position donnee
	 * @param image
	 * @param top : position y (vaut 0 en bas a gauche)
	 * @param left
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void ajouterImage(String image, int top, int left) throws IOException, DocumentException {
		Image img = Image.getInstance(Toolkit.getDefaultToolkit().createImage(image), null);
		img.setAbsolutePosition(top, left);
		this.add(img);
	}
	
	/**
	 * Ajouter une image distante a partir de son URL
	 * @param imageUrl
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void ajouterImageUrl(String imageUrl) throws MalformedURLException, IOException, DocumentException {
        this.add(Image.getInstance(new URL(imageUrl)));
	}
	
	public void ajouterImageUrl(String imageUrl, int pourcentageWidth) throws MalformedURLException, IOException, DocumentException {
        this.ajouterImageUrl(imageUrl, pourcentageWidth, Element.ALIGN_JUSTIFIED);
	}
	
	/**
	 * Ajouter une image distante redimensionnee
	 * @param imageUrl
	 * @param pourcentageWidth
	 * @param alignement
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void ajouterImageUrl(String imageUrl, int pourcentageWidth, int alignement) throws MalformedURLException, IOException, DocumentException {
        Image image = Image.getInstance(new URL(imageUrl));
        image.scalePercent((float)pourcentageWidth);
        image.setAlignment(alignement);
        this.add(image);
	}
	
	/**
	 * Ajouter une image distante en la positionnant de facon absolue
	 * @param left
	 * @param top
	 * @param imageUrl
	 * @param pourcentageWidth
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public void ajouterImageUrl(int left, int top, String imageUrl, int pourcentageWidth) throws MalformedURLException, IOException, DocumentException {
		Image image = Image.getInstance(new URL(imageUrl));
        image.scalePercent((float)pourcentageWidth);
        image.setAbsolutePosition((float)left, (float)top);
        this.add(image);
	}
	
	public void ajouterSautDePage() {
		this.newPage();
	}

	/**
	 * Ajouter un paragraphe avec choix de l'alignement (@Element.ALIGN_RIGHT, ...)
	 * @param texte
	 * @param style
	 * @param alignement
	 * @throws DocumentException
	 */
	public void ajouterLigneDeTexte(String texte, int style, int alignement) throws DocumentException {
		Chunk chunk = new Chunk(texte, new Font(policeTexte.getFamily(), policeTexte.getSize(), style, policeTexte.getColor()));
		Paragraph paragraphe = new Paragraph(chunk);
		paragraphe.setAlignment(alignement);
		this.add(paragraphe);
	}
	
	/**
	 * Ajouter du texte a la suite (equivalent de &lt;span&gt; en html)
	 * @param texte
	 * @throws DocumentException
	 */
	public void ajouterTexte(String texte) throws DocumentException {
		this.add(new Chunk(texte, policeTexte));
	}

	public void ajouterTexte(String texte, int style) throws DocumentException {
		this.add(new Chunk(texte, new Font(policeTexte.getFamily(), policeTexte.getSize(), style, policeTexte.getColor())));
	}
	
	public void ajouterTexteOrange(String texteOrange) throws DocumentException {
		this.add(new Chunk(texteOrange, policeOrange));
	}

	/**
	 * Inserer le contenu de tout un fichier texte present dans un sous-dossier
	 * de la racine de l'application
	 * @param fichier
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void ajouterFichierTexte(String fichier) throws DocumentException, IOException {
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(fichier));

		Pattern titlePattern = Pattern.compile("^(CHAPTER|PREFACE|CONTENTS).*");

		Paragraph paragraph = null;
		String line;
		while ((line = reader.readLine()) != null) {
			Boolean isTitle = titlePattern.matcher(line).matches();

			/* flush paragraph on empty lines and titles */
			if ((line.isEmpty() || isTitle) && (paragraph != null)) {
				this.add(paragraph);
				paragraph = null;
			}

			if (!line.isEmpty()) {
				if (paragraph == null) {
					paragraph = new Paragraph();
					paragraph.setSpacingAfter(8);
					paragraph.setSpacingBefore(8);
					paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
				}
				paragraph.add(new Chunk(line + " ", isTitle ? policeTitre : policeTexte));
			}

			/* New titles are immediately flushed */
			if (isTitle) {
				this.add(paragraph);
				paragraph = null;
			}
		}
		/* Flush paragraph at the end of file */
		if (paragraph != null)
			this.add(paragraph);
		
		reader.close();
	}

	//TODO DEBUG
	public  void ajouterTexteColore(String texte, int taille, BaseColor couleur) throws DocumentException {
		Font police = new Font(Font.FontFamily.HELVETICA, taille, Font.NORMAL, couleur);
		this.add(new Paragraph(new Chunk(texte, police)));
	}

//	//TODO DEBUG
//	public class MyPageEventListener extends PdfPageEventHelper {
//
//		/** The header text. */
//		String header;
//		/** The template with the total number of pages. */
//		PdfTemplate total;
//
//		/**
//		 * Allows us to change the content of the header.
//		 * @param header The new header String
//		 */
//		public void setHeader(String header) {
//			this.header = header;
//		}
//		
//		public MyPageEventListener() {
//			super();
//		}
//
//		@Override
//		public void onEndPage(PdfWriter writer, Document document) {
//			try {
//				writer.add(new Chunk("Header"));
//				document.add(new Chunk("En-tete"));
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			PdfPTable table = new PdfPTable(3);
////			try {
////				table.setWidths(new int[]{24, 24, 2});
////                table.setTotalWidth(527);
////                table.setLockedWidth(true);
////                table.getDefaultCell().setFixedHeight(20);
////                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
////                table.addCell(header);
////                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
////                table.addCell(String.format("Page %d of", writer.getPageNumber()));
////                PdfPCell cell = new PdfPCell(Image.getInstance(total));
////                cell.setBorder(Rectangle.BOTTOM);
////                table.addCell(cell);
////                table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
////			}
////			catch(DocumentException de) {
////				throw new ExceptionConverter(de);
////			}
//		}
//		
//		/**
//         * Fills out the total number of pages before the document is closed.
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//		@Override
//        public void onCloseDocument(PdfWriter writer, Document document) {
////            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
////                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
////                    2, 2, 0);
//        }
//	}

	/**
	 * Fonction de debug des objets
	 * @param o
	 */
	public static void debug(Object o) {
		System.out.println(o);
	}
	
}
