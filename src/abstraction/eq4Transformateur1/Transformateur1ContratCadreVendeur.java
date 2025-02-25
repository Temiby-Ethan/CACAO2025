package abstraction.eq4Transformateur1;

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
	
	public Transformateur1ContratCadreVendeur(IProduit produit) {
		super(produit);
		this.mesContratEnTantQueVendeur=new LinkedList<ExemplaireContratCadre>();
	}

    /*
     * Cette methode decrit la strategie de negociation du volume en jeu par le Transformateur1 en tant que vendeur
	 * @param contrat exemplaire du contrat au moment de la negociation
	 * 
	 * @return Echeancier qui contient les differents volume de vente pour les differentes periodes
     */
	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		if (contrat.getProduit().equals(produit)) {
			if (contrat.getEcheancier().getQuantiteTotale()<stock.getValeur()) {
				if (Filiere.random.nextDouble()<0.1) {
				return contrat.getEcheancier(); // on ne cherche pas a negocier sur le previsionnel de livraison
				} else {//dans 90% des cas on fait une contreproposition pour l'echeancier
					Echeancier e = contrat.getEcheancier();
					e.set(e.getStepDebut(), e.getQuantite(e.getStepDebut())/2.0);// on souhaite livrer deux fois moins lors de la 1ere livraison... un choix arbitraire, juste pour l'exemple...
					return e;
				}
			} else {
				return null; // on est frileux : on ne s'engage dans un contrat cadre que si on a toute la quantite en stock (on pourrait accepter meme si nous n'avons pas tout car nous pouvons produire/acheter pour tenir les engagements) 
			}
		} else {
			return null;// on ne vend pas de ce produit
		}
	}

	public double propositionPrix(ExemplaireContratCadre contrat) {
		return 0.5 + (5000.0-contrat.getQuantiteTotale());// plus la quantite est elevee, plus le prix est interessant
	}

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
