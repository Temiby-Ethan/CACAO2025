package abstraction.eq1Producteur1;

import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.filiere.IActeur;

import java.util.List;

import abstraction.eqXRomu.contratsCadres.Echeancier;

// ADRIEN BUCHER

public class Producteur1 extends Producteur1arbres {

    private Producteur1ContratCadre contratCadre;
    protected Feve typeFeve; // Type de fève géré par ce producteur
   // protected Stock stock; // Instance de Stock pour gérer les stocks

    public Producteur1() {
        this.contratCadre =this;// new Producteur1ContratCadre(); // Initialisation de l'instance
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
