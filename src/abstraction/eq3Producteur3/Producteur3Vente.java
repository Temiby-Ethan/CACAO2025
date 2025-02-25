package abstraction.eq3Producteur3;

import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur3Vente extends Producteur3Stock implements IVendeurBourse, IVendeurContratCadre{







    @Override
    public double offre(Feve f, double cours) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double notificationVente(Feve f, double quantiteEnT, double coursEnEuroParT) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificationBlackList(int dureeEnStep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
