package abstraction.eq4Transformateur1.contratCadre;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.contratsCadres.*;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve; 
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Gamme;

import java.util.List;
import java.awt.Color;
import java.util.LinkedList;

/**
 * @author MURY Julien
 */
public class Transformateur1ContratCadreVendeurAcheteur extends Transformateur1ContratCadreVendeur implements IAcheteurContratCadre {
    
    
    protected double qttInitialementVoulue;	
	protected double prixInitialementVoulu;
	protected double epsilon;


	public Transformateur1ContratCadreVendeurAcheteur() {
		super();
		this.mesContratEnTantQuAcheteur=new LinkedList<ExemplaireContratCadre>();
        this.epsilon  = 0.1;

        this.qttInitialementVoulue = STOCK_MAX_TOTAL_FEVES;//On cherche à acheter de quoi remplir ou vendre notre stock à hauteur de 50%

        this.prixInitialementVoulu = 2000.;

	}




	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {

        
		if (contrat.getEcheancier().getQuantiteTotale()>SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER){

			//Si la qtt proposée est cohérente avec la quantité que nous voulions initialement, on accepte l'echeancier
			if (Math.abs((this.qttInitialementVoulue - contrat.getEcheancier().getQuantiteTotale())/this.qttInitialementVoulue) <= epsilon){

				return contrat.getEcheancier();
			}
			//Sinon on négocie
			else{

				//Si Le nombre de contrat cadre est suffisant, on négocie la quantité des autres contrats via la quantité entrante et sortante à chaque step
				if(this.mesContratEnTantQuAcheteur.size() + this.mesContratEnTantQueVendeur.size() >=8){
					double qttSortant = 0.;
					Echeancier e = contrat.getEcheancier();
					for (int step = contrat.getEcheancier().getStepDebut() ; step <= contrat.getEcheancier().getStepFin() ; step++){

						Feve prod = (Feve)contrat.getProduit();

						//Calcul du manque de la quantité de fève nécessaire pour chacun des steps
						if(prod.getGamme().equals(Gamme.MQ)){
								if (prod.isEquitable()) qttSortant = determinerQttSortantChocoAuStep(step, Chocolat.C_MQ_E) +péremption_C_MQ_E_Limdt[11] + péremption_C_MQ_E_Limdt[10] - determinerQttEntrantFevesAuStep(step, prod);
								else qttSortant = determinerQttSortantChocoAuStep(step, Chocolat.C_MQ) + péremption_C_MQ_Limdt[11] + péremption_C_MQ_Limdt[10] - determinerQttEntrantFevesAuStep(step, prod);
						}
						else if (prod.getGamme().equals(Gamme.BQ)){
								qttSortant = determinerQttSortantChocoAuStep(step, Chocolat.C_BQ_E) + péremption_C_BQ_E_Limdt[11] + péremption_C_BQ_E_Limdt[10] - determinerQttEntrantFevesAuStep(step, prod);
						}
						else if (prod.getGamme().equals(Gamme.HQ)){
								qttSortant = determinerQttSortantChocoAuStep(step, Chocolat.C_HQ_BE) + péremption_C_HQ_BE_Limdt[11] + péremption_C_HQ_BE_Limdt[10] - determinerQttEntrantFevesAuStep(step, prod);
						}



						/* Mise à jour de l'échéancier selon nos exigeances */
						
						//si qtt sortante est très négatif, c'est que l'on achète plus de fève que l'on ne vent de chocolat, on annule donc le contrat
						if (qttSortant < -10000.){
							return null;
						}
						//Sinon, si on vend suffisamment de chocolat à chaque step et que la proposition est proche de ce que l'on souhaite, on met la qtt sortante à condition qu'elle soit positive
						//Mais il nous faut malgré tout des fèves et on va pour cela ajouter une certaine quantité de fève de secours au cas où on signerait un contrat en tant que vendeur 
						else if (Math.abs(1.5*qttSortant - e.getQuantite(step))/(1.5*qttSortant) < 0.1 && qttSortant>0. ){
							e.set(step, 1.5*qttSortant*0.95 + 0.05*e.getQuantite(step));
						}
						//si la qtt entrante est faible, on vérifie quand meme que celle ci respecte les spécifications sur les CC
						else if (Math.abs(1.5*qttSortant)< 10000. && 1.5*qttSortant > e.getQuantiteTotale()/(10*e.getNbEcheances())){
							e.set(step, 1.5*qttSortant);
						}
						//Sinon, on met le double du minimum pour le step
						else {
							e.set(step, e.getQuantiteTotale()/(5*e.getNbEcheances()));
						}

					}


					/*Vérification de la conformité de l'échéancier  */


					//Recherche du maximum de l'échancier
					double qttMax = 0.;
					double qttMin = e.getQuantite(e.getStepDebut()+1);
					for (int s =e.getStepDebut() ; s< e.getStepFin() ; s++){
						if (e.getQuantite(s) > qttMax){
							qttMax = e.getQuantite(s);
						}
						if (e.getQuantite(s) < qttMin){
							qttMin = e.getQuantite(s);
						}
					}

					//Si un des steps est inférieur à la quantité minimale, on met tous les steps à la quantité moyenne
					for (int s = e.getStepDebut() ; s <= e.getStepFin() ; s++){
						e.set(s, (qttMax + qttMin)/2);
					}

					//Si la quantité totale est trop faible, on va se mettre au minimum sur tout le contrat 
					if (e.getQuantiteTotale() < 100.){
						for (int s = e.getStepDebut() ; s <= e.getStepFin() ; s++){
							e.set(s, 1200./e.getNbEcheances());
						}
					}


					/*Vérification de l'échéancier renvoyé */
					qttMin = e.getQuantite(e.getStepDebut()+1);
					for (int s =e.getStepDebut() ; s<= e.getStepFin() ; s++){
						if (e.getQuantite(s) >= qttMax){
							qttMax = e.getQuantite(s);
						}
						if (e.getQuantite(s) <= qttMin){

							qttMin = e.getQuantite(s);
						}
					}
					
					/*Renvoie de l'échéancier modifié */

					this.qttInitialementVoulue = e.getQuantiteTotale();
					return e;

				}


				//Si on n'a pas suffisamment de contrats actifs, on accepte le contrat proposé
				else {
					return contrat.getEcheancier();
				}

			}
		}
		else {
			return null;
		}
	}
	
