package abstraction.eq4Transformateur1;

import java.awt.Color;

import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.appelDOffre.AppelDOffre;
import abstraction.eqXRomu.appelDOffre.IVendeurAO;
import abstraction.eqXRomu.appelDOffre.OffreVente;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque; 


/**
 * @author YAOU Reda
 */

public class Transformateur1VendeurAppelDoffre extends Transformateur1AcheteurBourse implements IVendeurAO {

    public Transformateur1VendeurAppelDoffre() {
		super();
	}

	public void initialiser(){
		super.initialiser();
	}

	@Override
	public OffreVente proposerVente(AppelDOffre offre) {
;
		double prixT = 0;

		if (chocolatsLimDt.contains(offre.getProduit())) {
			if (offre.getQuantiteT() <= 0.2*this.getQuantiteEnStock(offre.getProduit(), this.cryptogramme)) {
				if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_MQ) {
					prixT = (prixTChocoBase.get(Chocolat.C_MQ) + this.coutProd + this.coutStockage) * 1.3;
				} 
				else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_BQ_E) {
					prixT = (prixTChocoBase.get(Chocolat.C_BQ_E)+ this.coutProd + this.coutStockage) *1.1;
				} 
				else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_MQ_E) {
					prixT =(prixTChocoBase.get(Chocolat.C_MQ_E)+ this.coutProd + this.coutStockage) *1.1;
				} 
				else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_HQ_BE) {
					prixT = (prixTChocoBase.get(Chocolat.C_HQ_BE)+ this.coutProd + this.coutStockage) * 1.2;
				}
	
				if (prixT == 0) {
					return null;
				}
				
				this.journalTransactions.ajouter(Color.white, Color.RED, "AO: Je propose " + offre.getQuantiteT() + " tonnes de " + offre.getProduit() + " au cours de " + prixT + " euros par tonne.");
				return new OffreVente(offre, this, offre.getProduit(), prixT);
			
		    } else {
			    journalTransactions.ajouter(Color.pink, Color.RED, "--> AO "+ offre.getProduit() +" : Je ne peux pas proposer " + offre.getQuantiteT() + " tonnes.");
				journalTransactions.ajouter("\n");
			    return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public void notifierVenteAO(OffreVente propositionRetenue) {

		this.journalTransactions.ajouter(Romu.COLOR_LLGRAY, Color.RED, "-->AO "+ propositionRetenue.getProduit() +" : J'ai vendu " +propositionRetenue.getQuantiteT()+ " tonnes au prix de " + propositionRetenue.getPrixT() + " euros par tonne.");
		this.journalTransactions.ajouter("\n");
	 
		//Mettre à jour le stock et la qtt sortante par transaction
		ChocolatDeMarque chocoMarqueAO = (ChocolatDeMarque) propositionRetenue.getProduit();
		this.retirerDuStock(chocoMarqueAO, propositionRetenue.getQuantiteT(), this.cryptogramme);
		this.qttSortantesTransactions.put(chocoMarqueAO.getChocolat(), this.qttSortantesTransactions.get(chocoMarqueAO.getChocolat()) + propositionRetenue.getQuantiteT());
	}
	

	@Override
	public void notifierPropositionNonRetenueAO(OffreVente propositionRefusee) {

		this.journalTransactions.ajouter(Color.pink, Color.RED, "--> AO "+propositionRefusee.getProduit()+" : Vente non retenue.");
		this.journalTransactions.ajouter("\n");

	}
}


