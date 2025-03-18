package abstraction.eq7Distributeur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.filiere.Filiere;
<<<<<<< HEAD
import abstraction.eqXRomu.acteurs.TransformateurXActeur;
import abstraction.eqXRomu.clients.ClientFinal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur1 extends Distributeur1Acteur  {
	
	// défi 1 et 2 par Alexiho
	private Journal journal;  // Déclaration du journal
	protected Map<ChocolatDeMarque, Variable> stockChocolats; // Table de hachage pour stocker les quantités de chocolat
	protected List<ChocolatDeMarque> chocolats;
=======
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.clients.ClientFinal;
import abstraction.eqXRomu.general.Variable;

public class Distributeur1 extends Distributeur1AcheteurAppelOffre  {
	
	// défi 1 et 2 par Alexiho
	private Journal journal;  // Déclaration du journal
	// protected Map<ChocolatDeMarque, Variable> stocksChocolats; // Table de hachage pour stocker les quantités de chocolat
	//protected List<ChocolatDeMarque> chocolats;
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
	private List<Double> prix;
	private List<Double> capaciteDeVente;

    public Distributeur1() { // par Alexiho
        super();
        
        this.journal = new Journal("Journal de EQ7", this); // Initialisation du journal
<<<<<<< HEAD
        this.stockChocolats = new HashMap<>();
=======
		/*
        this.stocksChocolats = new HashMap<>();
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
        
        // Initialisation des stocks à 0.0

		this.chocolats = new ArrayList<ChocolatDeMarque>();
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_HQ_BE, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_HQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_MQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_MQ, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_BQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_BQ, "Hexafridge", 50));
<<<<<<< HEAD

=======
		*/
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
		this.prix = new ArrayList<Double>();
		this.capaciteDeVente = new ArrayList<Double>();

		for (int i=0; i<this.chocolats.size(); i++) {
			this.prix.add(10.0);
			this.capaciteDeVente.add(0.0);
<<<<<<< HEAD
			this.stockChocolats.put(chocolats.get(i), new Variable(this.getNom()+"Stock"+chocolats.get(i).getNom(), this, 1000.0));
		}
    }
	
	public double prix(ChocolatDeMarque choco) {
=======
			//this.stocksChocolats.put(chocolats.get(i), new Variable(this.getNom()+"Stock"+chocolats.get(i).getNom(), this, 1000.0));
		}
    }

	public double prix(ChocolatDeMarque choco) { // par Alexiho
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
		int pos= (chocolats.indexOf(choco));
		if (pos<0) {
			return 0.0;
		} else {
			return prix.get(pos);
		}
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

<<<<<<< HEAD
		capaciteDeVente.set(3, quantiteAjoutee);
=======
		//stockChocolat.put(Chocolat.C_MQ, stockChocolat.get(Chocolat.C_MQ) + quantiteAjoutee);

		capaciteDeVente.set(3, 100.0);
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49

        // Enregistrement dans le journal
        journal.ajouter("Étape " + etape + " : Ajout de " + quantiteAjoutee + " t de " + produit + " en rayon.");

		//System.out.println(journal);
		//System.out.println(stockChocolat.get(Chocolat.C_MQ));
	}

<<<<<<< HEAD
	public double quantiteEnVente(ChocolatDeMarque choco, int crypto) {
=======
	public List<String> getMarquesChocolat() { // par Alexiho
		List<String> marques = new ArrayList<String>();
		marques.add("Hexafridge");
		return marques;
	}

	public double quantiteEnVente(ChocolatDeMarque choco, int crypto) { // par Alexiho
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
		if (crypto!=this.cryptogramme) {
			journal.ajouter("Quelqu'un essaye de me pirater !");
			return 0.0;
		} else {
			int pos= (chocolats.indexOf(choco));
			if (pos<0) {
				return 0.0;
			} else {
				return Math.min(capaciteDeVente.get(pos), this.getStock(choco).getValeur());
			}
		}
	}
<<<<<<< HEAD
=======

>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
	// On met 10% de ce tout ce qu'on met en vente (on pourrait mettre l'accente sur
	// un produit a promouvoir mais il s'agit ici d'un exemple simpliste
	public double quantiteEnVenteTG(ChocolatDeMarque choco, int crypto) { // par Alexiho
		if (crypto!=this.cryptogramme) {
			journal.ajouter("Quelqu'un essaye de me pirater !");
			return 0.0;
		} else {
			int pos= (chocolats.indexOf(choco));
			if (pos<0) {
				return 0.0;
			} else {
				return Math.min(capaciteDeVente.get(pos), this.getStock(choco).getValeur())/10.0;
			}
		}
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant, int crypto) { // par Alexiho
		int pos= (chocolats.indexOf(choco));
		if (pos>=0) {
			this.getStock(choco).retirer(this, quantite);
		}
	}

	public Variable getStock(ChocolatDeMarque c) { // par Alexiho
<<<<<<< HEAD
		return this.stockChocolats.get(c);
=======
		return this.stocksChocolats.get(c);
	}

	public Map<ChocolatDeMarque, Variable> getStocksChocolats() { // par Alexiho
		return this.stocksChocolats;
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme) { // par Alexiho
		if (this.cryptogramme==cryptogramme) {
<<<<<<< HEAD
			for (ChocolatDeMarque c : this.stockChocolats.keySet()) {
				if (c.equals(p)) {
					return this.stockChocolats.get(c).getValeur();
=======
			for (ChocolatDeMarque c : this.stocksChocolats.keySet()) {
				if (c.equals(p)) {
					return this.stocksChocolats.get(c).getValeur();
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
				}
			}
			return 0;
		} else {
			return 0;
		}
	}
<<<<<<< HEAD
=======

>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
	public void notificationRayonVide(ChocolatDeMarque choco) {
		journal.ajouter(" Aie... j'aurais du mettre davantage de "+choco.getNom()+" en vente");
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() { // par Alexiho
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
		return res;
	}
	
<<<<<<< HEAD
}
=======
}
>>>>>>> 9992e9213b5e6215623ff0e38cec5819b349fc49