	//A MODIFIER 
	/*Il faudrait s'appuyer sur le cours de la bourse pour négocier les prix avec les producteurs */
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {

		//On détermine des tolérances par rapport au cours de la bourse pour chacune des gammes de fèves
		double tolerance = 1.;
		Feve prod = ((Feve)contrat.getProduit());
		if(prod.getGamme().equals(Gamme.BQ)) tolerance = 1.5;
		else if (prod.getGamme().equals(Gamme.MQ)) tolerance = 2.;
		else if (prod.getGamme().equals(Gamme.HQ)) tolerance = 3.; 
		

		// Récupération du cours de la bourse du cacao de basse qualité
		BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
		double cours = bourse.getCours(Feve.F_BQ).getValeur();

		//Si le prix est aberrant, on refuse d'office la négociation
        if (contrat.getPrix() > tolerance * cours){
			return tolerance*cours;
		}
		else{
			//On procède par dichotomie sur le prix proposé et notre prix voulu.
			//Si le prix proposé est inférieur à notre prix, on accepte le contrat
			if(contrat.getPrix() < this.prixInitialementVoulu){
				return contrat.getPrix(); 
			}

			//Sinon on vérifie si le prix est cohérent avec le notre d'un seuil epsilon
			if (Math.abs((contrat.getPrix()-prixInitialementVoulu)/prixInitialementVoulu) <= this.epsilon ){
				return contrat.getPrix();
			}
			//Sinon on contre-porpose un prix intermédiaire par rapport au prix proposé
			else{
				if (contrat.getPrix() <= 2* tolerance*cours) return (tolerance*cours + contrat.getPrix()) / 2;
				return -1;
			}
		}
	}

	public void initialiser(){
		super.initialiser();

		//Initialisation du prix initialement voulu au cours actuel du cacao de basse qualité
		this.prixInitialementVoulu = ((BourseCacao)Filiere.LA_FILIERE.getActeur("BourseCacao")).getCours(Feve.F_BQ).getValeur(); //On s'appuie sur le cours actuel de la fève F_BQ pour déterminer le prix à négocier
	
	}









