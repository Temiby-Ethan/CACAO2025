package abstraction.eq1Producteur1;

import java.util.HashMap;
import java.util.Map;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Stock {

    private Map<Feve, Double> stocks; // Stock de fèves
    private Journal journalStock;     // Journal des opérations de stock
    private Producteur1ContratCadre producteur1; // Référence au producteur
    private int cryptogramme;

    private Producteur1Parcelle parcelleBQ;
    private Producteur1Parcelle parcelleMQ;
    private Producteur1Parcelle parcelleHQ_E;

    private static final int DUREE_STOCKAGE_MAX = 8;
    private static final double PRIX_STOCKAGE_PAR_STEP = 7.5;

    public Stock(IActeur acteur, int cryptogramme) {
        this.producteur1 = (Producteur1ContratCadre) acteur;
        this.cryptogramme = cryptogramme;

        // Récupérer les parcelles depuis le producteur (qui hérite de Producteur1arbres)
        this.parcelleBQ = producteur1.getParcelle(Feve.F_BQ);
        this.parcelleMQ = producteur1.getParcelle(Feve.F_MQ);
        this.parcelleHQ_E = producteur1.getParcelle(Feve.F_HQ_E);

        // Initialiser le stock
        this.stocks = new HashMap<>();
        this.stocks.put(Feve.F_BQ, parcelleBQ != null ? parcelleBQ.getnombre_feves_total() : 0.0 );
        this.stocks.put(Feve.F_MQ, parcelleMQ != null ? parcelleMQ.getnombre_feves_total() : 0.0);
        this.stocks.put(Feve.F_HQ_E, parcelleHQ_E != null ? parcelleHQ_E.getnombre_feves_total() : 0.0);

        this.journalStock = new Journal("Journal de Stock", this.producteur1);
    }

    public Journal getJournal() {
        return journalStock;
    }

    public void ajouter(IProduit produit, double quantite, int cryptogramme) {
        if (this.cryptogramme != cryptogramme) {
            journalStock.ajouter("Erreur : Cryptogramme invalide pour ajout.");
            return;
        }
        if (!(produit instanceof Feve) || quantite < 0) {
            journalStock.ajouter("Erreur : Produit invalide ou quantité négative.");
            return;
        }

        Feve feve = (Feve) produit;
        double actuel = stocks.getOrDefault(feve, 0.0);
        stocks.put(feve, actuel + quantite);
        journalStock.ajouter("Ajout de " + quantite + "T à " + feve + ". Total : " + (actuel + quantite));
    }

    public boolean retirer(IProduit produit, double quantite, int cryptogramme) {
        if (this.cryptogramme != cryptogramme) {
            journalStock.ajouter("Erreur : Cryptogramme invalide pour retrait.");
            return false;
        }
        if (!(produit instanceof Feve) || quantite < 0) {
            journalStock.ajouter("Erreur : Produit invalide ou retrait négatif.");
            return false;
        }

        Feve feve = (Feve) produit;
        double actuel = stocks.getOrDefault(feve, 0.0);

        if (actuel < quantite) {
            journalStock.ajouter("Erreur : Retrait trop grand de " + feve);
            return false;
        }

        stocks.put(feve, actuel - quantite);
        journalStock.ajouter("Retrait de " + quantite + "T de " + feve + ". Nouveau stock : " + (actuel - quantite));
        return true;
    }

    public double getStock(Feve feve) {
        if (feve == null) throw new IllegalArgumentException("Fève nulle");
        return stocks.getOrDefault(feve, 0.0);
    }

    public void ajouterStock(double quantiteFMQ, double quantiteFBQ, double quantiteFHQ) {
        ajouter(Feve.F_MQ, quantiteFMQ, cryptogramme);
        ajouter(Feve.F_BQ, quantiteFBQ, cryptogramme);
        ajouter(Feve.F_HQ_E, quantiteFHQ, cryptogramme);
    }

    public double getStockTotal() {
        return stocks.values().stream().mapToDouble(Double::doubleValue).sum();
    }

}
