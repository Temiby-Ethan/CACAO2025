package abstraction.eq6Transformateur3;
// @author Henri Roth
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.IMarqueChocolat;

public class Transformateur3_marques extends Transformateur3 implements IMarqueChocolat {
    protected List<String> marques;
    
    public Transformateur3_marques(){
        this.marques = new ArrayList<String>();
        this.marques.add("Fraudolat");
        this.marques.add("Bollorolat");
        this.marques.add("Hypocritolat");
        this.marques.add("Anonymolat");
    }

    public List<String> getMarquesChocolat(){
        return marques;
    }
}
