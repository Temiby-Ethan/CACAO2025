package abstraction.eq4Transformateur1;

import abstraction.eqXRomu.appelDOffre.AppelDOffre;
import abstraction.eqXRomu.appelDOffre.IVendeurAO;
import abstraction.eqXRomu.appelDOffre.OffreVente;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;


/**
 * @author YAOU Reda
 */

public class Transformateur1VendeurAppelDoffre extends Transformateur1AcheteurBourse implements IVendeurAO {

	double prix_BQ = 2100;
	double prix_BQ_E = 2200;
	double prix_MQ_E = 2350;
	double prix_HQ_BE = 2700;

    public Transformateur1VendeurAppelDoffre() {
		super();
	}

	@Override
	public OffreVente proposerVente(AppelDOffre offre) {
		System.err.println(offre.toString());
		double prix = 9000;
		if (stockChocoMarque.keySet().contains(offre.getProduit()) && offre.getQuantiteT() <= stockChocoMarque.get(offre.getProduit())) {
			
            if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_BQ) {
				prix = prix_BQ * pourcentageTransfo.get(Feve.F_BQ).get(Chocolat.C_BQ);
			} else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_BQ_E) {
				prix = prix_BQ_E * pourcentageTransfo.get(Feve.F_BQ_E).get(Chocolat.C_BQ_E);
			} else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_MQ_E) {
				prix = prix_MQ_E * pourcentageTransfo.get(Feve.F_MQ_E).get(Chocolat.C_MQ_E);
			} else if (((ChocolatDeMarque) offre.getProduit()).getChocolat() == Chocolat.C_HQ_BE) {
				prix = prix_HQ_BE * pourcentageTransfo.get(Feve.F_HQ_BE).get(Chocolat.C_HQ_BE);
			}
			this.journalTransactions.ajouter("Je propose " + offre.getQuantiteT() + " tonnes de " + offre.getProduit() + " au cours de " + prix + " euros par tonne.");
			return new OffreVente(offre, this, offre.getProduit(), prix);
		} else {
			return null;	
		}
	}

	@Override
	public void notifierVenteAO(OffreVente propositionRetenue) {
		/*System.out.println("Votre proposition de vente a été retenue");*/
		this.journalTransactions.ajouter("J'ai vendu " + propositionRetenue.getQuantiteT() + " tonnes de " + propositionRetenue.getProduit() + " au cours de " + propositionRetenue.getPrixT() + " euros par tonne.");
	
		/* Mettre à jour les autres variables */
		stockChocoMarque.put((ChocolatDeMarque)(propositionRetenue.getProduit()), stockChocoMarque.get(propositionRetenue.getProduit()) - propositionRetenue.getQuantiteT());
		this.journalTransactions.ajouter("J'ai maintenant " + this.stockChocoMarque.get(propositionRetenue.getProduit()) + " tonnes de " + propositionRetenue.getProduit() + " en stock.");

		totalStocksChocoMarque.setValeur(this, this.totalStocksChocoMarque.getValeur(this.cryptogramme) - propositionRetenue.getQuantiteT(), this.cryptogramme); 
		this.journalTransactions.ajouter("J'ai maintenant " + this.totalStocksChocoMarque.getValeur(this.cryptogramme) + " tonnes de chocolat de marque en stock.");
	}
	

	@Override
	public void notifierPropositionNonRetenueAO(OffreVente propositionRefusee) {
		/*System.out.println("Votre proposition de vente n'a pas été retenue");*/
		this.journalTransactions.ajouter("J'ai proposé " + propositionRefusee.getQuantiteT() + " tonnes de " + propositionRefusee.getProduit() + " au cours de " + propositionRefusee.getPrixT() + " euros par tonne mais elle n'a pas été retenue.");
		if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_BQ) {
			prix_BQ -= 50;
		} else if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_BQ_E) {
			prix_BQ_E -= 50;
		} else if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_MQ_E) {
			prix_MQ_E -= 50;
		} else if (((ChocolatDeMarque) propositionRefusee.getProduit()).getChocolat() == Chocolat.C_HQ_BE) {
			prix_HQ_BE -= 50;
		}
	}
}


