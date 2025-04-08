package abstraction.eq1Producteur1;

import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.general.Journal;

public class Producteur1arbes extends plantation {
    private plantation basse_qualite;
    private plantation moyenne_qualite;
    private plantation haute_qualite;


    public Producteur1arbes() {
        this.basse_qualite = new plantation();
        this.moyenne_qualite = new plantation();
        this.haute_qualite = new plantation();
        this.journal = new Journal(getNom() + " - Journal arbres", this);
    }

    // Initialisation du journal avant de l'utiliser
        

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
        double arbres_morts=arbre.get(40*24);
        for (int i = 40*24; i >=1; i--){
            arbre.put(i,arbre.get(i-1));

        }
        journal.ajouter("il y a"+ arbres_morts + " arbres morts");
        nombre_arbes -= arbres_morts;
        journal.ajouter("il reste"+ nombre_arbes + " arbres");



        }

    public void next() {
        // Logique de mise à jour des arbres
        this.vie_arbre();
    }

     @Override
    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journal);
        return res;


}
}
