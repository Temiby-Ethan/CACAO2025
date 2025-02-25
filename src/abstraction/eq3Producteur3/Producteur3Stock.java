package abstraction.eq3Producteur3;

import abstraction.eqXRomu.general.VariablePrivee;
import abstraction.eqXRomu.produits.Feve;


public class Producteur3Stock extends Producteur3GestionTerrains {
    protected VariablePrivee stockFeve;
	protected Feve feve;


    public Producteur3Stock(){
        super();
    }
  

    public void initStock(Feve feve, double stock){
        if (feve==null ||stock<=0) {
			throw new IllegalArgumentException("creation d'une instance de Producteur3Stock avec des arguments non valides");
		}		
		this.stockFeve=new VariablePrivee(this.getNom()+"Stock"+feve, this, 0.0, 1000000.0,stock);
		this.feve = feve;
    }
    public void ajouterStock(double delta){
        stockFeve.ajouter(this,delta);
        
    }
    public void retirerStock(double delta){
        stockFeve.retirer(this,delta);
    }
    /*protected Feve getFeve() {
		return this.feve;
	}
    protected Variable getStock() {
		return this.stockFeve;
	}*/
    
    
}
