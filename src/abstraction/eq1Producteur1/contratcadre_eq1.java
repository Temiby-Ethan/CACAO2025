package abstraction.eq1Producteur1;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.produits.IProduit;

public class contratcadre_eq1 extends Producteur1Acteur implements IVendeurContratCadre{

    @Override
    public boolean vend(IProduit produit) {
        {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vend'");
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