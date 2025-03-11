package abstraction.eq1Producteur1;

public class Stock {
    private double stockFMQ; // Stock de fève de moyenne qualité
    private double stockFBQ; // Stock de fève de bonne qualité
    private double stockFHQ; // Stock de fève de haute qualité

    // Constructeur qui initialise les stocks à zéro
    public Stock() {
        this.stockFMQ = 0.0;
        this.stockFBQ = 0.0;
        this.stockFHQ = 0.0;
    }

    // Méthode pour ajouter des fèves à chaque type de stock
    public void ajouterStock(double ajoutFMQ, double ajoutFBQ, double ajoutFHQ) {
        this.stockFMQ += ajoutFMQ;
        this.stockFBQ += ajoutFBQ;
        this.stockFHQ += ajoutFHQ;
    }

    // Méthode pour retirer des fèves de moyenne qualité (FMQ)
    public boolean vendreStockFMQ(double quantite) {
        if (this.stockFMQ >= quantite) {
            this.stockFMQ -= quantite;
            return true;
        }
        return false;
    }

    // Méthode pour calculer et retourner le stock total
    public double getStockTotal() {
        return this.stockFMQ + this.stockFBQ + this.stockFHQ;
    }

    // Getters pour accéder aux stocks de chaque type de fève
    public double getStockFMQ() {
        return stockFMQ;
    }

    public double getStockFBQ() {
        return stockFBQ;
    }

    public double getStockFHQ() {
        return stockFHQ;
    }
}

