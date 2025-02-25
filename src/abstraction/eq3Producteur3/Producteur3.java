package abstraction.eq3Producteur3;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;

public class Producteur3 extends Producteur3Acteur  {

	protected Journal defiJournal = new Journal("Journal defi",this);
	protected int nbNext = 1;
	
	public Producteur3() {
		super();
	}


	@Override
	public void next() {
		defiJournal.ajouter(Filiere.LA_FILIERE.getEtape()+"\n");



	}

}
