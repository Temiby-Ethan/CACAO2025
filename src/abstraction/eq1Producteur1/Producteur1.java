package abstraction.eq1Producteur1;

import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur1 extends Producteur1Acteur implements IVendeurContratCadre {

    private Producteur1ContratCadre contratCadre; // Composition : instance de Producteur1ContratCadre

    public Producteur1() {
        super(producteur1);
        this.contratCadre = new Producteur1ContratCadre(); // Initialisation de l'instance
    }

    @Override
    public boolean vend(IProduit produit) {
        // Délégation à l'instance de Producteur1ContratCadre
        return contratCadre.vend(produit);
    }

    @Override
    public abstraction.eqXRomu.contratsCadres.Echeancier contrePropositionDuVendeur(
            abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre contrat) {
        // Délégation à l'instance de Producteur1ContratCadre
        return contratCadre.contrePropositionDuVendeur(contrat);
    }

    @Override
    public double propositionPrix(abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre contrat) {
        // Délégation à l'instance de Producteur1ContratCadre
        return contratCadre.propositionPrix(contrat);
    }

    @Override
    public double contrePropositionPrixVendeur(abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre contrat) {
        // Délégation à l'instance de Producteur1ContratCadre
        return contratCadre.contrePropositionPrixVendeur(contrat);
    }

    @Override
    public void notificationNouveauContratCadre(abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre contrat) {
        // Délégation à l'instance de Producteur1ContratCadre
        contratCadre.notificationNouveauContratCadre(contrat);
    }

    @Override
    public double livrer(IProduit produit, double quantite,
            abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre contrat) {
        // Délégation à l'instance de Producteur1ContratCadre
        return contratCadre.livrer(produit, quantite, contrat);
    }
}