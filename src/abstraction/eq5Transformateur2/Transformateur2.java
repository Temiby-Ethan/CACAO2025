package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.bourseCacao.IAcheteurBourse;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;

public class Transformateur2 extends Transformateur2Acteur implements IAcheteurBourse {
    
	protected Variable stocktotal;

    public Transformateur2() {
        super();
		this.stocktotal = new Variable("Stock Equipe 5", this);
		

    }

    @Override
    public double demande(Feve f, double cours) {
        if (f == Feve.F_MQ) { 
            System.out.println("[Bourse] Demande de 80 tonnes de " + f + " au prix de " + cours + " €/T");
            return 80.0; // Achat de 80 tonnes de F_MQ à chaque next
        }
        return 0.0;
    }

    @Override
    public void notificationAchat(Feve f, double quantiteEnT, double coursEnEuroParT) {
		this.stocktotal.setValeur(this, this.stocktotal.getValeur()+quantiteEnT);
		System.out.println("on a un stock total de " + this.stocktotal.getValeur() + " tonnes de " + f);

	}


    @Override
    public void notificationBlackList(int dureeEnStep) {
        System.out.println("Attention ! Nous sommes exclus de la bourse pour " + dureeEnStep + " étapes.");
    }
}