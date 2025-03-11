package abstraction.eq1Producteur1;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur1ContratCadre extends Producteur1Acteur implements IVendeurContratCadre{

    @Override
    public boolean vend(IProduit produit) {
        // Vérifiez si vous avez suffisamment de stock pour vendre 25% de votre production
        double stockDisponible = getStock(produit); // Supposons que vous avez une méthode getStock pour obtenir le stock disponible
        double productionTotale = getProductionTotale(produit); // Supposons que vous avez une méthode getProductionTotale pour obtenir la production totale
        double quantiteVoulue = 0.25 * productionTotale;

        // Si le stock disponible est suffisant pour vendre 25% de la production, retournez true
        return stockDisponible >= quantiteVoulue;
    } {
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
