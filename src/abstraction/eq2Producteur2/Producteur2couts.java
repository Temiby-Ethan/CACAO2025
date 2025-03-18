//Maxime Philippon
package abstraction.eq2Producteur2;

public class Producteur2couts extends Producteur2stock {

    private double cout_tot;
    private double cout_unit_F_BQ;
    private double cout_unit_F_BQ_E;
    private double cout_unit_F_MQ;
    private double cout_unit_F_MQ_E;
    private double cout_unit_F_HQ_E;
    private double cout_unit_F_HQ_BE;
    private double prix_F_BQ;
    private double prix_F_BQ_E;
    private double prix_F_MQ;
    private double prix_F_MQ_E;
    private double prix_F_HQ_E;
    private double prix_F_HQ_BE;
    private double cout_stockage;
    
    public Producteur2couts() {
        super();
        this.cout_tot = 0;
        this.cout_unit_F_BQ = 0;
        this.cout_unit_F_BQ_E = 0;
        this.cout_unit_F_MQ = 0;
        this.cout_unit_F_MQ_E = 0;
        this.cout_unit_F_HQ_E = 0;
        this.cout_unit_F_HQ_BE = 0;
        this.prix_F_BQ = 0;
        this.prix_F_BQ_E = 0;
        this.prix_F_MQ = 0;
        this.prix_F_MQ_E = 0;
        this.prix_F_HQ_E = 0;
        this.prix_F_HQ_BE = 0;
        this.cout_stockage = 0;
    }

    public void initialiser() {
        super.initialiser();
    }

    public void next() {
        super.next();
        calculCout();
    }

    public void calculCout() {
        
    }
    
}
