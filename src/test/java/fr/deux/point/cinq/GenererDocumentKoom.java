package fr.deux.point.cinq;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import fr.deux.point.cinq.server.pdf.DocumentKoom;
import fr.deux.point.cinq.server.pdf.GenerateurDefi;

public class GenererDocumentKoom {

	@Test
	public void sectionEtChapitres() throws DocumentException, IOException {
		
		DocumentKoom document = new DocumentKoom("test/results/sections-chapters.pdf");
		Paragraph paragraph = new Paragraph();
		Chapter chapter = new Chapter(paragraph, 1);

		Section section1 = chapter.addSection("This is section 1", 2);
		Section section2 = chapter.addSection("This is section 2", 2);

		document.add(chapter);
		
		document.close();
	}
	
	
	@Test
	public void genererDefiKoom() throws DocumentException, IOException {
		final String nomKoomer = "Serge Prod'homme";
		final String nomEntreprise = "Netapsys Conseil";
		String intituleAction = "'Recycler ses anciennes ampoules'";
		String descriptionDefi = "Recycler ses anciennes ampoules permet plusieurs choses : d'une part, s'en séparer, et d'autre part, fournir de la matière devenue inutile à des sociétés de traitement qui permettront de récupérer ce qui peut l'être et ainsi de pouvoir en refabriquer des neuves.";
		String intituleDefi = "Si 100 Koomers s'engagent à " + intituleAction + ", alors l'entreprise " + nomEntreprise + " s'engage à " + intituleAction + ".";
		
		final Map<String, String> parametres = new HashMap<>();
		parametres.put("nomKoomer", nomKoomer);
		parametres.put("nomEntreprise", nomEntreprise);
		parametres.put("intituleDefi", intituleDefi);
		parametres.put("descriptionDefi", descriptionDefi);
		parametres.put("intituleAction", intituleAction);
		
		nomKoomer.replaceAll("à", "a")
				 .replaceAll("é", "e")
				 .replaceAll("è", "e")
				 .replaceAll("ê", "e")
				 .replaceAll("ï", "i")
				 .replaceAll("î", "i")
				 .replaceAll("ô", "ö")
				 .replaceAll("ü", "u")
				 .replaceAll("ü", "u")
				 .replaceAll("ù", "u")
				 .replaceAll("ÿ", "y")
		;
		
		Runnable th = new Runnable() {
			
			@Override
			public void run() {
				GenerateurDefi.genererDocumentDefi("war/", "test/results/" + nomEntreprise.replaceAll("'", "").replaceAll(" ", "") + nomKoomer.replaceAll("'", "").replaceAll(" ", "") + "-DefiKoom.pdf", parametres);
			}
		};
		
		th.run();
		
//		GenerateurDefi.genererDocumentDefi("war/", "test/results/" + nomEntreprise.replaceAll("'", "").replaceAll(" ", "") + "-DefiKoom.pdf", parametres);
	}
	
	public class MaThread implements Runnable {

		@Override
		public void run() {
			String nomKoomer = "Serge Prod'homme";
			String nomEntreprise = "Netapsys Conseil";
			String intituleAction = "'Recycler ses anciennes ampoules'";
			String descriptionDefi = "Recycler ses anciennes ampoules permet plusieurs choses : d'une part, s'en séparer, et d'autre part, fournir de la matière devenue inutile à des sociétés de traitement qui permettront de récupérer ce qui peut l'être et ainsi de pouvoir en refabriquer des neuves.";
			String intituleDefi = "Si 100 Koomers s'engagent à " + intituleAction + ", alors l'entreprise " + nomEntreprise + " s'engage à " + intituleAction + ".";
			
			Map<String, String> parametres = new HashMap<>();
			parametres.put("nomKoomer", nomKoomer);
			parametres.put("nomEntreprise", nomEntreprise);
			parametres.put("intituleDefi", intituleDefi);
			parametres.put("descriptionDefi", descriptionDefi);
			parametres.put("intituleAction", intituleAction);
			
			
			GenerateurDefi.genererDocumentDefi("war/", "test/results/thread-" + nomEntreprise.replaceAll("'", "").replaceAll(" ", "") + "-DefiKoom.pdf", parametres);
		}
		
	}
	
//	@Test
	public void gererLesThreads() {
		MaThread th = new MaThread();
		th.run();
	}
	
//	@Test
	public void ajouterHtml() throws DocumentException, IOException {
		DocumentKoom document = new DocumentKoom("test/results/document-html.pdf");
		try {
			//ca permet d'adapter ton contenu html au pdf, si tu veus faire des modifications
			StyleSheet styles = new StyleSheet();
			styles.loadTagStyle("table", "width", "100%");
			styles.loadTagStyle("table-cell", "height", "10px");
			styles.loadTagStyle("img", "width", "70%");
			styles.loadTagStyle("body", "font-size", "2px");
			PdfWriter.getInstance(document, new FileOutputStream("html2.pdf"));
			document.open();

			java.util.List<Element> objects = new ArrayList<>();
			objects = HTMLWorker.parseToList(new StringReader("<html><head></head><body>Ceci est mon code <b>HTML</b><br>Il peut donc <u>contenir</u> <i>du texte riche</i> saisi dans un RichEditor !</body></html>"), styles);
			for (int k = 0; k < objects.size(); ++k)
				document.add((Element) objects.get(k));
		}

		catch (Exception e) {
			e.printStackTrace();

			System.err.println(e.getMessage());
		}

		document.close();
	}
	
