package abstraction.eq8Distributeur2;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;


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

	
	protected List<ExemplaireContratCadre> contrat_en_cours;
	protected List<ExemplaireContratCadre> contrat_term;
	protected Journal journalCC;
	
	
    protected List<ChocolatDeMarque> produit_voulue;

	public Distributeur2AcheteurContratCadre() {
		super();
		this.contrat_en_cours = new LinkedList<ExemplaireContratCadre>();
		this.contrat_term = new LinkedList<ExemplaireContratCadre>();
		this.journalCC = new Journal (this.getNom() + "journal CC", this);

		this.produit_voulue = new LinkedList<ChocolatDeMarque>();
        
	}
    
    //@author ArmandCHANANE
    public void initialiser() {
		super.initialiser();
        for (ChocolatDeMarque cm : chocolats){
            if (cm.getChocolat() == Chocolat.C_HQ_E || cm.getChocolat() == Chocolat.C_HQ_BE || cm.getChocolat() == Chocolat.C_MQ_E){
                produit_voulue.add(cm);
            }
        }
        
    }
    
    //@author tidzzz
    public boolean achete(IProduit produit) {
        if (produit instanceof ChocolatDeMarque 
        && produit_voulue.contains(produit)   
        && stock_Choco.get(produit) <= 100000){
            journalCC.ajouter("Demande d'achat de " + produit.toString() + " acceptée.");
            return true;
        }
        return false;
    }

    //@author ArmandCHANANE
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
        journalCC.ajouter("Contre-proposition sur l'échéancier pour le contrat " + contrat.toString() + " : aucun changement.");
        return contrat.getEcheancier();
    }
    

    //@author ArmandCHANANE
    public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
        double prixPropose = contrat.getPrix();
        journalCC.ajouter("Contre-proposition de prix pour le contrat " + contrat.toString() + " : " + prixPropose + " euros la tonne.");
        // Exemple : Accepter le prix proposé. Pour renoncer aux négociations, il suffirait de retourner une valeur négative ou nulle.
        return prixPropose;
    }

    //@author ArmandCHANANE
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        journalCC.ajouter("Nouveau contrat cadre conclu : " + contrat);
    }

    //@author tidzzz
    public void receptionner(IProduit p, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
        stock_Choco.put((ChocolatDeMarque) p,this.stock_Choco.get(p)+quantiteEnTonnes);
        journal.ajouter("Réception de " + quantiteEnTonnes + " tonnes de " + p.toString() + " du contrat " + contrat.toString());
        journalCC.ajouter("Réception de " + quantiteEnTonnes + " tonnes de " + p.toString() + " pour le contrat " + contrat.toString());
    }

    //@author tidzzz
    public List<Journal> getJournaux() {
		
		List<Journal> jour = super.getJournaux();
		jour.add(this.journalCC);
		return jour;
	}
}
