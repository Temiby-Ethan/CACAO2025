package abstraction.eq1Producteur1;

import abstraction.eqXRomu.general.Journal;

public class Producteur1arbes extends plantation {
    private Journal journal;

    public Producteur1arbes(){
        super();
    }


    public void planter_parcelle(){
        this.journal.ajouter("Plantation de la parcelle de type " + this.typedeplant);
    }

}
