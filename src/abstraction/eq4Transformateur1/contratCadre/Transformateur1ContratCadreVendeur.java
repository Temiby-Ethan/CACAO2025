package abstraction.eq4Transformateur1.contratCadre;

import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.contratsCadres.*;


/*
 * @author MURY Julien
 * Cette calsse, héritant de TransformateurContratCadre, décrit le comportement de notre acteur lors de la creation d'un contrat cadre en tant que venndeur
 */

public class Transformateur1ContratCadreVendeur extends TransformateurContratCadre implements IVendeurContratCadre {

	protected List<ExemplaireContratCadre> mesContratEnTantQueVendeur;//Contient tous les contrats de vente
	protected double qttInitialementVoulue;	
	protected double prixInitialementVoulu;
	protected double epsilon; 
	
	public Transformateur1ContratCadreVendeur(IProduit produit) {
		super(produit);
		this.mesContratEnTantQueVendeur=new LinkedList<ExemplaireContratCadre>();
		this.qttInitialementVoulue = stock.getValeur() * 0.90;  //On cherche à vendre 75% de notre stock total actuel
		this.epsilon = 0.1;  //Pourcentage d'erreur entre la quantite voulue et celle du contrat actuel

	}

    /*
     * Cette methode decrit la strategie de negociation du volume en jeu par le Transformateur1 en tant que vendeur
	 * @param contrat exemplaire du contrat au moment de la negociation
	 * 
	 * @return Echeancier du vendeur qui contient les differents volume de vente pour les differentes periodes
     */
	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {

		if (contrat.getProduit().equals(produit)) {
			double qtt_totale_voulue = qttInitialementVoulue;

			if (contrat.getEcheancier().getQuantiteTotale()<stock.getValeur()) {  //On chercher à honorer le contrat sur les qtts

				//On vérifie que le contrat propose met en jeu suffisemment de quantite par rapport aux quantite voulues par notre acteur, a plus ou moins epsilon pres
				double delta = contrat.getEcheancier().getQuantiteTotale() - qtt_totale_voulue;

				if (Math.abs(delta) <= epsilon*qtt_totale_voulue ) {
					return contrat.getEcheancier(); // on ne cherche pas a negocier sur le previsionnel de livraison
				} 
				else {//Si la proposition n'est pas dans la fourchette voulue, on contre-propose
					Echeancier e = contrat.getEcheancier();
					//On procède par dichotomie en augmentant ou diminuant le volume de vente de nos produits

					qtt_totale_voulue = qtt_totale_voulue + delta/8;//si detla<0, proposition trop faible donc on diminue notre demande
																	//si delta>0, proposition trop forte donc on augmante notre demande

					//Redistribution uniforme de la hausse ou de la baisse des qtt vendues 
					for(int i = e.getStepDebut() ; i< e.getStepFin() ; i++){
						double qtti = e.getQuantite(i-e.getStepDebut());
						e.set(i, qtti*1.125);
					}
					return e;
				}
			} else {
				return null; // on est frileux : on ne s'engage dans un contrat cadre que si on a toute la quantite en stock (on pourrait accepter meme si nous n'avons pas tout car nous pouvons produire/acheter pour tenir les engagements) 
			}
		} else {
			return null;// on ne vend pas de ce produit
		}
	}

	/*
	 * @param contrat exemplaire du contrat en cours de negociation
	 * 
	 * @return prix initial propose par notre acteur
	 */
	public double propositionPrix(ExemplaireContratCadre contrat) {
		return 0.5 + (5000.0-contrat.getQuantiteTotale());// plus la quantite est elevee, plus le prix est interessant
	}


	/*
	 * @param contrat exemplaire du contrat en cours de negociation
	 * 
	 * @return nouveau prix negocie par notre acteur
	 */
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		if (Filiere.random.nextDouble()<0.1) {
			return contrat.getPrix(); // on ne cherche pas a negocier dans 10% des cas
		} else {//dans 90% des cas on fait une contreproposition differente
			return 0.5 + (5000.0-(contrat.getQuantiteTotale()*Filiere.random.nextDouble()));
		}
	}

	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.mesContratEnTantQueVendeur.add(contrat);
	}
	
	public void next() {
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQueVendeur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQueVendeur.removeAll(contratsObsoletes);
	}

	public boolean vend(IProduit produi) {
		return produit==produi;
	}

	public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
		double livre = Math.min(stock.getValeur(), quantite);
		if (livre>0.0) {
			stock.retirer(this,  livre);
		}
		return livre;
	}

	public boolean peutVendre(IProduit produit) {
		return super.produit.equals(produit);
	}
	public String toString() {
		return this.getNom();
	}

}
