package abstraction.eq1Producteur1;


import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

// AMAL MONCER

public class Producteur1Parcelle  {

    protected int temps_avant_production; // en steps
    protected int duree_de_vie = 960; // en steps
    protected int besoin_main_doeuvre;
    protected int production_par_arbre;
    protected int nb_feves_par_cabosse;
    protected double poids_feve_par_cabosse_apres_sechage;
    protected int temps_de_pousse;
    protected int temps_de_sechage;
    protected double prix_achat;
    protected double prix_replantation;
    protected int nombre_hectares;
    protected int nombre_feves;
    protected int nombre_feves_total;
    protected int nombre_arbes;
    protected Feve typeFeve;
    protected Journal journal;
    private Producteur1Acteur producteur1; // Référence au Producteur1
    protected Producteur1arbres arbre;



   public Producteur1Parcelle(IActeur acteur, Feve feve, Producteur1arbres  arbre) {
        this.typeFeve = feve;
        this.arbre = arbre;
        this.producteur1 = (Producteur1Acteur) acteur;
        this.journal = new Journal(" EQ1 - Journal parcelles", producteur1);
    }
    



    public void production(){
        switch(typeFeve){
            case F_BQ:
            this.nombre_arbes = 950;
            this.temps_avant_production = 72; // en steps
            this.besoin_main_doeuvre = 8; // employés/hectare : min 2 adultes
            this.production_par_arbre = 32; // cabosses/arbre/an
            this.nb_feves_par_cabosse = 23 + Filiere.random.nextInt(28 - 23); // Générer un nombre entre 23 et 27 inclus
            this.poids_feve_par_cabosse_apres_sechage = 0.753; //en g
            this.temps_de_pousse = 12; // en steps
            this.temps_de_sechage = 2; // en steps
            this.prix_achat = 2250/950; // par hectare
            this.prix_replantation = 800/950; // par hectare -> 800
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
            this.prix_achat = 4250/750;
            this.prix_replantation = 1400/750 ; // par hectare 1400
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
            this.prix_achat = 7000/500;
            this.prix_replantation = 2350/500;  // par hectare 2350
            this.nombre_hectares = 0;
            this.nombre_feves = this.nombre_arbes * this.production_par_arbre * this.nb_feves_par_cabosse;
            this.nombre_feves_total = this.nombre_feves * this.nombre_hectares;
            break;
            default:
                throw new IllegalArgumentException("Nous ne possédons pas de " + typeFeve);
            
        }


    }

    public double getnombre_feves_total(){
        Integer nombre_arbres = getNombre_arbres();

        switch(typeFeve){
            case F_BQ:
            return nombre_arbres * this.production_par_arbre * this.nb_feves_par_cabosse;
            case F_MQ:
            return nombre_arbres * this.production_par_arbre * this.nb_feves_par_cabosse;
            case F_HQ_E:
            return nombre_arbres * this.production_par_arbre * this.nb_feves_par_cabosse;
            default:
                throw new IllegalArgumentException("Nous ne possédons pas de " + typeFeve);

    }
}

    public Integer getNombre_arbres() {
        if (typeFeve == null) {
            throw new IllegalStateException("Le type de fève n'a pas été initialisé !");
        }

        switch(typeFeve) {
            case F_BQ:
                return(this.nombre_arbes);

            case F_MQ:
                return(this.nombre_arbes);

            case F_HQ_E:
                return(this.nombre_arbes);

            default:
                throw new IllegalArgumentException("Nous n'avons pas de " + typeFeve);
        }
  
    }


    public int nb_parcelles(){
        switch(typeFeve){
            case F_BQ:
                return 374000;
            case F_MQ:
                return 11000;
            case F_HQ_E:
                return 200 ;
            default:
                throw new IllegalArgumentException("Nous ne possédons pas de " + typeFeve);
        }
        
    }

    public void replanter_arbres(int nb_arbres_voulu){
        Integer nombreArbres = getNombre_arbres();
        switch(typeFeve){
            case F_BQ:
                this.journal.ajouter("Replantation de la parcelle de type " + Feve.F_BQ);
                nombreArbres+= nb_arbres_voulu;
                break;
            case F_MQ:
                this.journal.ajouter("Replantation de la parcelle de type " + Feve.F_MQ);
                nombreArbres+= nb_arbres_voulu;
                break;
            case F_HQ_E:
                this.journal.ajouter("Replantation de la parcelle de type " + Feve.F_HQ_E);
                nombreArbres+= nb_arbres_voulu;
                break;

            default:
                this.journal.ajouter("Type de fève non reconnu pour la replantation.");
                break;

        }

    }

    public double cout_replantation(int nb_arbres, double budget){
        double prix_a_payer = nb_arbres * this.prix_replantation;
        if (budget >= prix_a_payer){
            replanter_arbres(nb_arbres);
            return prix_a_payer;
        } else {
            this.journal.ajouter("Budget insuffisant pour la replantation de " + nb_arbres + " arbres.");
            return 0.0;
        }
    }

    



    public int vente_parcelles(int nb_parcelles) {
        
        // Vérification que le nombre de parcelles à vendre est valide
        if (nb_parcelles <= 0) {
            this.journal.ajouter("Vente impossible : le nombre de parcelles à vendre doit être supérieur à 0.");
            return 0;
        }
    
        // Vérification que le nombre de parcelles disponibles est suffisant
        if (nb_parcelles > this.nombre_hectares) {
            this.journal.ajouter("Vente impossible : nombre de parcelles insuffisant. Parcelles disponibles : " + this.nombre_hectares);
            return 0 ;
        }
    
        // Calcul du prix de vente en fonction du type de fève
        int prixParHectare = 0;
        switch (typeFeve) {
            case F_BQ:
                prixParHectare = 1450;

            case F_MQ:
                prixParHectare = 2850;
            case F_HQ_E:
                prixParHectare = 4650;
            default:
                this.journal.ajouter("Type de fève non reconnu pour la vente de parcelles.");
        }
        return nb_parcelles * prixParHectare;
    
    }

    public double prix_replantation(){
        switch (typeFeve) {
            case F_BQ:
                this.prix_replantation = 800/950;
                break;
            case F_MQ:
                this.prix_replantation = 1400/750;
                break;
            case F_HQ_E:
                this.prix_replantation = 2350/500;
                break; 
            default:
                return 0.0;
                
        }
        return 0.0;

    }

  




}