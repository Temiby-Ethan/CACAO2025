package abstraction.eq3Producteur3;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.Feve;

//Zoé
public class Producteur3 extends Producteur3Vente  {

	
	
	
	public Producteur3() {
		super();
		

		initStock();
		calculTotalStock();
		initTerrain();

		
	}


	@Override
	public void next() {
		journal.ajouter(Filiere.LA_FILIERE.getEtape()+"\n");
		actualiserTerrain();
		vieillirStock();
		recolte();
		calculTotalStock();
		getCump(Feve.F_HQ_BE);
		getCump(Feve.F_HQ_E);
		getCump(Feve.F_MQ_E);
		getCump(Feve.F_MQ);
		getCump(Feve.F_BQ_E);
		getCump(Feve.F_BQ);
		actualiserJournalMasseSalariale();
		actualiserJournalCump();
		}
	
	}


