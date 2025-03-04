package abstraction.eq3Producteur3;
public class Producteur3Vente extends Producteur3Stock /*implements IVendeurBourse*/{

   
   
    public Producteur3Vente() {
        super();
    }





/*
    //Pour vendre en bourse
    @Override
    public double offre(Feve f, double cours) {
        if(this.feve.equals(f)){
            BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
            double pourcentage = (bourse.getCours(feve).getValeur()-bourse.getCours(feve).getMin())/(bourse.getCours(feve).getMax()-bourse.getCours(feve).getMin());
			return this.stockFeve.getValeur()*pourcentage;
        }else{
            return 0.0;
        }
       
    }

    @Override
    public double notificationVente(Feve f, double quantiteEnT, double coursEnEuroParT) {
        double livrable = Math.min(quantiteEnT, this.stockFeve.getValeur());
		this.stockFeve.setValeur(this, this.stockFeve.getValeur()-livrable);
		return livrable;
    }

    @Override
    public void notificationBlackList(int dureeEnStep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



*/

    
    
}
