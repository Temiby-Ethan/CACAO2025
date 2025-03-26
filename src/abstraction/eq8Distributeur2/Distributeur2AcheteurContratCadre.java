package abstraction.eq8Distributeur2;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariablePrivee;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur2AcheteurContratCadre extends Distributeur2Vendeur implements IAcheteurContratCadre{

	private SuperviseurVentesContratCadre supCC;
	protected List<ExemplaireContratCadre> contrat_en_cours;
	protected List<ExemplaireContratCadre> contrat_term;
	protected Journal journalCC;
	protected  int test;
	protected List<ExemplaireContratCadre> choix;
	protected Double attract_tot;

	public Distributeur2AcheteurContratCadre() {
		super();
		this.contrat_en_cours = new LinkedList<ExemplaireContratCadre>();
		this.contrat_term= new LinkedList<ExemplaireContratCadre>();
		this.journalCC= new Journal (this.getNom() + "journal CC", this);
		this.choix=new LinkedList<ExemplaireContratCadre>();
		this.test=1;
	}
    
    public boolean achete(IProduit produit) {
        return produit instanceof ChocolatDeMarque && stockChocoMarque.containsKey(produit) && stockChocoMarque.get(produit) <= 100000;
    }

    
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
        return null;
    }

    
    public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
        return 0;
    }

    
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {

    }

    
    public void receptionner(IProduit p, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
        stockChocoMarque.put((ChocolatDeMarque) p,this.stockChocoMarque.get(p)+quantiteEnTonnes);
        journalActeur.ajouter("reception de "+quantiteEnTonnes+" tonnes de "+p.toString()+" du contrat "+ contrat.toString());
        this.MAJStocks();
    }
}
