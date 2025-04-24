package abstraction.eq4Transformateur1.contratCadre;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;


import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.contratsCadres.*; 
import abstraction.eqXRomu.produits.Gamme;


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

		//A MODIFIER 
		//On cherche à vendre une partie de la quantité de chocolat correspondant à la qtt de fèves entrantes
		double qttVoulue = 0.;

		Chocolat chocoVendu = ((ChocolatDeMarque)contrat.getProduit()).getChocolat();

		if(chocoVendu.getGamme().equals(Gamme.BQ) && chocoVendu.isEquitable())
				qttVoulue = 0.3 * qttEntrantesFeve.get(Feve.F_BQ_E) * contrat.getEcheancier().getNbEcheances();
			
		else if(chocoVendu.getGamme().equals(Gamme.MQ) && !chocoVendu.isEquitable())
				qttVoulue = 0.3 * qttEntrantesFeve.get(Feve.F_MQ) * contrat.getEcheancier().getNbEcheances();
		
		else if(chocoVendu.getGamme().equals(Gamme.MQ) && chocoVendu.isEquitable()) 
				qttVoulue = 0.3 * qttEntrantesFeve.get(Feve.F_MQ_E) * contrat.getEcheancier().getNbEcheances();
		
		else if(chocoVendu.getGamme().equals(Gamme.HQ) && chocoVendu.isEquitable() && chocoVendu.isBio())
				qttVoulue = 0.3 * qttEntrantesFeve.get(Feve.F_HQ_BE) * contrat.getEcheancier().getNbEcheances();

		else
				System.out.println("Ce chocolat n'est pas sensé être vendu : " + contrat.getProduit());
		

		double qttEntrant = 0.;
		//A MODIFIER	
		//On vérifie que l'échéancier renvoyé respecte les règles et que la quantité en stock de produit est au moins le quart de la quantité totale
		//Il faudrait dans l'idéal modifier cette condition pour prendre en compte la quantité de chocolat sortante et la quantité produite par step
		if (qttVoulue>= SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER && getQuantiteEnStock(contrat.getProduit(), this.cryptogramme) >= contrat.getEcheancier().getQuantiteTotale()/contrat.getEcheancier().getNbEcheances()){
			IProduit produit;
			
			//On vend des chocolat de marque
			if (contrat.getProduit().getType() == "ChocolatDeMarque"){
				produit = ((ChocolatDeMarque)contrat.getProduit());

				if (!this.peutVendre(produit)) return null; //On ne vend pas de ce produit

				Echeancier e = contrat.getEcheancier(); //Récupération de l'échéancier actuel


				//Cas d'acceptation : la quantité totale est légale et proche de la quantité que l'on souhaite vendre à 30% près
				if(e.getNbEcheances()> 20 && e.getQuantiteTotale()> 100. && Math.abs(e.getQuantiteTotale()-qttVoulue)/qttVoulue < 0.3){
					return e;
				}


				//On modifie l'échéancier pour se rapporcher de nos exigeances
				else{
					
					for(int s = e.getStepDebut() ; s<=e.getStepFin() ; s++){

						ChocolatDeMarque prod = (ChocolatDeMarque)contrat.getProduit();

						//Détermination de la quantité entrante de chocolat que l'on ne va pas vendre au step s
						if(chocoVendu.getGamme().equals(Gamme.BQ) && chocoVendu.isEquitable()) 
								qttEntrant = determinerQttEntrantFevesAuStep(s, Feve.F_BQ_E) * this.pourcentageTransfo.get(Feve.F_BQ_E).get(Chocolat.C_BQ_E) - determinerQttSortantChocoAuStep(s, prod.getChocolat());
						else if(chocoVendu.getGamme().equals(Gamme.MQ) && chocoVendu.isEquitable()) 
								qttEntrant = determinerQttEntrantFevesAuStep(s, Feve.F_MQ_E) * this.pourcentageTransfo.get(Feve.F_MQ_E).get(Chocolat.C_MQ_E) - determinerQttSortantChocoAuStep(s, prod.getChocolat());
						else if(chocoVendu.getGamme().equals(Gamme.MQ) && !chocoVendu.isEquitable())
								qttEntrant = determinerQttEntrantFevesAuStep(s, Feve.F_MQ) * this.pourcentageTransfo.get(Feve.F_MQ).get(Chocolat.C_MQ) - determinerQttSortantChocoAuStep(s, prod.getChocolat());
						else if(chocoVendu.getGamme().equals(Gamme.HQ) && chocoVendu.isEquitable() && chocoVendu.isBio())
								qttEntrant = determinerQttEntrantFevesAuStep(s, Feve.F_HQ_BE) * this.pourcentageTransfo.get(Feve.F_HQ_BE).get(Chocolat.C_HQ_BE) - determinerQttSortantChocoAuStep(s, prod.getChocolat());
						else
								System.out.println("Ce chocolat n'est pas censé être vendu : " + prod);
							
						
						//Selon la valeur de qttEntrant, on va agir différemment sur le contrat		
						//si qtt entrant est très négatif, c'est que l'on vend plus de chocolat que l'on ne recoit de fèves, on annule donc le contrat
						if (qttEntrant < -10000.){
							return null;
						}
						//Sinon, si on a suffisamment de fève qui entrent en stock à chaque step et que la proposition est proche de ce que l'on souhaite, on met la qtt entrante à condition qu'elle soit positive
						else if (Math.abs(qttEntrant - e.getQuantite(s))/qttEntrant < 0.2 && qttEntrant>0. ){
							e.set(s, 0.3*qttEntrant*0.75 + 0.25*e.getQuantite(s));
						}
						//si la qtt entrante est faible, on vérifie quand meme que celle ci respecte les spécifications sur les CC
						else if (Math.abs(qttEntrant)< 10000. && 0.3*qttEntrant > e.getQuantiteTotale()/(10*e.getNbEcheances())){
							e.set(s,0.3*qttEntrant);
						}
						//Sinon, on met le double du minimum pour le step
						else {
							e.set(s, e.getQuantiteTotale()/(5*e.getNbEcheances()));
						}
						
					}


					/*Vérification de la conformité de l'échéancier  */


					//Recherche du step correspondant au maximum de l'échancier
					double qttMax = 0.;
					double qttMin = e.getQuantite(e.getStepDebut()+1);
					for (int s =e.getStepDebut() ; s<= e.getStepFin() ; s++){
						if (e.getQuantite(s) >= qttMax){
							qttMax = e.getQuantite(s);
						}
						if (e.getQuantite(s) <= qttMin){
							qttMin = e.getQuantite(s);
						}
					}

					//On vérifie que notre contrat respecte bien les règles des contrats cadres par rapport aux quantité minimale par step
					//Si l'une des échéances est trop faible, on modifie tout l'échéancier
					boolean modifNecessaires = false;
					for (int s = e.getStepDebut() ; s <= e.getStepFin() ; s++){
						if (e.getQuantite(s)< e.getQuantiteTotale()/(10*e.getNbEcheances())) modifNecessaires = true;
					}

					if (modifNecessaires){
						for (int s = e.getStepDebut() ; s <= e.getStepFin() ; s++){
							e.set(s, (qttMin + qttMax)/2);
						}
					}


					//Si la quantité totale est trop faible, on va se mettre au minimum sur tout le contrat 
					if (e.getQuantiteTotale() < 100.){
						for (int s = e.getStepDebut() ; s <= e.getStepFin() ; s++){
							e.set(s, 110./e.getNbEcheances());
						}
					}

					

					/*Renvoie de l'échéancier modifié */
					qttMin = e.getQuantite(e.getStepDebut()+1);
					for (int s =e.getStepDebut() ; s<= e.getStepFin() ; s++){
						if (e.getQuantite(s) >= qttMax){
							qttMax = e.getQuantite(s);
						}
						if (e.getQuantite(s) <= qttMin){
							qttMin = e.getQuantite(s);
						}
					}
					return e;
				}

				
			}

			else return null; //On n'a pas implémenté le cas ou le chocolat n'est pas marqué


			//Vente d'un chocolat non marqué
			/*else {
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
			}*/
		}
		else return null ; //On annule les négociations si le nouveau contrat a une quantité illégale
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

		//Si le produit vendu est un chocolat MQ, on négocie de manière à avoir de très grandes marges
		if (produit.equals(Chocolat.C_MQ) || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat().equals(Chocolat.C_MQ))){
			//Si le prix proposé est plus élevé que celui que l'on a calculé, on accepte le contrat
			if (contrat.getPrix() > prixTChocoBase.get(Chocolat.C_MQ) * marges.get(Chocolat.C_MQ)){
				return contrat.getPrix();
			}
			//Si le prix est trop faible, on reste sur le prix minimum auquel on veut vendre
			if (contrat.getPrix()< prixTChocoBase.get(Chocolat.C_MQ)*marges.get(Chocolat.C_MQ)*0.75){
				return prixTChocoBase.get(Chocolat.C_MQ)*marges.get(Chocolat.C_MQ)*0.75;
			}
			//Si le prix du contrat est à un epsilon près de notre prix, on accepte
			double notrePrix = prixTChocoBase.get(Chocolat.C_MQ)*marges.get(Chocolat.C_MQ);
			double diffRelative = Math.abs(contrat.getPrix()- notrePrix)/notrePrix;
			if (diffRelative<epsilon){
				return contrat.getPrix();
			}
			//Sinon on cherche à négocier le prix, vers le bas par dichotomie en notre faveur à 90%
			else{
				double nouveauPrix = contrat.getPrix() * 0.1 + notrePrix * 0.9;
				if (nouveauPrix < prixTChocoBase.get(Chocolat.C_MQ)*marges.get(Chocolat.C_MQ)*0.75){
					return prixTChocoBase.get(Chocolat.C_MQ)*marges.get(Chocolat.C_MQ)*0.75;
				}
				else{
					notrePrix = nouveauPrix;
					return notrePrix;
				}
			}
		}

		//Si le produit vendu est BQ_E, on cherche a des marges réduites ce qui réduit le champ des négociations
		if (produit.equals(Chocolat.C_BQ_E) || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat().equals(Chocolat.C_BQ_E))){
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
		if (produit.equals(Chocolat.C_MQ_E) || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat().equals(Chocolat.C_MQ_E))){
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
		if (produit.equals(Chocolat.C_HQ_BE) || (produit.getType()=="ChocolatDeMarque" && ((ChocolatDeMarque)produit).getChocolat().equals(Chocolat.C_HQ_BE))){
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

			this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Achat: Nouveau contrat cadre obtenu en tant qu'acheteur :");
		    this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Vendeur : " + contrat.getVendeur());
		    this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Produit :  " + contrat.getProduit());
		    this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Echeancier : " + contrat.getEcheancier());
			this.journalCC.ajouter("\n");
		}
		else{
			this.mesContratEnTantQueVendeur.add(contrat);

			this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Vente: Nouveau contrat cadre obtenu en tant que vendeur :");
		    this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Acheteur : " + contrat.getAcheteur());
		    this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN,"Produit :  " + contrat.getProduit());
		    this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Echeancier : " + contrat.getEcheancier());
			this.journalCC.ajouter("\n");
		}
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
			return lesChocolats.contains(produit);
		}
		else if (produit.getType() == "ChocolatDeMarque"){
			return ((ChocolatDeMarque)produit).getMarque() == "LimDt" && chocolatsLimDt.contains(produit);
		}
		else{
			return false;
		}
	}










	public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
		

			if (produit.getType() == "ChocolatDeMarque"){
				double livre = Math.min(Math.max(getQuantiteEnStock(produit, this.cryptogramme), 0.), quantite);
				if (livre > 0.){

					//Retrait du produit concerné par le contrat
					this.retirerDuStock(produit, quantite, this.cryptogramme);


				}
				this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_PURPLE, "Vente CC LimDt :");
				this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_PURPLE, "Retrait de " + livre + "T " + contrat.getProduit() + "(CC avec "+ contrat.getAcheteur() + ")");
				this.journalStock.ajouter("\n");
			
				return livre;
			}
			else{
				double livre = Math.min(getQuantiteEnStock(produit, this.cryptogramme), quantite);
				if (livre>0.0) {
					//AFFICHAGE EN CONSOLE
					journalStock.ajouter(Color.pink, Romu.COLOR_PURPLE, "Le chocolat " + produit + " n'est pas censé sortir du stock, il est de type " + produit.getType());
					
					this.retirerDuStock(produit, quantite, this.cryptogramme);

				}
				this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Vente CC :");
				this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Retrait de " + livre + "T " + contrat.getProduit() + "(CC avec "+ contrat.getAcheteur() + ")");
				this.journalStock.ajouter("\n");
			
				return livre;
			}
			
	}








	
	public boolean peutVendre(IProduit produit) {
		//On vérifie que 30% de notre stock est supérieur à 100T
		return this.getQuantiteEnStock(produit, this.cryptogramme) * partInitialementVoulue > 100.;

	}






	public String toString() {
		return this.getNom();
	}

}
