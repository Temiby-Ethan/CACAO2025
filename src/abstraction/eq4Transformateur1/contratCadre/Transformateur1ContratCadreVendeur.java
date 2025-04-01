package abstraction.eq4Transformateur1.contratCadre;


import java.util.LinkedList;
import java.util.List;


import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.contratsCadres.*;


/*
 * @author MURY Julien
 * Cette calsse, héritant de Transformateur1ContratCadre, décrit le comportement de notre acteur lors de la creation d'un contrat cadre en tant que venndeur
 */

public class Transformateur1ContratCadreVendeur extends TransformateurContratCadre implements IVendeurContratCadre {

	
	protected double partInitialementVoulue;	
	protected double prixInitialementVoulu;
	protected double epsilon;
	
	public Transformateur1ContratCadreVendeur() {

		super();
		this.mesContratEnTantQueVendeur=new LinkedList<ExemplaireContratCadre>();

		this.partInitialementVoulue = 0.3; //A MODIFIER On cherche initialement à vendre 30% du stock du produit dont il est question
		this.epsilon = 0.1;  //A MODIFIER Pourcentage d'erreur entre la quantite voulue et celle du contrat actuel

	}








	//A MODIFIER
	//La stratégie de négociation doit être différenciée selon le produit mais pour la quantité, cela est probablement peu pertinent
	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {

		double qttVoulue = contrat.getEcheancier().getQuantiteTotale();

		if (qttVoulue>= SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER){
			IProduit produit;
			//On vend des chocolat de marque
			if (contrat.getProduit().getType() == "ChocolatDeMarque"){
				produit = ((ChocolatDeMarque)contrat.getProduit()).getChocolat();

				if (!this.peutVendre(produit)) return null; //On ne vend pas de ce produit

				Echeancier e = contrat.getEcheancier(); //Récupération de l'échéancier actuel


				//Cas d'acceptation : la quantité totale est légale et proche de la quantité que l'on souhaite vendre à 10% près
				if(e.getQuantiteTotale()> 100. && Math.abs(e.getQuantiteTotale()-qttVoulue)/qttVoulue < 0.1){
					return e;
				}
				//On modifie l'échéancier uniformément pour se rapporcher de nos exigeances
				else{
					for(int s = e.getStepDebut() ; s<e.getStepFin() ; s++){
						double qttActuelle = e.getQuantite(s);
						e.set(s, qttVoulue +  (qttActuelle - qttVoulue)/16);
					}

					//On vérifie que notre contrat respecte bien les règles des contrats cadres par rapport aux quantité minimale par step
					for(int s = e.getStepDebut() ; s<e.getStepFin() ; s++){
						if (e.getQuantite(s) < e.getQuantiteTotale()/(10*e.getNbEcheances())){
							e.set(s, e.getQuantite(s) +  e.getQuantiteTotale()/(10*e.getNbEcheances()));
						}
					}

					return e;
				}

				
			}

			//Vente d'un chocolat non marqué
			else {
				produit = contrat.getProduit();

				if (!this.peutVendre(produit)) return null; //On ne vend pas de ce produit

				Echeancier e = contrat.getEcheancier(); //Récupération de l'échéancier actuel


				//Cas d'acceptation : la quantité totale est légale et proche de la quantité que l'on souhaite vendre à 10% près
				if(e.getQuantiteTotale()> 100. && Math.abs(e.getQuantiteTotale()-qttVoulue)/qttVoulue < 0.1){
					return e;
				}
				//On modifie l'échéancier uniformément pour se rapporcher de nos exigeances
				else{
					for(int s = e.getStepDebut() ; s<e.getStepFin() ; s++){
						double qttActuelle = e.getQuantite(s);
						e.set(s, qttVoulue +  (qttActuelle - qttVoulue)/16);
					}

					//On vérifie que notre contrat respecte bien les règles des contrats cadres par rapport aux quantité minimale par step
					for(int s = e.getStepDebut() ; s<e.getStepFin() ; s++){
						if (e.getQuantite(s) < e.getQuantiteTotale()/(10*e.getNbEcheances())){
							e.set(s, e.getQuantite(s) +  e.getQuantiteTotale()/(10*e.getNbEcheances()));
						}
					}

					return e;
				}
			}
		}
		return null ; //On annule les négociations si le nouveau contrat a une quantité illégale
	}
	







