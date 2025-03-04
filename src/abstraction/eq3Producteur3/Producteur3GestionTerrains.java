package abstraction.eq3Producteur3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//Zoé
public class Producteur3GestionTerrains extends Producteur3Acteur{

    protected LinkedList<Terrain> terrainBQ = new LinkedList();
    protected LinkedList<Terrain> terrainMQ = new LinkedList();
    protected LinkedList<Terrain> terrainHQ = new LinkedList();

    protected HashMap<Integer,LinkedList<Terrain>> vie = new HashMap<Integer,LinkedList<Terrain>>();
    protected  HashMap<Integer,LinkedList<Terrain>> récolte = new HashMap<Integer,LinkedList<Terrain>>();   

    protected LinkedList<Integer> deficteTerrain = new LinkedList();
    protected LinkedList<Integer> beneficeTerrain = new LinkedList();



    void achatTerrain(Qualite q, boolean bio){
        if(q instanceof QualiteBQ){
            terrainBQ.add(new Terrain(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
        }else if( q instanceof QualiteMQ){
            terrainMQ.add(new Terrain(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
        }else if( q instanceof QualiteHQ){
            terrainHQ.add(new Terrain(q,0));//modifier date de début
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

    void initVie(){

    }

    void initRecolte(){

    }


    void replanter(){
        LinkedList<Terrain> tmp = vie.get(0);
        for(Terrain t : tmp){
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
