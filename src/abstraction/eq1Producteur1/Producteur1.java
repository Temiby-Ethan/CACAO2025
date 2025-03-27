package abstraction.eq1Producteur1;

import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;

import java.util.List;

import abstraction.eqXRomu.contratsCadres.Echeancier;

public class Producteur1 extends Producteur1Bourse implements IVendeurContratCadre {

    private Producteur1ContratCadre contratCadre;

    public Producteur1() {
        super();
        this.contratCadre = new Producteur1ContratCadre(); // Initialisation de l'instance
    }

    @Override
    public boolean vend(IProduit produit) {
        return contratCadre.vend(produit);
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        return contratCadre.contrePropositionDuVendeur(contrat);
    }

    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        return contratCadre.propositionPrix(contrat);
    }

    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        return contratCadre.contrePropositionPrixVendeur(contrat);
    }

    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        contratCadre.notificationNouveauContratCadre(contrat);
    }

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        return contratCadre.livrer(produit, quantite, contrat);
    }
	public List<Variable> getIndicateurs() {
		return super.getIndicateurs();
	}

    @Override
    public void next() {
        super.next(); // mise à jour stock / journal
        //  livraison automatique des contrats cadres
    }
}
