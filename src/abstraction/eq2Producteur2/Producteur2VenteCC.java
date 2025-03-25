package abstraction.eq2Producteur2;

import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.produits.IProduit;

import java.util.List;
import java.util.ArrayList;


//BRUN Thomas
public class Producteur2VenteCC extends Producteur2couts implements IVendeurBourse, IVendeurContratCadre{
	
	private Journal JournalEQ2CC;
   
    public Producteur2VenteCC() {
        super();
		this.JournalEQ2CC = new Journal("JournalCCEq2",this);

    }
    // CONTRAT CADRE //

   

    @Override
    public boolean vend(IProduit produit) {
        if (produit instanceof Feve) {
            Feve feve = (Feve)produit;
            return feve.getGamme().equals(Gamme.BQ);
        }
        return false;
    }
    
    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        Echeancier echeancier = contrat.getEcheancier();
        double qteTotale = contrat.getQuantiteTotale();
        double qteInit = contrat.getEcheanciers().get(0).getQuantiteTotale();
        if (qteTotale >= qteInit*1.1) {
            return echeancier;
        }
        int stepDebut = contrat.getEcheancier().getStepDebut();
        int stepFin = contrat.getEcheancier().getStepFin();
        int nbStep = stepFin - stepDebut;
        return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nbStep,(int)(qteInit*1.2/nbStep));
    }


    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        Feve feve = (Feve)contrat.getProduit();
        Gamme gamme = feve.getGamme();
		double Prix = prix.get(feve);
        return Prix;
    }


    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        Feve feve = (Feve)contrat.getProduit();
        Gamme gamme = feve.getGamme();
        Double prixProp = contrat.getPrix();
        double Prix = prix.get(feve);
        if (prixProp >= Prix*1.1) {
            return prixProp;
        }
        return Prix*1.1;
    }

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        Feve f = (Feve)produit;
        Variable stockActuel = stockvar.get(f);
		double sa = stockActuel.getValeur();
        double livrable = Math.min(sa, quantite);
        DeleteStock(f,livrable); 
		JournalEQ2CC.ajouter(" Le livrable est "+livrable);
        return livrable;
    }



	@Override
	public double offre(Feve f, double cours) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'offre'");
	}



	@Override
	public double notificationVente(Feve f, double quantiteEnT, double coursEnEuroParT) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'notificationVente'");
	}



	@Override
	public void notificationBlackList(int dureeEnStep) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'notificationBlackList'");
	}
    
	public List<Journal> getJournaux() { //Mets à jour les journaux
		List<Journal> res = super.getJournaux();
		res.add(JournalEQ2CC);
		return res;
	}



	@Override
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'notificationNouveauContratCadre'");
	}
}
