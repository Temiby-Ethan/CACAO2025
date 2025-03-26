package abstraction.eq1Producteur1;

import java.util.Random;

public class basse_qualite extends plantation {

    public basse_qualite(Producteur1 producteur1){
        super(producteur1);
        this.typedeplant = "basse_qualite";
        this.nombre_arbes = 950; // par arbre par hecatre
        this.temps_avant_production = 72; // en steps
        this.besoin_main_doeuvre = 8; // employés/hectare : min 2 adultes
        this.production_par_arbre = 32; // cabosses/arbre/an
        this.nb_feves_par_cabosse = getRandomNumberInRange(23, 28); // Générer un nombre aléatoire entre 23 et 28
        this.poids_feve_par_cabosse_apres_sechage = 0.753; //en g
        this.temps_de_pousse = 12; // en steps
        this.temps_de_sechage = 2; // en steps
        this.prix_achat = 2250; // par hectare
        this.prix_replantation = 800; // par hectare
        this.nombre_hectares = 840; 
        this.nombre_feves = this.nombre_arbes * this.production_par_arbre * this.nb_feves_par_cabosse;
        this.nombre_feves_total = this.nombre_feves * this.nombre_hectares;
        this.producteur1=producteur1;
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max doit être supérieur à min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public double getStock(){
        double nb = this.nombre_feves_total;
        return nb;
    }

}