	public void next() {
		super.next();

		//On actualise le prix que l'on veut 
		this.prixInitialementVoulu = ((BourseCacao)Filiere.LA_FILIERE.getActeur("BourseCacao")).getCours(Feve.F_BQ).getValeur(); //On s'appuie sur le cours actuel de la fève F_BQ pour déterminer le prix à négocier
	

		// On enleve les contrats obsolete (nous pourrions vouloir les conserver pour "archive"...)
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQuAcheteur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQuAcheteur.removeAll(contratsObsoletes);

		

		//On essaie pour chacune des fèves dont on a besoin de négocier un contrat cadre avec tout les vendeurs de cette fève
		for(IProduit produit : this.lesFeves){
			if(qttEntrantesFeve.get((Feve)produit)< 0.1*STOCK_MAX_TOTAL_FEVES){
				journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Recherche d'un vendeur aupres de qui acheter " + produit);

				List<IVendeurContratCadre> vendeurs = supCCadre.getVendeurs(produit);
				if (vendeurs.contains(this)) {
					vendeurs.remove(this);
				}

                if (vendeurs.size()==0) {
					journalCC.ajouter(Color.pink, Romu.COLOR_BROWN, "-->Pas de vendeur potentiel");
					journalCC.ajouter("\n");
				} else {
				    journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Voici les vendeurs potentiels : " + vendeurs);
				    journalCC.ajouter("\n");
				}

				for (IVendeurContratCadre vendeur : vendeurs){
					if (vendeur!=null) {
						journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Demande au superviseur de debuter les negociations pour un contrat cadre de "+produit+" avec le vendeur "+vendeur);
						this.qttInitialementVoulue = STOCK_MAX_TOTAL_FEVES;
						ExemplaireContratCadre cc = supCCadre.demandeAcheteur((IAcheteurContratCadre)this, vendeur, produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 30, STOCK_MAX_TOTAL_FEVES/30), cryptogramme,false);
						if (cc!=null) {
							journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "-->aboutit au contrat "+cc);
							journalCC.ajouter("\n");
							this.mesContratEnTantQuAcheteur.add(cc);
						}
						else {
							journalCC.ajouter(Color.pink, Romu.COLOR_BROWN, "-->Le contrat n'a pas pu aboutir");
							journalCC.ajouter("\n");
						}
					}
				}
			}
		}





		
		// Recherche d'acheteurs de chocolat de marque
		for(ChocolatDeMarque produit : chocolatsLimDt){

			Feve feveAssociee = Feve.F_BQ_E;

			switch(produit.getChocolat()){
				case C_MQ : 
					feveAssociee = Feve.F_MQ;
					break;
				
				case C_MQ_E : 
					feveAssociee = Feve.F_MQ_E;
					break;

				case C_BQ_E : 
					feveAssociee = Feve.F_BQ_E;
					break;

				case C_HQ_BE : 
					feveAssociee = Feve.F_HQ_BE;
					break;

				default : 
					System.out.println("Ce chocolat ne devrait pas faire partie de la gamme : " + produit);
					break;
			}

			//On ne cherche des contrats cadres que si l'on a un minimum de chocolat en stock et d'approvisionnement
			if (this.getQuantiteEnStock(produit, this.cryptogramme) > 1000. && determinerQttEntrantFevesAuStep(this.cryptogramme, feveAssociee) > 100.){
				journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_PURPLE, "Recherche d'un acheteur aupres de qui vendre LimDt " + produit);

				List<IAcheteurContratCadre> acheteurs = supCCadre.getAcheteurs(produit);
				if (acheteurs.contains(this)) {
					acheteurs.remove(this);
				}

				if (acheteurs.size()==0) {
					journalCC.ajouter(Color.pink, Romu.COLOR_PURPLE, "-->Pas d'acheteur potentiel");
					journalCC.ajouter("\n");
				} else {
					journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_PURPLE, "Voici les acheteurs potentiels pour LimDt : " + acheteurs);
					journalCC.ajouter("\n");
				}

				for(IAcheteurContratCadre acheteur : acheteurs){
					if (acheteur!=null) {
						journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_PURPLE, "Demande au superviseur de debuter les negociations pour un contrat cadre de "+produit+" avec l'acheteur "+acheteur);

						if (0.15*this.getQuantiteEnStock(produit, this.cryptogramme) > 100.){

							ExemplaireContratCadre cc = supCCadre.demandeVendeur(acheteur, (IVendeurContratCadre)this, produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 25, 0.15*this.getQuantiteEnStock(produit, this.cryptogramme)), cryptogramme, false);
							
							if (cc!=null) {
								journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_PURPLE, "-->aboutit au contrat "+cc);
								journalCC.ajouter("\n");
								this.mesContratEnTantQueVendeur.add(cc);
							}
							
							else {
								journalCC.ajouter(Color.pink, Romu.COLOR_PURPLE, "-->Le contrat n'a pas pu aboutir");
								journalCC.ajouter("\n");
							}
						}
						
					}
				}
			}
		}

		//Recherche d'acheteurs de chocolat non marqué
		for(Chocolat produit : lesChocolats){
			journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Recherche d'un acheteur aupres de qui vendre " + produit);

			List<IAcheteurContratCadre> acheteurs = supCCadre.getAcheteurs(produit);
			if (acheteurs.contains(this)) {
				acheteurs.remove(this);
			}

			if (acheteurs.size()==0) {
				journalCC.ajouter(Color.pink, Romu.COLOR_GREEN, "-->Pas d'acheteur potentiel");
				journalCC.ajouter("\n");
			} else {
			       journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Voici les acheteurs potentiels : " + acheteurs);
			       journalCC.ajouter("\n");
			}


			for(IAcheteurContratCadre acheteur : acheteurs){
				if (acheteur!=null) {
					journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "Demande au superviseur de debuter les negociations pour un contrat cadre de "+produit+" avec l'acheteur "+acheteur);
					ExemplaireContratCadre cc = supCCadre.demandeVendeur(acheteur, (IVendeurContratCadre)this, produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, (SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER+10.0)/10), cryptogramme,false);
					if (cc!=null) {
					    journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_GREEN, "-->aboutit au contrat "+cc);
					    journalCC.ajouter("\n");
					}
					else {
					    journalCC.ajouter(Color.pink, Romu.COLOR_GREEN, "-->Le contrat n'a pas pu aboutir");
					    journalCC.ajouter("\n");
					}
				}
			}
		}



		//Affichage dans les journaux des contrats cadres actifs
		journalCC.ajouter("Voici nos contrats actifs en tant qu'acheteur : ");
		journalCC.ajouter("");
		for (ExemplaireContratCadre cc : mesContratEnTantQuAcheteur){
			journalCC.ajouter("Acheteur : " + cc.getAcheteur() );
			journalCC.ajouter("Vendeur : " + cc.getVendeur() );
			journalCC.ajouter("Produit : " + cc.getProduit() );
			journalCC.ajouter("Prix : " + cc.getPrix() + "\n");
			journalCC.ajouter("Echeancier : " + cc.getEcheancier() );
			journalCC.ajouter("");
		}

		//Affichage des contrats vendeurs 
		journalCC.ajouter("Voici nos contrats actifs en tant que vendeur : ");
		journalCC.ajouter("");
		for (ExemplaireContratCadre cc : this.mesContratEnTantQueVendeur){
			journalCC.ajouter("Acheteur : " + cc.getAcheteur() );
			journalCC.ajouter("Vendeur : " + cc.getVendeur() );
			journalCC.ajouter("Produit : " + cc.getProduit() );
			journalCC.ajouter("Prix : " + cc.getPrix() );
			journalCC.ajouter("Echeancier : " + cc.getEcheancier() );
			journalCC.ajouter("");

		}
	}






	public void receptionner(IProduit produit, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
		ajouterAuStock(produit, quantiteEnTonnes, this.cryptogramme);
		
        journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Achat CC :");
		journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN, "Reception de " + quantiteEnTonnes +"feves " + ((Feve)produit).getGamme() + "(CC avec" + contrat.getVendeur() + ")");
		journalStock.ajouter("\n");
	}







	public boolean achete(IProduit produit) {
		//On n'achète que les fèves nous permettant de produire les chocolats que l'on veut produire
		return lesFeves.contains(produit);
	}




	public String toString() {
		return this.getNom();
	}




	public int fixerPourcentageRSE(IAcheteurContratCadre acheteur, IVendeurContratCadre vendeur, IProduit produit,
			Echeancier echeancier, long cryptogramme, boolean tg) {
		return 5;
	}


}
