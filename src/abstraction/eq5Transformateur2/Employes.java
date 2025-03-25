package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.Filiere;


public class Employes extends Stock {
    private int nbrEmployesTotal;
    private double salaireEmployes;
    private double prixLicenciment;
    private double capaciteProduction;


    public Employes(){
        super();
        this.nbrEmployesTotal = 85400;
        this.salaireEmployes = 10000;
        this.prixLicenciment=10000;
        this.capaciteProduction= 15000; // par step et en tablette
    }

    public void embaucher(int nbrEmployes){
        this.nbrEmployesTotal += nbrEmployes;
    }
    
    public void licencier(int nbrEmployes){
        this.nbrEmployesTotal -= nbrEmployes;
        Filiere.LA_FILIERE.getBanque().payerCout(this ,super.cryptogramme,  "licenciment", prixLicenciment*nbrEmployes);

    }

    public double getSalaireTotal(){
        return this.salaireEmployes* this.nbrEmployesTotal;

    }
    
    public double capaciteProductionEmployes(){
        return this.nbrEmployesTotal*this.capaciteProduction;
    }

    public void next(){
        super.next();
        Filiere.LA_FILIERE.getBanque().payerCout(this, super.cryptogramme, "stockage", 100.0);
    }

}

