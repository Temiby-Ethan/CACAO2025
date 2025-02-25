package abstraction.eq3Producteur3;

import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;


//Classe écrite par Zoé
public class Producteur3Vente extends Producteur3Stock implements IVendeurBourse, IVendeurContratCadre{






    //Pour vendre en bourse
    @Override
    public double offre(Feve f, double cours) {
        /*if(this.feve.equals(f)){
            BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
            double pourcentage = (bourse.getCours(feve).getValeur()-bourse.getCours(feve).getMin())/(bourse.getCours(feve).getMax()-bourse.getCours(feve).getMin());
			return this.stockFeve.getValeur()*pourcentage;
        }else{
            return 0.0;
        }*/
        throw new UnsupportedOperationException("Not supported yet.");
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





    //Pour vendre en contrat Cadre
    @Override
    public boolean vend(IProduit produit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
