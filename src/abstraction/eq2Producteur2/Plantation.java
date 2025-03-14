//Maxime Philippon
package abstraction.eq2Producteur2;


import abstraction.eqXRomu.produits.Feve;


public class Plantation extends Producteur2prod {
    
    private static final int HECTARES_PARCELLE = 100; // Une parcelle = 100 ha
    
    private Feve typeFeve;          // Type de fèves cultivées
    private int parcelles;          // Nombre de parcelles de 100 ha
    private int age;                // Âge de la plantation en steps
    private final int dureeDeVie;   // Durée de vie maximale avant remplacement
    private final int tempsAvantProduction; // Temps nécessaire avant production
    private final int densite;      // Nombre d'arbres par hectare
    private final int productionParArbre; // Nombre de cabosses par arbre/an

    
    public Plantation(Feve typeFeve, int parcelles) {
        this.typeFeve = typeFeve;
        this.parcelles = parcelles;
        this.age = 0; // Plantation récente
        
        // Initialisation des paramètres selon la qualité des fèves
        switch (typeFeve) {
            case F_BQ:
                this.dureeDeVie = 960;  // 40 ans
                this.tempsAvantProduction = 72;  // 3 ans
                this.densite = 950; // Arbres par hectare
                this.productionParArbre = 32; // Cabosses par arbre
                break;
            case F_MQ:
                this.dureeDeVie = 960;
                this.tempsAvantProduction = 96; // 4 ans
                this.densite = 750;
                this.productionParArbre = 30;
                break;
            case F_HQ_E:
                this.dureeDeVie = 960;
                this.tempsAvantProduction = 120; // 5 ans
                this.densite = 500;
                this.productionParArbre = 20;
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
        int nombreArbres = parcelles * HECTARES_PARCELLE * densite; // Arbres sur toutes les parcelles
        int cabossesTotales = nombreArbres * productionParArbre;
        double fevesTotales = cabossesTotales * getFevesParCabosse();

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
                return 25; // Moyenne entre 23 et 28
            case F_MQ:
                return 27; // Moyenne entre 25 et 29
            case F_HQ_E:
                return 30; // Moyenne entre 29 et 31
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
}