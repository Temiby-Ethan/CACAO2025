package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import java.util.HashMap;
import java.util.List;
import javax.crypto.spec.OAEPParameterSpec;
import abstraction.eqXRomu.filiere.Banque;


public class DecisionsActeur extends ProcessChoco{
    
    private HashMap<Feve, Double> proportion; // proportion de chaque chocolat dans la production totale
    private double objectifChangementProportion;
    

    public DecisionsActeur(){
        super();
        this.proportion = new HashMap<Feve, Double>();
        this.proportion.put(Feve.F_MQ, 0.4);
        this.proportion.put(Feve.F_MQ_E, 0.4);
        this.proportion.put(Feve.F_HQ_E, 0.1);
        this.proportion.put(Feve.F_HQ_BE, 0.1);

        this.objectifChangementProportion = 10.0 *10000000000.00; // augmentation de 10% de notre capitale de départ en euros
    }

    public double getProductionTotale(){
        return Math.min(super.getCapaciteProductionMachines() ,super.capaciteProductionEmployes());
    }
    
    public void ChangementProportion(){
        if(this.proportion.get(Chocolat.C_MQ)>1){
            this.proportion.put(Feve.F_MQ, this.proportion.get(Chocolat.C_MQ)-0.05);
            this.proportion.put(Feve.F_MQ_E, this.proportion.get(Chocolat.C_MQ_E)-0.05);
            this.proportion.put(Feve.F_HQ_E, this.proportion.get(Chocolat.C_HQ_E)+0.05);
            this.proportion.put(Feve.F_HQ_BE, this.proportion.get(Chocolat.C_HQ_BE)+0.05);

        }
    }

    public boolean objecifChangementProportion(){
        if(Filiere.LA_FILIERE.getBanque().getSolde(this,super.cryptogramme)>=this.objectifChangementProportion){
            this.objectifChangementProportion=this.objectifChangementProportion*1.1;
            return true;
        }
        return false;
    }

    public void next(){
        super.next();
    }



    
}