	@Test
	public void headerEtFooter() throws DocumentException, IOException {
		 // step 1
		DocumentKoom document = new DocumentKoom("test/results/header-footer.pdf");
        PdfWriter writer = document.writer;
//        MyPageEventListener event = new MyPageEventListener();
        PdfPageEventHelper event = new PdfPageEventHelper();
        writer.setPageEvent(event);
        // step 3 - fill in the document
        document.open();

//        event.setHeader("Hello!");
        document.add(new Paragraph("Testing."));
        document.newPage();
//        event.setHeader("There!");
        document.add(new Paragraph("Testing."));
        
        event.onEndPage(writer, document);
        
        document.close();
	}
	
//	@Test
	public void creerListes() throws DocumentException, IOException {
		
		DocumentKoom document = new DocumentKoom("test/results/document-liste.pdf");

        List orderedList = new List(List.ORDERED);
        orderedList.add(new ListItem("Item 1"));
        orderedList.add(new ListItem("Item 2"));
        orderedList.add(new ListItem("Item 3"));

        document.add(orderedList);

        List unorderedList = new List(List.UNORDERED);
        unorderedList.add(new ListItem("Item 1"));
        unorderedList.add(new ListItem("Item 2"));
        unorderedList.add(new ListItem("Item 3"));

        document.add(unorderedList);
        
        document.close();
	}
	
//	@Test
	public void modifierPdfExistant() {
		try {
		      PdfReader pdfReader = new PdfReader("HelloWorld.pdf");

		      PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("HelloWorld-Stamped.pdf"));

		      Image image = Image.getInstance("http://tutorials.jenkov.com/images/layout/small-portrait.jpg");

		      for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
		          PdfContentByte content = pdfStamper.getUnderContent(i);
		          image.setAbsolutePosition(100f, 700f);
		          content.addImage(image);
		      }

		      pdfStamper.close();

		    } catch (IOException e) {
		      e.printStackTrace();
		    } catch (DocumentException e) {
		      e.printStackTrace();
		    }
	}
	
	
