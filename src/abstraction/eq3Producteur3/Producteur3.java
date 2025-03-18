package abstraction.eq3Producteur3;

import abstraction.eqXRomu.filiere.Filiere;

//Zoé
public class Producteur3 extends Producteur3GestionDesCoûts  {

	
	
	
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
		


	}

}
