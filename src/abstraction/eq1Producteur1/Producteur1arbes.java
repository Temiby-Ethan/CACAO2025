package abstraction.eq1Producteur1;

import java.util.HashMap;

import abstraction.eqXRomu.general.Journal;

public class Producteur1arbes extends plantation {
    protected Journal journal;
    private plantation basse_qualite;
    private plantation moyenne_qualite;
    private plantation haute_qualite;


    public Producteur1arbes() {
        this.basse_qualite = new plantation();
        this.moyenne_qualite = new plantation();
        this.haute_qualite = new plantation();
    }


    public void planter_parcelle_basse_q(){
        this.journal.ajouter("Plantation de la parcelle de type " + this.basse_qualite);
    }

    public void planter_parcelle_moyenne_q(){
        this.journal.ajouter("Plantation de la parcelle de type " + this.moyenne_qualite);
    }
    
    public void planter_parcelle_haute_q(){
        this.journal.ajouter("Plantation de la parcelle de type " + this.haute_qualite);
    }

    public void vie_arbre(){
        HashMap<Integer, Integer> arbre = new HashMap<>();
        for (int i = 0; i < 40; i++) { 
            arbre.put(i, (1/(40*24))*nombre_arbes);
        }
        for (int i = 40*24; i >=1; i--){
            arbre.put(i,arbre.get(i-1));
        }

        }

    public void next() {
        // Logique de mise à jour des arbres
        this.vie_arbre();
    }
    public void setJournal(Journal journal) {
        this.journal = journal;

}

}
