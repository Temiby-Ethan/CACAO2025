//Nathan
package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.Filiere;

public class FraisAditionnel extends Machine {

    private double fraisAditionnel;

    public FraisAditionnel(){
        super();
        this.fraisAditionnel= 200000000; 

    }

    public double getFraisAditionnel(){
        return this.fraisAditionnel;
    }
    public void next(){
        super.next();
        Filiere.LA_FILIERE.getBanque().payerCout(this ,super.cryptogramme,  "licenciment", getFraisAditionnel() );
        
    }
    
}