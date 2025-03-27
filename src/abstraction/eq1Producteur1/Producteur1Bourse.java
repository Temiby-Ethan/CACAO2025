package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.bourseCacao.IVendeurBourse;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

public class Producteur1Bourse extends Producteur1ContratCadre implements IVendeurBourse {

    private Journal journalBourse;


    public Producteur1Bourse() {
        super();
        this.journalBourse = new Journal(getNom() + " - Journal Bourse", this);
    }

    @Override
    public double offre(Feve typeFeve, double prixCourant) {
        // On vend uniquement les fèves de basse qualité (BQ) et moyenne qualité (MQ)
        if (typeFeve.equals(Feve.F_BQ)) {
            double stockBQ = stock.getStockFBQ(); // Stock spécifique pour F_BQ
            double quantiteOfferte = Math.min(stockBQ, 1000.0); // Limite à 1000 tonnes
            journalBourse.ajouter("Étape " + Filiere.LA_FILIERE.getEtape() +
                " : Offre bourse de " + quantiteOfferte + " tonnes de fèves BQ.");
            return quantiteOfferte;
        } else if (typeFeve.equals(Feve.F_MQ)) {
            double stockMQ = stock.getStockFMQ(); // Stock spécifique pour F_MQ
            double quantiteOfferte = Math.min(stockMQ, 1000.0); // Limite à 1000 tonnes
            journalBourse.ajouter("Étape " + Filiere.LA_FILIERE.getEtape() +
                " : Offre bourse de " + quantiteOfferte + " tonnes de fèves MQ.");
            return quantiteOfferte;
        } else {
            // Si le type de fève n'est pas pris en charge, aucune offre n'est faite
            journalBourse.ajouter("Étape " + Filiere.LA_FILIERE.getEtape() +
                " : Aucune offre pour le type de fève " + typeFeve + ".");
            return 0.0;
        }
    }

    @Override
    public double notificationVente(Feve typeFeve, double quantiteVendue, double prixVente) {
        double stockDispo = stock.getStockTotal();
        double quantiteLivree = Math.min(stockDispo, quantiteVendue);
        stock.retirer(typeFeve, quantiteLivree); // méthode ajoutée dans Stock.java
        journalBourse.ajouter("Étape " + Filiere.LA_FILIERE.getEtape() +
            " : VENTE bourse de " + quantiteLivree + " tonnes de " + typeFeve +
            " à " + prixVente + " €/tonne");
        return quantiteLivree;
    }

    @Override
    public void notificationBlackList(int dureeBlacklist) {
        journalBourse.ajouter("Blacklisté pour " + dureeBlacklist + " étapes.");
    }

    @Override
    public void next() {
        super.next();
    }

    @Override
    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journalBourse);
        return res;
    }
}
