package abstraction.eq3Producteur3;

import abstraction.eqXRomu.filiere.Filiere;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//Zoé
public class Producteur3GestionTerrains extends Producteur3Acteur{

    protected LinkedList<Parcelle> terrainBQ = new LinkedList();
    protected LinkedList<Parcelle> terrainMQ = new LinkedList();
    protected LinkedList<Parcelle> terrainHQ = new LinkedList();

    protected HashMap<Integer,LinkedList<Parcelle>> vie = new HashMap<Integer,LinkedList<Parcelle>>();
    protected  HashMap<Integer,LinkedList<Parcelle>> recolte = new HashMap<Integer,LinkedList<Parcelle>>();   

    protected LinkedList<Integer> deficteTerrain = new LinkedList();
    protected LinkedList<Integer> beneficeTerrain = new LinkedList();



    void achatTerrain(Qualite q, boolean bio){
        if(q instanceof QualiteBQ){
            terrainBQ.add(new Parcelle(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
        }else if( q instanceof QualiteMQ){
            terrainMQ.add(new Parcelle(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
        }else if( q instanceof QualiteHQ){
            terrainHQ.add(new Parcelle(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
        }else{
            journal.ajouter("Erreur qualité achat terrain.");
        }
    }

    void venteTerrain(Qualite q){
        if(q instanceof QualiteBQ){
            terrainBQ.removeFirst();
            beneficeTerrain.add(q.vente);
        }else if( q instanceof QualiteMQ){
            terrainMQ.removeFirst();
            beneficeTerrain.add(q.vente);
        }else if( q instanceof QualiteHQ){
            terrainMQ.removeFirst();
            beneficeTerrain.add(q.vente);
        }else{
            journal.ajouter("Erreur qualité vente terrain.");
        }
    }

   
    void initTerrain(){
        for (int i = 0; i < 40; i++ ){
            vie.put(i,new LinkedList<>());
        }
        for (int i = 0; i< 12; i++){
            recolte.put(i, new LinkedList<>());
        }
        for (int i = 0; i < 3720; i++) {
            int mois = Filiere.random.nextInt(11);
            int annee = Filiere.random.nextInt(35)+5;

            Parcelle p = new Parcelle(new QualiteHQ(true), annee);
            vie.get(annee).add(p);
            recolte.get(mois).add(p);
        }
        for (int i = 0; i < 6510; i++){
            int mois = Filiere.random.nextInt(11);
            int annee = Filiere.random.nextInt(36)+4;

            Parcelle p = new Parcelle(new QualiteMQ(true), annee);
            vie.get(annee).add(p);
            recolte.get(mois).add(p);
        }
        for (int i = 0; i < 6510; i++){
            int mois = Filiere.random.nextInt(11);
            int annee = Filiere.random.nextInt(37)+3;

            Parcelle p = new Parcelle(new QualiteBQ(false), annee);
            vie.get(annee).add(p);
            recolte.get(mois).add(p);

        }

    }


    void replanter(){
        LinkedList<Parcelle> tmp = vie.get(0);
        for(Parcelle t : tmp){
            deficteTerrain.add(t.qualite.replanter);
        } 
    }


    void cleanDeficite(){
        deficteTerrain.clear();
    }
    void cleanBenefice(){
        beneficeTerrain.clear();
    }
    void cleanCout(){
        cleanBenefice();
        cleanDeficite();
    }

    







    
}
