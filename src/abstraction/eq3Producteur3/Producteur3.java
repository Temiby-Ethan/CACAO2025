package abstraction.eq3Producteur3;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.Feve;

public class Producteur3 extends Producteur3GestionDesCoûts  {

	
	
	
	public Producteur3() {
		super();
		
		
		initStock(Feve.F_BQ, 1000000 );
		initStock(Feve.F_BQ_E, 1000000 );
		initStock(Feve.F_MQ, 1000000 );
		initStock(Feve.F_MQ_E, 1000000 );
		initStock(Feve.F_HQ_E, 1000000 );
		initStock(Feve.F_HQ_BE, 1000000 );
	}


	@Override
	public void next() {
		defiJournal.ajouter(Filiere.LA_FILIERE.getEtape()+"\n");
		//defiJournal.ajouter(Double.toString(stockFeve.getValeur()));
		


	}

}
