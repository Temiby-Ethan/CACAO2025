package abstraction.eq1Producteur1;

import java.util.*;

import abstraction.eqXRomu.contratsCadres.*;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

// ADAM SEBIANE
//


public class Producteur1ContratCadre extends Producteur1Acteur implements IVendeurContratCadre {

    private Producteur1 vendeur; // Référence au Producteur1 principal
    private List<ExemplaireContratCadre> contrats;
    private Journal journal; // Journal pour enregistrer les opérations

    public Producteur1ContratCadre() {
        
        // Initialisation du journal avant de l'utiliser
        this.journal = new Journal(getNom() + " - Journal Contrat Cadre",this);

        // Initialisation du stock avec le journal
        //this.stock = new Stock(this);

        this.contrats = new ArrayList<>();
    } 


    @Override
    public boolean vend(IProduit produit) {
        return produit instanceof Feve;
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        IProduit produit = contrat.getProduit();

        // Vérification du type de produit
        if (!(produit instanceof Feve)) {
            journal.ajouter("Erreur : Produit non reconnu pour la contre-proposition.");
            return null;
        }
        Feve feve = (Feve) produit;

        // Vérification du stock disponible
        double stockDispo = stock.getStock(feve);
        double quantiteMax = 0.25 * stockDispo;

        Echeancier echeancierPropose = contrat.getEcheancier();

        // Si l'échéancier proposé dépasse la quantité maximale
        if (echeancierPropose.getQuantiteTotale() > quantiteMax) {
            Echeancier contreProp = new Echeancier(echeancierPropose.getStepDebut());
            double quantiteRestante = quantiteMax;

            for (int step = echeancierPropose.getStepDebut(); step <= echeancierPropose.getStepFin(); step++) {
                double q = echeancierPropose.getQuantite(step);

                // Vérification des quantités négatives
                if (q < 0) {
                    journal.ajouter("Erreur : Quantité négative détectée dans la contre-proposition.");
                    return null;
                }

                // Répartition de la quantité maximale
                double quantitePourEtape = Math.min(q, quantiteRestante);
                contreProp.ajouter(quantitePourEtape);
                quantiteRestante -= quantitePourEtape;
            }

            journal.ajouter("Contre-proposition envoyée : " + contreProp);
            return contreProp;
        }

        // Si l'échéancier proposé est acceptable
        journal.ajouter("Échéancier proposé accepté : " + echeancierPropose);
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
        double prixPropose = contrat.getPrix();
        double contreProposition = prixPropose * 1.10; // 10% de plus que la proposition de l'acheteur
        journal.ajouter("Contre-proposition de prix : " + contreProposition + " au lieu de " + prixPropose);
        return contreProposition;
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
