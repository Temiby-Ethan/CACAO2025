package abstraction.eq1Producteur1;

import java.util.Random;

public class plantation {
    private String typedeplant;
    private int nombre_arbes;
    private int temps_avant_production;
    private int age;
    private int duree_de_vie = 960; // en steps
    private int besoin_main_doeuvre;
    private int production_par_arbre;
    private int nb_feves_par_cabosse;
    private double poids_feve_par_cabosse_apres_sechage;
    private int temps_de_pousse;
    private int temps_de_sechage;
    private double prix_achat;
    private int prix_replantation;

    public void basse_qualite(){
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
    }

    public void moyenne_qualite(){
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

    public void haute_qualite(){
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

    // Méthode pour générer un nombre aléatoire entre min et max (inclus)
    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max doit être supérieur à min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
