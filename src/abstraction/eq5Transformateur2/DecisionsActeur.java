package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import java.util.HashMap;
import java.util.List;

public class DecisionsActeur extends MarqueChocolat{
    

    private double quantiteAProduire; // en tablette à chaque step
    private HashMap<Chocolat, Double> proportion; // proportion de chaque chocolat dans la production totale
    

    public DecisionsActeur(){
        super();
        this.quantiteAProduire=Math.min(this.capaciteProductionEmployes(),this.getCapaciteProductionMachines());
        this.proportion = new HashMap<Chocolat, Double>();
        }
    

    


    public void approvisionnementEnFeve(){
        
    }



    public void next(){
        super.next();
    }



    
}
