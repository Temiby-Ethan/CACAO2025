package abstraction.eq6Transformateur3;

import java.awt.Color;
import java.util.List;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

public class Transformateur3ContratCadreVendeur extends Transformateur3Fabriquant implements IVendeurContratCadre {

    public void next(){
        super.next();
        for(ChocolatDeMarque choco : chocolatDeMarques){
            if(stockChoco.getQuantityOf(choco)>10000){
                
            }
        }
    }
    @Override
    public boolean vend(IProduit produit) {
        if(produit == super.fraud
            || produit == super.arna
            || produit == super.hypo
            || produit == super.bollo){
            if(stockChoco.getQuantityOf(produit)>100){
                return true;
            }
            }
        return false;
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        return contrat.getEcheancier();
    }

    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        return 500;
    }

    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        return contrat.getPrix();
    }

    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        journalCC.ajouter("Nouveau contrat cadre" +contrat);
    }

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        double stockActuel = stockChoco.getQuantityOf(produit);
		double aLivre = Math.min(quantite, stockActuel);
		journalCC.ajouter("   Livraison de "+aLivre+" T de "+produit+" sur "+quantite+" exigees pour contrat "+contrat.getNumero());
		stockChoco.remove(produit, aLivre);
		return aLivre;
	}
    
}
