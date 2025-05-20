package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;


import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.contratsCadres.SuperviseurVentesContratCadre;

import abstraction.eqXRomu.produits.IProduit;

import java.util.LinkedList;

public class Producteur1ContratCadre extends Producteur1Acteur implements IVendeurContratCadre {

	private static int PRIX_DEFAUT = 5000;
	
	private SuperviseurVentesContratCadre supCC;
	private List<ExemplaireContratCadre> contratsEnCours;
	private List<ExemplaireContratCadre> contratsTermines;
	protected Journal journalCC;

	public Producteur1ContratCadre() {
		super();
		this.contratsEnCours=new LinkedList<ExemplaireContratCadre>();
		this.contratsTermines=new LinkedList<ExemplaireContratCadre>();
		this.journalCC = new Journal(getNom()+" journal CC", this);
	}
	
	public void initialiser() {
		super.initialiser();
		this.supCC = (SuperviseurVentesContratCadre) Filiere.LA_FILIERE.getActeur("Sup.CCadre");
    	}	

	public void next() {
		super.next();
		this.journalCC.ajouter("Etape :  "+Filiere.LA_FILIERE.getEtape());
		for (Feve f : Feve.values()) { // pas forcement equitable : on avise si on lance un contrat cadre pour tout type de feve
			if (stock.getStock(f)-restantDu(f)>1200) { // au moins 100 tonnes par step pendant 6 mois
				this.journalCC.ajouter("   "+f+" suffisamment en stock pour passer un CC");
				double parStep = Math.max(100, (stock.getStock(f)-restantDu(f))/24); // au moins 100, et pas plus que la moitie de nos possibilites divisees par 2
				Echeancier e = new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 12, parStep);
				List<IAcheteurContratCadre> acheteurs = supCC.getAcheteurs(f);
				if (acheteurs.size()>0) {
					IAcheteurContratCadre acheteur = acheteurs.get(Filiere.random.nextInt(acheteurs.size()));
					journalCC.ajouter("   "+acheteur.getNom()+" retenu comme acheteur parmi "+acheteurs.size()+" acheteurs potentiels");
					ExemplaireContratCadre contrat = supCC.demandeVendeur(acheteur, this, f, e, cryptogramme, false);
					if (contrat==null) {
						journalCC.ajouter(Color.RED, Color.white,"   echec des negociations");
					} else {
						this.contratsEnCours.add(contrat);
						journalCC.ajouter(Color.GREEN, acheteur.getColor(), "   contrat signe");
					}
				} else {
					journalCC.ajouter("   pas d'acheteur");
				}
			}
		}
		// On archive les contrats termines
		for (ExemplaireContratCadre c : this.contratsEnCours) {
			if (c.getQuantiteRestantALivrer()==0.0) {
				this.contratsTermines.add(c);
			}
		}
		for (ExemplaireContratCadre c : this.contratsTermines) {
			this.contratsEnCours.remove(c);
		}
		this.journalCC.ajouter("---------------------------------");
		for (ExemplaireContratCadre cc: this.contratsEnCours) {
			this.journalCC.ajouter(cc.toString());
		}
		for (Feve f : Feve.values()) { // pas forcement equitable : on avise si on lance un contrat cadre pour tout type de feve
			this.journalCC.ajouter("Feve "+f+" en stock="+stock.getStock(f)+" restant du="+restantDu(f));
		}
		this.journalCC.ajouter("=================================");
	}

	public double restantDu(Feve f) {
		double res=0;
		this.journalCC.ajouter("RESTANT DU "+f+" ----");
		for (ExemplaireContratCadre c : this.contratsEnCours) {
			if (c.getProduit().equals(f)) {
				res+=c.getQuantiteRestantALivrer();
			}
			this.journalCC.ajouter("contrat "+c.getNumero()+" feve "+c.getProduit()+" vs "+f+" RD="+res);
		}
		return res;
	}

	public double prix(Feve f) {
		double res=0;
		List<Double> lesPrix = new LinkedList<Double>();
		for (ExemplaireContratCadre c : this.contratsEnCours) {
			if (c.getProduit().equals(f)) {
				lesPrix.add(c.getPrix());
			}
		}
		for (ExemplaireContratCadre c : this.contratsTermines) {
			if (c.getProduit().equals(f)) {
				lesPrix.add(c.getPrix());
			}
		}
		if (lesPrix.size()>0) {
			double somme=0;
			for (Double d : lesPrix) {
				somme+=d;
			}
			res=somme/lesPrix.size();
		}
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> jx=super.getJournaux();
		jx.add(journalCC);
		return jx;
	}

	public boolean vend(IProduit produit) {
		return produit.getType().equals("Feve");
    }

	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		journalCC.ajouter("      contreProposition("+contrat.getProduit()+" avec echeancier "+contrat.getEcheancier());
		Echeancier ec = contrat.getEcheancier();
		IProduit produit = contrat.getProduit();
		Echeancier res = ec;
		journalCC.ajouter("Echeancier contrat #"+contrat.getNumero()+" volume total="+ec.getQuantiteTotale()+" stock="+stock.getStock((Feve)produit)+" restantdu="+restantDu((Feve)produit));
		boolean acceptable = produit.getType().equals("Feve")
				&& ec.getQuantiteTotale()>=1200  // au moins 100 tonnes par step pendant 6 mois
				&& ec.getStepFin()-ec.getStepDebut()>=11   // duree totale d'au moins 12 etapes
				&& ec.getStepDebut()<Filiere.LA_FILIERE.getEtape()+8 // ca doit demarrer dans moins de 4 mois
				&& ec.getQuantiteTotale()<stock.getStock((Feve)produit)-restantDu((Feve)produit);
				journalCC.ajouter("Echeancier contrat # acceptable ? "+acceptable);
				
				if (!acceptable) {
					if (!produit.getType().equals("Feve") || stock.getStock((Feve)produit)-restantDu((Feve)produit)<1200) {
						if (!produit.getType().equals("Feve")) {
							journalCC.ajouter("      ce n'est pas une feve : je retourne null");
						} else {
							journalCC.ajouter("      je n'ai que "+(stock.getStock((Feve)produit)-restantDu((Feve)produit))+" de disponible (moins de 1200) : je retourne null");
						}
						return null;
					}
					if (ec.getQuantiteTotale()<=stock.getStock((Feve)produit)-restantDu((Feve)produit)) {
						journalCC.ajouter("      je retourne "+new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 12,  (int)(ec.getQuantiteTotale()/12)));
						return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 12,  (int)(ec.getQuantiteTotale()/12));
					} else {
						journalCC.ajouter("      je retourne "+new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 12,  (int)(((stock.getStock((Feve)produit)-restantDu((Feve)produit))/12))));
						res = new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 12,  (int)(((stock.getStock((Feve)produit)-restantDu((Feve)produit))/12)));
						return res.getQuantiteTotale()>=1200 ? res : null;
					}
				}
				journalCC.ajouter("      j'accepte l'echeancier");
				return res;
	}

	public double propositionPrix(ExemplaireContratCadre contrat) {
		if (!contrat.getProduit().getType().equals("Feve")) {
			return 0; // ne peut pas etre le cas normalement 
		}
		BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
		double cours = ((Feve)(contrat.getProduit())).isEquitable() ? 0.0 : bourse.getCours((Feve)contrat.getProduit()).getValeur();
		double prixCC = prix((Feve)contrat.getProduit());
		if (prixCC==0.0) {
			PRIX_DEFAUT=(int)(PRIX_DEFAUT*0.98); // on enleve 2% tant qu'on n'a pas passe un contrat
		}
		double res = prixCC>cours ? prixCC*1.25 : (cours<PRIX_DEFAUT ? PRIX_DEFAUT : (int)(cours*1.5));
		journalCC.ajouter("      propositionPrix retourne "+res);
		return res;
	}

	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		if (!contrat.getProduit().getType().equals("Feve")) {
			return 0; // ne peut pas etre le cas normalement 
		}
		List<Double> prix = contrat.getListePrix();
		if (prix.get(prix.size()-1)>=0.995*prix.get(0)) {
			journalCC.ajouter("      contrePropose le prix demande : "+contrat.getPrix());
			return contrat.getPrix();
		} else {
			int percent = (int)(100* Math.pow((contrat.getPrix()/prix.get(0)), prix.size()));
			int alea = Filiere.random.nextInt(100);
			if (alea< percent) { // d'autant moins de chance d'accepter que le prix est loin de ce qu'on proposait
				if (Filiere.random.nextInt(100)<20) { // 1 fois sur 5 on accepte
					journalCC.ajouter("      contrePropose le prix demande : "+contrat.getPrix());
					return contrat.getPrix();
				} else {
					double res = (prix.get(prix.size()-2)+contrat.getPrix())/2.0; // la mmoyenne des deux derniers prix
					journalCC.ajouter("      contreproposition("+contrat.getPrix()+") retourne "+res);
					return res;
				}
			} else {
				journalCC.ajouter("      contreproposition("+contrat.getPrix()+") retourne "+prix.get(0)*1.05);
				return prix.get(0)*1.05;
			}
		}
	}

	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.contratsEnCours.add(contrat);
	}

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        double quantiteEnStock = stock.getStock((Feve) produit);
        double quantiteLivree = Math.min(quantite, quantiteEnStock);

        if (quantiteLivree <= 0) {
            journal.ajouter("Échec de livraison : stock vide pour " + produit);
            return 0.0;
        }

        boolean retraitOk = stock.retirer(produit, quantiteLivree,cryptogramme);

        if (retraitOk) {
            journal.ajouter("Livraison de " + quantiteLivree + " de " + produit + " pour le contrat " + contrat);
            return quantiteLivree;
        } else {
            journal.ajouter("Erreur de retrait lors de la livraison de " + produit);
            return 0.0;
 }
}
}