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
		//System.err.println(offre.toString());
		double prixT = 0;
		if (chocolatsLimDt.contains(offre.getProduit())) {
			if (offre.getQuantiteT() <= 0.4*this.getQuantiteEnStock(offre.getProduit(), this.cryptogramme)) {
				if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_BQ) {
					prixT = prixTChocoBase.get(Chocolat.C_BQ);
				} else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_BQ_E) {
					prixT = prixTChocoBase.get(Chocolat.C_BQ_E);
				} else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_MQ_E) {
					prixT = prixTChocoBase.get(Chocolat.C_MQ_E);
				} else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_HQ_BE) {
					prixT = prixTChocoBase.get(Chocolat.C_HQ_BE);
				}
	
				if (prixT == 0) {
					return null;
				}
				
				this.journalTransactions.ajouter(Color.white, Color.RED, "AO: Je propose " + offre.getQuantiteT() + " tonnes de " + offre.getProduit() + " au cours de " + prixT + " euros par tonne.");
				return new OffreVente(offre, this, offre.getProduit(), prixT);
			
		    } else {
			    journalTransactions.ajouter(Color.pink, Color.RED, "--> AO "+ offre.getProduit() +" : Je ne peux pas proposer " + offre.getQuantiteT() + " tonnes de au cours de " + prixT + " euros par tonne.");
				journalTransactions.ajouter("\n");
			    return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public void notifierVenteAO(OffreVente propositionRetenue) {
		//System.out.println("Votre proposition de vente a été retenue");
		this.journalTransactions.ajouter(Romu.COLOR_LLGRAY, Color.RED, "-->AO "+ propositionRetenue.getProduit() +" : J'ai vendu " +propositionRetenue.getQuantiteT()+ " tonnes au prix de " + propositionRetenue.getPrixT() + " euros par tonne.");
		this.journalTransactions.ajouter("\n");
	 
		//Mettre à jour les autres variables
		ChocolatDeMarque chocoMarqueAO = (ChocolatDeMarque) propositionRetenue.getProduit();


		this.retirerDuStock(chocoMarqueAO, propositionRetenue.getQuantiteT(), this.cryptogramme);
	}
	

	@Override
	public void notifierPropositionNonRetenueAO(OffreVente propositionRefusee) {
		//System.out.println("Votre proposition de vente n'a pas été retenue");
		this.journalTransactions.ajouter(Color.pink, Color.RED, "--> AO "+propositionRefusee.getProduit()+" : Vente non retenue.");
		this.journalTransactions.ajouter("\n");
		/*if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_BQ) {
			prix_BQ = prix_BQ*0.95;
		} else if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_BQ_E) {
			prix_BQ_E -= 50;
		} else if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_MQ_E) {
			prix_MQ_E -= 50;
		} else if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_HQ_BE) {
			prix_HQ_BE -= 50;
		}*/
	}
}


