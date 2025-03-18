package abstraction.eq3Producteur3;

import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.produits.IProduit;

import java.util.List;
import java.util.ArrayList;

// Paul
public class Producteur3Vente extends Producteur3Stock implements IVendeurBourse, IVendeurContratCadre{
    protected List<ExemplaireContratCadre> mesContratCadres = new ArrayList<ExemplaireContratCadre>();

   
        public Producteur3Vente() {
            super();

        }


    // VENTE EN BOURSE //

    
    @Override
    public double offre(Feve feve, double cours) {
       double stock = calculTotalStockParticulier(feve);
        if (stock > 0){
            BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
            double pourcentage = (bourse.getCours(feve).getValeur()-bourse.getCours(feve).getMin())/(bourse.getCours(feve).getMax()-bourse.getCours(feve).getMin());
            return stock*pourcentage;

        }else{
            return 0.0;
        }
       
    }

    @Override
    public double notificationVente(Feve f, double coursEnEuroParT, double quantiteEnT) {
        double livrable = Math.min(calculTotalStockParticulier(f), quantiteEnT);
        if(f.getGamme().equals(Gamme.BQ)){
            if(f.isEquitable()){
                retirerStockBQ_E(f,livrable);
            }else{
                retirerStockBQ(f, livrable);
            }
        }else if (f.getGamme().equals(Gamme.MQ)){
            if (f.isEquitable()){
                retirerStockMQ_E(f,livrable);
            }else{
                retirerStockMQ(f, livrable);
            }
        }else{
            if (f.isBio()){
                retirerStockHQ_B(f,livrable);
            }else{
                retirerStockHQ(f, livrable);
            }
        }
		return livrable;
    }

    @Override
    public void notificationBlackList(int dureeEnStep) {
        
    }

    // CONTRAT CADRE //

   

    @Override
    public boolean vend(IProduit produit) {
        if (produit instanceof Feve) {
            Feve feve = (Feve)produit;
            return feve.getGamme().equals(Gamme.MQ) && feve.isEquitable();
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
        BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
        double cours = bourse.getCours(Feve.get(gamme, false, false)).getValeur();
        return cours*1.5;
    }


    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        Feve feve = (Feve)contrat.getProduit();
        Gamme gamme = feve.getGamme();
        Double prixProp = contrat.getPrix();
        BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
        double cours = bourse.getCours(Feve.get(gamme, false, false)).getValeur();
        if (prixProp >= cours*1.1) {
            return prixProp;
        }
        return cours*1.1;
    }


    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        this.mesContratCadres.add(contrat);
    }


    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        Feve f = (Feve)produit;
        double stockActuel = calculTotalStockParticulier(f);
        double livrable = Math.min(stockActuel, quantite);
        if(f.getGamme().equals(Gamme.BQ)){
            if(f.isEquitable()){
                retirerStockBQ_E(f,livrable);
            }else{
                retirerStockBQ(f, livrable);
            }
        }else if (f.getGamme().equals(Gamme.MQ)){
            if (f.isEquitable()){
                retirerStockMQ_E(f,livrable);
            }else{
                retirerStockMQ(f, livrable);
            }
        }else{
            if (f.isBio()){
                retirerStockHQ_B(f,livrable);
            }else{
                retirerStockHQ(f, livrable);
            }
        }
        return livrable;
    }
    
}
