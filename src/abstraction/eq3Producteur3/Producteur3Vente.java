package abstraction.eq3Producteur3;

import java.util.List;

import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.produits.IProduit;


public class Producteur3Vente extends Producteur3Stock implements IVendeurBourse,IVendeurContratCadre{

   
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
        
    }

    // CONTRAT CADRE //

    protected List<ExemplaireContratCadre> mesContratEnTantQueVendeur;

    @Override
    public boolean vend(IProduit produit) {
        if (produit instanceof Feve) {
            Feve feve = (Feve)produit;
            return feve.getGamme().equals(Gamme.MQ);
        }
        return false;
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contrePropositionDuVendeur'");
    }


    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'propositionPrix'");
    }


    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contrePropositionPrixVendeur'");
    }


    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificationNouveauContratCadre'");
    }


    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'livrer'");
    }


    




    
    
}
