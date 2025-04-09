package abstraction.eq1Producteur1;

import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

public class Producteur1arbes extends plantation {

    protected Feve feve;
    private Journal journal;

    public Producteur1arbes() {
        this.journal = new Journal(getNom() + " - Journal arbres", this);
        this.nombre_arbes = getNombre_arbes();
    }

    // Initialisation du journal avant de l'utiliser
        

    public void planter_parcelle_basse_q(){
        this.journal.ajouter("Plantation de la parcelle de type " + Feve.F_BQ);
    }

    public void planter_parcelle_moyenne_q(){
        this.journal.ajouter("Plantation de la parcelle de type " + Feve.F_MQ);
    }
    
    public void planter_parcelle_haute_q(){
        this.journal.ajouter("Plantation de la parcelle de type " + Feve.F_HQ_E);
    }



    public void vie_arbre(){
        HashMap<Integer, Integer> arbre = new HashMap<>();
        for (int i = 0; i < 40; i++) { 
            arbre.put(i, (1/(40*24))*getNombre_arbes());
        }
        double arbres_morts=arbre.get(40*24);
        for (int i = 40*24; i >=1; i--){
            arbre.put(i,arbre.get(i-1));

        }
        journal.ajouter("il y a"+ getNombre_arbes() + " arbres morts");
        nombre_arbes -= arbres_morts;
        journal.ajouter("il reste"+ getNombre_arbes() + " arbres");

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


