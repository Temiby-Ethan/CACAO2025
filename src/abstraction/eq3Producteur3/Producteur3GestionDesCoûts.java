package abstraction.eq3Producteur3;

import abstraction.eqXRomu.produits.Feve;

public class Producteur3GestionDesCoûts extends Producteur3Employés{
    
    double cump = 7.5;

    public Producteur3GestionDesCoûts() {
        super();
    }
    
    // Paul
    
    public double coutsTotaux() {
        return calculCoutStock() + getMasseSalariale();
    }

    public double getCoutUnitaire(Feve feve) {
        return 7.5 + (getMasseSalariale() / calculTotalStock());
    }

    public double getCump(Feve feve) {
        double nouveauStock = getNouveauStockParticulier(feve);
        double totalStock = calculTotalStockParticulier(feve);
        double ancienStock = totalStock - nouveauStock;
        cump = (ancienStock * cump + nouveauStock * getCoutUnitaire(feve)) / totalStock;
        return cump;
        }
    }

