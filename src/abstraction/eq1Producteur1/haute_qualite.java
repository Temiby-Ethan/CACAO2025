package abstraction.eq1Producteur1;

import java.util.Random;

public class haute_qualite extends plantation {

    public haute_qualite(){
        super();
        this.typedeplant = "haute_qualite";
        this.nombre_arbes = 500; // par arbre par hecatre
        this.temps_avant_production = 120; // en steps
        this.besoin_main_doeuvre = 4; // employés/hectare : min 2 adultes
        this.production_par_arbre = 20; // cabosses/arbre/an
        this.nb_feves_par_cabosse = getRandomNumberInRange(29, 31);
        this.poids_feve_par_cabosse_apres_sechage = 0.765; //en g
        this.temps_de_pousse = 12; // en steps
        this.temps_de_sechage = 1; // en steps
        this.prix_achat = 7000;
        this.prix_replantation = 2350;  // par hectare
    }


    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max doit être supérieur à min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static double nombre_arbes(){
        return 500;
    }

}

