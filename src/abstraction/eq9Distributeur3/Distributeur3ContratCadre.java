package abstraction.eq9Distributeur3;

import abstraction.eqXRomu.contratsCadres.*;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.produits.IProduit;

import java.util.ArrayList;
import java.util.List;

public class Distributeur3ContratCadre extends Distributeur3Charges implements IAcheteurContratCadre{

    ArrayList<ExemplaireContratCadre> contrats;
    boolean BQ;
    boolean BQ_E;
    boolean MQ;

    public Distributeur3ContratCadre() {
        super();
        contrats = new ArrayList<>();

    }

    @Override
    // Implémentée par Héloise et Jeanne
    public void next() {

        double ressourcesBQ = this.stockBQ.getValeur(this.cryptogramme);
        double ressourcesBQ_E = this.stockBQ_E.getValeur(this.cryptogramme);
        double ressourcesMQ = this.stockMQ.getValeur(this.cryptogramme);

        for(ExemplaireContratCadre contrat : contrats) {
            journalPrintContrat.ajouter(contrat.getNumero()+" : "+ contrat.getEcheancier().toString());
            if(contrat.getEcheancier().getStepFin()>=Filiere.LA_FILIERE.getEtape()+3) {
                ChocolatDeMarque choc = (ChocolatDeMarque) contrat.getProduit();
                if(choc.getGamme().equals(Gamme.BQ)){
                    if(choc.isEquitable()){
                        ressourcesBQ_E = ressourcesBQ_E+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape())+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+1)+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+2)+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+3);
                    }else{
                        ressourcesBQ = ressourcesBQ+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape())+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+1)+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+2)+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+3);
                    }
                }else{
                    ressourcesMQ = ressourcesMQ+ressourcesBQ_E+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape())+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+1)+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+2)+contrat.getEcheancier().getQuantite(Filiere.LA_FILIERE.getEtape()+3);
                }
            }
        }

        double besoinBQ=0.0;
        double besoinBQ_E=0.0;
        double besoinMQ=0.0;

        for(ChocolatDeMarque choc : Filiere.LA_FILIERE.getChocolatsProduits()){
            if(choc.getGamme().equals(Gamme.BQ)){
                if(choc.isEquitable()){
                    besoinBQ_E = besoinBQ_E+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-24);
                    besoinBQ_E = besoinBQ_E+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-23);
                    besoinBQ_E = besoinBQ_E+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-22);
                    besoinBQ_E = besoinBQ_E+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-21);
                }else{
                    besoinBQ = besoinBQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-24);
                    besoinBQ = besoinBQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-23);
                    besoinBQ = besoinBQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-22);
                    besoinBQ = besoinBQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-21);
                }
            }else{
                besoinMQ = besoinMQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-24);
                besoinMQ = besoinMQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-23);
                besoinMQ = besoinMQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-22);
                besoinMQ = besoinMQ+Filiere.LA_FILIERE.getVentes(choc,Filiere.LA_FILIERE.getEtape()-21);
            }
        }

        BQ = ressourcesBQ<=0.75*besoinBQ;
        BQ_E = ressourcesBQ_E<=0.75*besoinBQ_E;
        MQ = ressourcesMQ<=0.75*besoinMQ;



        super.next();
       SuperviseurVentesContratCadre superviseur = (SuperviseurVentesContratCadre) Filiere.LA_FILIERE.getActeur("Sup.CCadre");
        List<ChocolatDeMarque> listeChcocolatPertinents = new ArrayList<ChocolatDeMarque>();
        List<IActeur> transfo = new ArrayList<>();
        transfo.add(Filiere.LA_FILIERE. getActeur("EQ4"));
        transfo.add(Filiere.LA_FILIERE. getActeur("EQ5"));
        transfo.add(Filiere.LA_FILIERE. getActeur("EQ6"));
       for(ChocolatDeMarque choco :  Filiere.LA_FILIERE.getChocolatsProduits()){
           if(choco.getGamme()== Gamme.BQ){
               listeChcocolatPertinents.add(choco);
           }
       }
       
       for (IActeur a : transfo){
           if(a instanceof IVendeurContratCadre && Filiere.LA_FILIERE.getActeursSolvables().contains(a)){
               for(ChocolatDeMarque choco : listeChcocolatPertinents) {
                   if((choco.getGamme()== Gamme.BQ && BQ) || (choco.getGamme()== Gamme.BQ && choco.isEquitable() && BQ_E) || (choco.getGamme().equals(Gamme.MQ) && MQ)) {
                       if (Filiere.LA_FILIERE.getFabricantsChocolatDeMarque(choco).contains(a)) {
                           Echeancier e = new Echeancier(Filiere.LA_FILIERE.getEtape() + 1, 8, 200);
                           ExemplaireContratCadre cc = superviseur.demandeAcheteur(this, (IVendeurContratCadre) a, choco, e, this.cryptogramme, false);
                           if (cc != null) {
                               contrats.add(cc);
                           }
                       }
                   }else{
                       journalPrintContrat.ajouter("conditions non remplis pour passer le contrat");
                   }
               }
           }
       }
    }

    @Override
    public boolean achete(IProduit produit) {
        return produit instanceof ChocolatDeMarque && stockChocoMarque.containsKey(produit) && stockChocoMarque.get(produit) <= 100000;
    }

    @Override
    // Implémentée par Héloïse et Jeanne
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
        double FourchetteHauteNego = 2000;
        double FourchetteHauteAchat = 1700;
        double FourchetteBasseNego = 1000;
        double FourchetteBasseAchat = 1500;

        /*if(contrat.getEcheancier().getNbEcheances()==8 && contrat.getQuantiteTotale()<FourchetteHauteNego && contrat.getQuantiteTotale()>FourchetteBasseNego){

        }else {
            return null;
        }
        return null;*/
        return contrat.getEcheancier();
    }

    @Override
    // Implémentée par Héloïse
    public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat){
        journalContrats.ajouter("negocie le contrat");

        ChocolatDeMarque choco = (ChocolatDeMarque) contrat.getProduit();
        if(((choco.getGamme()== Gamme.BQ && BQ) || (choco.getGamme()== Gamme.BQ && choco.isEquitable() && BQ_E) || (choco.getGamme().equals(Gamme.MQ) && MQ))){
            return -1;
        }

        int prixMoyen=0;
        if(Filiere.LA_FILIERE.getEtape()==0) {
            prixMoyen = 200000;
        }else{
            prixMoyen = (int) Filiere.LA_FILIERE.prixMoyen((ChocolatDeMarque) contrat.getProduit(), Filiere.LA_FILIERE.getEtape()-1);
            //System.out.println(" le prix moyen de "+((ChocolatDeMarque) contrat.getProduit()).getNom()+" est : "+prixMoyen);
        }

        journalContrats.ajouter("proposition équipe en face a "+contrat.getPrix()+" euros");

        //double fourchetteLimiteNegociation  = 1500;

        // a enlever, on laisse juste ça pour pouvoir négocier avec les autres
        double fourchetteLimiteAchat;
        double fourchetteLimiteRenta;
        double fourchetteLimiteNegociation;

        this.valeurMoyennes.ajouter("etape "+Filiere.LA_FILIERE.getEtape()+"\n"+contrat.getProduit().toString()+ "  "+prixMoyen);
        double total = contrat.getQuantiteTotale();

        if(Filiere.LA_FILIERE.getEtape()==0) {
            fourchetteLimiteNegociation  = prixMoyen;
            fourchetteLimiteRenta = prixMoyen;
            fourchetteLimiteAchat = fourchetteLimiteRenta*0.95;
        }else {
            fourchetteLimiteNegociation = prixMoyen*total - (chargesTotal() / getVentesByStep(Filiere.LA_FILIERE.getEtape() - 1)) * 0;
            fourchetteLimiteRenta = prixMoyen*total - (chargesTotal() / getVentesByStep(Filiere.LA_FILIERE.getEtape() - 1));
            fourchetteLimiteAchat = fourchetteLimiteRenta * 0.95;
        }
        journalContrats.ajouter("prix Moyen : "+prixMoyen);
        journalContrats.ajouter("fourchette limite nego : "+fourchetteLimiteNegociation);
        journalContrats.ajouter("fourchette limite de renta "+fourchetteLimiteRenta);
        journalContrats.ajouter("fourchette limite achat : "+fourchetteLimiteAchat);



        if(contrat.getPrix()<=fourchetteLimiteNegociation){
            if(contrat.getPrix()<=fourchetteLimiteAchat){
                journalContrats.ajouter("accepte le contrat");
                return contrat.getPrix();
            }else{
                journalContrats.ajouter("continue les négociations");
                return fourchetteLimiteNegociation*0.85;
            }
        }else{
            journalContrats.ajouter("refus des négociations");
            return -1;
        }
    }

    @Override
    // Implémentée par Jeanne
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        // System.out.println("Nouveau contrat signé pour l'équipe 9");
        this.journalActeur.ajouter("Nouveau contrat signé n°" + contrat.getNumero() + " : " + contrat.getQuantiteTotale() + " tonnes de " + contrat.getProduit() + " pour" + contrat.getPrix() + "€/tonne");
        //this.journalPrintContrat.ajouter(contrat.getNumero()+" : "+ contrat.getEcheancier().toString());
        contrats.add(contrat);
    }

    @Override
    public void receptionner(IProduit p, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
        //System.out.println("quantité du chocolat après recepetion : "+p.toString()+" "+this.stockChocoMarque.get(p));
        stockChocoMarque.put((ChocolatDeMarque) p,this.stockChocoMarque.get(p)+quantiteEnTonnes);
        journalActeur.ajouter("reception de "+quantiteEnTonnes+" tonnes de "+p.toString()+" du contrat "+ contrat.toString());
        this.MAJStocks();
        //System.out.println("quantité du chocolat après recepetion : "+p.toString()+" "+this.stockChocoMarque.get(p));
        ChocolatDeMarque choco = (ChocolatDeMarque) p;
//        if(choco.getChocolat().isEquitable()){
//            if(Filiere.LA_FILIERE.getEtape()!=0 && Filiere.LA_FILIERE.prixMoyen((ChocolatDeMarque) p,Filiere.LA_FILIERE.getEtape())!=0){
//                this.prix.put((ChocolatDeMarque) p,(float) (Filiere.LA_FILIERE.prixMoyen((ChocolatDeMarque) p,Filiere.LA_FILIERE.getEtape())*0.97));
//            }else {
//                this.prix.put((ChocolatDeMarque) p, 2500.0F);
//            }
//        }else{
//            if(Filiere.LA_FILIERE.getEtape()!=0 && Filiere.LA_FILIERE.prixMoyen((ChocolatDeMarque) p,Filiere.LA_FILIERE.getEtape())!=0){
//                this.prix.put((ChocolatDeMarque) p,(float) (Filiere.LA_FILIERE.prixMoyen((ChocolatDeMarque) p,Filiere.LA_FILIERE.getEtape())*0.97));
//            }else {
//                this.prix.put((ChocolatDeMarque)p, 3000.0F);
//            }
//
//            this.prix.put((ChocolatDeMarque) p,4000.0F);
        }
    
}
