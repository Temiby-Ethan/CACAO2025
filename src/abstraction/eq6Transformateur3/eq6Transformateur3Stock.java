// @author Florian Malveau
package abstraction.eq6Transformateur3;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.filiere.IActeur;

public class eq6Transformateur3Stock {
    private double stockTotal;
    private HashMap<IProduit, Double> stockProduit; 
    private HashMap<IProduit, Variable> dicoIndicateur;
    private Journal journalStock;
    private IActeur monActeur;
    private String nomProduit;

    public eq6Transformateur3Stock(Transformateur3Acteur acteur, Journal journal, String nomProduit, double initial_value, List<IProduit> listProduit, HashMap<IProduit, Variable> indicateurs){
        
        // Récupère les variables associés au stock
        this.journalStock = journal;
        this.monActeur = acteur;
        this.dicoIndicateur = indicateurs;
        this.nomProduit = nomProduit;
        
        // Initialise le stockProduit
        this.stockProduit = new HashMap<IProduit, Double>();
        
        // Initialise les quantitée pour chaque produit
        this.stockTotal = 0.0;
        for (IProduit prod : listProduit) {
			stockProduit.put(prod, initial_value);
            dicoIndicateur.get(prod).setValeur(monActeur, initial_value);
		}

        journalStock.ajouter("Initialisation du stock de "+this.nomProduit);
    }

    public double getQuantityOf(IProduit prod){
        if(stockProduit.containsKey(prod)){
            return stockProduit.get(prod);
        }
        else{
            return 0.0;
        }
    }

    public void addToStock(IProduit prod, double quantity){
        if(quantity>0.0){
            stockProduit.put(prod, stockProduit.get(prod)+quantity);
            this.stockTotal += quantity;
            
            //Mise à jour indicateur
            dicoIndicateur.get(prod).setValeur(monActeur, stockProduit.get(prod));

        }
        else {
            this.journalStock.ajouter("ERROR_stock_add : ajout avec quantité négative ou nulle");
        }
    }

    public void remove(IProduit prod, double quantity){
        if(quantity>0.0){
            //Si la quantité demandée est supérieur à celle disponible en stock
            if(stockProduit.get(prod)-quantity < 0.0){
                this.journalStock.ajouter("ERROR_stock_rmv : stock négatif");
                //this.stockTotal -= stockProduit.get(prod); //Retire le stock restant
                //stockProduit.put(prod, stockProduit.get(prod)+quantity);
            }
            else{
                stockProduit.put(prod, stockProduit.get(prod)-quantity);
                this.stockTotal -= quantity;
            }
            
            //Mise à jour indicateur
            dicoIndicateur.get(prod).setValeur(monActeur, stockProduit.get(prod));

        }
        else {
            this.journalStock.ajouter("ERROR_stock_rmv : ajout avec quantité négative ou nulle");
        }
    }

    protected void display() {
        if (nomProduit=="fèves"){
            journalStock.ajouter("STOCK DE FEVES DE CACAO (en t)");
        }
        else{
            journalStock.ajouter("STOCK DE CHOCOLAT (en t)");
        }
		
		for (IProduit prod : this.stockProduit.keySet()) {
			int nbspace = 20-prod.toString().length();
			String space = "";
			for(int i=0;i<nbspace;i++){
				space=space+".";
			}
			this.journalStock.ajouter(prod+space+" : "+this.stockProduit.get(prod));
		}
		journalStock.ajouter("");
	}
}
