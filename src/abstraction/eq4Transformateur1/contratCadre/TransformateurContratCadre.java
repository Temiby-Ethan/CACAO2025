package abstraction.eq4Transformateur1.contratCadre;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.*;
import abstraction.eqXRomu.general.*;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.contratsCadres.*;
import abstraction.eq4Transformateur1.Transformateur1Stocks;
import abstraction.eq4Transformateur1.Transformateur1AcheteurBourse;

/*
 * @author MURY Julien
 * Cette classe décrit le comportement de Transfromateur1 lors d'un contrat cadre
 */
public class TransformateurContratCadre extends Transformateur1AcheteurBourse {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	private int numero;
	private IProduit produit;
	protected Journal journal;
	protected SuperviseurVentesContratCadre supCCadre;

	public TransformateurContratCadre() {	
		super();
	
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		

	}
	
	public String getNom() {
		return "TCC"+this.numero+""+produit.toString();
	}

	public String getDescription() {
		return "TransformateurContratCadre "+this.numero+" "+this.produit.toString();
	}

	public Color getColor() {
		return new Color(128+((numero)*(127/NB_INSTANCES)), 64+((numero)*(191/NB_INSTANCES)), 0);
	}

	public void initialiser() {
		super.initialiser();
		this.supCCadre = (SuperviseurVentesContratCadre) (Filiere.LA_FILIERE.getActeur("Sup.CCadre")); //Creation d'un superviseur pour la négociation du contrat cadre
	}

	public void next() {
		super.next();
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res= super.getIndicateurs();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res= super.getParametres();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> j= new ArrayList<Journal>();
		j.add(this.journal);
		return j;
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public void notificationFaillite(IActeur acteur) {
	}
	
	public void notificationOperationBancaire(double montant) {
	}
	
	public String toString() {
		return this.getNom();
	}
	
	
}
