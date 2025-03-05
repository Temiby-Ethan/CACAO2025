package abstraction.eq3Producteur3;

import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariablePrivee;
import abstraction.eqXRomu.produits.Feve;
import java.util.HashMap;


public class Producteur3Stock extends Producteur3GestionTerrains {
    //protected VariablePrivee stockFeve;
    protected HashMap<Feve,VariablePrivee> stockFeve = new HashMap<Feve,VariablePrivee>();
    protected VariablePrivee totalStock;


    public Producteur3Stock(){
        super();
        totalStock = new VariablePrivee(this.getNom()+"Stock Total", this, 0.0, 100000000000000.0, 0);
        getIndicateurs().add(totalStock);
    }
  
    //Zoé
    public void initStock(Feve feve, double stock){
        if (feve==null ||stock<=0) {
			throw new IllegalArgumentException("creation d'une instance de Producteur3Stock avec des arguments non valides");
		}		
		VariablePrivee tmp = new VariablePrivee(this.getNom()+"Stock"+feve, this, 0.0, 1000000000.0,stock);
        tmp.setValeur(this, 1000000.0, cryptogramme);
        journal.ajouter(Double.toString(tmp.getHistorique().getValeur()));
        stockFeve.put(feve,tmp);
        getIndicateurs().add(tmp);
    }

    public void calculTotalStock(){
        double total = 0;
        total+= stockFeve.get(Feve.F_BQ).getValeur(cryptogramme);
        total+= stockFeve.get(Feve.F_BQ_E).getValeur(cryptogramme);
        total+= stockFeve.get(Feve.F_MQ).getValeur(cryptogramme);
        total+= stockFeve.get(Feve.F_MQ_E).getValeur(cryptogramme);
        total+= stockFeve.get(Feve.F_HQ_E).getValeur(cryptogramme);
        total+= stockFeve.get(Feve.F_HQ_BE).getValeur(cryptogramme);
        totalStock.setValeur(this, total, cryptogramme);

    }
    
    

    public void ajouterStock(Feve feve,double delta){
        stockFeve.get(feve).ajouter(this,delta);
        journal.ajouter("Ajout de " + delta + " au stock de " + feve + ". Nouveau stock : " + stockFeve.get(feve).getValeur());
        
    }
    public void retirerStock(Feve feve,double delta){
        stockFeve.get(feve).retirer(this,delta);
        journal.ajouter("Retrait de " + delta + " du stock de " + feve + ". Nouveau stock : " + stockFeve.get(feve).getValeur());
    }



    protected VariablePrivee getStock(Feve feve){
        return this.stockFeve.get(feve);
    }
    
    
}
