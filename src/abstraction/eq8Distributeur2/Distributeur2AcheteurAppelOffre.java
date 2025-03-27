package abstraction.eq8Distributeur2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import java.util.Iterator;
import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.appelDOffre.IAcheteurAO;
import abstraction.eqXRomu.appelDOffre.OffreVente;
import abstraction.eqXRomu.appelDOffre.SuperviseurVentesAO;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;




public class Distributeur2AcheteurAppelOffre extends Distributeur2AcheteurContratCadre implements IAcheteurAO {
    
    
    private static final double PRIX_MAX_TONNE = 5000.0;
    private HashMap<ChocolatDeMarque, List<Double>> prixRetenus;
	private SuperviseurVentesAO supAO;
	protected Journal journalAO;
	protected HashMap<Integer,OffreVente> choix;
    
    public Distributeur2AcheteurAppelOffre(){
        super();
        this.journalAO = new Journal(this.getNom() +"Journal AO", this);
        
    }

    public void initialiser() {
		super.initialiser();
		this.supAO = (SuperviseurVentesAO)(Filiere.LA_FILIERE.getActeur("Sup.AO"));
		
        this.prixRetenus = new HashMap<ChocolatDeMarque, List<Double>>();
		for (ChocolatDeMarque cm : this.stock_Choco.keySet()) {
			this.prixRetenus.put(cm, new LinkedList<Double>());
		}		
	}

    
    
    public OffreVente choisirOV(List<OffreVente> propositions) {
        if (propositions == null || propositions.isEmpty()) {
            return null;
        }

        OffreVente meilleureOffre = null;

        for (OffreVente offre : propositions) {
            
            if (offre.getPrixT() > PRIX_MAX_TONNE) {
                continue;
            }
            
            if (meilleureOffre == null || offre.getPrixT() < meilleureOffre.getPrixT()) {
                meilleureOffre = offre;
            }
        }

        return meilleureOffre; 
    }


    public void next() {
		super.next();
		this.journalAO.ajouter("=== Étape "+Filiere.LA_FILIERE.getEtape()+" ====================");
		
        for (ChocolatDeMarque cm : this.stock_Choco.keySet()) {
			
            if (produit_voulue.contains(cm) && this.stock_Choco.get(cm)<5000) {
				double quantite = 10000;
				
                OffreVente ov = supAO.acheterParAO(this,  cryptogramme, cm, quantite);
				
                journalAO.ajouter("   Je lance un appel d'offre de "+quantite+"T de "+cm);
				if (ov!=null) { 
					journalAO.ajouter("   AO finalise : on ajoute "+quantite+"T de "+cm+" au stock");
					stock_Choco.put(cm, stock_Choco.get(cm)+quantite);
					stockTotal.ajouter(this, quantite, cryptogramme);
					prixRetenus.get(cm).add(ov.getPrixT());
					
                    if (prixRetenus.get(cm).size()>10) {
						prixRetenus.get(cm).remove(0); 
						journalAO.ajouter("   Les derniers prix pour "+cm+" sont "+prixRetenus.get(cm));
					}
				}
			}
		}
    }

    public List<Journal> getJournaux() {
		
		List<Journal> jour = super.getJournaux();
		jour.add(this.journalAO);
		return jour;
	}


}