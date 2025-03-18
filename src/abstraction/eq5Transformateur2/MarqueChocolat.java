package abstraction.eq5Transformateur2;


import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.filiere.IMarqueChocolat;
import java.util.List;
import java.util.ArrayList;



public class MarqueChocolat extends FabricantChocolatDeMarque implements IMarqueChocolat {

    public List<String> marquesChocolat;

    public MarqueChocolat(){
        super();
        this.marquesChocolat =new ArrayList<String>();
        this.marquesChocolat.add("EQ5Marque1");

    }

    
    public List<String> getMarquesChocolat() {
        return this.marquesChocolat;
    }

    
}
