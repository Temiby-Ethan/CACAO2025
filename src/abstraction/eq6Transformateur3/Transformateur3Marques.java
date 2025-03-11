package abstraction.eq6Transformateur3;
// @author Henri Roth
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.IMarqueChocolat;

public class Transformateur3Marques extends Transformateur3Acteur implements IMarqueChocolat {
    protected List<String> marques;
    
    public Transformateur3Marques(){
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