	public double propositionPrix(ExemplaireContratCadre contrat) {
		double prixBase;
		if (contrat.getProduit().getType() ==  "ChocolatDeMarque"){
			IProduit prod = ((ChocolatDeMarque)contrat.getProduit()).getChocolat();
			if (prixTChocoBase.get(prod) ==  null){
				return 6000;
			}
			else{
				prixBase = prixTChocoBase.get(prod)*marges.get(prod);
				if(contrat.getQuantiteTotale() < 2000){
					return prixBase*(1 - 0.05*contrat.getQuantiteTotale()/2000);
				}
				return 0.95*prixBase;// plus la quantite est elevee, plus le prix est interessant
			}
		}
		else {
			IProduit prod = contrat.getProduit();
			if (prixTChocoBase.get(prod) ==  null){
				return 6000;
			}
			else{
				prixBase = prixTChocoBase.get(prod)*marges.get(prod);
				if(contrat.getQuantiteTotale() < 2000){
					return prixBase*(1 - 0.05*contrat.getQuantiteTotale()/2000);
				}
				return 0.95*prixBase;// plus la quantite est elevee, plus le prix est interessant
			}
		}
	}








	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		IProduit produit = contrat.getProduit();

		//Si le produit vendu est un chocolat BQ, on négocie de manière à avoir de très grandes marges
		if (produit == Chocolat.C_BQ || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat()==Chocolat.C_BQ)){
			//Si le prix proposé est plus élevé que celui que l'on a calculé, on accepte le contrat
			if (contrat.getPrix() > prixTChocoBase.get(Chocolat.C_BQ) * marges.get(Chocolat.C_BQ)){
				return contrat.getPrix();
			}
			//Si le prix est trop faible, on reste sur le prix minimum auquel on veut vendre
			if (contrat.getPrix()< prixTChocoBase.get(Chocolat.C_BQ)*marges.get(Chocolat.C_BQ)*0.75){
				return prixTChocoBase.get(Chocolat.C_BQ)*marges.get(Chocolat.C_BQ)*0.75;
			}
			//Si le prix du contrat est à un epsilon près de notre prix, on accepte
			double notrePrix = prixTChocoBase.get(Chocolat.C_BQ)*marges.get(Chocolat.C_BQ);
			double diffRelative = Math.abs(contrat.getPrix()- notrePrix)/notrePrix;
			if (diffRelative<epsilon){
				return contrat.getPrix();
			}
			//Sinon on cherche à négocier le prix, vers le bas par dichotomie en notre faveur à 90%
			else{
				double nouveauPrix = contrat.getPrix() * 0.1 + notrePrix * 0.9;
				if (nouveauPrix < prixTChocoBase.get(Chocolat.C_BQ)*marges.get(Chocolat.C_BQ)*0.75){
					return prixTChocoBase.get(Chocolat.C_BQ)*marges.get(Chocolat.C_BQ)*0.75;
				}
				else{
					notrePrix = nouveauPrix;
					return notrePrix;
				}
			}
		}

		//Si le produit vendu est BQ_E, on cherche a des marges réduites ce qui réduit le champ des négociations
		if (produit == Chocolat.C_BQ_E || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat()==Chocolat.C_BQ_E)){
			//Si le prix proposé est plus élevé que celui que l'on a calculé, on vérifie que le prix n'est pas trop élevé non plus pour maitriser un minimum le prix de vente final
			if (contrat.getPrix() > prixTChocoBase.get(Chocolat.C_BQ_E) * marges.get(Chocolat.C_BQ_E)){
				return contrat.getPrix();
			}
			//Si le prix est trop faible, on reste sur le prix minimum auquel on veut vendre
			if (contrat.getPrix()< prixTChocoBase.get(Chocolat.C_BQ_E)*marges.get(Chocolat.C_BQ_E)*0.93){
				return prixTChocoBase.get(Chocolat.C_BQ_E)*marges.get(Chocolat.C_BQ_E)*0.93;
			}
			//Si le prix du contrat est à un epsilon près de notre prix, on accepte
			double notrePrix = prixTChocoBase.get(Chocolat.C_BQ_E)*marges.get(Chocolat.C_BQ_E);
			double diffRelative = Math.abs(contrat.getPrix()- notrePrix)/notrePrix;
			if (diffRelative<epsilon){
				return contrat.getPrix();
			}
			//Sinon on cherche à négocier le prix, vers le bas par dichotomie en notre faveur à 90%
			else{
				double nouveauPrix = contrat.getPrix() * 0.1 + notrePrix * 0.9;
				if (nouveauPrix < prixTChocoBase.get(Chocolat.C_BQ_E)*marges.get(Chocolat.C_BQ_E)*0.93){
					return prixTChocoBase.get(Chocolat.C_BQ_E)*marges.get(Chocolat.C_BQ_E)*0.93;
				}
				else{
					notrePrix = nouveauPrix;
					return notrePrix;
				}
			}
		}


		//Idem, si le produit est de moyenne gamme équitable, on veut avoir des marges faibles et controler le prix de vente sans pour autant vendre à perte
		if (produit == Chocolat.C_MQ_E || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat()==Chocolat.C_MQ_E)){
			//Si le prix proposé est plus élevé que celui que l'on a calculé, on accepte le contrat
			if (contrat.getPrix() > prixTChocoBase.get(Chocolat.C_MQ_E) * marges.get(Chocolat.C_MQ_E)){
				return contrat.getPrix();
			}
			//Si le prix est trop faible, on reste sur le prix minimum auquel on veut vendre
			if (contrat.getPrix()< prixTChocoBase.get(Chocolat.C_MQ_E)*marges.get(Chocolat.C_MQ_E)*0.93){
				return prixTChocoBase.get(Chocolat.C_MQ_E)*marges.get(Chocolat.C_MQ_E)*0.93;
			}
			//Si le prix du contrat est à un epsilon près de notre prix, on accepte
			double notrePrix = prixTChocoBase.get(Chocolat.C_MQ_E)*marges.get(Chocolat.C_MQ_E);
			double diffRelative = Math.abs(contrat.getPrix()- notrePrix)/notrePrix;
			if (diffRelative<epsilon){
				return contrat.getPrix();
			}
			//Sinon on cherche à négocier le prix, vers le bas par dichotomie en notre faveur à 90%
			else{
				double nouveauPrix = contrat.getPrix() * 0.1 + notrePrix * 0.9;
				if (nouveauPrix < prixTChocoBase.get(Chocolat.C_MQ_E)*marges.get(Chocolat.C_MQ_E)*0.93){
					return prixTChocoBase.get(Chocolat.C_MQ_E)*marges.get(Chocolat.C_MQ_E)*0.93;
				}
				else{
					notrePrix = nouveauPrix;
					return notrePrix;
				}
			}
		}

		//Si le produit vendu est HQ_BE, on peut se permettre de prendre des marges plus grandes car un acheteur de haut de gamme sera peu regardant sur le prix, cherchant principalement à se faire plaisir
		if (produit == Chocolat.C_HQ_BE || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat()==Chocolat.C_HQ_BE)){
			//Si le prix proposé est plus élevé que celui que l'on a calculé, on accepte le contrat
			if (contrat.getPrix() > prixTChocoBase.get(Chocolat.C_HQ_BE) * marges.get(Chocolat.C_HQ_BE)){
				return contrat.getPrix();
			}
			//Si le prix est trop faible, on reste sur le prix minimum auquel on veut vendre
			if (contrat.getPrix()< prixTChocoBase.get(Chocolat.C_HQ_BE)*marges.get(Chocolat.C_HQ_BE)*0.78){
				return prixTChocoBase.get(Chocolat.C_HQ_BE)*marges.get(Chocolat.C_HQ_BE)*0.78;
			}
			//Si le prix du contrat est à un epsilon près de notre prix, on accepte
			double notrePrix = prixTChocoBase.get(Chocolat.C_HQ_BE)*marges.get(Chocolat.C_HQ_BE);
			double diffRelative = Math.abs(contrat.getPrix()- notrePrix)/notrePrix;
			if (diffRelative<epsilon){
				return contrat.getPrix();
			}
			//Sinon on cherche à négocier le prix, vers le bas par dichotomie en notre faveur à 90%
			else{
				double nouveauPrix = contrat.getPrix() * 0.1 + notrePrix * 0.9;
				if (nouveauPrix < prixTChocoBase.get(Chocolat.C_HQ_BE)*marges.get(Chocolat.C_HQ_BE)*0.78){
					return prixTChocoBase.get(Chocolat.C_HQ_BE)*marges.get(Chocolat.C_HQ_BE)*0.78;
				}
				else{
					notrePrix = nouveauPrix;
					return notrePrix;
				}
			}
		}

		return -1;
	}







	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		if(contrat.getAcheteur() == this){
			this.mesContratEnTantQuAcheteur.add(contrat);
		}
		else{
			this.mesContratEnTantQueVendeur.add(contrat);
		}

		this.journalCC.ajouter("Nouveau contrat cadre obtenu \n");
		this.journalCC.ajouter("Acheteur : " + contrat.getAcheteur() + " ; Vendeur : " + contrat.getVendeur() + "\n");
		this.journalCC.ajouter("Produit :  " + contrat.getProduit() + "\n");
		this.journalCC.ajouter("Echeancier : " + contrat.getEcheancier() + "\n");
	}
	








	public void next() {

		super.next();

		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQueVendeur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQueVendeur.removeAll(contratsObsoletes);
	}








	//A MODIFIER
	//Ici le problème est qu'on ne vérifie que s'il est possible de vendre à l'instant T
	//On ne prend pas en compte le fait que l'on ait possiblement d'autre livraisons à réaliser sur la même période
	//Il faudra s'assurer que l'on ait du stock pour cette transaction spécifiquement
	public boolean vend(IProduit produit) {
		if (produit.getType() == "Chocolat"){
			return stockChoco.get(produit)!=null;
		}
		else if (produit.getType() == "ChocolatDeMarque"){
			return ((ChocolatDeMarque)produit).getMarque() == "LimDt" && stockChoco.get(((ChocolatDeMarque)produit).getChocolat())!=null;
		}
		else{
			return false;
		}
	}










	public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
		

			if (produit.getType() == "ChocolatDeMarque"){
				double livre = Math.min(stockChoco.get(((ChocolatDeMarque)produit).getChocolat()), quantite);
				if (livre > 0.){
					totalStocksChoco.retirer(this,  livre, cryptogramme);
					totalStocksChocoMarque.retirer(this, livre, this.cryptogramme);
					stockChocoMarque.put((ChocolatDeMarque)produit, stockChocoMarque.get((ChocolatDeMarque)produit)-livre);

					double currStockChoco = stockChoco.get(((ChocolatDeMarque)produit).getChocolat());
					stockChoco.put(((ChocolatDeMarque) produit).getChocolat(), currStockChoco-livre);
				}
				this.journalStock.ajouter("Retrait de " + livre + "T " + contrat.getProduit() + "(CC avec "+ contrat.getAcheteur() + ")");
			
				return livre;
			}
			else{
				double livre = Math.min(stockChoco.get((Chocolat) produit), quantite);
				if (livre>0.0) {

					totalStocksChoco.retirer(this,  livre, cryptogramme);
					totalStocksChocoNonMarquee.retirer(this, livre, this.cryptogramme);
					double currStockChoco = stockChoco.get(produit);
					stockChoco.put((Chocolat) produit, currStockChoco-livre);
				}
				this.journalStock.ajouter("Retrait de " + livre + "T " + contrat.getProduit() + "(CC avec "+ contrat.getAcheteur() + ")");
			
				return livre;
			}
			
	}








	
	public boolean peutVendre(IProduit produit) {
		for (IProduit produi : stockChoco.keySet()){
			if (produi.equals(produit)){
				//On vérifie que le stock n'est pas vide et que la quantité que l'on souhaite vendre est légale
				return stockChoco.get(produi)>0. && stockChoco.get(produi) * partInitialementVoulue > 100.;
			}
		}
		return false;

	}






	public String toString() {
		return this.getNom();
	}

}
