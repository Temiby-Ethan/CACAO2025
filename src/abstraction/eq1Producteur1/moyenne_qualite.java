package abstraction.eq1Producteur1;

import java.util.Random;

public class moyenne_qualite extends plantation {

    public moyenne_qualite(){
        super();
        this.typedeplant = "moyenne_qualite";
        this.nombre_arbes = 750; // par arbre par hecatre
        this.temps_avant_production = 96; // en steps
        this.besoin_main_doeuvre = 6; // employés/hectare : min 2 adultes
        this.production_par_arbre = 30; // cabosses/arbre/an
        this.nb_feves_par_cabosse = getRandomNumberInRange(25, 29);
        this.poids_feve_par_cabosse_apres_sechage = 0.750; //en g
        this.temps_de_pousse = 11; // en steps
        this.temps_de_sechage = 1; // en steps
        this.prix_achat = 4250;
        this.prix_replantation = 1400 ; // par hectare
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max doit être supérieur à min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
