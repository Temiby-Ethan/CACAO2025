//Simon
package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

class ContratCadreAcheteur extends ContratCadreVendeur implements IAcheteurContratCadre{
    public ContratCadreAcheteur() {
        super();
    }
    /**
	 * Methode appelee par le superviseur afin de savoir si l'acheteur est pret a
	 * faire un contrat cadre sur le produit indique.
	 * 
	 * @param produit
	 * @return Retourne false si l'acheteur ne souhaite pas etablir de contrat a
	 *         cette etape pour ce type de produit (retourne true si il est pret a
	 *         negocier un contrat cadre pour ce type de produit).
	 */
	public boolean achete(IProduit produit){
		if (produit == Feve.F_MQ){
			return true;
		}
		if (produit == Feve.F_MQ_E){
			return true;
		}
		if (produit == Feve.F_HQ_E){
			return true;
		}
		if (produit == Feve.F_HQ_BE){
			return true;
		}
		if (produit == Chocolat.C_HQ_E){
			return true;
		}

        return false;

	}

	/**
	 * Methode appelee par le SuperviseurVentesContratCadre lors des negociations
	 * sur l'echeancier afin de connaitre la contreproposition de l'acheteur. Les
	 * precedentes propositions d'echeancier peuvent etre consultees via un appel a
	 * la methode getEcheanciers() sur le contrat passe en parametre.
	 * 
	 * @param contrat. Notamment, getEcheancier() appelee sur le contrat retourne
	 *                 l'echeancier que le vendeur vient de proposer.
	 * @return Retoune null si l'acheteur souhaite mettre fin aux negociation (et
	 *         abandonner du coup ce contrat). Retourne le meme echeancier que celui
	 *         du contrat (contrat.getEcheancier()) si l'acheteur est d'accord pour
	 *         un tel echeancier. Sinon, retourne un nouvel echeancier que le
	 *         superviseur soumettra au vendeur.
	 */
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat){
		Echeancier e=contrat.getEcheancier();
		int nbrStep = e.getNbEcheances();
		int stepDebut = e.getStepDebut();
		double quantite = e.getQuantite(stepDebut);
		Feve f = (Feve) contrat.getProduit();
		double quantiteVoulue = this.getProductionTotale()*getProportion(f);

		if (quantite > (quantiteVoulue)*1.1){
			e.ajouter(quantiteVoulue*1.1 -quantite);
		}
		if (quantite < (quantiteVoulue)*0.1){
			e.ajouter(quantiteVoulue*0.1 -quantite);
		}
		return e;
		
		
    }
	
	/**
	 * Methode appelee par le SuperviseurVentesContratCadre lors des negociations
	 * sur le prix a la tonne afin de connaitre la contreproposition de l'acheteur.
	 * L'acheteur peut consulter les precedentes propositions via un appel a la
	 * methode getListePrix() sur le contrat. En particulier la methode getPrix()
	 * appelee sur contrat indique la derniere proposition faite par le vendeur.
	 * 
	 * @param contrat
	 * @return Retourne un prix negatif ou nul si l'acheteur souhaite mettre fin aux
	 *         negociations (en renoncant a ce contrat). Retourne le prix actuel
	 *         (contrat.getPrix()) si il est d'accord avec ce prix. Sinon, retourne
	 *         un autre prix correspondant a sa contreproposition.
	 */
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat){
        Double prixVendeur= contrat.getPrix();
		Feve f = (Feve) contrat.getProduit();
		BourseCacao bc =  ((BourseCacao) (Filiere.LA_FILIERE.getActeur("BourseCacao")));
		Double prixBourse= bc.getCours(f).getValeur();
		if (prixVendeur <= prixBourse){  
			return prixVendeur;
		}
		else{
			return prixBourse;
		}
    }

	/**
	 * Methode appelee par le SuperviseurVentesContratCadre afin de notifier le
	 * l'acheteur de la reussite des negociations sur le contrat precise en
	 * parametre qui a ete initie par le vendeur. Le superviseur veillera a
	 * l'application de ce contrat (des appels a livrer(...) seront effectues
	 * lorsque le vendeur devra livrer afin d'honorer le contrat, et des transferts
	 * d'argent auront lieur lorsque l'acheteur paiera les echeances prevues)..
	 * 
	 * @param contrat
	 */
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat){
    
    }

	/**
	 * Methode appelee par le SuperviseurVentesContratCadre afin de notifier
	 * l'acheteur de la livraison du lot de produit precise en parametre
	 * (dans le cadre du contrat contrat). Il se peut que la quantitee livree
	 * soit inferieure a la quantite prevue par le contrat si le vendeur est dans 
	 * l'incapacite de la fournir. Dans ce cas, le vendeur aura une penalite 
	 * (un pourcentage de produit a livrer en plus). L'acheteur doit a minima 
	 * mettre ce produit dans son stock.
	 */
	public void receptionner(IProduit p, double quantiteEnTonnes, ExemplaireContratCadre contrat){
        this.ajouterStock(this, p, quantiteEnTonnes, super.cryptogramme);
		this.journal.ajouter("ajout de " + quantiteEnTonnes + " tonnes de " + p + " à notre stock grâce à un contrat cadre");	
    }

}


