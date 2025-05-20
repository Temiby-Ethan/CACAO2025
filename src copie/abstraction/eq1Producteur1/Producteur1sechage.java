package abstraction.eq1Producteur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

public class Producteur1sechage extends Producteur1arbres {
 

    private HashMap<Feve, List<Double>> fileSechage;
    private HashMap<Feve, List<Integer>> fileSechageSteps;
    private HashMap<Feve, Double> fevesSeches;
    private Journal journalSechage;

    private static final double PRIX_STOCKAGE_PAR_STEP = 7.5; // euros/tonne/step
    private static final int DUREE_SECHAGE_BQ = 2; // steps
    private static final int DUREE_SECHAGE_MQ_HQ = 1; // steps
   // private static final int DUREE_STOCKAGE_MAX = 8; // steps
   // private static final int DUREE_VENTE_MAX = 4; // steps

    public Producteur1sechage() {
        super();
        this.fileSechage = new HashMap<>();
        this.fileSechageSteps = new HashMap<>();
        this.fevesSeches = new HashMap<>();
        this.journalSechage = new Journal(getNom() + "Journal Sechage", this);

        for (Feve f : Feve.values()) {
            fileSechage.put(f, new ArrayList<>());
            fileSechageSteps.put(f, new ArrayList<>());
            fevesSeches.put(f, 0.0);
        }
    }

    /**
     * Ajoute les fèves récoltées à la file de séchage avec leur step de fin.
     */
    public void ajouterAuSechage(HashMap<Feve, Double> fevesRecoltees) {
        int stepActuel = Filiere.LA_FILIERE.getEtape();
        for (Feve f : Feve.values()) {
            double quantite = fevesRecoltees.getOrDefault(f, 0.0);
            if (quantite > 0) {
                int duree = (f == Feve.F_BQ || f == Feve.F_BQ_E) ? DUREE_SECHAGE_BQ : DUREE_SECHAGE_MQ_HQ;
                int stepFin = stepActuel + duree;
                fileSechage.get(f).add(quantite);
                fileSechageSteps.get(f).add(stepFin);
                journalSechage.ajouter("Step " + stepActuel + " : Ajout au séchage de " + quantite + " fèves " + f + " (fin prévue au step " + stepFin + ")");
            }
        }
    }

    /**
     * Met à jour le séchage des fèves, libère celles qui ont atteint leur step de fin.
     */
    public void mettreAJourSechage() {
        int stepActuel = Filiere.LA_FILIERE.getEtape();
        for (Feve f : Feve.values()) {
            List<Double> lots = fileSechage.get(f);
            List<Integer> stepsFin = fileSechageSteps.get(f);
            List<Double> nouveauxLots = new ArrayList<>();
            List<Integer> nouveauxSteps = new ArrayList<>();
            double totalSeche = fevesSeches.get(f);

            for (int i = 0; i < lots.size(); i++) {
                if (stepsFin.get(i) <= stepActuel) {
                    totalSeche += lots.get(i);
                } else {
                    nouveauxLots.add(lots.get(i));
                    nouveauxSteps.add(stepsFin.get(i));
                }
            }
            fileSechage.put(f, nouveauxLots);
            fileSechageSteps.put(f, nouveauxSteps);
            fevesSeches.put(f, totalSeche);
        }
        journalSechage.ajouter("Step " + stepActuel + " : Fèves sèches disponibles : " + fevesSeches);
    }

    /**
     * Calcule le coût de stockage pour toutes les fèves sèches.
     */
    public double coutStockage() {
        double total = 0.0;
        for (Feve f : Feve.values()) {
            total += fevesSeches.get(f) * PRIX_STOCKAGE_PAR_STEP;
        }
        return total;
    }

    public double getFevesSeches(Feve f) {
        return fevesSeches.get(f);
    }

    public void next() {
        super.next();
        mettreAJourSechage();
        gererRecolteEtStock();
    }

    /**
     * Gère la récolte, l'ajout au séchage et la mise à jour du stock.
     */
    private void gererRecolteEtStock() {
        // Récupérer la récolte réelle depuis les parcelles via Stock
        HashMap<Feve, Double> fevesRecoltees = new HashMap<>();
        if (this instanceof Producteur1arbres) {
            Producteur1arbres prodArbres = (Producteur1arbres) this;
            fevesRecoltees.put(Feve.F_BQ, stock.getStock(Feve.F_BQ));
            fevesRecoltees.put(Feve.F_MQ, stock.getStock(Feve.F_MQ));
            fevesRecoltees.put(Feve.F_HQ_E, stock.getStock(Feve.F_HQ_E));
            ajouterAuSechage(fevesRecoltees);

            // Mettre à jour le stock avec les fèves sèches
            for (Feve f : Feve.values()) {
                double quantiteSeche = fevesSeches.getOrDefault(f, 0.0);
                stock.ajouter(f, quantiteSeche, prodArbres.cryptogramme);
            }
        }
    }

    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journalSechage);
        return res;
    }
}
