package abstraction.eq1Producteur1;

import java.util.HashMap;
import java.util.Map;

import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Stock {
    private Map<Feve, Double> stocks; // Map pour gérer les stocks de fèves
    private basse_qualite stockBasseQualite;
    private moyenne_qualite stockMoyenneQualite;
    private haute_qualite stockHauteQualite;
    protected Producteur1 Producteur1;

    public Stock() {
        this.stocks = new HashMap<>(); // Initialisation du Map
        this.stockBasseQualite = new basse_qualite();
        this.stockMoyenneQualite = new moyenne_qualite();
        this.stockHauteQualite = new haute_qualite();

        // Initialisation des stocks pour chaque type de fève
        this.stocks.put(Feve.F_BQ, stockBasseQualite.getStock());
        this.stocks.put(Feve.F_MQ, stockMoyenneQualite.getStock());
        this.stocks.put(Feve.F_HQ_E, stockHauteQualite.getStock());
    }

    // Ajouter une quantité pour une fève donnée
    public void ajouter(IProduit produit, double quantite) {
        if (produit instanceof Feve) {
            Feve feve = (Feve) produit;
            double actuel = stocks.getOrDefault(feve, 0.0);
            stocks.put(feve, actuel + quantite);
        }
    }

    // Retirer une quantité si possible, retourne true si réussi
    public boolean retirer(IProduit produit, double quantite) {
        if (produit instanceof Feve) {
            Feve feve = (Feve) produit;
            double actuel = stocks.getOrDefault(feve, 0.0);
            if (actuel >= quantite) {
                stocks.put(feve, actuel - quantite);
                return true;
            }
        }
        return false;
    }

    // Obtenir le stock d'une fève donnée
    public double getStockFMQ() {
        return stockMoyenneQualite.getStock();
    }

    public double getStockFBQ() {
        return stockBasseQualite.getStock();
    }

    public double getStockFHQ() {
        return stockHauteQualite.getStock();
    }

    // Ajouter des quantités pour chaque type de fève
    public void ajouterStock(double quantiteFMQ, double quantiteFBQ, double quantiteFHQ) {
        ajouter(Feve.F_MQ, quantiteFMQ); // Ajoute au stock de fèves moyenne qualité
        ajouter(Feve.F_BQ, quantiteFBQ); // Ajoute au stock de fèves basse qualité
        ajouter(Feve.F_HQ_E, quantiteFHQ); // Ajoute au stock de fèves haute qualité
    }

    // Obtenir le stock total (toutes fèves confondues)
    public double getStockTotal() {
        // Somme des stocks de chaque type de fève 
        return stocks.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
