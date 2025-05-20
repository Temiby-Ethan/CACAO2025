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
import abstraction.eqXRomu.produits.Chocolat;


public class Distributeur2AcheteurAppelOffre extends Distributeur2AcheteurContratCadre implements IAcheteurAO {
    

    private static final double PRIX_MAX_TONNE = 50000.0;
    private HashMap<ChocolatDeMarque, List<Double>> prixRetenus;
	private SuperviseurVentesAO supAO;
	protected Journal journalAO;
	protected HashMap<Integer,OffreVente> choix;
    private Journal journalStock;
    private double coutStockage;

    
    public Distributeur2AcheteurAppelOffre(){
        super();
        this.journalAO = new Journal(this.getNom() +"Journal AO", this);
        journalStock = new Journal(this.getNom() + " journal stock", this);
    }

    //@author pebinoh
    public void initialiser() {
		super.initialiser();
		this.supAO = (SuperviseurVentesAO)(Filiere.LA_FILIERE.getActeur("Sup.AO"));
        this.coutStockage = Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur() * 16;

		
        this.prixRetenus = new HashMap<ChocolatDeMarque, List<Double>>();
		for (ChocolatDeMarque cm : this.stock_Choco.keySet()) {
			this.prixRetenus.put(cm, new LinkedList<Double>());
		}
        		
	}

    
    //@author ArmandChanane
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

    //@author tidzzz
    public void next() {
		super.next();
		this.journalAO.ajouter("=== Étape "+Filiere.LA_FILIERE.getEtape()+" ====================");
		
        for (ChocolatDeMarque cm : this.stock_Choco.keySet()) {
			
            if (produit_voulue.contains(cm) && this.stock_Choco.get(cm)<5000) {
				double quantite = 10000;
				
                OffreVente ov = supAO.acheterParAO(this,  cryptogramme, cm, quantite);
				
                journalAO.ajouter("Je lance un appel d'offre de "+quantite+"T de "+cm);
				if (ov!=null) { 
                    journalAO.ajouter(Romu.COLOR_LLGRAY,Romu.COLOR_GREEN,"AO finalise : on ajoute "+quantite+"T de "+cm+" au stock");
					stock_Choco.put(cm, stock_Choco.get(cm)+quantite);
					
					prixRetenus.get(cm).add(ov.getPrixT());
					
                    if (prixRetenus.get(cm).size()>10) {
						prixRetenus.get(cm).remove(0); 
						journalAO.ajouter("   Les derniers prix pour "+cm+" sont "+prixRetenus.get(cm));
					}
				}
			}
		}

		
        // maj stock total
         double nouveauStockTotal = getQuantiteEnStockTotal();
         stockTotal.setValeur(this, nouveauStockTotal, cryptogramme);
         this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_LBLUE,"==================== STOCK TOTAL étape: "+Filiere.LA_FILIERE.getEtape()+" = "+nouveauStockTotal+" ====================");
            
        // maj stock chocolat qualite
		for (Chocolat choc : Chocolat.values()) {
			double totalStock = 0;
			for (ChocolatDeMarque cm : chocolats) {
				if (cm.getChocolat().equals(choc)) {
					totalStock += stock_Choco.get(cm);
				}
			}
			stock_chocolat_qualite.get(choc).setValeur(this, totalStock, cryptogramme);
			journal.ajouter("stock chocolat "+choc.toString()+" : "+stock_chocolat_qualite.get(choc).getValeur());
		}
		this.journal.ajouter("");

        for (Chocolat choc : Chocolat.values()) {
            journalStock.ajouter(Romu.COLOR_LBLUE, Romu.COLOR_LLGRAY, "=== Stock pour la qualité de chocolat : " + choc + " ===");
            for (ChocolatDeMarque cm : this.stock_Choco.keySet()) {
            if (cm.getChocolat().equals(choc)) {
                journalStock.ajouter(Romu.COLOR_LBLUE, Romu.COLOR_LLGRAY, "Stock de " + cm + " : " + this.stock_Choco.get(cm));
            }
            }
        }
        journalStock.ajouter(Romu.COLOR_LBLUE, Romu.COLOR_LLGRAY, "Stock total : " + this.stockTotal.getValeur(cryptogramme));
        journalStock.ajouter(Romu.COLOR_LBLUE, Romu.COLOR_LLGRAY, "====================================================");

        // paiement des stocks
        double montant_a_payer_stock = stockTotal.getValeur(cryptogramme) * coutStockage;
        Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "coutStockage", montant_a_payer_stock);

        // paiement des employes
        double salaire = 1200;
        int nb_employes = (int) Math.ceil(0.0067 * stockTotal.getValeur(cryptogramme));
        double montant_a_payer_salaire = nb_employes * salaire;
        Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "salaire", montant_a_payer_salaire);

        // payer mise en rayon
        double coutParT = 0.03 * 1000;
        double coutParTTG = 0.08 * 1000;

        double montant_a_payer_rayon = coutParT * this.quantiteEnVenteTotal() + coutParTTG * this.quantiteEnVenteTGTotal();
        Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "coutMiseEnRayon", montant_a_payer_rayon);

        
        journal.ajouter(Romu.COLOR_LPURPLE, Romu.COLOR_LLGRAY, "Montant à payer pour le stockage : " + montant_a_payer_stock + "€");
        journal.ajouter(Romu.COLOR_LPURPLE, Romu.COLOR_LLGRAY, "Montant à payer pour les employés : " + montant_a_payer_salaire + "€");
        journal.ajouter(Romu.COLOR_LPURPLE, Romu.COLOR_LLGRAY, "Montant à payer pour la mise en rayon : " + montant_a_payer_rayon + "€");
        journal.ajouter(Romu.COLOR_LPURPLE, Romu.COLOR_LLGRAY, String.format(
            "Montant total à payer : %,.2f€",
            (montant_a_payer_stock + montant_a_payer_salaire + montant_a_payer_rayon)
        ));
    }

    //@author pebinoh
    public List<Journal> getJournaux() {
		
		List<Journal> jour = super.getJournaux();
		jour.add(this.journalAO);
        jour.add(this.journalStock);
		return jour;
	}


}