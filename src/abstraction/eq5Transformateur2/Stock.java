package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import java.util.HashMap;


public class Stock extends FabricantChocolatDeMarque{
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
}