//	@Test
	public void simpleDocumentKoom() throws DocumentException, MalformedURLException, IOException {
		
		String nomKoomer = "Serge Prod'homme";
		String nomEntreprise = "Netapsys";
		
		DocumentKoom document = new DocumentKoom("test/results/document-genere.pdf");
		document.ajouterImageUrl("http://acteursduparisdurable.fr/sites/default/files/styles/img_carrousel_500x330/public/koom-logo.jpg", 40, Element.ALIGN_CENTER);
		document.ajouterTitrePrincipal("Proposition de Défi Koom");
		document.ajouterSousTitrePrincipal("A l'attention de la société :");
		document.ajouterSousTitrePrincipalEntreprise(nomEntreprise);
		document.addAuthor(nomKoomer);
		document.addCreationDate();
		document.addCreator("Koom SA");
		document.addHeader("Type", "Défi Koom");
		document.addHeader("Action", "S'équiper en ampoules basse consommation");
		document.ajouterSousTitrePrincipal("Portée par :");
		document.ajouterSousTitrePrincipalOrange("" + nomKoomer);
		document.ajouterSousTitrePrincipalVert("Dans le but de s'engager sur une action orientée vers le Développement Durable");
		document.ajouterImageUrl("http://www.koom.org/web/skin/suz/images/triptyque-Koom-v3.jpg", 50);

		
		document.ajouterTitreIndependant("Introduction");
		document.ajouterFichierTexte("war/resources/koom/introduction.txt");
		
		document.ajouterTitre("Présentation de la société Koom SA");
		document.ajouterSoustitre("Histoire");
		document.ajouterFichierTexte("war/resources/koom/histoire.txt");
		document.ajouterSoustitre("Les fondateurs");
		document.ajouterFichierTexte("war/resources/koom/les_fondateurs.txt");
		document.ajouterSoustitre("Mission et valeurs");
		document.ajouterFichierTexte("war/resources/koom/valeurs.txt");
		document.ajouterSoustitre("Koom et le développement durable");
		document.ajouterFichierTexte("war/resources/koom/koom_et_le_developpement_durable.txt");
		
		document.ajouterTitre("Présentation des défis Koom");
		document.ajouterSoustitre("Principe");
		document.ajouterFichierTexte("war/resources/koom/principe_des_defis.txt");
		document.ajouterSoustitre("Description du défi proposé");
		document.ajouterFichierTexte("war/resources/koom/description_du_defi.txt"); //TODO ajouter le texte saisi ici
		document.ajouterSoustitre("Enjeux du défi");
		document.ajouterFichierTexte("war/resources/koom/enjeux_du_defi.txt"); //TODO ajouter le texte saisi ici
		document.ajouterSoustitre("Impacts");
			document.ajouterSoussoustitre("Impacts environnementaux");
			document.ajouterFichierTexte("war/resources/koom/impacts_environnementaux.txt"); //TODO ajouter les résultats des simulations
			document.ajouterSoussoustitre("Impacts sur l'image de l'entreprise");
			document.ajouterFichierTexte("war/resources/koom/impacts_image_entreprise.txt"); //TODO ajouter les résultats des simulations
		document.ajouterSoustitre("Simulation budgétaire");
		document.ajouterFichierTexte("war/resources/koom/simulation_budgetaire.txt"); //TODO ajouter les résultats budgétaires
			document.ajouterSoussoustitre("Méthode de calcul");
			document.ajouterFichierTexte("war/resources/koom/simulation_budgetaire_methode_calcul.txt");
			document.ajouterSoussoustitre("Application");
			document.ajouterFichierTexte("war/resources/koom/simulation_budgetaire_application.txt");

		document.ajouterTitre("Proposition de partenariat avec Koom");
		document.ajouterTexte("Le site http://www.koom.org vous offre plusieurs possibilités de promotion de vos actions en faveur du Développement Durable.");
		// Les liens hypertextes sont interprŽtŽs directement dans cette fonction
		document.ajouterSoustitre("Création d'une page de promotion");
		document.ajouterFichierTexte("war/resources/koom/partenariat_page_de_promotion.txt");
		document.ajouterSoustitre("Géolocalisation avancée de vos enseignes");
		document.ajouterFichierTexte("war/resources/koom/partenariat_geolocalisation.txt");
		document.ajouterSoustitre("Encart publicitaire");
		document.ajouterFichierTexte("war/resources/koom/partenariat_encart.txt"); // TODO dépend de l'action choisie pour le dŽfi
		
		document.ajouterTitreIndependant("Conclusion");
		document.ajouterFichierTexte("war/resources/koom/conclusion.txt"); // TODO dépend des titres de l'action et du dŽfi choisis

		// Fin du document (crŽdits)
		document.add(new Paragraph(" "));
		document.add(new Paragraph(" "));
		document.add(new Paragraph(" "));
		document.ajouterTexte("Jérôme Lhote, co-fondateur, Président de Koom");
		document.add(new Paragraph(" "));
		document.ajouterTexte("Alexis Delaporte, co-fondateur, Directeur des Systèmes d'Information");
		document.add(new Paragraph(""));
		document.add(new Paragraph(" "));
		document.ajouterTexte("Soyez le changement que vous souhaitez pour ce monde, Mahatmah Gandhi");
		document.add(new Paragraph(""));
		document.add(new Paragraph(" "));
		document.ajouterTexte("Document généré suite à la saisie d'un défi par " + nomKoomer // TODO dépend du Koomer
		+ " à partir d'un outil développé par Serge Prod'homme, Architecte mobile et Java, attaché commercial.");
			
		// Obligatoire pour enregistrer les modifications
		document.close();
	}
}
