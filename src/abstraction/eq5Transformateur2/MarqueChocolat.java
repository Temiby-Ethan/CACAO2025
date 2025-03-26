package abstraction.eq5Transformateur2;


import abstraction.eqXRomu.filiere.IMarqueChocolat;
import java.util.ArrayList;
import java.util.List;



public class MarqueChocolat extends FabricantChocolatDeMarque implements IMarqueChocolat {

    public List<String> marquesChocolat;

    public MarqueChocolat(){
        super();
        this.marquesChocolat = new ArrayList<String>();
        this.marquesChocolat.add("ChocoMoyen");
        this.marquesChocolat.add("ChocoMoyenEq");
        this.marquesChocolat.add("ChocoHautEq");
        this.marquesChocolat.add("ChocoParfait");

    }

    
    public List<String> getMarquesChocolat() {
        return this.marquesChocolat;
    }

    
}
