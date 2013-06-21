/**
 * 
 */
package fr.deux.point.cinq.server.pdf;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;

/**
 * @author Serge Prodhomme
 *
 */
public class GenerateurDefi {

	/**
	 * Generateur de defi Koom en une page
	 * @param nomFichier
	 * @param parametres
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String genererDefi(final String repertoire, String nomFichier, Map<String, String> parametres) {
		String nomKoomer = (parametres.containsKey("nomKoomer")) ? parametres.get("nomKoomer") : "nomKoomer";
//		String nomEntreprise = (parametres.containsKey("nomEntreprise")) ? parametres.get("nomEntreprise") : "nomEntreprise";
		String intituleDefi = (parametres.containsKey("intituleDefi")) ? parametres.get("intituleDefi") : "intituleDefi";

		
		DocumentKoom document = null;
		try {
			
			document = new DocumentKoom(nomFichier, 0, 0, 0, 0);
			document.ajouterImage(repertoire + "resources/banniereKoom.png");
//			document.ajouterImage(repertoire + "resources/banniereKoom.png", 0, 638);

			Paragraph paragraphePropositionDefi = new Paragraph("Proposition de défi Koom", new Font(Font.FontFamily.HELVETICA, 29, Font.BOLD, DocumentKoom.orangeKoom));
			//Paragraph paragrapheKoomer = new Paragraph("Le Koomer " + nomKoomer + " vous propose le défi suivant :", new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL, DocumentKoom.bleuKoom));
			Font police = DocumentKoom.policeGloriaHallelujah;
			police.setColor(DocumentKoom.bleuKoom);
			police.setSize(17f);
			Paragraph paragrapheKoomer = new Paragraph(nomKoomer + "\nvous propose le défi suivant :", police);
			Paragraph paragrapheIntituleDefi = new Paragraph(intituleDefi, new Font(Font.FontFamily.HELVETICA, 21, Font.ITALIC, DocumentKoom.vertKoom));

			paragraphePropositionDefi.setAlignment(Element.ALIGN_CENTER);
			paragrapheKoomer.setAlignment(Element.ALIGN_CENTER);
			paragrapheIntituleDefi.setAlignment(Element.ALIGN_CENTER);

			paragraphePropositionDefi.setSpacingBefore(20); // 200
			paragrapheKoomer.setSpacingBefore(20);
			paragrapheIntituleDefi.setSpacingBefore(20);
			paragrapheIntituleDefi.setIndentationLeft(50f);
			paragrapheIntituleDefi.setIndentationRight(50f);

//			Rectangle rectanglePropositionDefi = new Rectangle(50f, 470f, 550f, 300f);
//			rectanglePropositionDefi.setBackgroundColor(BaseColor.BLACK);
//			document.add(rectanglePropositionDefi);

			document.add(paragraphePropositionDefi);
			document.add(paragrapheKoomer);
			document.add(paragrapheIntituleDefi);

			Chunk chunkApprendrePlus = new Chunk("Vous souhaitez en apprendre plus sur les engagements citoyens en faveur du développement durable ? Contactez le co-fondateur de Koom, Jérôme Lhote (jerome@koom.org), ou rendez-vous sur le site www.koom.org");
			chunkApprendrePlus.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
			Paragraph paragrapheApprendrePlus = new Paragraph(chunkApprendrePlus);
			paragrapheApprendrePlus.setAlignment(Element.ALIGN_CENTER);
			paragrapheApprendrePlus.setSpacingBefore(90);
			document.add(paragrapheApprendrePlus);


			String annee = (new Date()).toString().substring(25, 29); // recuperation de l'annee seule
			Chunk chunkCopyright = new Chunk("© " + annee + " - Koom Société à Action Simplifiée - RCS Paris N°533 421 152");
			chunkCopyright.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, DocumentKoom.grisKoom));
			Paragraph paragrapheCopyright = new Paragraph(chunkCopyright);
			paragrapheCopyright.setAlignment(Element.ALIGN_CENTER);
			paragrapheCopyright.setSpacingBefore(30);
			document.add(paragrapheCopyright);

			document.ajouterImage(repertoire + "resources/footerKoom.png", 0, 0); // 0, -30
//			document.ajouterImage("war/resources/footerKoom.png", 0, 0);

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

		document.close();
		
		return nomFichier;
	}

	
	/**
	 * Generer le defi Koom avec description de la societe, description des
	 * defis Koom et proposition de partenariat commercial
	 * @param parametres
	 * <br>
	 * "nomKoomer" : le nom du Koomer qui presente le defi
	 * <br>
	 * "nomEntreprise" : l'entreprise a laquelle est propose le defi
	 * <br>
	 * @throws IOException 
	 * @throws DocumentException 
	 * 
	 */
	public static String genererDocumentDefi(final String repertoire, final String nomFichier, final Map<String, String> parametres) {
		
		Runnable th = new Runnable() {
			
			@Override
			public void run() {
				
				String nomKoomer = parametres.containsKey("nomKoomer") ? parametres.get("nomKoomer") : "nomKoomer";
				String nomEntreprise = parametres.containsKey("nomEntreprise") ? parametres.get("nomEntreprise") : "nomEntreprise";
				String intituleDefi = parametres.containsKey("intituleDefi") ? parametres.get("intituleDefi") : "intituleDefi";
				String descriptionDefi = parametres.containsKey("descriptionDefi") ? parametres.get("descriptionDefi") : "descriptionDefi";
				String intituleAction = parametres.containsKey("intituleAction") ? parametres.get("intituleAction") : "intituleAction";
				
				DocumentKoom document = null;
				try {
					document = new DocumentKoom(nomFichier);
					document.ajouterParagraphe("");
					document.ajouterImageUrl("http://acteursduparisdurable.fr/sites/default/files/styles/img_carrousel_500x330/public/koom-logo.jpg", 40, Element.ALIGN_CENTER);
					document.ajouterTitrePrincipal("Proposition de défi Koom");
					document.ajouterSousTitrePrincipal("A l'attention de la société :");
					document.ajouterSousTitrePrincipalEntreprise(nomEntreprise);
					document.addAuthor(nomKoomer);
					document.addCreationDate();
					document.addCreator("Koom SA");
					document.addHeader("Type", "Défi Koom");
					document.addHeader("Action", intituleAction);
					document.ajouterSousTitrePrincipal("Portée par :");
					document.ajouterSousTitrePrincipalOrange("" + nomKoomer);
					document.ajouterSousTitrePrincipalVert("Dans le but de s'engager sur une action orientée vers le Développement Durable");
					document.ajouterImageUrl(0, 0, "http://www.koom.org/web/skin/suz/images/triptyque-Koom-v3.jpg", 64);
	
					
					document.ajouterTitreIndependant("Introduction");
					document.ajouterFichierTexte(repertoire + "resources/koom/introduction.txt");
					
					document.ajouterTitre("Présentation de la société Koom SA");
					document.ajouterSoustitre("Histoire");
					document.ajouterFichierTexte(repertoire + "resources/koom/histoire.txt");
					document.ajouterSoustitre("Les fondateurs");
					document.ajouterFichierTexte(repertoire + "resources/koom/les_fondateurs.txt");
					document.ajouterSoustitre("Mission et valeurs");
					document.ajouterFichierTexte(repertoire + "resources/koom/valeurs.txt");
					document.ajouterSoustitre("Koom et le développement durable");
					document.ajouterFichierTexte(repertoire + "resources/koom/koom_et_le_developpement_durable.txt");
					
					document.ajouterTitre("Présentation des défis Koom");
					document.ajouterSoustitre("Principe");
					document.ajouterFichierTexte(repertoire + "resources/koom/principe_des_defis.txt");
	//				document.ajouterSoustitre("Description du défi proposé");
					document.ajouterSoustitre("Le défi : " + intituleDefi);
					document.ajouterTexte(descriptionDefi);
	//				document.ajouterFichierTexte(dir + "resources/koom/description_du_defi.txt"); //TODO ajouter le texte saisi ici
					document.ajouterSoustitre("Enjeux du défi");
					document.ajouterFichierTexte(repertoire + "resources/koom/enjeux_du_defi.txt"); //TODO ajouter le texte saisi ici
					document.ajouterSoustitre("Impacts");
						document.ajouterSoussoustitre("Impacts environnementaux");
						document.ajouterFichierTexte(repertoire + "resources/koom/impacts_environnementaux.txt"); //TODO ajouter les resultats des simulations
						document.ajouterSoussoustitre("Impacts sur l'image de l'entreprise");
						document.ajouterFichierTexte(repertoire + "resources/koom/impacts_image_entreprise.txt"); //TODO ajouter les resultats des simulations
					document.ajouterSoustitre("Simulation budgétaire");
					document.ajouterFichierTexte(repertoire + "resources/koom/simulation_budgetaire.txt"); //TODO ajouter les resultats budgetaires
						document.ajouterSoussoustitre("Méthode de calcul");
						document.ajouterFichierTexte(repertoire + "resources/koom/simulation_budgetaire_methode_calcul.txt");
						document.ajouterSoussoustitre("Application");
						document.ajouterFichierTexte(repertoire + "resources/koom/simulation_budgetaire_application.txt");
	
					document.ajouterTitre("Proposition de partenariat avec Koom");
					document.ajouterTexte("Le site http://www.koom.org vous offre plusieurs possibilités de promotion de vos actions en faveur du Développement Durable.");
					// Les liens hypertextes sont interpretes directement dans cette fonction
					document.ajouterSoustitre("Création d'une page de promotion");
					document.ajouterFichierTexte(repertoire + "resources/koom/partenariat_page_de_promotion.txt");
					document.ajouterSoustitre("Géolocalisation avancée de vos enseignes");
					document.ajouterFichierTexte(repertoire + "resources/koom/partenariat_geolocalisation.txt");
					document.ajouterSoustitre("Encart publicitaire");
					document.ajouterFichierTexte(repertoire + "resources/koom/partenariat_encart.txt"); // TODO depend de l'action choisie pour le defi
					
					document.ajouterTitreIndependant("Conclusion");
					document.ajouterFichierTexte(repertoire + "resources/koom/conclusion.txt"); // TODO depend des titres de l'action et du dŽfi choisis
	
					// Fin du document (credits)
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.ajouterTexte("Jérôme Lhote, co-fondateur, Président de Koom");
					document.add(new Paragraph(" "));
					document.ajouterTexte("Alexis Delaporte, co-fondateur, Directeur des Systèmes d'Information");
					document.add(new Paragraph(""));
					document.add(new Paragraph(" "));
					document.ajouterLigneDeTexte("Soyez le changement que vous souhaitez pour ce monde, Mahatmah Gandhi", Font.ITALIC, Element.ALIGN_RIGHT);
					document.add(new Paragraph(""));
					document.add(new Paragraph(" "));
					document.ajouterTexte("Document généré suite à la saisie d'un défi par " + nomKoomer // TODO depend du Koomer
					+ " à partir d'un outil développé par Serge Prod'homme, Architecte mobile et Java, attaché commercial.");
				} catch (DocumentException | IOException e) {
					e.printStackTrace();
				}
					
				// Obligatoire pour enregistrer les modifications
				document.close();
				
			}
		};
		
		th.run();
		
//		String nomKoomer = parametres.containsKey("nomKoomer") ? parametres.get("nomKoomer") : "nomKoomer";
//		String nomEntreprise = parametres.containsKey("nomEntreprise") ? parametres.get("nomEntreprise") : "nomEntreprise";
//		String intituleDefi = parametres.containsKey("intituleDefi") ? parametres.get("intituleDefi") : "intituleDefi";
//		String descriptionDefi = parametres.containsKey("descriptionDefi") ? parametres.get("descriptionDefi") : "descriptionDefi";
//		String intituleAction = parametres.containsKey("intituleAction") ? parametres.get("intituleAction") : "intituleAction";
//		
//		DocumentKoom document = new DocumentKoom(nomFichier);
//		
//		document.ajouterParagraphe("");
//		document.ajouterImageUrl("http://acteursduparisdurable.fr/sites/default/files/styles/img_carrousel_500x330/public/koom-logo.jpg", 40, Element.ALIGN_CENTER);
//		document.ajouterTitrePrincipal("Proposition de défi Koom");
//		document.ajouterSousTitrePrincipal("A l'attention de la société :");
//		document.ajouterSousTitrePrincipalEntreprise(nomEntreprise);
//		document.addAuthor(nomKoomer);
//		document.addCreationDate();
//		document.addCreator("Koom SA");
//		document.addHeader("Type", "Défi Koom");
//		document.addHeader("Action", intituleAction);
//		document.ajouterSousTitrePrincipal("Portée par :");
//		document.ajouterSousTitrePrincipalOrange("" + nomKoomer);
//		document.ajouterSousTitrePrincipalVert("Dans le but de s'engager sur une action orientée vers le Développement Durable");
//		document.ajouterImageUrl(0, 0, "http://www.koom.org/web/skin/suz/images/triptyque-Koom-v3.jpg", 64);
//
//		
//		document.ajouterTitreIndependant("Introduction");
//		document.ajouterFichierTexte(dir + "resources/koom/introduction.txt");
//		
//		document.ajouterTitre("Présentation de la société Koom SA");
//		document.ajouterSoustitre("Histoire");
//		document.ajouterFichierTexte(dir + "resources/koom/histoire.txt");
//		document.ajouterSoustitre("Les fondateurs");
//		document.ajouterFichierTexte(dir + "resources/koom/les_fondateurs.txt");
//		document.ajouterSoustitre("Mission et valeurs");
//		document.ajouterFichierTexte(dir + "resources/koom/valeurs.txt");
//		document.ajouterSoustitre("Koom et le développement durable");
//		document.ajouterFichierTexte(dir + "resources/koom/koom_et_le_developpement_durable.txt");
//		
//		document.ajouterTitre("Présentation des défis Koom");
//		document.ajouterSoustitre("Principe");
//		document.ajouterFichierTexte(dir + "resources/koom/principe_des_defis.txt");
////		document.ajouterSoustitre("Description du défi proposé");
//		document.ajouterSoustitre("Le défi : " + intituleDefi);
//		document.ajouterTexte(descriptionDefi);
////		document.ajouterFichierTexte(dir + "resources/koom/description_du_defi.txt"); //TODO ajouter le texte saisi ici
//		document.ajouterSoustitre("Enjeux du défi");
//		document.ajouterFichierTexte(dir + "resources/koom/enjeux_du_defi.txt"); //TODO ajouter le texte saisi ici
//		document.ajouterSoustitre("Impacts");
//			document.ajouterSoussoustitre("Impacts environnementaux");
//			document.ajouterFichierTexte(dir + "resources/koom/impacts_environnementaux.txt"); //TODO ajouter les résultats des simulations
//			document.ajouterSoussoustitre("Impacts sur l'image de l'entreprise");
//			document.ajouterFichierTexte(dir + "resources/koom/impacts_image_entreprise.txt"); //TODO ajouter les résultats des simulations
//		document.ajouterSoustitre("Simulation budgétaire");
//		document.ajouterFichierTexte(dir + "resources/koom/simulation_budgetaire.txt"); //TODO ajouter les résultats budgétaires
//			document.ajouterSoussoustitre("Méthode de calcul");
//			document.ajouterFichierTexte(dir + "resources/koom/simulation_budgetaire_methode_calcul.txt");
//			document.ajouterSoussoustitre("Application");
//			document.ajouterFichierTexte(dir + "resources/koom/simulation_budgetaire_application.txt");
//
//		document.ajouterTitre("Proposition de partenariat avec Koom");
//		document.ajouterTexte("Le site http://www.koom.org vous offre plusieurs possibilités de promotion de vos actions en faveur du Développement Durable.");
//		// Les liens hypertextes sont interprŽtŽs directement dans cette fonction
//		document.ajouterSoustitre("Création d'une page de promotion");
//		document.ajouterFichierTexte(dir + "resources/koom/partenariat_page_de_promotion.txt");
//		document.ajouterSoustitre("Géolocalisation avancée de vos enseignes");
//		document.ajouterFichierTexte(dir + "resources/koom/partenariat_geolocalisation.txt");
//		document.ajouterSoustitre("Encart publicitaire");
//		document.ajouterFichierTexte(dir + "resources/koom/partenariat_encart.txt"); // TODO dépend de l'action choisie pour le dŽfi
//		
//		document.ajouterTitreIndependant("Conclusion");
//		document.ajouterFichierTexte(dir + "resources/koom/conclusion.txt"); // TODO dépend des titres de l'action et du dŽfi choisis
//
//		// Fin du document (crŽdits)
//		document.add(new Paragraph(" "));
//		document.add(new Paragraph(" "));
//		document.add(new Paragraph(" "));
//		document.ajouterTexte("Jérôme Lhote, co-fondateur, Président de Koom");
//		document.add(new Paragraph(" "));
//		document.ajouterTexte("Alexis Delaporte, co-fondateur, Directeur des Systèmes d'Information");
//		document.add(new Paragraph(""));
//		document.add(new Paragraph(" "));
//		document.ajouterLigneDeTexte("Soyez le changement que vous souhaitez pour ce monde, Mahatmah Gandhi", Font.ITALIC, Element.ALIGN_RIGHT);
//		document.add(new Paragraph(""));
//		document.add(new Paragraph(" "));
//		document.ajouterTexte("Document généré suite à la saisie d'un défi par " + nomKoomer // TODO dépend du Koomer
//		+ " à partir d'un outil développé par Serge Prod'homme, Architecte mobile et Java, attaché commercial.");
//			
//		// Obligatoire pour enregistrer les modifications
//		document.close();
		
		return nomFichier;
//		return document;
	}
	
}
