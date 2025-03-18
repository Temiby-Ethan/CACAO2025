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

public class Transformateur3ContratCadreAcheteur extends Transformateur3Fabriquant implements IAcheteurContratCadre{
	protected List<ExemplaireContratCadre> ContratsAcheteur;
	protected List<ExemplaireContratCadre> contratsObsoletes;

	public Transformateur3ContratCadreAcheteur() {
		this.ContratsAcheteur=new LinkedList<ExemplaireContratCadre>();
		this.contratsObsoletes =new LinkedList<ExemplaireContratCadre>();
	}

	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		return contrat.getEcheancier(); // on ne cherche pas a negocier sur le previsionnel de livraison
	}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		return contrat.getPrix();
	}
	public void next() {
		SuperviseurVentesContratCadre supCCadre = (SuperviseurVentesContratCadre) Filiere.LA_FILIERE.getActeur("Sup.CCadre");

		journalCC.ajouter("======= Contrats cadres finis période"+Filiere.LA_FILIERE.getEtape()+"=======");
		// On enleve les contrats obsolete (nous pourrions vouloir les conserver pour "archive"...)
		for (ExemplaireContratCadre contrat : this.ContratsAcheteur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.ContratsAcheteur.removeAll(contratsObsoletes);
		journalCC.ajouter("==============");
		
		// Proposition d'un nouveau contrat a tous les vendeurs possibles
		IProduit produit = Feve.F_BQ;
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur!=this && acteur instanceof IVendeurContratCadre && ((IVendeurContratCadre)acteur).vend(produit)) {
				supCCadre.demandeAcheteur((IAcheteurContratCadre)this, ((IVendeurContratCadre)acteur), produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 5.0), cryptogramme, false);
			}
		}
		// OU proposition d'un contrat a un des vendeurs choisi aleatoirement
		/*
		journalCC.ajouter("Recherche d'un vendeur aupres de qui acheter");
		List<IVendeurContratCadre> vendeurs = supCCadre.getVendeurs(produit);
		if (vendeurs.contains(this)) {
			vendeurs.remove(this);
		}
		IVendeurContratCadre vendeur = null;
		if (vendeurs.size()==1) {
			vendeur=vendeurs.get(0);
		} else if (vendeurs.size()>1) {
			vendeur = vendeurs.get((int)( Filiere.random.nextDouble()*vendeurs.size()));
		}
		if (vendeur!=null) {
			journalCC.ajouter("Demande au superviseur de debuter les negociations pour un contrat cadre de "+produit+" avec le vendeur "+vendeur);
			ExemplaireContratCadre cc = supCCadre.demandeAcheteur((IAcheteurContratCadre)this, vendeur, produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, (SuperviseurVentesContratCadre.QUANTITE_MIN_ECHEANCIER+10.0)/10), cryptogramme,false);
			journalCC.ajouter("-->aboutit au contrat "+cc);
		}*/
	}

	public void receptionner(IProduit produit, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
		journalCC.ajouter("Reception de "+quantiteEnTonnes+" de T de chococal en provenance du contrat "+contrat.getNumero());
		super.stockFeves.addToStock(produit, quantiteEnTonnes);
	}

	public boolean achete(IProduit produit) {
		return true;
	}
	public String toString() {
		return this.getNom();
	}

	public int fixerPourcentageRSE(IAcheteurContratCadre acheteur, IVendeurContratCadre vendeur, IProduit produit,
			Echeancier echeancier, long cryptogramme, boolean tg) {
		return 5;
	}

	@Override
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		journalCC.ajouter("Nouveau contrat cadre" +contrat);
	}
}
