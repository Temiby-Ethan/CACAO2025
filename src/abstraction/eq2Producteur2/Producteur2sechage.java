//Maxime Philippon
package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

public class Producteur2sechage extends Producteur2recolte {
    
    protected HashMap<Feve,Double> fevesSeches;
    private HashMap<Feve, List<Double>> fileSechage;
    private HashMap<Feve, List<Integer>> fileSechageSteps; 
    private Journal journalSechage;

    public Producteur2sechage() {
        super();
        this.fevesSeches = new HashMap<Feve, Double>();
        this.fileSechage = new HashMap<Feve, List<Double>>();
        this.fileSechageSteps = new HashMap<Feve, List<Integer>>();
        this.journalSechage = new Journal("Journal Sechage Eq2",this);

        for (Feve f : Feve.values()) {
            this.fevesSeches.put(f, 0.0);
            this.fileSechage.put(f, new ArrayList<>());
            this.fileSechageSteps.put(f, new ArrayList<>());
        }
        
    }

    /**
     * Ajoute les fèves récoltées à la file de séchage avec leur step de fin.
     */
    public void ajouterAuSechage() {
        int stepActuel = Filiere.LA_FILIERE.getEtape();

        for (Feve f : Feve.values()) {
            double quantite = feve_recolte.get(f);
            if (quantite > 0) {
                int stepFinSechage = stepActuel + (f == Feve.F_BQ || f == Feve.F_BQ_E ? 2 : 1);
                fileSechage.get(f).add(quantite);
                fileSechageSteps.get(f).add(stepFinSechage);
                journalSechage.ajouter("Step " + stepActuel + " : Ajout au séchage de " + quantite + " fèves " + f + " (fin prévue au step " + stepFinSechage + ")");
            }
        }
    }

    /**
     * Met à jour le séchage des fèves, libère celles qui ont atteint leur step de fin.
     */
    public void mettreAJourSechage() {
        int stepActuel = Filiere.LA_FILIERE.getEtape();
        HashMap<Feve, Double> sechageFini = new HashMap<>();

        for (Feve f : Feve.values()) {
            sechageFini.put(f, 0.0);

            List<Double> lots = fileSechage.get(f);
            List<Integer> stepsFin = fileSechageSteps.get(f);
            List<Double> nouveauxLots = new ArrayList<>();
            List<Integer> nouveauxSteps = new ArrayList<>();

            for (int i = 0; i < lots.size(); i++) {
                if (stepsFin.get(i) <= stepActuel) {
                    sechageFini.put(f, sechageFini.get(f) + lots.get(i));
                } else {
                    nouveauxLots.add(lots.get(i));
                    nouveauxSteps.add(stepsFin.get(i));
                }
            }

            // Mise à jour des listes après suppression des lots séchés
            fileSechage.put(f, nouveauxLots);
            fileSechageSteps.put(f, nouveauxSteps);

            // Mise à jour des fèves sèches
            fevesSeches.put(f, sechageFini.get(f));
        }

        journalSechage.ajouter("Step " + stepActuel + " : Nouvelles fèves sèches : " + fevesSeches);

    }

    public void next() {
        super.next();
        ajouterAuSechage();
        mettreAJourSechage();
    }

    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journalSechage);
        return res;
    }
}
