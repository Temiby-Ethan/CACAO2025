package abstraction.eq4Transformateur1.contratCadre;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.contratsCadres.*;
import abstraction.eqXRomu.produits.Feve;

import java.util.List;
import java.util.LinkedList;

public class Transformateur1ContratCadreVendeurAcheteur extends Transformateur1ContratCadreVendeur implements IAcheteurContratCadre {
    
    
    protected double qttInitialementVoulue;	
	protected double prixInitialementVoulu;
	protected double epsilon;


	public Transformateur1ContratCadreVendeurAcheteur() {
		super();
		this.mesContratEnTantQuAcheteur=new LinkedList<ExemplaireContratCadre>();
        this.epsilon  = 0.1;

        this.qttInitialementVoulue = 0.5*STOCK_MAX_TOTAL_FEVES;//On cherche à acheter de quoi remplir ou vendre notre stock à hauteur de 50%

        this.prixInitialementVoulu = 0.75*9500; //Une valeur arbitraire s'appuyant sur le prix moyen des fèves de cacao en 2024
	}

	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {

        //Si la qtt proposée est cohérente avec la quantité que nous voulions initialement, on accepte l'echeancier
		if (contrat.getEcheancier().getQuantiteTotale()>SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER){
			if (Math.abs((this.qttInitialementVoulue - contrat.getEcheancier().getQuantiteTotale())/this.qttInitialementVoulue) <= epsilon){

				return contrat.getEcheancier();
			}
			//Sinon on négocie par dichotomie particulière
			else{

				double qttContrat = contrat.getEcheancier().getQuantiteTotale();
				double qttVoulue = 0.25*qttContrat + 0.75 * this.qttInitialementVoulue;

				//Si on calcule une quantité voulue inférieure à celle du contrat, on accepte la dernière offre
				if (qttVoulue<SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER) return contrat.getEcheancier();

				//Mise à jour de l'échéancier pour prendre en compte ces modifications
				Echeancier e = contrat.getEcheancier();

				//Redistribution uniforme de la hausse ou de la baisse des qtt vendues 
				for(int i = e.getStepDebut() ; i< e.getStepFin() ; i++){
					double qtti = e.getQuantite(i)*(qttVoulue/qttContrat);

					//Si la quantité par step ne respecte pas les exigeances des règles du contrat, on met la part minimale
					//Mais si cette condition est vérifiée, il risque d'y avoir une augmentation de la qtt totale et donc que le problème se reporte sur d'autres step
					if (qtti<qttVoulue/(10*e.getNbEcheances())){

						e.set(i, qttVoulue/(10*e.getNbEcheances()));
					}
				}
				if (e.getQuantiteTotale()<SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER) return contrat.getEcheancier();
				else return e;

			}
		}
		else {
			return null;
		}
	}
	

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		//Si le prix est aberrant, on refuse d'office la négociation
        if (contrat.getPrix() > 20000){
			return -1;
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
				this.prixInitialementVoulu = this.prixInitialementVoulu + (contrat.getPrix()-prixInitialementVoulu)*0.2;
				return this.prixInitialementVoulu;
			}
		}
	}

	public void initialiser(){
		super.initialiser();
	}

	public void next() {
		super.next();

		// On enleve les contrats obsolete (nous pourrions vouloir les conserver pour "archive"...)
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQuAcheteur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQuAcheteur.removeAll(contratsObsoletes);

		

		// OU proposition d'un contrat a un des vendeurs choisi aleatoirement
		for(IProduit produit : pourcentageTransfo.keySet()){
			if(qttEntrantesFeve.get((Feve)produit)< 0.1*STOCK_MAX_TOTAL_FEVES){
				journalCC.ajouter("Recherche d'un vendeur aupres de qui acheter " + produit);

				List<IVendeurContratCadre> vendeurs = supCCadre.getVendeurs(produit);
				if (vendeurs.contains(this)) {
					vendeurs.remove(this);
				}
				IVendeurContratCadre vendeur = null;
				if (vendeurs.size()==1) {
					vendeur=vendeurs.get(0);
				} else if (vendeurs.size()>1) {
					//A MODIFIER
					//Recherche aléatoire d'un vendeur
					vendeur = vendeurs.get((int)( Filiere.random.nextDouble()*vendeurs.size()));
				}
				if (vendeur!=null) {
					journalCC.ajouter("Demande au superviseur de debuter les negociations pour un contrat cadre de "+produit+" avec le vendeur "+vendeur);
					ExemplaireContratCadre cc = supCCadre.demandeAcheteur((IAcheteurContratCadre)this, vendeur, produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, (SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER+10.0)/10), cryptogramme,false);
					journalCC.ajouter("-->aboutit au contrat "+cc);
				}
			}
		}
		// Proposition d'un contrat a un des achteur choisi aleatoirement
		for(IProduit produit : stockChoco.keySet()){
			journalCC.ajouter("Recherche d'un acheteur aupres de qui vendre " + produit);

			List<IAcheteurContratCadre> acheteurs = supCCadre.getAcheteurs(produit);
			if (acheteurs.contains(this)) {
				acheteurs.remove(this);
			}
			for(IAcheteurContratCadre acheteur : acheteurs){
				if (acheteur!=null) {
					journalCC.ajouter("Demande au superviseur de debuter les negociations pour un contrat cadre de "+produit+" avec l'acheteur "+acheteur);
					ExemplaireContratCadre cc = supCCadre.demandeVendeur(acheteur, (IVendeurContratCadre)this, produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, (SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER+10.0)/10), cryptogramme,false);
					journalCC.ajouter("-->aboutit au contrat "+cc);
				}
			}
		}
	}

	public void receptionner(IProduit produit, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
		totalStocksFeves.ajouter(this, quantiteEnTonnes, cryptogramme); 
        double currStockFeves = stockFeves.get((Feve) produit);
        stockFeves.put((Feve) produit, currStockFeves+quantiteEnTonnes);
        journalStock.ajouter("Reception de " + quantiteEnTonnes +"feves " + ((Feve)produit).getGamme() + "(CC avec" + contrat.getVendeur() + ")");
	}

	public boolean achete(IProduit produit) {
		//On n'achète que les fèves nous permettant de produire les chocolats que l'on veut produire
		return pourcentageTransfo.keySet().contains(produit);
	}

	public String toString() {
		return this.getNom();
	}

	public int fixerPourcentageRSE(IAcheteurContratCadre acheteur, IVendeurContratCadre vendeur, IProduit produit,
			Echeancier echeancier, long cryptogramme, boolean tg) {
		return 5;
	}


}
