package abstraction.eq1Producteur1;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur1ContratCadre extends Producteur1Acteur implements IVendeurContratCadre{

    @Override
    public boolean vend(IProduit produit) {
       return true;
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        // Récupère l'échéancier proposé par l'acheteur
        Echeancier echeancierPropose = contrat.getEcheancier();

        // Récupère le stock disponible et le stock total pour le produit concerné
        double stockDisponible = getStock(contrat.getProduit());
        double stockTotal = getStockTotal(contrat.getProduit());

        // Calcule 25% du stock total
        double quantiteMaximale = 0.25 * stockTotal;
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
