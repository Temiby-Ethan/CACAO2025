package abstraction.eq4Transformateur1;

import abstraction.eqXRomu.appelDOffre.AppelDOffre;
import abstraction.eqXRomu.appelDOffre.IVendeurAO;
import abstraction.eqXRomu.appelDOffre.OffreVente;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import java.util.Scanner;

public class Transformateur1VendeurAppelDoffre extends Transformateur1Stocks implements IVendeurAO {

    public Transformateur1VendeurAppelDoffre() {
		super();
	}

	@Override
	public OffreVente proposerVente(AppelDOffre offre) {
		System.err.println(offre.toString());
		if (super.getChocolatsProduits().contains(offre.getProduit()) && offre.getQuantiteT() <= stockChocoMarque.get(offre.getProduit())) {
            Scanner scanner = new Scanner(System.in);
			System.out.println("Proposer votre prix de vente:");
			double prix = scanner.nextDouble();
			scanner.close();
			return new OffreVente(offre, this, offre.getProduit(), prix);
		} else {
			return null;	
		}
	}

	@Override
	public void notifierVenteAO(OffreVente propositionRetenue) {
		System.out.println("Votre proposition de vente a été retenue");
		this.journalTransactions.ajouter("J'ai vendu " + propositionRetenue.getQuantiteT() + " tonnes de " + propositionRetenue.getProduit() + " au cours de " + propositionRetenue.getPrixT() + " euros par tonne.");
	
		/* Mettre à jour les autres variables */
		stockChocoMarque.put((ChocolatDeMarque)(propositionRetenue.getProduit()), stockChocoMarque.get(propositionRetenue.getProduit()) - propositionRetenue.getQuantiteT());

		totalStocksChocoMarque.setValeur(this, this.totalStocksChocoMarque.getValeur(this.cryptogramme) - propositionRetenue.getQuantiteT(), this.cryptogramme); 

		VolumeTotalDeStock.setValeur(this, VolumeTotalDeStock.getValeur(this.cryptogramme) - propositionRetenue.getQuantiteT(), this.cryptogramme);
	}
	


	@Override
	public void notifierPropositionNonRetenueAO(OffreVente propositionRefusee) {
		System.out.println("Votre proposition de vente n'a pas été retenue");
	}
}


