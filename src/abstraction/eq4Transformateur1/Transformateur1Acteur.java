package abstraction.eq4Transformateur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.filiere.IMarqueChocolat;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariablePrivee;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Transformateur1Acteur implements IActeur, IMarqueChocolat {

	protected Journal journal;	
	protected Journal journalStock;
	protected Journal journalCC;
	protected Journal journalTransactions;
	protected int cryptogramme;

	protected List<Feve> lesFeves; 
	protected HashMap<Feve, Double> stockFeves;
	protected HashMap<Chocolat, Double> stockChoco;
	protected HashMap<ChocolatDeMarque, Double> stockChocoMarque;


	protected Variable totalStocksFeves;  // La quantite totale de stock de feves 
	protected Variable totalStocksChoco;  // La quantite totale de stock de chocolat 

	protected Variable totalStocksChocoMarque;  // La quantite totale de stock de chocolat de marque
	protected Variable totalStocksChocoNonMarquee; // La quantie totale de stock de chocolat non marquee

	public Transformateur1Acteur() {

		this.journal = new Journal("Journal " + this.getNom(), this);
		this.journalStock = new Journal("Journal Stock " + this.getNom(), this);
		this.journalCC = new Journal("Journal CC " + this.getNom(), this);
		this.journalTransactions = new Journal("Journal Transactions " + this.getNom(), this);

		//Constructions des variables de stocks (quantités globales)
		this.totalStocksFeves = new VariablePrivee("Eq4TStockFeves", "<html>Quantite totale de feves en stock</html>",this, 0.0, 1000000.0, 0.0);
		this.totalStocksChoco = new VariablePrivee("Eq4TStockTotalChoco", "<html>Quantite totale de chocolat en stock</html>",this, 0.0, 1000000.0, 0.0);
		this.totalStocksChocoMarque = new VariablePrivee("Eq4TStockChocoMarque", "<html>Quantite totale de chocolat de marque en stock</html>",this, 0.0, 1000000.0, 0.0);
		this.totalStocksChocoNonMarquee = new VariablePrivee("Eq4TStockChocoNonMarquee", "<html>Quantite totale de chocolat non marquee en stock</html>",this, 0.0, 1000000.0, 0.0);
	}

	public void initialiser() {
		this.lesFeves = new LinkedList<Feve>();
		this.lesFeves.add(Feve.F_HQ_BE);
		this.lesFeves.add(Feve.F_MQ_E);
		this.lesFeves.add(Feve.F_BQ_E);
		this.lesFeves.add(Feve.F_MQ);
		this.lesFeves.add(Feve.F_BQ);
		this.journal.ajouter("N° Etape " + Filiere.LA_FILIERE.getEtape());

		//Test stock de fèves
		this.stockFeves=new HashMap<Feve,Double>();
		for (Feve f : this.lesFeves) {
			this.stockFeves.put(f, 0.0);
			this.totalStocksFeves.ajouter(this, 0.0, this.cryptogramme);
			this.journalStock.ajouter("Initialisation de 0 de "+f+" au stock de chocolat --> total="+this.totalStocksFeves.getValeur(this.cryptogramme));
		}

		//Test stock de choco
		this.stockChoco=new HashMap<Chocolat,Double>();
		for (Chocolat c : Chocolat.values()) {
			this.stockChoco.put(c, 0.0);
			this.totalStocksChoco.ajouter(this, 0.0, this.cryptogramme);
			this.journalStock.ajouter("Initialisation de 0 de "+c+" au stock de chocolat --> total="+this.totalStocksChoco.getValeur(this.cryptogramme));
		}

	}

	public void main(){
		System.out.println(Feve.values());
	}

	public String getNom() {// NE PAS MODIFIER
		return "EQ4T";
	}
	
	public String toString() {// NE PAS MODIFIER
		return this.getNom();
	}

	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
		

		this.journalStock.ajouter("Stock de fèves : " + this.totalStocksFeves.getValeur(this.cryptogramme));
		this.journalStock.ajouter("Stock de chocolat : " + this.totalStocksChoco.getValeur(this.cryptogramme));
		this.journalStock.ajouter("Stock de chocolat de marque : " + this.totalStocksChocoMarque.getValeur(this.cryptogramme));
		this.journal.ajouter("Solde : " + this.getSolde());

	}

	public Color getColor() {// NE PAS MODIFIER
		return new Color(229, 243, 157); 
	}

	public String getDescription() {
		return "Transformateur spécialisé dans le chocolat équitable à petits prix";
	}

	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.add(this.totalStocksFeves);
		res.add(this.totalStocksChoco);
		res.add(this.totalStocksChocoMarque);
		res.add(this.totalStocksChocoNonMarquee);
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
		res.add(this.journal);
		res.add(this.journalStock);
		res.add(this.journalTransactions);
		res.add(this.journalCC);
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
		journal.ajouter("Opération sur compte bancaire : " +  montant);
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

	@Override
	public List<String> getMarquesChocolat() {
		LinkedList<String> marques = new LinkedList<String>();
		marques.add("LimDt");
		return marques;
	}

	@Override
	public double getQuantiteEnStock(IProduit p, int cryptogramme) {
		if (this.cryptogramme==cryptogramme) { // c'est donc bien un acteur assermente qui demande a consulter la quantite en stock
			if (p instanceof Feve) {
				if (this.stockFeves.keySet().contains(p)) {
					return this.stockFeves.get(p);
				} else {
					return 0.0;
				}
			} else if (p instanceof Chocolat) {
				if (this.stockChoco.keySet().contains(p)) {
					return this.stockChoco.get(p);
				} else {
					return 0.0;
				}
			} else {
				if (this.stockChocoMarque.keySet().contains(p)) {
					return this.stockChocoMarque.get(p);
				} else {
					return 0.0;
				}
			}
		} else {
			return 0; // Les acteurs non assermentes n'ont pas a connaitre notre stock
		}
	}
}
