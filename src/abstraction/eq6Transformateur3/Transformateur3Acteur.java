package abstraction.eq6Transformateur3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.IProduit;

public class Transformateur3Acteur implements IActeur {
	
	protected int cryptogramme;
	private Journal journalTransfo;
	private int nEtape = 0;

	public Transformateur3Acteur() {
		this.journalTransfo = new Journal("Activites Transfo_6", this);
	}
	
	public void initialiser() {
	}

	public String getNom() {// NE PAS MODIFIER
		return "EQ6";
	}
	
	public String toString() {// NE PAS MODIFIER
		return this.getNom();
	}

	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
		//"agios \"autorises\" de "+Journal.texteColore(a.getColor(), Color.BLACK, Journal.texteSurUneLargeurDe(a.getNom(),10))+" d'un mondant de "+Journal.doubleSur(montantAgiosAutorises, 15,3))
		this.journalTransfo.ajouter("Étape n°"+this.nEtape);
		this.nEtape++;
	}

	public Color getColor() {// NE PAS MODIFIER
		return new Color(158, 242, 226); 
	}

	public String getDescription() {
		return "Bla bla bla";
	}

	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
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
			return 0; // Les acteurs non assermentes n'ont pas a connaitre notre stock
		}
	}
}
