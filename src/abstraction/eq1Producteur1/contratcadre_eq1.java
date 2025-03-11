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
        throw new UnsupportedOperationException("Unimplemented method 'vend'")
    }
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
    	/**
	 * Methode appelee par le SuperviseurVentesContratCadre apres une contreproposition
	 * de prix different de la part de l'acheteur, afin de connaitre la contreproposition
	 * de prix du vendeur.
	 * @param contrat
	 * @return Retourne un nombre inferieur ou egal a 0.0 si le vendeur souhaite mettre fin
	 * aux negociation en renoncant a ce contrat. Retourne le prix actuel a la tonne du 
	 * contrat (contrat.getPrix()) si le vendeur est d'accord avec ce prix.
	 * Sinon, retourne une contreproposition de prix.
	 */
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {


    }
        

    @Override
    
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        // Amal Moncer
        journal.ajouter("Nouveau contrat cadre : "+contrat);
    }

    @Override
    /**
	 * Methode appelee par le SuperviseurVentesContratCadre lorsque le vendeur doit livrer 
	 * quantite tonnes de produit afin d'honorer le contrat precise en parametre. 
	 * @param produit
	 * @param quantite
	 * @param contrat
	 * @return Retourne la quantite livree. Une penalite est prevue si cette quantite
	 *  est inferieure a celle precisee en parametre
	 */
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