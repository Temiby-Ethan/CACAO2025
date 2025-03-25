package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.bourseCacao.IAcheteurBourse;
import abstraction.eqXRomu.produits.Feve;

public class AcheteurBourse extends ContratCadreAcheteur implements IAcheteurBourse {

    public AcheteurBourse(){
                super();
}

   
    public double demande(Feve f, double cours) {
        if (f == Feve.F_HQ_BE || f == Feve.F_HQ_E || f == Feve.F_MQ_E || f == Feve.F_MQ){
            double enStock = super.getQuantiteStock(f);
            double quantiteVoulueProduction= super.getProductionTotale()*super.getProportion(f);
            double difference= quantiteVoulueProduction-enStock;

            if (difference>0){
                return difference;
            }

        
        
        }
        return 0.0;
    }

    
    protected int cryptogramme;

    public void notificationAchat(Feve f, double quantiteEnT, double coursEnEuroParT) {
        super.journal.ajouter("achat effectues");
        this.ajouterStock(this,f,quantiteEnT, this.cryptogramme);
        super.journal.ajouter("ajout de " + quantiteEnT + " tonnes de " + f + " à notre stock");
		

	}


   
    public void notificationBlackList(int dureeEnStep) {
        super.journal.ajouter("Blacklist de la bourse pour " + dureeEnStep + " steps");
    }


public int getCryptogramme() {
    return this.cryptogramme;
}
}
    

