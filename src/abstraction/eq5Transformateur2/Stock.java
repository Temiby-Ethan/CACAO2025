package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import java.util.HashMap;

public class Stock {
    private HashMap<Feve, Variable> stockFeve;
    private HashMap<Chocolat, Variable> stockChoco;
    private HashMap<ChocolatDeMarque, Variable> stockChocoMarque;
    private Variable stockFeveTotal;
    private Variable stockChocoTotal;

    public Stock() {
        this.stockFeve = new HashMap<>();
        this.stockChoco = new HashMap<>();
        this.stockChocoMarque = new HashMap<>();
        
        for (Feve f : Feve.values()) {
            this.stockFeve.put(f, new Variable("Stock Feve " + f, null, 0.0));
        }
        for (Chocolat c : Chocolat.values()) {
            this.stockChoco.put(c, new Variable("Stock Chocolat " + c, null, 0.0));
        }
        
        this.stockFeveTotal = new Variable("Stock Feve Total", null, 0.0);
        this.stockChocoTotal = new Variable("Stock Chocolat Total", null, 0.0);
    }
    
    public void ajouterStock(IProduit produit, double quantite) {
        if (quantite > 0) {
            if (produit instanceof Feve) {
                stockFeve.get(produit).ajouter(null, quantite);
            } else if (produit instanceof Chocolat) {
                stockChoco.get(produit).ajouter(null, quantite);
            } else if (produit instanceof ChocolatDeMarque) {
                stockChocoMarque.putIfAbsent((ChocolatDeMarque) produit, new Variable("Stock Chocolat Marque " + produit, null, 0.0));
                stockChocoMarque.get(produit).ajouter(null, quantite);
            }
        }
    }
    
    public void retirerStock(IProduit produit, double quantite) {
        if (quantite > 0) {
            if (produit instanceof Feve) {
                stockFeve.get(produit).retirer(null, quantite);
            } else if (produit instanceof Chocolat) {
                stockChoco.get(produit).retirer(null, quantite);
            } else if (produit instanceof ChocolatDeMarque) {
                stockChocoMarque.get(produit).retirer(null, quantite);
            }
        }
    }
    
    public double getQuantite(IProduit produit) {
        if (produit instanceof Feve) {
            return stockFeve.get(produit).getValeur();
        } else if (produit instanceof Chocolat) {
            return stockChoco.get(produit).getValeur();
        } else if (produit instanceof ChocolatDeMarque) {
            return stockChocoMarque.getOrDefault(produit, new Variable("Temp", null, 0.0)).getValeur();
        }
        return 0;
    }
}
