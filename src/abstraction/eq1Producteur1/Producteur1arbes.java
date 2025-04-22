package abstraction.eq1Producteur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

// AMAL MONCER

public class Producteur1arbes extends Producteur1 {


    private int nb_arbres_total;
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
    protected Feve typeFeve;


    public Producteur1arbes(Feve typeFeve) {
        this.typeFeve = typeFeve; // Initialisation par défaut
        this.journal = new Journal(getNom() + " - Journal arbres", this);
        this.nb_arbres_total= nb_arbres_total();
    }

    // Initialisation du journal avant de l'utiliser
        

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
            default:
                throw new IllegalArgumentException("Nous ne possédons pas de " + typeFeve);
            
        }


    }

    public double getSolde() {
        return Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme);
    }

    public HashMap getNombre_arbes() {
        if (this.typeFeve == null) {
            throw new IllegalStateException("Le type de fève n'a pas été initialisé !");
        }

        HashMap<Feve, Integer> arbre = new HashMap<>();
        switch(typeFeve) {
            case F_BQ:
                arbre.put(Feve.F_BQ,this.nombre_arbes);
                break;
            case F_MQ:
                arbre.put(Feve.F_MQ,this.nombre_arbes);
                break;
            case F_HQ_E:
                arbre.put(Feve.F_HQ_E,this.nombre_arbes);
                break;
            default:
                throw new IllegalArgumentException("Nous n'avons pas de " + typeFeve);
        }
        return arbre;
    }
    public int nb_arbres_total(){
        HashMap<Feve, Integer> arbre = getNombre_arbes();
        return (int) arbre.get(typeFeve);
    }


    public int mort_arbre(){
        HashMap<Integer, Integer> arbre = new HashMap<>();
        for (int i = 0; i < 40; i++) { 
            arbre.put(i, (1/(40*24))*nb_arbres_total);
        }
        for (int i = 40*24; i >=1; i--){
            arbre.put(i,arbre.get(i-1));

        }
        return (int) arbre.get(0);

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

    public double getPrix_achat_par_arbre() {
        return 800*nb_parcelles()*nb_arbres_total;
    }
    
    public void mise_a_jour_arbre(){
        HashMap<Integer, Integer> arbre = new HashMap<>();
        List<Integer> arbres_qui_produisent = new ArrayList<>();

        for (int i = 0; i < 40*24; i++) {
            arbre.put(i, (1/(40*24))*nb_arbres_total);
            if (i >= temps_avant_production) {
                arbres_qui_produisent.add(arbre.get(i));
            }
        }
        for (int i = 40*24; i >=1; i--){
            arbre.put(i,arbre.get(i-1));

        }
        int arbres_morts = arbre.get(0);
        int arbres_vivants = nb_arbres_total - arbres_morts;

        double solde = getSolde();

        if (arbres_morts * getPrix_achat_par_arbre() < solde) {
            // Débit via la banque
            boolean succes = Filiere.LA_FILIERE.getBanque().virer(
                this, // L'acteur actuel est débité
                cryptogramme, // Cryptogramme pour authentification
                null, // Aucun acteur n'est crédité
                arbres_morts * getPrix_achat_par_arbre() // Montant à débiter
            );

            if (succes) {
                replanter_arbres(arbres_morts);
            } else {
                this.journal.ajouter("Erreur : Solde insuffisant pour replanter " + arbres_morts + " arbres.");
            }
        } else {
            int nb_arbres_voulu = (int) (solde / getPrix_achat_par_arbre());

            // Débit via la banque
            boolean succes = Filiere.LA_FILIERE.getBanque().virer(
                this, // L'acteur actuel est débité
                cryptogramme, // Cryptogramme pour authentification
                null, // Aucun acteur n'est crédité
                nb_arbres_voulu * getPrix_achat_par_arbre() // Montant à débiter
            );

            if (succes) {
                replanter_arbres(nb_arbres_voulu);
            } else {
                this.journal.ajouter("Erreur : Solde insuffisant pour replanter " + nb_arbres_voulu + " arbres.");
            }
        }

        this.journal.ajouter("il y a " + arbres_morts + " arbres morts");
        this.journal.ajouter("il y a " + arbres_vivants + " arbres vivants");
        // Logique de mise à jour des arbres
    }



    public void replanter_arbres(int nb_arbres_voulu){
        switch(typeFeve){
            case F_BQ:
                if (nb_arbres_voulu <= mort_arbre()){
                    this.journal.ajouter("Replantation de la parcelle de type " + Feve.F_BQ);
                    HashMap<Feve, Integer> nombreArbres = getNombre_arbes();
                    nombreArbres.put(Feve.F_BQ, nombreArbres.get(Feve.F_BQ) + nb_arbres_voulu);

                }
                else{
                    this.journal.ajouter("Il n'est pas possible de replanter " + Feve.F_BQ + " car le stock ne convient pas.");
                }  
                break;
            case F_MQ:
                if (mort_arbre() > 0){
                    this.journal.ajouter("Replantation de la parcelle de type " + Feve.F_MQ);
                    HashMap<Feve, Integer> nombreArbres = getNombre_arbes();
                    nombreArbres.put(Feve.F_MQ, nombreArbres.get(Feve.F_MQ) + nb_arbres_voulu);

                }
            else{
                this.journal.ajouter("Il n'est pas possible de replanter " + Feve.F_MQ + " car le stock ne convient pas.");
            }
            break;
            case F_HQ_E:
            if (mort_arbre() > 0){
                this.journal.ajouter("Replantation de la parcelle de type " + Feve.F_HQ_E);
                HashMap<Feve, Integer> nombreArbres = getNombre_arbes();
                nombreArbres.put(Feve.F_HQ_E, nombreArbres.get(Feve.F_HQ_E) + nb_arbres_voulu);

            }
        else{
            this.journal.ajouter("Il n'est pas possible de replanter " + Feve.F_MQ + " car le stock ne convient pas.");
        }
        break;
            default:
                this.journal.ajouter("Type de fève non reconnu pour la replantation.");
                break;

        }

    }


    public void vente_parcelles(int nb_parcelles) {
        // Vérification que le nombre de parcelles à vendre est valide
        if (nb_parcelles <= 0) {
            this.journal.ajouter("Vente impossible : le nombre de parcelles à vendre doit être supérieur à 0.");
            return;
        }
    
        // Vérification que le nombre de parcelles disponibles est suffisant
        if (nb_parcelles > this.nombre_hectares) {
            this.journal.ajouter("Vente impossible : nombre de parcelles insuffisant. Parcelles disponibles : " + this.nombre_hectares);
            return;
        }
    
        // Calcul du prix de vente en fonction du type de fève
        double prixParHectare = 0.0;
        switch (typeFeve) {
            case F_BQ:
                prixParHectare = 1450.0;
                break;
            case F_MQ:
                prixParHectare = 2850.0;
                break;
            case F_HQ_E:
                prixParHectare = 4650.0;
                break;
            default:
                this.journal.ajouter("Type de fève non reconnu pour la vente de parcelles.");
                return;
        }
    
        // Calcul du montant total de la vente
        double montantVente = nb_parcelles * prixParHectare;
    
        // Mise à jour du solde via la banque
        boolean succes = Filiere.LA_FILIERE.getBanque().virer(
            null, // Aucun acteur n'est débité (la vente génère de l'argent)
            0,    // Pas de cryptogramme nécessaire pour un transfert entrant
            this, // L'acteur actuel est crédité
            montantVente
        );
    
        if (!succes) {
            this.journal.ajouter("Erreur : La banque n'a pas pu créditer le montant de la vente.");
            return;
        }
    
        // Mise à jour du nombre de parcelles
        this.nombre_hectares -= nb_parcelles;
    
        // Ajout d'un message au journal
        this.journal.ajouter("Vente de " + nb_parcelles + " parcelles de type " + typeFeve + " pour un montant total de " + montantVente + " €.");
    }

    public void achat_parcelle(int nb_parcelles_achetees) {
        double solde = getSolde(); // Récupération du solde actuel
        double coutTotal = 0.0; // Coût total de l'achat

        switch (typeFeve) {
            case F_BQ:
                coutTotal = 2250 * nb_parcelles_achetees;
                break;

            case F_MQ:
                coutTotal = 4250 * nb_parcelles_achetees;
                break;

            case F_HQ_E:
                coutTotal = 7000 * nb_parcelles_achetees;
                break;

            default:
                this.journal.ajouter("Type de fève non reconnu pour l'achat de parcelle.");
                return;
        }

        // Vérification du solde
        if (solde < coutTotal) {
            this.journal.ajouter("Achat impossible : solde insuffisant pour " + nb_parcelles_achetees + " parcelles.");
            return;
        }

        // Mise à jour du solde
        Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme);

        // Mise à jour du nombre de parcelles
        this.nombre_hectares += nb_parcelles_achetees;

        // Ajout d'un message au journal
        this.journal.ajouter("Achat de " + nb_parcelles_achetees + " parcelles de type " + typeFeve + " pour un coût total de " + coutTotal + " €.");
    }
    

    public void next() {
        // Logique de mise à jour des arbres
        mise_a_jour_arbre();
        

    }


    @Override
    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journal);
        return res;
    }

}


