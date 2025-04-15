package abstraction.eq1Producteur1;

import abstraction.eqXRomu.general.Journal;

public class Producteur1Couts extends Employes {

    protected Journal journal;
    protected Producteur1 Producteur1; // Référence au Producteur1

    public Producteur1Couts() {
        this.journal = new Journal("Journal Coûts", Producteur1);

        
    }


    public double calculerCoutEntretien(){
        double coutTotal = 0.0;
        coutTotal += (enfants * coutEnfant) + (adultesNonFormes * coutAdulteNonForme) + (adultesFormes * coutAdulteForme);
        return coutTotal;
    }

    public void next(){
        double coutEntretien = calculerCoutEntretien();
        journal.ajouter("Coût d'entretien total par step : " + coutEntretien);
    }

}
