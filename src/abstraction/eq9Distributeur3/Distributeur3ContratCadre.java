package abstraction.eq9Distributeur3;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur3ContratCadre extends Distributeur3Distributeur implements IAcheteurContratCadre{

    @Override
    public boolean achete(IProduit produit) {
        return produit instanceof ChocolatDeMarque && stockChocoMarque.containsKey(produit) && stockChocoMarque.get(produit) <= 100000;
    }

    @Override
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
        double FourchetteHauteNego = 2000;
        double FourchetteHauteAchat = 1700;
        double FourchetteBasseNego = 1000;
        double FourchetteBasseAchat = 1500;

        if(contrat.getEcheancier().getNbEcheances()==8 && contrat.getQuantiteTotale()<FourchetteHauteNego && contrat.getQuantiteTotale()>FourchetteBasseNego){

        }else {
            return null;
        }
        return null;
    }

    @Override
    public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
        double fourchetteLimiteNegociation  = 1500;
        double fourchetteLimiteAchat = 1000;
        double baisseNego = 0.8; // Correspond a 80% du prix proposé soit une baisse de 20%
        if(contrat.getPrix()/contrat.getQuantiteTotale()<fourchetteLimiteNegociation){
            if(contrat.getPrix()/contrat.getQuantiteTotale()<fourchetteLimiteAchat){
                return contrat.getPrix();
            }else{
                return contrat.getPrix()*baisseNego;
            }
        }else{
            return -1;
        }
    }

    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        this.journalActeur.ajouter("Nouveau contrat signé n°" + contrat.getNumero() + " : " + contrat.getQuantiteTotale() + " tonnes de " + contrat.getProduit() + " pour" + contrat.getPrix() + "€/tonne");
    }

    @Override
    public void receptionner(IProduit p, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
        stockChocoMarque.put((ChocolatDeMarque) p,this.stockChocoMarque.get(p)+quantiteEnTonnes);
        journalActeur.ajouter("reception de "+quantiteEnTonnes+" tonnes de "+p.toString()+" du contrat "+ contrat.toString());
        this.MAJStocks();
    }
}
