package abstraction.eq7Distributeur1;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.acteurs.TransformateurXActeur;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur1 extends Distributeur1Acteur  {
	
	private Journal journal;  // Déclaration du journal
	private double stockC_HQ_BE; // CHOCOLAT HAUTE QUALITE BIO EQUITABLE
	private double stockC_HQ_E; // CHOCOLAT HAUTE QUALITE EQUITABLE
	private double stockC_MQ_E; // CHOCOLAT MOYENNE QUALITE EQUITABLE
	private double stockC_MQ;// CHOCOLAT MOYENNE QUALITE (NI BIO NI EQUITABLE)
	private double stockC_BQ_E; // CHOCOLAT BASSE QUALITE EQUITABLE
	private double stockC_BQ; // CHOCOLAT BASSE QUALITE (NI BIO NI EQUITABLE)

	public Distributeur1() {
		super();

		this.journal = new Journal("Journal de EQX", this); // Initialisation du journal
		this.stockC_HQ_BE = 0.0; // CHOCOLAT HAUTE QUALITE BIO EQUITABLE
		this.stockC_HQ_E = 0.0; // CHOCOLAT HAUTE QUALITE EQUITABLE
		this.stockC_MQ_E = 0.0; // CHOCOLAT MOYENNE QUALITE EQUITABLE
		this.stockC_MQ = 0.0;// CHOCOLAT MOYENNE QUALITE (NI BIO NI EQUITABLE)
		this.stockC_BQ_E = 0.0; // CHOCOLAT BASSE QUALITE EQUITABLE
		this.stockC_BQ = 0.0; // CHOCOLAT BASSE QUALITE (NI BIO NI EQUITABLE)
	}

	public void next() // par Alexiho
	{
		int etape = Filiere.LA_FILIERE.getEtape(); // Récupération du numéro de l'étape
        //journal.ajouter("Étape " + etape + " : Avancement de la simulation."); // Ajout d'une entrée dans le journal

		//List<String> marques = getMarquesChocolat() ;

		ChocolatDeMarque produit = new ChocolatDeMarque(Chocolat.C_MQ, "Villors", 50); // Produit choisi
        double quantiteAjoutee = 100.0; // 100 tonnes

        // Mettre en rayon (ajouter au stock)
        this.stockC_MQ += quantiteAjoutee;

        // Enregistrement dans le journal
        journal.ajouter("Étape " + etape + " : Ajout de " + quantiteAjoutee + " t de " + produit + " en rayon.");

		System.out.println(journal);
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme) {
		if (this.cryptogramme == cryptogramme) { // Vérification que l'accès est autorisé
			if (p == Chocolat.C_HQ_BE) {
				return stockC_HQ_BE;
			} else if (p == Chocolat.C_HQ_E) {
				return stockC_HQ_E;
			} else if (p == Chocolat.C_MQ_E) {
				return stockC_MQ_E;
			} else if (p == Chocolat.C_MQ) {
				return stockC_MQ;
			} else if (p == Chocolat.C_BQ_E) {
				return stockC_BQ_E;
			} else if (p == Chocolat.C_BQ) {
				return stockC_BQ;
			} else {
				return 0; // Produit non reconnu
			}
		} else {
			return 0; // Les acteurs non assermentés n'ont pas à connaître notre stock
		}
	}
	
}
