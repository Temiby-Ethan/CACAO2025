package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.bourseCacao.IAcheteurBourse;
import abstraction.eqXRomu.produits.Feve;

public class AcheteurBourse extends ContratCadreAcheteur implements IAcheteurBourse {

    public AcheteurBourse(){
        super();
}

   
    public double demande(Feve f, double cours) {
        super.journal.ajouter("Demande de " + f + " à " + cours + " euros par tonne");
        if (f == Feve.F_MQ) { 
            return 80.0; // Achat de 80 tonnes de F_MQ à chaque next
        }
        return 0.0;
    }

    
    private int cryptogramme;

    public void notificationAchat(Feve f, double quantiteEnT, double coursEnEuroParT) {
        super.journal.ajouter("achat effectues");
        super.stock.ajouterStock(this,f,quantiteEnT, this.cryptogramme);
        super.journal.ajouter("");
        super.journal.ajouter("Achat de " + quantiteEnT + " tonnes de " + f + " à " + coursEnEuroParT + " euros par tonne");
		

	}


   
    public void notificationBlackList(int dureeEnStep) {
        super.journal.ajouter("Blacklist de la bourse pour " + dureeEnStep + " steps");
    }
}
    

