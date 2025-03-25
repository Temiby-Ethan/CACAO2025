//Roth Jérôme

package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.produits.IProduit;



public class Producteur2VendeurBourse extends Producteur2VenteCC implements IVendeurBourse{

	private Journal journalBourse;
	
	public Producteur2VendeurBourse() {

		super();
		this.journalBourse = new Journal(" journal Bourse Eq2", this);

	}

	
	public double offre(Feve f, double cours) {
	
			double stock_a = stockvar.get(f).getValeur();
			if(stock_a > seuil_stock.get(f)){

				double offre = seuil_stock.get(f) - stock_a;
				journalBourse.ajouter(Filiere.LA_FILIERE.getEtape()+"je mets en vente "+offre+" T de "+f);
				return offre;

			}else{

				return 0;
			
			}

		}

	public double notificationVente(Feve f, double quantiteEnT, double coursEnEuroParT) {
		double retire = Math.min(this.stockvar.get(f).getValeur(), quantiteEnT);
		journalBourse.ajouter(Filiere.LA_FILIERE.getEtape()+" : j'ai vendu "+quantiteEnT+" T de "+f);
		DeleteStock(f,retire);

		return retire;
	}

	public void notificationBlackList(int dureeEnStep) {
		journalBourse.ajouter(Filiere.LA_FILIERE.getEtape()+" : je suis blackliste pour une duree de "+dureeEnStep+" etapes");
	}



	public List<Journal> getJournaux() {
		List<Journal> res = super.getJournaux();
		res.add(journalBourse);
		return res;
	}

	public void next() {
		super.next();
	}
}
