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
        // Amal Moncer
        return contrat.getPrix();
    }

    @Override

    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        // Amal Moncer
        if (contrePropositionDuVendeur(contrat)==null){
            return 0.0;
        }
        else{
            return contrat.getPrix();
        }
    }
        

    @Override
    
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        // Amal Moncer
        journal.ajouter("Nouveau contrat cadre : "+contrat);
    }

    @Override

    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        // Amal Moncer
        if (quantite > getQuantiteEnStock(produit, cryptogramme)) {
            journal.ajouter("Le vendeur n'a pas pu livrer la quantite demandee");
            return getQuantiteEnStock(produit, cryptogramme);
        }
        else {
            journal.ajouter("Le vendeur a livre la quantite demandee");
            double quantiteEnStock = getQuantiteEnStock(produit, cryptogramme);
            quantiteEnStock -= quantite;
            return quantiteEnStock;
        }
    } 

}
