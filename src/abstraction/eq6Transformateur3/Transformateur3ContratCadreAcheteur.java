package abstraction.eq6Transformateur3;

import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.filiere.*;
import abstraction.eqXRomu.produits.IProduit;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre; 
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eqXRomu.produits.Feve;

// @author Eric SCHILTZ & Henri Roth

public class Transformateur3ContratCadreAcheteur extends Transformateur3ContratCadreVendeur implements IAcheteurContratCadre{
	protected List<ExemplaireContratCadre> ContratsAcheteur;
	protected List<ExemplaireContratCadre> contratsObsoletes;

	public Transformateur3ContratCadreAcheteur() {
		this.ContratsAcheteur=new LinkedList<ExemplaireContratCadre>();
		this.contratsObsoletes =new LinkedList<ExemplaireContratCadre>();
	}
	//@author Henri Roth
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		return contrat.getEcheancier(); // on ne cherche pas a negocier sur le previsionnel de livraison
	}
	//@author Henri Roth
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		return contrat.getPrix()*1.2;
	}
	// @author Eric Schiltz
	public void next() {
		super.next();
		SuperviseurVentesContratCadre supCCadre = (SuperviseurVentesContratCadre) Filiere.LA_FILIERE.getActeur("Sup.CCadre");

		journalCC.ajouter("======= Contrats cadres finis période"+Filiere.LA_FILIERE.getEtape()+"=======");
		// On enleve les contrats obsolete (nous pourrions vouloir les conserver pour "archive"...)
		for (ExemplaireContratCadre contrat : this.ContratsAcheteur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.ContratsAcheteur.removeAll(contratsObsoletes);
		for (ExemplaireContratCadre contrat : this.ContratsVendeur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.ContratsVendeur.removeAll(contratsObsoletes);
		journalCC.ajouter("======================");
		
		// Proposition d'un nouveau contrat a tous les vendeurs possibles
		IProduit produit = Feve.F_BQ;
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur!=this && acteur instanceof IVendeurContratCadre && ((IVendeurContratCadre)acteur).vend(produit)) {
				supCCadre.demandeAcheteur((IAcheteurContratCadre)this, ((IVendeurContratCadre)acteur), produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 100.0), cryptogramme, false);
			}
		}
	}
	//@author Henri Roth
	public void receptionner(IProduit produit, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
		journalCC.ajouter("Reception de "+quantiteEnTonnes+" de T de " + produit + " en provenance du contrat "+contrat.getNumero());
		super.stockFeves.addToStock(produit, quantiteEnTonnes);
	}
	//@author Henri Roth
	public boolean achete(IProduit produit) {
		if(produit == Feve.F_BQ
		|| produit == Feve.F_BQ_E
		|| produit == Feve.F_MQ
		|| produit == Feve.F_HQ_E){
			if(stockFeves.getQuantityOf(produit)<500){
				return true;
			}
		}
		return false;
	}
	//@author Henri Roth
	public String toString() {
		return this.getNom();
	}
	//@author Henri Roth
	public int fixerPourcentageRSE(IAcheteurContratCadre acheteur, IVendeurContratCadre vendeur, IProduit produit,
			Echeancier echeancier, long cryptogramme, boolean tg) {
		return 5;
	}

	@Override //@author Henri Roth
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		journalCC.ajouter("Nouveau contrat cadre" +contrat);
		ContratsAcheteur.add(contrat);
	}
}
