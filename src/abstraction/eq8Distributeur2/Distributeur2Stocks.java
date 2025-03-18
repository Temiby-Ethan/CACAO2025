package abstraction.eq8Distributeur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

public class Distributeur2Stocks extends Distributeur2Acteur {
    protected Variable stockTotal;




    public Distributeur2Stocks(){
        super();
        stockTotal = new Variable("Volume total du stock de l'EQ8", "Volume total du stock", this);
    }
    
    public Variable getStockTotal(){
		return stockTotal;
	}

    public void initialiser() {
		
		this.stock_Choco=new HashMap<ChocolatDeMarque,Double>();
		this.nombreMarquesParType=new HashMap<Chocolat,Integer>();
		
		
		chocolats= Filiere.LA_FILIERE.getChocolatsProduits();
		
		for (ChocolatDeMarque cm : chocolats) {
		    Chocolat typeChoco = cm.getChocolat();
		    nombreMarquesParType.put(typeChoco, nombreMarquesParType.getOrDefault(typeChoco, 0) + 1);
	    }
		
		this.journal.ajouter("===== STOCK INITIALE =====");
		for (ChocolatDeMarque cm : chocolats) {
			double stock = 0;
			
			if (cm.getChocolat()==Chocolat.C_MQ_E) {
				stock=12000;
			}
			
			if (cm.getChocolat()==Chocolat.C_HQ_E) {
				stock=12000;
			}
			if (cm.getChocolat()==Chocolat.C_HQ_BE)  {
				stock=12000;
			}
			this.stock_Choco.put(cm, stock);
			this.journal.ajouter(cm+"->"+this.stock_Choco.get(cm));
			this.stockTotal.ajouter(this, stock, cryptogramme);
		}
		this.journal.ajouter("");
	}

    public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.add(getStockTotal());
		return res;
	}
}
