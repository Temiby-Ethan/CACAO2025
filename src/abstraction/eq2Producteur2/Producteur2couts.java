//Maxime Philippon
package abstraction.eq2Producteur2;

import java.util.HashMap;

import abstraction.eqXRomu.produits.Feve;

public class Producteur2couts extends Producteur2stock {

    protected HashMap<Feve, Double> cout_unit_t;
    protected HashMap<Feve, Double> prix;
    
    public Producteur2couts() {
        super();
        this.cout_unit_t = new HashMap<Feve, Double>();
        this.prix = new HashMap<Feve, Double>();

        for (Feve f : Feve.values()) {
            this.cout_unit_t.put(f, 0.0);
            this.prix.put(f,0.0);
        }
    }

    public void initialiser() {
        super.initialiser();
    }

    public void next() {
        super.next();
        calcul_cout_unit();
    }

    /*
     * Calcul des couts unitaires de production de chaque tonne de feve
     */
    public void calcul_cout_unit() {
        for (Feve f : Feve.values()) {
            double cout_r = cout_recolte.get(f);
            switch (f) {
                case F_BQ:
                    double quantite_T_BQ = feve_recolte.get(f) * 0.000000753;
                    double cout_unit_BQ = cout_r + 8 * cout_stockage * quantite_T_BQ;
                    cout_unit_t.put(f, cout_unit_BQ / quantite_T_BQ);
                    break;
                case F_BQ_E:
                    double quantite_T_BQ_E = feve_recolte.get(f) * 0.000000753;
                    double cout_unit_BQ_E = cout_r + 8 * cout_stockage * quantite_T_BQ_E; 
                    cout_unit_t.put(f, cout_unit_BQ_E / quantite_T_BQ_E);
                    break;
                case F_MQ:
                    double quantite_T_MQ = feve_recolte.get(f) * 0.00000075;
                    double cout_unit_MQ = cout_r + 8 * cout_stockage * quantite_T_MQ; 
                    cout_unit_t.put(f, cout_unit_MQ / quantite_T_MQ);
                    break;
                case F_MQ_E:
                    double quantite_T_MQ_E = feve_recolte.get(f) * 0.00000075;
                    double cout_unit_MQ_E = cout_r + 8 * cout_stockage * quantite_T_MQ_E;
                    cout_unit_t.put(f, cout_unit_MQ_E / quantite_T_MQ_E);
                    break;
                case F_HQ_E:
                    double quantite_T_HQ_E = feve_recolte.get(f) * 0.000000765;
                    double cout_unit_HQ_E = cout_r + 8 * cout_stockage * quantite_T_HQ_E; 
                    cout_unit_t.put(f, cout_unit_HQ_E / quantite_T_HQ_E);
                    break;
                case F_HQ_BE:
                    double quantite_T_HQ_BE = feve_recolte.get(f) * 0.000000765;
                    double cout_unit_HQ_BE = cout_r + 8 * cout_stockage * quantite_T_HQ_BE; 
                    cout_unit_t.put(f, cout_unit_HQ_BE / quantite_T_HQ_BE);
                    break;
            }
            
        }
    }

    /*
     * Calcul des prix unitaires de chaque tonne de feve
     */
    public void calcul_prix() {
        for (Feve f : Feve.values()) {
            prix.put(f, cout_unit_t.get(f) * 100); //Fixe le prix telle que la marge soit de 20%
        }
    }
}
