package abstraction.eq6Transformateur3;

import abstraction.eqXRomu.appelDOffre.AppelDOffre;
import abstraction.eqXRomu.appelDOffre.IVendeurAO;
import abstraction.eqXRomu.appelDOffre.OffreVente;
import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.IProduit;

public class Transformateur3AO extends Transformateur3AcheteurBourse implements IVendeurAO {

    public Transformateur3AO(){
    }

    @Override
    public OffreVente proposerVente(AppelDOffre offre) {
        IProduit produit = offre.getProduit();
        if (stockChoco.contains(produit)){
            double Q = offre.getQuantiteT();
            double prix = Q * 1.7;
            OffreVente offrevente = new OffreVente(offre, this, produit, prix);
        return offrevente;
        }
        return null;
    }

    @Override
    public void notifierVenteAO(OffreVente propositionRetenue) {
        journalAO.ajouter("Nouvelle vente " + propositionRetenue);
        IProduit P = propositionRetenue.getProduit();
        double Q = propositionRetenue.getQuantiteT();
        stockChoco.remove(P, Q);
    }

    @Override
    public void notifierPropositionNonRetenueAO(OffreVente propositionRefusee) {
        journalAO.ajouter("Proposition non retenue " + propositionRefusee);
    }
    
}
