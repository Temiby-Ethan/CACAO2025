package abstraction.eq1Producteur1;

import java.util.*;

import abstraction.eqXRomu.contratsCadres.*;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur1ContratCadre extends Producteur1arbes implements IVendeurContratCadre {

    private Producteur1 vendeur; // Référence au Producteur1 principal
    private List<ExemplaireContratCadre> contrats;
    private Journal journal; // Journal pour enregistrer les opérations

    public Producteur1ContratCadre() {
        super();
        
        // Initialisation du journal avant de l'utiliser
        this.journal = new Journal(getNom() + " - Journal Contrat Cadre",this);

        // Initialisation du stock avec le journal
        this.stock = new Stock();

        this.contrats = new ArrayList<>();

        // Initialisation des stocks pour chaque type de fève
        stock.ajouter(Feve.F_BQ, 100000);
        stock.ajouter(Feve.F_MQ, 100000);
        stock.ajouter(Feve.F_HQ_BE, 100000);
    }

    @Override
    public boolean vend(IProduit produit) {
        return produit instanceof Feve;
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        IProduit produit = contrat.getProduit();
        Echeancier echeancierPropose = contrat.getEcheancier();

        double stockDispo = stock.getStock((Feve) produit); // Vérifie le stock spécifique
        double quantiteMax = 0.25 * stockDispo;

        if (echeancierPropose.getQuantiteTotale() > quantiteMax) {
            Echeancier contreProp = new Echeancier(echeancierPropose.getStepDebut());
            for (int step = echeancierPropose.getStepDebut(); step <= echeancierPropose.getStepFin(); step++) {
                double q = echeancierPropose.getQuantite(step);
                if (q < 0) {
                    journal.ajouter("Erreur : Quantité négative détectée dans la contre-proposition.");
                    return null;
                }
                contreProp.ajouter(Math.min(q, quantiteMax / echeancierPropose.getNbEcheances()));
            }
            return contreProp;
        }
        return echeancierPropose;
    }

    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        IProduit produit = contrat.getProduit();

        if (produit.equals(Feve.F_BQ)) return 1.2;
        if (produit.equals(Feve.F_MQ)) return 1.8;
        if (produit.equals(Feve.F_HQ_BE)) return 2.5;

        return 1.0;
    }

    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        return propositionPrix(contrat);
    }

    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        this.contrats.add(contrat);
        journal.ajouter("Nouveau contrat cadre accepté : " + contrat);
    }

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        double quantiteLivree = Math.min(quantite, stock.getStock((Feve) produit));
        stock.retirer(produit, quantiteLivree);
        journal.ajouter("Livraison de " + quantiteLivree + " de " + produit + " pour le contrat " + contrat);
        return quantiteLivree;
    }

    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journal);
        return res;
    }
}
