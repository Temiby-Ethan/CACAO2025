package abstraction.eq7Distributeur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.acteurs.TransformateurXActeur;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur1 extends Distributeur1Acteur  {
	
	// défi 1 et 2 par Alexiho
	private Journal journal;  // Déclaration du journal
	private Map<Chocolat, Double> stockChocolat; // Table de hachage pour stocker les quantités de chocolat

    public Distributeur1() { // par Alexiho
        super();
        
        this.journal = new Journal("Journal de EQ7", this); // Initialisation du journal
        this.stockChocolat = new HashMap<>();
        
        // Initialisation des stocks à 0.0
        stockChocolat.put(Chocolat.C_HQ_BE, 0.0); // CHOCOLAT HAUTE QUALITE BIO EQUITABLE
        stockChocolat.put(Chocolat.C_HQ_E, 0.0);  // CHOCOLAT HAUTE QUALITE EQUITABLE
        stockChocolat.put(Chocolat.C_MQ_E, 0.0);  // CHOCOLAT MOYENNE QUALITE EQUITABLE
        stockChocolat.put(Chocolat.C_MQ, 0.0);   // CHOCOLAT MOYENNE QUALITE (NI BIO NI EQUITABLE)
        stockChocolat.put(Chocolat.C_BQ_E, 0.0);  // CHOCOLAT BASSE QUALITE EQUITABLE
        stockChocolat.put(Chocolat.C_BQ, 0.0);   // CHOCOLAT BASSE QUALITE (NI BIO NI EQUITABLE)
    }

	public void next() // par Alexiho
	{
		int etape = Filiere.LA_FILIERE.getEtape(); // Récupération du numéro de l'étape
        //journal.ajouter("Étape " + etape + " : Avancement de la simulation."); // Ajout d'une entrée dans le journal

		//List<String> marques = getMarquesChocolat() ;

		ChocolatDeMarque produit = new ChocolatDeMarque(Chocolat.C_MQ, "Villors", 50); // Produit choisi
        double quantiteAjoutee = 100.0; // 100 tonnes

        // Mettre en rayon (ajouter au stock)
        //this.stockC_MQ += quantiteAjoutee;

		stockChocolat.put(Chocolat.C_MQ, stockChocolat.get(Chocolat.C_MQ) + quantiteAjoutee);

        // Enregistrement dans le journal
        journal.ajouter("Étape " + etape + " : Ajout de " + quantiteAjoutee + " t de " + produit + " en rayon.");

		System.out.println(journal);
		System.out.println(stockChocolat.get(Chocolat.C_MQ));
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme) { // par Alexiho
		if (this.cryptogramme == cryptogramme) { // Vérification que l'accès est autorisé
			return stockChocolat.get(p);
		} else {
			return 0; // Les acteurs non assermentés n'ont pas à connaître notre stock
		}
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() { // par Alexiho
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
		return res;
	}
	
}
