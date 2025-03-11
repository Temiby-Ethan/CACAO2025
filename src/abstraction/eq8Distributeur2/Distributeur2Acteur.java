package abstraction.eq8Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur2Acteur implements IActeur {
	

	//stockTotal et journal par Tidiane
	private Journal journal_next = new Journal("journal Eq8", this);
	
	protected Variable stockTotal;
	protected int cryptogramme;
	protected double coutStockage;
	protected IProduit produit;
	protected List<ChocolatDeMarque> chocolats;
	protected HashMap<ChocolatDeMarque, Double> stock_Choco;
	protected HashMap<Chocolat,Integer> nombreMarquesParType;
	protected HashMap<Chocolat, Variable> variables;
	protected List<ChocolatDeMarque> chocoProduits;

	public Distributeur2Acteur() {
		stockTotal = new Variable("Volume total du stock de l'EQ8", "Volume total du stock", this);
		this.chocolats = new LinkedList<ChocolatDeMarque>();
		this.chocoProduits = new LinkedList<ChocolatDeMarque>();
		this.variables= new HashMap<Chocolat, Variable>();
		for (Chocolat c : Chocolat.values()) {
			this.variables.put(c,new Variable ("EQ8 stock de : "+c,this,0));}
	}
	
	public void initialiser() {
	}

	public String getNom() {// NE PAS MODIFIER
		return "EQ8";
	}
	
	public String toString() {// NE PAS MODIFIER
		return this.getNom();
	}

	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
		//Journal par Tidiane
		journal_next.ajouter("" + Filiere.LA_FILIERE.getEtape());
	}

	public Journal getJournal(){
		return this.journal_next;
	}

	public Variable getStockTotal(){
		return stockTotal;
	}
	
	public Color getColor() {// NE PAS MODIFIER
		return new Color(209, 179, 221); 
	}

	public String getDescription() {
		return "Bla bla bla";
	}

	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.add(getStockTotal());
		return res;
	}

	// Renvoie les parametres
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		
		
		return res;
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(getJournal());
		return res;
	}

	////////////////////////////////////////////////////////
	//               En lien avec la Banque               //
	////////////////////////////////////////////////////////

	// Appelee en debut de simulation pour vous communiquer 
	// votre cryptogramme personnel, indispensable pour les
	// transactions.
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	// Appelee lorsqu'un acteur fait faillite (potentiellement vous)
	// afin de vous en informer.
	public void notificationFaillite(IActeur acteur) {
	}

	// Apres chaque operation sur votre compte bancaire, cette
	// operation est appelee pour vous en informer
	public void notificationOperationBancaire(double montant) {
	}
	
	// Renvoie le solde actuel de l'acteur
	protected double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
	}

	////////////////////////////////////////////////////////
	//        Pour la creation de filieres de test        //
	////////////////////////////////////////////////////////

	// Renvoie la liste des filieres proposees par l'acteur
	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		return(filieres);
	}

	// Renvoie une instance d'une filiere d'apres son nom
	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme) {
		if (this.cryptogramme==cryptogramme) { // c'est donc bien un acteur assermente qui demande a consulter la quantite en stock
			return 0; // A modifier
		} else {
			System.out.println("Cet acteur n'est pas assermenté");
			return 0; // Les acteurs non assermentes n'ont pas a connaitre notre stock
		}
	}
	
	

	
}
