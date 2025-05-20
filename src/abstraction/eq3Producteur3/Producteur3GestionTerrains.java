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

    protected double nbParcellesBQ = 0;
    protected double nbParcellesBQ_E = 0;
    protected double nbParcellesMQ = 0;
    protected double nbParcellesMQ_E = 0;
    protected double nbParcellesHQ = 0;
    protected double nbParcellesHQ_B = 0;

    protected HashMap<Integer,LinkedList<Parcelle>> vie = new HashMap<Integer,LinkedList<Parcelle>>();
    protected  HashMap<Integer,LinkedList<Parcelle>> recolte = new HashMap<Integer,LinkedList<Parcelle>>();   

    protected LinkedList<Integer> deficteTerrain = new LinkedList();
    protected LinkedList<Integer> beneficeTerrain = new LinkedList();

    protected int [] effectifs = new int [3];
    protected int feveProduites = 0;
    protected double [] sechageHQ_B = new double [1];
    protected double [] sechageHQ = new double [1];
    protected double [] sechageMQ_E = new double [1];
    protected double [] sechageMQ = new double [1];
    protected double [] sechageBQ_E = new double [2];
    protected double [] sechageBQ = new double [2];


    public Producteur3GestionTerrains(){
        super();
    }


    void achatTerrain(Qualite q, boolean bio){
        if(q instanceof QualiteBQ){
            terrainBQ.add(new Parcelle(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
            if(((QualiteBQ) q).equitable){
                effectifs[2]+=800;
                nbParcellesBQ_E += 1;
            }else{
                effectifs[0]+=1200;
                effectifs[1]+=200;
                nbParcellesBQ += 1;
            }
        }else if( q instanceof QualiteMQ){
            terrainMQ.add(new Parcelle(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
            if(((QualiteMQ) q).equitable){
                effectifs[2]+=700;
                nbParcellesMQ_E += 1;
            }else{
                effectifs[1]+=700;
                nbParcellesMQ += 1;
            }
        }else if( q instanceof QualiteHQ){
            terrainHQ.add(new Parcelle(q,0));//modifier date de début
            deficteTerrain.add(q.achat);
            effectifs[2]+=500;
            if (bio){
                nbParcellesHQ_B += 1;
            }
            else{
                nbParcellesHQ += 1;
            }
        }else{
            journal.ajouter("Erreur qualité achat terrain.");
        }
    }

    void venteTerrain(Qualite q){
        if(q instanceof QualiteBQ){
            terrainBQ.removeFirst();
            beneficeTerrain.add(q.vente);
            if(((QualiteBQ) q).equitable){
                effectifs[2]-=800;
                nbParcellesBQ_E -= 1;
            }else{
                effectifs[0]-=1200;
                effectifs[1]-=200;
                nbParcellesBQ -= 1;
            }
        }else if( q instanceof QualiteMQ){
            terrainMQ.removeFirst();
            beneficeTerrain.add(q.vente);
            if(((QualiteMQ) q).equitable){
                effectifs[2]-=700;
                nbParcellesMQ_E -= 1;
            }else{
                effectifs[1]-=700;
                nbParcellesMQ -= 1;
            }
        }else if( q instanceof QualiteHQ){
            terrainMQ.removeFirst();
            beneficeTerrain.add(q.vente);
            effectifs[2]-=500;
            if (((QualiteHQ) q).bio){
                nbParcellesHQ_B -= 1;
            }
            else{
                nbParcellesHQ -= 1;
            }
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

            effectifs[2]+=500;

            Parcelle p = new Parcelle(new QualiteHQ(true,true), annee);
            nbParcellesHQ_B += 1;
            vie.get(annee).add(p);
            recolte.get(mois).add(p);
        }
        for (int i = 0; i < 6510; i++){
            int mois = Filiere.random.nextInt(11);
            int annee = Filiere.random.nextInt(36)+4;

            effectifs[2]+=700;

            Parcelle p = new Parcelle(new QualiteMQ(true), annee);
            nbParcellesMQ_E += 1;
            vie.get(annee).add(p);
            recolte.get(mois).add(p);
        }
        for (int i = 0; i < 6510; i++){
            int mois = Filiere.random.nextInt(11);
            int annee = Filiere.random.nextInt(37)+3;

            effectifs[0]+=1200;
            effectifs[1]+=200;

            Parcelle p = new Parcelle(new QualiteBQ(false), annee);
            nbParcellesBQ += 1;
            vie.get(annee).add(p);
            recolte.get(mois).add(p);

        }
    }

    
    // Paul
    void recolte(){
        LinkedList<Parcelle> prete = new LinkedList<>();
        prete = recolte.get(Filiere.LA_FILIERE.getNumeroMois());

        // Actualisation des sechages durant 2 nexts
        sechageBQ_E[0] = sechageBQ_E[1];
        sechageBQ_E[1] = 0;
        sechageBQ[0] = sechageBQ[1];
        sechageBQ[1] = 0;
        sechageMQ_E[0] = 0;
        sechageMQ[0] = 0;
        sechageHQ_B[0] = 0;
        sechageHQ[0] = 0;
        
        if(prete != null){
            for (Parcelle p : prete) {
                if(p.qualite instanceof QualiteBQ){
                    int nbFeve = Filiere.random.nextInt(5)+23;
                    if(p.qualite.equitable){
                        sechageBQ_E[1] += ((16*nbFeve)*0.753)*p.qualite.densité/1000000;
                    }
                    else{
                        sechageBQ[1] += ((16*nbFeve)*0.753)*p.qualite.densité/1000000;
                    }
                }else if( p.qualite instanceof QualiteMQ){
                    int nbFeve = Filiere.random.nextInt(4)+25;
                    if(p.qualite.equitable){
                        sechageMQ_E[0] += ((15*nbFeve)*0.750)*p.qualite.densité/1000000;
                    }
                    else {
                        sechageMQ[0] += ((15*nbFeve)*0.750)*p.qualite.densité/1000000;
                    }
                    
                }else if(p.qualite instanceof QualiteHQ){
                    int nbFeve = Filiere.random.nextInt(3)+29;
                    QualiteHQ qualite = (QualiteHQ) p.qualite;
                    if (qualite.bio){
                        sechageHQ_B[0] += ((10*nbFeve)*0.765)*p.qualite.densité/1000000;
                    }
                    else{
                        sechageHQ[0] += ((10*nbFeve)*0.765)*p.qualite.densité/1000000;
                    }   
            }
            
            }
        } 
    }


    void replanter(){
        LinkedList<Parcelle> tmp = vie.get(39);
        for(Parcelle t : tmp){
            deficteTerrain.add(t.qualite.replanter);
            tmp.remove(t);
            vie.get(0).add(t);
        } 
    }

    
    void actualiserTerrain(){
        if (Filiere.LA_FILIERE.getNumeroMois() == 1 && Filiere.LA_FILIERE.getJour() == 1){
            LinkedList<Parcelle> aReplanter = vie.get(39);
            // On fait vieillir toutes les parcelles d'un an
            for (int i = 38; i >= 0; i--){
                vie.put(i+1,vie.get(i));
            }
            // On replante toutes les parcelles de 40 ans
            vie.put(0, new LinkedList<>());
            for(Parcelle t : aReplanter){
                deficteTerrain.add(t.qualite.replanter);
                vie.get(0).add(t);
            }
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
