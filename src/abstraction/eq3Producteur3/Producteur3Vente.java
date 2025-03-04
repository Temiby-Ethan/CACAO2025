package abstraction.eq3Producteur3;

import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.filiere.Filiere;


public class Producteur3Vente extends Producteur3Stock implements IVendeurBourse{

   
        public Producteur3Vente() {
            super();

        }


    // VENTE EN BOURSE //

    // Paul
    @Override
    public double offre(Feve feve, double cours) {
       
        if (this.stockFeve.get(feve).getValeur(cryptogramme)>0){
            BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
            double pourcentage = (bourse.getCours(feve).getValeur()-bourse.getCours(feve).getMin())/(bourse.getCours(feve).getMax()-bourse.getCours(feve).getMin());
            return this.stockFeve.get(feve).getValeur(cryptogramme)*pourcentage;

        }else{
            return 0.0;
        }
       
    }

    @Override
    public double notificationVente(Feve f, double coursEnEuroParT, double quantiteEnT) {
        double livrable = Math.min(this.stockFeve.get(f).getValeur(cryptogramme), quantiteEnT);
		this.stockFeve.get(f).setValeur(this, stockFeve.get(f).getValeur(cryptogramme)-livrable,cryptogramme);
		return livrable;
    }

    @Override
    public void notificationBlackList(int dureeEnStep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // CONTRAT CADRE //
    




    
    
}
