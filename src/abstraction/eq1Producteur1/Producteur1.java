package abstraction.eq1Producteur1;

import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;


import java.util.List;



// ADRIEN BUCHER

public class Producteur1 extends Producteur1Couts {

    protected Feve typeFeve; // Type de fève géré par ce producteur
   // protected Stock stock; // Instance de Stock pour gérer les stocks

    public Producteur1() {
        super();
    }


	public List<Variable> getIndicateurs() {
		return super.getIndicateurs();
	}

    @Override
    public void next() {
        super.next(); // mise à jour stock / journal
        //  livraison automatique des contrats cadres
    }

    


}
