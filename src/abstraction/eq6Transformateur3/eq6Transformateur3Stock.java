package abstraction.eq6Transformateur3;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.produits.Feve;
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

    public eq6Transformateur3Stock(Transformateur3Acteur acteur, Journal journal, List<IProduit> listProduit, HashMap<IProduit, Variable> indicateurs){
    //eq6Transformateur3Stock(IActeur acteur,Journal journalStock, List<IProduit> listProduit, HashMap<IProduit, Variable> dicoIndicateur){
        // Récupère le journal des stocks
        this.journalStock = journal;
        this.monActeur = acteur;
        this.dicoIndicateur = indicateurs;
        
        // Initialise le stockProduit
        this.stockProduit = new HashMap<IProduit, Double>();
        
        // Initialise les quantitée pour chaque produit
        this.stockTotal = 0.0;
        for (IProduit prod : listProduit) {
			stockProduit.put(prod, 0.0);
		}
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
            this.journalStock.ajouter("ERROR_stock : ajout avec quantité négative ou nulle");
        }
    }

    protected void display() {
		journalStock.ajouter("STOCK DE FEVES DE CACAO");
		for (IProduit prod : this.stockProduit.keySet()) {
			int nbspace = 10-prod.toString().length();
			String space = "";
			for(int i=0;i<nbspace;i++){
				space=space+".";
			}
			this.journalStock.ajouter(prod+space+" : "+this.stockProduit.get(prod));
		}
		journalStock.ajouter("");
	}
}
