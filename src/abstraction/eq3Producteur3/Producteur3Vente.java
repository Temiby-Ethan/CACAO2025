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

    public void degraderfeve(Feve feve) {
        int contrat_en_cours = 0;
        for (ExemplaireContratCadre c : this.mesContratCadres){
            if (c.getProduit().equals((IProduit)feve)){
                contrat_en_cours++;
            }
        }
        if (contrat_en_cours == 0){
            double stock = calculTotalStockParticulier(feve);
            if (feve.getGamme().equals(Gamme.HQ)){
                if (feve.isBio()){
                    retirerStockHQ_B(feve, 0.25*stock);
                    ajouterStockMQFin(feve, 0.25*stock);
                }else{
                    retirerStockHQ(feve, getPerimeParticulier(feve));
                    ajouterStockMQFin(feve, getPerimeParticulier(feve));
                }
            }else if (feve.getGamme().equals(Gamme.MQ)){
                if (feve.isEquitable()){
                    retirerStockMQ_E(feve, 0.25*stock);
                    ajouterStockMQFin(feve, 0.25*stock);
                }
            }else{
                if (feve.isEquitable()){
                    retirerStockBQ_E(feve, 0.25*stock);
                    ajouterStockBQFin(feve, 0.25*stock);
                }
            }
        }else{
            int a_livrer = 0;
                for (ExemplaireContratCadre c : this.mesContratCadres){
                    if (c.getProduit().equals((IProduit)feve)){
                        a_livrer += c.getQuantiteRestantALivrer();
                    }
                }
            double va_Perime = getPerimeParticulier(feve);
            if (va_Perime > a_livrer){
                double surplus = va_Perime - a_livrer;
                if (feve.getGamme().equals(Gamme.HQ)){
                    if (feve.isBio()){
                        retirerStockHQ_B(feve, surplus);
                        ajouterStockMQ(feve, surplus);
                    }else{
                        retirerStockHQ(feve, surplus);
                        ajouterStockMQ(feve, surplus);
                    }
                }else if (feve.getGamme().equals(Gamme.MQ)){
                    if (feve.isEquitable()){
                        retirerStockMQ_E(feve, surplus);
                        ajouterStockMQ(feve, surplus);
                    }
                }else{
                    if (feve.isEquitable()){
                        retirerStockBQ_E(feve, surplus);
                        ajouterStockBQ(feve, surplus);
                    }
                }
            }
        }
                
        
    }

    @Override
    public double offre(Feve feve, double cours) {
        /* 
        int contrat_en_cours = 0;
        for (ExemplaireContratCadre c : this.mesContratCadres){
            if (c.getProduit().equals((IProduit)feve)){
                contrat_en_cours++;
            }
        }
        if (contrat_en_cours == 0){
            return getDerniere2periodeParticulier(feve);
        */

        if (feve.getGamme().equals(Gamme.MQ)){
            return calculTotalStockMQ();
        }
        else{
            int a_livrer = 0;
            for (ExemplaireContratCadre c : this.mesContratCadres){
                if (c.getProduit().equals((IProduit)feve)){
                    a_livrer += c.getQuantiteRestantALivrer();
                }
            }
            double va_Perime = getPerimeParticulier(feve);
            if (va_Perime > a_livrer){
                return va_Perime - a_livrer;
            }else{
                return 0.0;
            }
        }
    }
        /* 
        double stock = calculTotalStockParticulier(feve);
        if (stock > 0){
            BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
            double pourcentage = (bourse.getCours(feve).getValeur()-bourse.getCours(feve).getMin())/(bourse.getCours(feve).getMax()-bourse.getCours(feve).getMin());
            return stock*pourcentage;

        }else{
            return 0.0;
        }*/
       
    

    @Override
    public double notificationVente(Feve f, double coursEnEuroParT, double quantiteEnT) {
        double livrable = Math.min(calculTotalStockParticulier(f), quantiteEnT);
        if(f.getGamme().equals(Gamme.BQ)){
            if(f.isEquitable()){
                retirerStockBQ_E(f,livrable);
                journalBourse.ajouter("Vente de " + livrable + " tonnes de " + f + " à la bourse pour " + coursEnEuroParT + " euros par tonne.");
            }else{
                retirerStockBQ(f, livrable);
                journalBourse.ajouter("Vente de " + livrable + " tonnes de " + f + " à la bourse pour " + coursEnEuroParT + " euros par tonne.");
            }
        }else if (f.getGamme().equals(Gamme.MQ)){
            if (f.isEquitable()){
                retirerStockMQ_E(f,livrable);
                journalBourse.ajouter("Vente de " + livrable + " tonnes de " + f + " à la bourse pour " + coursEnEuroParT + " euros par tonne.");
            }else{
                retirerStockMQ(f, livrable);
                journalBourse.ajouter("Vente de " + livrable + " tonnes de " + f + " à la bourse pour " + coursEnEuroParT + " euros par tonne.");
            }
        }else{
            if (f.isBio()){
                retirerStockHQ_B(f,livrable);
                journalBourse.ajouter("Vente de " + livrable + " tonnes de " + f + " à la bourse pour " + coursEnEuroParT + " euros par tonne.");
            }else{
                retirerStockHQ(f, livrable);
                journalBourse.ajouter("Vente de " + livrable + " tonnes de " + f + " à la bourse pour " + coursEnEuroParT + " euros par tonne.");
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
        double date = Filiere.LA_FILIERE.getEtape();
        if (produit instanceof Feve) {

            Feve feve = (Feve)produit;
            double stockActuel = calculTotalStockParticulier(feve);
            double stockNouveau = getNouveauStockParticulier(feve);
            double aLivrer_mi = 0;
            double aLivrer_ma = 0;
            int mi = 1000;
            int ma = 0;
            for (ExemplaireContratCadre c : this.mesContratCadres){
                if (c.getProduit().equals(feve)){
                    if (c.getEcheancier().getStepFin() > mi + Filiere.LA_FILIERE.getEtape()){
                        mi = Math.abs(c.getEcheancier().getStepFin() - Filiere.LA_FILIERE.getEtape());
                    }
                    if (c.getEcheancier().getStepFin() < ma + Filiere.LA_FILIERE.getEtape()){
                        ma = Math.abs(c.getEcheancier().getStepFin() - Filiere.LA_FILIERE.getEtape());
                    }
                }
            }
            for (ExemplaireContratCadre c : this.mesContratCadres){
                if (c.getProduit().equals(feve)){
                    aLivrer_mi += c.getQuantiteALivrerAuStep()*mi;
                    aLivrer_ma += c.getQuantiteRestantALivrer();
                }
            }
            double stockPotentiel_mi = stockActuel + stockNouveau*mi;
            double stockPotentiel_ma = stockActuel + stockNouveau*ma;
            boolean Mi = stockPotentiel_mi*0.5 >= aLivrer_mi;
            boolean Ma = stockPotentiel_ma*0.5 >= aLivrer_ma;
            boolean A = feve.getGamme().equals(Gamme.MQ) && feve.isEquitable() && calculTotalStockParticulier(feve) >= 0 && Mi && Ma;
            boolean B = feve.getGamme().equals(Gamme.BQ) && !feve.isEquitable() && calculTotalStockParticulier(feve) >= 0 && Mi && Ma;
            boolean C = feve.getGamme().equals(Gamme.HQ) && feve.isBio() && calculTotalStockParticulier(feve) >= 0 && date != 0 && Mi && Ma;
            return A || B || C;
        }
        return false;
    }
    
    
    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        Echeancier echeancier = contrat.getEcheancier();
        double stockActuel = calculTotalStockParticulier((Feve)contrat.getProduit());
        double stockNouveau = getNouveauStockParticulier((Feve)contrat.getProduit());
        int stepDebut = contrat.getEcheancier().getStepDebut();
        int stepFin = contrat.getEcheancier().getStepFin();
        int nbStep = stepFin - stepDebut + 1;
        int contrat_en_cours = 0;
        for (ExemplaireContratCadre c : this.mesContratCadres){
            if (c.getProduit().equals(contrat.getProduit())){
                contrat_en_cours++;
            }
        }
        
        if (contrat_en_cours == 0){
            if ( stockActuel*0.5 >= contrat.getQuantiteTotale() && stockActuel*0.05 <= contrat.getQuantiteTotale() && contrat.getQuantiteTotale()/nbStep >= 100){
                return echeancier;
            }
            if (stockActuel*0.5 <= contrat.getQuantiteTotale()){
                return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nbStep, stockActuel*0.5/nbStep);
            }
            if (stockActuel*0.05 >= contrat.getQuantiteTotale() || contrat.getQuantiteTotale()/nbStep < 100){
                return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nbStep, stockActuel*0.3/nbStep);
            }
            else{
                return null;
            }
        }
        else {
            double stockPotentiel = stockActuel + stockNouveau*nbStep;
            double parStep = contrat.getQuantiteTotale()/nbStep;

            double aLivrer = contrat.getQuantiteTotale();
            for (ExemplaireContratCadre c : this.mesContratCadres){
                if (c.getProduit().equals(contrat.getProduit())){
                    aLivrer += c.getQuantiteRestantALivrer();
                }
            
            }
            if (aLivrer <= stockPotentiel*0.5 && parStep >= 100){
                return echeancier;
            }
            else{
                return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nbStep, stockPotentiel*0.3/nbStep);
            }
        }
        
    }


    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        Feve feve = (Feve)contrat.getProduit();
        double Cump = getCump(feve);
        Gamme gamme = feve.getGamme();
        if (gamme.equals(Gamme.HQ) ) {
            return Cump*1.1;
        }
        else{
            BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
            double cours = bourse.getCours(Feve.get(gamme,false,false)).getValeur();
            if (Cump*1.1 >= cours*1.2) {
                return Cump*1.1;
            }
            else{
                return cours*1.2;
            }
        }
        
    }


    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        Double prixProp = contrat.getPrix();
        Feve feve = (Feve)contrat.getProduit();
        double Cump = getCump(feve);
        double ancienneOffre = contrat.getListePrix().get(contrat.getListePrix().size()-2);
        if (prixProp >= ancienneOffre){
            return prixProp;
        }
        double nouvelleOffre = ((ancienneOffre + prixProp)/2);
        if (Cump >= nouvelleOffre) {
            return Cump;
        }
        else{
            return nouvelleOffre;}
    }


    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        this.mesContratCadres.add(contrat);
        journalContratCadre.ajouter("Nouveau contrat cadre : "+ "\n");
        journalContratCadre.ajouter("Produit : " + contrat.getProduit() + "\n");
        journalContratCadre.ajouter("Prix : " + contrat.getPrix() + "\n");
        journalContratCadre.ajouter("Quantité : " + contrat.getQuantiteTotale() + "\n");
        journalContratCadre.ajouter("Acheteur : " + contrat.getAcheteur() + "\n");
        journalContratCadre.ajouter("Nb de Contrats en cours : " + this.mesContratCadres.size() + "\n");
    }

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        Feve f = (Feve)produit;
        double stockActuel = calculTotalStockParticulier(f);
        double livrable = Math.max(0,Math.min(stockActuel, quantite));
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
