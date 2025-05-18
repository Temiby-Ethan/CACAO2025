package abstraction.eq1Producteur1;

import java.util.HashMap;
import java.util.Map;

import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Stock {

    private Map<Feve, Double> stocks; // Map pour gérer les stocks de fèves
    private Journal journalStock; // Journal pour enregistrer les opérations
    private Producteur1ContratCadre producteur1; // Référence au Producteur1
    private int cryptogramme; // Cryptogramme associé à l'acteur
    private Producteur1Parcelle parcelleBQ; // Référence à la parcelle de fèves basse qualité
    private Producteur1Parcelle parcelleMQ; // Référence à la parcelle de fèves moyenne qualité
    private Producteur1Parcelle parcelleHQ_E; // Référence à la parcelle de fèves haute qualité

    public Stock(IActeur acteur, int cryptogramme) {
        this.stocks = new HashMap<>();
        this.producteur1 = (Producteur1ContratCadre) acteur;
        this.cryptogramme = cryptogramme;
        this.stocks.put(Feve.F_BQ, parcelleBQ.getnombre_feves_total());
        this.stocks.put(Feve.F_MQ, parcelleMQ.getnombre_feves_total());
        this.stocks.put(Feve.F_HQ_E, parcelleHQ_E.getnombre_feves_total());
        this.journalStock = new Journal("Journal de Stock", producteur1);
    }

    public int getCryptogramme() {
        return cryptogramme;
    }

    public void setCryptogramme(int cryptogramme) {
        this.cryptogramme = cryptogramme;
    }

    public Journal getJournal() {
        return journalStock;
    }

    // Ajouter une quantité pour une fève donnée
    public void ajouter(IProduit produit, double quantite, int cryptogramme) {
        if (this.cryptogramme != cryptogramme) {
            journalStock.ajouter("Erreur : Cryptogramme invalide pour ajout de stock.");
            return;
        }
        if (produit instanceof Feve) {
            if (quantite < 0) {
                journalStock.ajouter("Erreur : Quantité négative pour " + produit);
                return;
            }
            Feve feve = (Feve) produit;
            double actuel = stocks.getOrDefault(feve, 0.0);
            stocks.put(feve, actuel + quantite);
            journalStock.ajouter("Ajout de " + quantite + " au stock de " + feve + " → Total : " + (actuel + quantite));
        }
    }

    // Retirer une quantité si possible, retourne true si réussi
    public boolean retirer(IProduit produit, double quantite, int cryptogramme) {
        if (this.cryptogramme != cryptogramme) {
            journalStock.ajouter("Erreur : Cryptogramme invalide pour retrait de stock.");
            return false;
        }
        if (produit instanceof Feve) {
            Feve feve = (Feve) produit;
            double actuel = stocks.getOrDefault(feve, 0.0);
            if (quantite < 0) {
                journalStock.ajouter("Erreur : Retrait négatif pour " + produit);
                return false;
            } else if (actuel < quantite) {
                journalStock.ajouter("Erreur : Retrait trop grand pour " + produit);
                return false;
            } else {
                stocks.put(feve, actuel - quantite);
                journalStock.ajouter("Retiré " + quantite + " du stock de " + feve + ". Nouveau stock : " + (actuel - quantite));
                return true;
            }
        }
        return false;
    }

    // Obtenir le stock d'une fève
    public double getStock(Feve feve) {
        if (feve == null) {
            throw new IllegalArgumentException("La fève spécifiée est nulle.");
        }

        double stock = 0.0;

        // Vérifie si le producteur est une instance de Producteur1arbres
        if (producteur1 instanceof Producteur1arbres) {
            Producteur1arbres producteurArbres = (Producteur1arbres) producteur1;

            // Récupère les parcelles et additionne les stocks pour la fève donnée
            if (feve == Feve.F_BQ && producteurArbres.parcelleBQ != null) {
                stock = producteurArbres.parcelleBQ.getnombre_feves_total();
            } else if (feve == Feve.F_MQ && producteurArbres.parcelleMQ != null) {
                stock = producteurArbres.parcelleMQ.getnombre_feves_total();
            } else if (feve == Feve.F_HQ_E && producteurArbres.parcelleHQ_E != null) {
                stock = producteurArbres.parcelleHQ_E.getnombre_feves_total();
            } else {
                return 0.0;
            }

            return stock;
        } else {
            throw new IllegalStateException("Le producteur n'est pas une instance de Producteur1arbres.");
        }
    }

    // Ajouter des quantités pour chaque type de fève
    public void ajouterStock(double quantiteFMQ, double quantiteFBQ, double quantiteFHQ) {
        ajouter(Feve.F_MQ, quantiteFMQ,cryptogramme); // Ajoute au stock de fèves moyenne qualité
        ajouter(Feve.F_BQ, quantiteFBQ,cryptogramme); // Ajoute au stock de fèves basse qualité
        ajouter(Feve.F_HQ_E, quantiteFHQ, cryptogramme); // Ajoute au stock de fèves haute qualité
    }


    // Obtenir le stock total (toutes fèves confondues)
    public double getStockTotal() {
        return stocks.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    
 
}
