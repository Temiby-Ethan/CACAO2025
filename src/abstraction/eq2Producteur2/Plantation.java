//Maxime Philippon
package abstraction.eq2Producteur2;
import java.util.concurrent.ThreadLocalRandom;

import abstraction.eqXRomu.produits.Feve;



public class Plantation extends Producteur2recolte {
    
    private Feve typeFeve;          // Type de fèves cultivées
    private int parcelles;          // Nombre de parcelles de 100 ha
    private int age;                // Âge de la plantation en steps
    private final int dureeDeVie;   // Durée de vie maximale avant remplacement
    private final int tempsAvantProduction; // Temps nécessaire avant production
    private final int productionParParcelle; // Nombre de cabosses par parcelle a chaque step
    private double prix_achat; // Prix d'achat de la plantation
    private double prix_vente; // Prix de vente de la plantation
    private double prix_replantation; // Prix de replantation de la plantation
    private double salaire_employe; // Prix que coûtente les employés par step par parcelle

    
    public Plantation(Feve typeFeve, int parcelles, int age) {
        super();
        this.typeFeve = typeFeve;
        this.parcelles = parcelles;
        this.age = age; // Plantation récente
        
        // Initialisation des paramètres selon la qualité des fèves
        switch (typeFeve) {
            case F_BQ:
                this.dureeDeVie = 960;  // 40 ans
                this.tempsAvantProduction = 72;  // 3 ans
                this.productionParParcelle = 126666; // 126666 cabosses par parcelle a chaque next
                this.prix_achat = 2250000;
                this.prix_vente = 1450000;
                this.prix_replantation = 800000;
                this.salaire_employe = 6600;
                break;
            case F_BQ_E:
                this.dureeDeVie = 960;  // 40 ans
                this.tempsAvantProduction = 72;  // 3 ans
                this.productionParParcelle = 126666; // 126666 cabosses par parcelle a chaque next
                this.prix_achat = 2250000;
                this.prix_vente = 1450000;
                this.prix_replantation = 800000;
                this.salaire_employe = 30000;
                break;
            case F_MQ:
                this.dureeDeVie = 960;
                this.tempsAvantProduction = 96; // 4 ans
                this.productionParParcelle = 93750;
                this.prix_achat = 4250000;
                this.prix_vente = 2850000;
                this.prix_replantation = 1400000;
                this.salaire_employe = 5400;
                break;
            case F_MQ_E:
                this.dureeDeVie = 960;
                this.tempsAvantProduction = 96; // 4 ans
                this.productionParParcelle = 93750;
                this.prix_achat = 4250000;
                this.prix_vente = 2850000;
                this.prix_replantation = 1400000;
                this.salaire_employe = 22500;
                break;
            case F_HQ_E:
                this.dureeDeVie = 960;
                this.tempsAvantProduction = 120; // 5 ans
                this.productionParParcelle = 41666;
                this.prix_achat = 7000000;
                this.prix_vente = 4650000;
                this.prix_replantation = 2350000;
                this.salaire_employe = 15000;
                break;
            case F_HQ_BE:
                this.dureeDeVie = 960;
                this.tempsAvantProduction = 120; // 5 ans
                this.productionParParcelle = 29166;
                this.prix_achat = 7000000;
                this.prix_vente = 4650000;
                this.prix_replantation = 2350000;
                this.salaire_employe = 15000;
                break;
            default:
                throw new IllegalArgumentException("Type de fève non reconnu !");
        }
    }

    /**
     * Avance l'âge de la plantation d'un step et renvoie la quantité de fèves produites.
     */
    public double prodPlantation() {
        if (age < tempsAvantProduction) {
            return 0; // La plantation n'est pas encore en production
        }
        if (age >= dureeDeVie) {
            return 0; // Plantation morte, nécessite un remplacement
        }

        // Calcul de la production en fèves sèches
        double fevesTotales = this.productionParParcelle * getFevesParCabosse();

        return fevesTotales;
    }

    public void add_age() {
        age++;
    }
    /**
     * Retourne le nombre moyen de fèves par cabosse selon le type.
     */
    private int getFevesParCabosse() {
        switch (typeFeve) {
            case F_BQ:
                return ThreadLocalRandom.current().nextInt(23, 29); // Moyenne entre 23 et 28
            case F_BQ_E:
                return ThreadLocalRandom.current().nextInt(23, 29); // Moyenne entre 23 et 28
            case F_MQ:
                return ThreadLocalRandom.current().nextInt(25, 30); // Moyenne entre 25 et 29
            case F_MQ_E:
                return ThreadLocalRandom.current().nextInt(25, 30); // Moyenne entre 25 et 29
            case F_HQ_E:
                return ThreadLocalRandom.current().nextInt(29, 32); // Moyenne entre 29 et 31
            case F_HQ_BE:
                return ThreadLocalRandom.current().nextInt(29, 32); // Moyenne entre 29 et 31
            default:
                return 0;
        }
    }


    // Getters pour récupérer des informations sur la plantation
    public Feve getTypeFeve() {
        return typeFeve;
    }

    public int getParcelles() {
        return parcelles;
    }

    public int getAge() {
        return age;
    }

    public boolean estEnProduction() {
        return age >= tempsAvantProduction && age < dureeDeVie;
    }

    public boolean estMorte() {
        return age >= dureeDeVie;
    }

    public double getcout() {
        if (age == 0){
            return prix_achat;
        }
        else if (age <= dureeDeVie){
            return salaire_employe;
        }
        else {
            return 0;
        }
    }
}