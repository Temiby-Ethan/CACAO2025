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
import abstraction.eqXRomu.produits.IProduit;

public class Transformateur3ContratCadreVendeur extends Transformateur3Fabriquant implements IVendeurContratCadre {

    @Override
    public boolean vend(IProduit produit) {
        return true;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'livrer'");
    }
    
}
