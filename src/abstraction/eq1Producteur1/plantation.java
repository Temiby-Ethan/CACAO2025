package abstraction.eq1Producteur1;



import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

// Amal Moncer
public class plantation extends Employes {

    protected int temps_avant_production; // en steps
    protected int duree_de_vie = 960; // en steps
    protected int besoin_main_doeuvre;
    protected int production_par_arbre;
    protected int nb_feves_par_cabosse;
    protected double poids_feve_par_cabosse_apres_sechage;
    protected int temps_de_pousse;
    protected int temps_de_sechage;
    protected double prix_achat;
    protected int prix_replantation;
    protected Journal journal;
    protected int nombre_hectares;
    protected int nombre_feves;
    protected int nombre_feves_total;
    protected int nombre_arbes;
    private Feve typeFeve;         

    public void production(){
        switch(typeFeve){
            case F_BQ:
            this.nombre_arbes = 950;
            this.temps_avant_production = 72;
            this.besoin_main_doeuvre = 8; // employés/hectare : min 2 adultes
            this.production_par_arbre = 32; // cabosses/arbre/an
            this.nb_feves_par_cabosse = 23 + Filiere.random.nextInt(28 - 23); // Générer un nombre entre 23 et 27 inclus
            this.poids_feve_par_cabosse_apres_sechage = 0.753; //en g
            this.temps_de_pousse = 12; // en steps
            this.temps_de_sechage = 2; // en steps
            this.prix_achat = 2250; // par hectare
            this.prix_replantation = 800; // par hectare
            this.nombre_hectares = 840; 
            this.nombre_feves = this.nombre_arbes * this.production_par_arbre * this.nb_feves_par_cabosse;
            this.nombre_feves_total = this.nombre_feves * this.nombre_hectares;            
            break;
            case F_MQ:
            this.nombre_arbes = 750; // par arbre par hecatre
            this.temps_avant_production = 96; // en steps
            this.besoin_main_doeuvre = 6; // employés/hectare : min 2 adultes
            this.production_par_arbre = 30; // cabosses/arbre/an
            this.nb_feves_par_cabosse = 25 + Filiere.random.nextInt(29 - 25); // Générer un nombre entre 23 et 27 inclus
            this.poids_feve_par_cabosse_apres_sechage = 0.750; //en g
            this.temps_de_pousse = 11; // en steps
            this.temps_de_sechage = 1; // en steps
            this.prix_achat = 4250;
            this.prix_replantation = 1400 ; // par hectare
            this.nombre_feves = this.nombre_arbes * this.production_par_arbre * this.nb_feves_par_cabosse;
            this.nombre_hectares = 120000; // en milliers d'hectares
            this.nombre_feves_total = this.nombre_feves * this.nombre_hectares;
            break;
            case F_HQ_E:
            this.nombre_arbes = 500; // par arbre par hecatre
            this.temps_avant_production = 120; // en steps
            this.besoin_main_doeuvre = 4; // employés/hectare : min 2 adultes
            this.production_par_arbre = 20; // cabosses/arbre/an
            this.nb_feves_par_cabosse = 29 + Filiere.random.nextInt(31 - 29); // Générer un nombre entre 23 et 27 inclus
            this.poids_feve_par_cabosse_apres_sechage = 0.765; //en g
            this.temps_de_pousse = 12; // en steps
            this.temps_de_sechage = 1; // en steps
            this.prix_achat = 7000;
            this.prix_replantation = 2350;  // par hectare
            this.nombre_hectares = 0;
            this.nombre_feves = this.nombre_arbes * this.production_par_arbre * this.nb_feves_par_cabosse;
            this.nombre_feves_total = this.nombre_feves * this.nombre_hectares;
            break;
            
        }


    }

    public int getNombre_arbes() {
        int totalArbres = 0;

        // Ajoutez le nombre d'arbres pour chaque type de fève
        this.typeFeve = Feve.F_BQ;
        this.production(); // Met à jour les données pour F_BQ
        totalArbres += this.nombre_arbes;

        this.typeFeve = Feve.F_MQ;
        this.production(); // Met à jour les données pour F_MQ
        totalArbres += this.nombre_arbes;

        this.typeFeve = Feve.F_HQ_E;
        this.production(); // Met à jour les données pour F_HQ_E
        totalArbres += this.nombre_arbes;

        return totalArbres;
    }

}
