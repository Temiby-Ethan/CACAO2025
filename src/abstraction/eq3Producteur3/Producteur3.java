package abstraction.eq3Producteur3;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import abstraction.eqXRomu.filiere.Filiere;

public class Producteur3 extends Producteur3Acteur  {

	
	
	
	public Producteur3() {
		super();
	}
	

	@Override
	public void next() {
		defiJournal.ajouter(Filiere.LA_FILIERE.getEtape()+"\n");
		


	}

}
