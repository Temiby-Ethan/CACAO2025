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
        double quantiteMax = 0.25 * stockDispo; // Limite à 25% du stock disponible

        Echeancier echeancierPropose = contrat.getEcheancier();

        // Vérification de la durée minimale (8 steps)
        int duree = echeancierPropose.getNbEcheances();
        if (duree < 8) {
            journal.ajouter("Erreur : Durée de l'échéancier inférieure à 8 steps.");
            return null;
        }

        // Vérification de la quantité totale minimale (100 tonnes)
        double quantiteTotale = echeancierPropose.getQuantiteTotale();
        if (quantiteTotale < 100) {
            journal.ajouter("Erreur : Quantité totale inférieure à 100 tonnes.");
            return null;
        }

        // Vérification de la quantité minimale par période (M / (10 × D))
        double quantiteMinParPeriode = quantiteTotale / (10 * duree);
        for (int step = echeancierPropose.getStepDebut(); step <= echeancierPropose.getStepFin(); step++) {
            double quantiteStep = echeancierPropose.getQuantite(step);
            if (quantiteStep < quantiteMinParPeriode) {
                journal.ajouter("Erreur : Quantité par période inférieure à la limite minimale (" + quantiteMinParPeriode + ").");
                return null;
            }
        }

        // Si l'échéancier proposé dépasse la quantité maximale disponible
        if (quantiteTotale > quantiteMax) {
            Echeancier contreProp = new Echeancier(echeancierPropose.getStepDebut());
            double quantiteRestante = quantiteMax;

            for (int step = echeancierPropose.getStepDebut(); step <= echeancierPropose.getStepFin(); step++) {
                double q = echeancierPropose.getQuantite(step);

                // Répartition de la quantité maximale
                double quantitePourEtape = Math.min(q, quantiteRestante / (echeancierPropose.getNbEcheances() - step + 1));
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

        if (produit.equals(Feve.F_BQ)) return 1200.0;
        if (produit.equals(Feve.F_MQ)) return 1800.0;
        if (produit.equals(Feve.F_HQ_BE)) return 2500.0;

        return 1.0;
    }

    @Override
<<<<<<< HEAD
    /**
	 * Methode appelee par le SuperviseurVentesContratCadre apres une contreproposition
	 * de prix different de la part de l'acheteur, afin de connaitre la contreproposition
	 * de prix du vendeur.
	 * @param contrat
	 * @return Retourne un nombre inferieur ou egal a 0.0 si le vendeur souhaite mettre fin
	 * aux negociation en renoncant a ce contrat. Retourne le prix actuel a la tonne du 
	 * contrat (contrat.getPrix()) si le vendeur est d'accord avec ce prix.
	 * Sinon, retourne une contreproposition de prix.
	 */
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
    IProduit produit = contrat.getProduit();

    // Vérification du type de produit
    if (!(produit instanceof Feve)) {
        journal.ajouter("Erreur : Produit non reconnu pour la contre-proposition de prix.");
        return 0.0; // Met fin aux négociations si le produit n'est pas une fève
=======
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        double prixPropose = contrat.getPrix();
        double contreProposition = prixPropose * 1.10; // 10% de plus que la proposition de l'acheteur
        journal.ajouter("Contre-proposition de prix : " + contreProposition + " au lieu de " + prixPropose);
        return contreProposition;
>>>>>>> c4e34404128b8b51534d51a5c7f1b279ff89ee4a
    }

    // Vérification des conditions de l'échéancier
    Echeancier echeancier = contrat.getEcheancier();
    double quantiteTotale = echeancier.getQuantiteTotale();
    int duree = echeancier.getNbEcheances();

    // Condition 1 : Durée minimale (8 steps)
    if (duree < 8) {
        journal.ajouter("Erreur : Durée de l'échéancier inférieure à 8 steps.");
        return 0.0; // Met fin aux négociations
    }

    // Condition 2 : Quantité totale minimale (100 tonnes)
    if (quantiteTotale < 100) {
        journal.ajouter("Erreur : Quantité totale inférieure à 100 tonnes.");
        return 0.0; // Met fin aux négociations
    }

    // Condition 3 : Quantité minimale par période (M / (10 × D))
    double quantiteMinParPeriode = quantiteTotale / (10 * duree);
    for (int step = echeancier.getStepDebut(); step <= echeancier.getStepFin(); step++) {
        double quantiteStep = echeancier.getQuantite(step);
        if (quantiteStep < quantiteMinParPeriode) {
            journal.ajouter("Erreur : Quantité par période inférieure à la limite minimale (" + quantiteMinParPeriode + ").");
            return 0.0; // Met fin aux négociations
        }
    }

    // Liste des prix proposés dans le contrat
    List<Double> prixHistorique = contrat.getListePrix();
    double prixInitial = prixHistorique.get(0); // Premier prix proposé
    double dernierPrix = prixHistorique.get(prixHistorique.size() - 1); // Dernier prix proposé

    // Si le dernier prix est proche du prix initial (>= 99.5% du prix initial), on accepte
    if (dernierPrix >= 0.995 * prixInitial) {
        journal.ajouter("Contre-proposition : accepte le prix demandé (" + dernierPrix + ").");
        return dernierPrix;
    }

    // Calcul de la probabilité d'accepter en fonction de l'écart avec le prix initial
    int pourcentageAcceptation = (int) (100 * Math.pow((dernierPrix / prixInitial), prixHistorique.size()));
    Random random = new Random();
    int alea = random.nextInt(100); // Génère un entier entre 0 et 99

    if (alea < pourcentageAcceptation) {
        // Une fois sur 5, on accepte même si le prix est loin
        if (new Random().nextInt(100) < 20) { // 20% de chance d'accepter
            journal.ajouter("Contre-proposition : accepte le prix demandé (" + dernierPrix + ").");
            return dernierPrix;
        } else {
            // Sinon, on propose une moyenne entre le dernier prix et l'avant-dernier prix
            double contreProposition = (prixHistorique.get(prixHistorique.size() - 2) + dernierPrix) / 2.0;
            journal.ajouter("Contre-proposition : propose une moyenne (" + contreProposition + ") contre " + dernierPrix + ".");
            return contreProposition;
        }
    } else {
        // Si on refuse, on propose un prix légèrement supérieur au prix initial (par exemple, +5%)
        double contreProposition = prixInitial * 1.05;
        journal.ajouter("Contre-proposition : propose un prix supérieur au prix initial (" + contreProposition + ") contre " + dernierPrix + ".");
        return contreProposition;
    }
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
