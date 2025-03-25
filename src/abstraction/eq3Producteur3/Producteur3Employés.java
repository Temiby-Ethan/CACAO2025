package abstraction.eq3Producteur3;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.filiere.Filiere;

public class Producteur3Employés extends Producteur3Stock {
    
    public Producteur3Employés() {
        super();
    }
    
    // Paul
    double getMasseSalariale(){
        double masseSalariale = 0;
        double[] salaires = {6,15,37.5};
        for (int i = 0; i < 3; i++){
            masseSalariale += effectifs[i] * salaires[i];
        }
        return masseSalariale;
        }
    
    double getMasseSalarialeParticulier(Feve feve){
        if(feve.getGamme().equals(Gamme.BQ)){
            if(feve.isEquitable()){
                double masseSalariale = 100*nbParcellesBQ_E*8*37.5;
                return masseSalariale;
            }else{
                double masseSalariale = 100*nbParcellesBQ*(2*15+12*6);
                return masseSalariale;
            }
        }
        else if(feve.getGamme().equals(Gamme.MQ)){
            if(feve.isEquitable()){
                double masseSalariale = 100*nbParcellesMQ_E*6*37.5;
                return masseSalariale;
            }else{
                double masseSalariale = 100*nbParcellesMQ*6*15;
                return masseSalariale;
            }
        }else{
            if (feve.isBio()){
                double masseSalariale = 100*nbParcellesHQ_B*4*37.5;
                return masseSalariale;
            }else{
                double masseSalariale = 100*nbParcellesHQ*4*37.5;
                return masseSalariale;
            }
        }
        }

        void actualiserJournalMasseSalariale(){
            journalMasseSalariale.ajouter("-----ETAPE " + Filiere.LA_FILIERE.getEtape() + "-----\n");
            journalMasseSalariale.ajouter("Masse salariale : " + getMasseSalariale() + "\n");
            for (Feve feve : Feve.values()){
                journalMasseSalariale.ajouter("Masse salariale " + feve + " : " + getMasseSalarialeParticulier(feve) + "\n");
            }
        }
}

