package abstraction.eq1Producteur1;

import java.util.HashMap;
import java.util.Map;

import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Stock {

    private Map<Feve, Double> stocks;

    public Stock() {
        this.stocks = new HashMap<>();

        // Initialisation avec zéro stock pour chaque type connu
        for (Feve f : Feve.values()) {
            stocks.put(f, 0.0);
        }
    }

    // Ajouter une quantité pour une fève donnée
    public void ajouter(IProduit produit, double quantite) {
        if (produit instanceof Feve) {
            Feve f = (Feve) produit;
            double actuel = stocks.getOrDefault(f, 0.0);
            stocks.put(f, actuel + quantite);
        }
    }

    // Retirer une quantité si possible, retourne true si réussi
    public boolean retirer(IProduit produit, double quantite) {
        if (produit instanceof Feve) {
            Feve f = (Feve) produit;
            double actuel = stocks.getOrDefault(f, 0.0);
            if (actuel >= quantite) {
                stocks.put(f, actuel - quantite);
                return true;
            }
        }
        return false;
    }

    // Obtenir la quantité disponible pour une fève
    public double getStock(IProduit produit) {
        if (produit instanceof Feve) {
            return stocks.getOrDefault((Feve) produit, 0.0);
        }
        return 0.0;
    }

    // Obtenir le stock total (toutes fèves confondues)
    public double getStockTotal() {
        return stocks.values().stream().mapToDouble(Double::doubleValue).sum();
    }
   }