package abstraction.eq3Producteur3;

import abstraction.eqXRomu.general.VariablePrivee;
import abstraction.eqXRomu.produits.Feve;
import java.util.HashMap;


public class Producteur3Stock extends Producteur3GestionTerrains {
    //protected VariablePrivee stockFeve;
    protected HashMap<Feve,VariablePrivee> stockFeve = new HashMap<Feve,VariablePrivee>();


    public Producteur3Stock(){
        super();
    }
  

    public void initStock(Feve feve, double stock){
        if (feve==null ||stock<=0) {
			throw new IllegalArgumentException("creation d'une instance de Producteur3Stock avec des arguments non valides");
		}		
		VariablePrivee tmp = new VariablePrivee(this.getNom()+"Stock"+feve, this, 0.0, 1000000000.0,stock);
        tmp.setValeur(this, 1000000.0);
        defiJournal.ajouter(Double.toString(tmp.getHistorique().getValeur()));
        stockFeve.put(feve,tmp);
        getIndicateurs().add(tmp);
    }

    public void ajouterStock(Feve feve,double delta){
        stockFeve.get(feve).ajouter(this,delta);
        /*defiJournal.ajouter("Ajout de " + delta + " au stock de " + feve + ". Nouveau stock : " + stockFeve.get(feve).getValeur());*/
        
    }
    public void retirerStock(Feve feve,double delta){
        stockFeve.get(feve).retirer(this,delta);
        /*defiJournal.ajouter("Retrait de " + delta + " du stock de " + feve + ". Nouveau stock : " + stockFeve.get(feve).getValeur());*/
    }
    /*protected Feve getFeve() {
		return this.feve;
	}
    protected Variable getStock() {
		return this.stockFeve;
	}*/
    
    
}
