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
public class Producteur3Vente extends Producteur3GestionDesCoûts implements IVendeurBourse, IVendeurContratCadre{
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
            boolean A = feve.getGamme().equals(Gamme.MQ) && feve.isEquitable();
            boolean B = feve.getGamme().equals(Gamme.BQ) && !feve.isEquitable();
            boolean C = feve.getGamme().equals(Gamme.HQ) && feve.isBio();
            return A || B || C;
        }
        return false;
    }
    
    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        Echeancier echeancier = contrat.getEcheancier();
        double qteTotale = contrat.getQuantiteTotale();
        double stockActuel = calculTotalStockParticulier((Feve)contrat.getProduit());
        int stepDebut = contrat.getEcheancier().getStepDebut();
        int stepFin = contrat.getEcheancier().getStepFin();
        int nbStep = stepFin - stepDebut + 1;
        if (qteTotale <= stockActuel*0.7 && qteTotale >= stockActuel*0.1 && qteTotale >= 100*nbStep){
            return echeancier;
        }
        double contreQuantite = (qteTotale + stockActuel*0.5)/2;
        if (contreQuantite < stepFin*101){
            return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nbStep, 101);
        }
        else{
            return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nbStep, (int)(contreQuantite/nbStep));
        }
    }


    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        Feve feve = (Feve)contrat.getProduit();
        double Cump = getCump(feve);
        Gamme gamme = feve.getGamme();
        if (gamme.equals(Gamme.HQ)){
            return Cump*1.2;
        }
        else{
            BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
            double cours = bourse.getCours(Feve.get(gamme,false,false)).getValeur();
            if (Cump*1.2 >= cours*1.5) {
                return Cump*1.2;
            }
            else{
                return cours*1.5;
            }
        }
        
    }


    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        Double prixProp = contrat.getPrix();
        Feve feve = (Feve)contrat.getProduit();
        double Cump = getCump(feve);
        double ancienneOffre = contrat.getListePrix().get(contrat.getListePrix().size()-2);
        double nouvelleOffre = (ancienneOffre + prixProp)/2;
        if (Cump*1.1 >= nouvelleOffre) {
            return Cump*1.1;
        }
        else{
            return nouvelleOffre;}
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
