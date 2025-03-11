package abstraction.eq9Distributeur3;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

import java.awt.*;
import java.util.List;

public class Distributeur3ContratCadre extends Distributeur3Distributeur implements IAcheteurContratCadre{

    @Override
    public boolean achete(IProduit produit) {
        return produit instanceof ChocolatDeMarque && stocks.containsKey(produit) && stocks.get(produit) <= 100000;
    }

    @Override
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
        return null;
    }

    @Override
    public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
        return 0;
    }

    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {

    }

    @Override
    public void receptionner(IProduit p, double quantiteEnTonnes, ExemplaireContratCadre contrat) {
        stocks.put((ChocolatDeMarque) p,this.stocks.get(p)+quantiteEnTonnes);
        journalActeur.ajouter("reception de "+quantiteEnTonnes+" tonnes de "+p.toString()+" du contrat "+ contrat.toString());
        this.MAJStocksTotal();
    }
}
