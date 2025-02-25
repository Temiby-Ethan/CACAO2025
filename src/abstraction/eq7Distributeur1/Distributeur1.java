package abstraction.eq7Distributeur1;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.acteurs.TransformateurXActeur;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Feve;

public class Distributeur1 extends Distributeur1Acteur  {
	
	private Journal journal;  // Déclaration du journal
	private Variable stockChocolat;

	public Distributeur1() {
		super();

		this.journal = new Journal("Journal de EQX", this); // Initialisation du journal
		this.stockChocolat = new Variable("Stock de chocolat", this, 0.0); 
	}

	public void next() // par Alexiho
	{
		int etape = Filiere.LA_FILIERE.getEtape(); // Récupération du numéro de l'étape
        //journal.ajouter("Étape " + etape + " : Avancement de la simulation."); // Ajout d'une entrée dans le journal

		//List<String> marques = getMarquesChocolat() ;

		ChocolatDeMarque produit = new ChocolatDeMarque(Chocolat.C_MQ, "Villors", 50); // Produit choisi
        double quantiteAjoutee = 100.0; // 100 tonnes

        // Mettre en rayon (ajouter au stock)
        stockChocolat.ajouter(this, quantiteAjoutee);

        // Enregistrement dans le journal
        journal.ajouter("Étape " + etape + " : Ajout de " + quantiteAjoutee + " t de " + produit + " en rayon.");

		System.out.println(journal);
	}
}
