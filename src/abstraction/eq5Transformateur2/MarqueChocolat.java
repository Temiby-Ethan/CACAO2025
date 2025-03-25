package abstraction.eq5Transformateur2;


import abstraction.eqXRomu.filiere.IMarqueChocolat;
import java.util.List;



public class MarqueChocolat extends FabricantChocolatDeMarque implements IMarqueChocolat {

    public List<String> marquesChocolat;

    public MarqueChocolat(){
        super();

    }

    
    public List<String> getMarquesChocolat() {
        return this.marquesChocolat;
    }

    
}
