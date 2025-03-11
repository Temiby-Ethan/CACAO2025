package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.IProduit;


public class Producteur1Acteur implements IActeur {
    
    protected int cryptogramme;
    
    protected Journal journal; // Journal pour enregistrer les étapes
    private Stock stock; // Utilisation de la classe Stock
    
    // Variables pour les indicateurs
    private Variable stockTotal;
    private Variable stockFMQ;
    private Variable stockFBQ;
    private Variable stockFHQ;
    
    public Producteur1Acteur() {
        this.journal = new Journal(this.getNom() + " Journal", this);
        this.stock = new Stock(); // Initialisation du stock
        
        // Initialisation des indicateurs avec les valeurs initiales des stocks
        this.stockTotal = new Variable("Stock Total", this, stock.getStockTotal());
        this.stockFMQ = new Variable("Stock FMQ", this, stock.getStockFMQ());
        this.stockFBQ = new Variable("Stock FBQ", this, stock.getStockFBQ());
        this.stockFHQ = new Variable("Stock FHQ", this, stock.getStockFHQ());
    }
    
    public void initialiser() {
        journal.ajouter("Initialisation du producteur");
    }

    public String getNom() {
        return "EQ1";
    }
    
    public String toString() {
        return this.getNom();
    }

    public void next() {
        int etape = Filiere.LA_FILIERE.getEtape(); // Récupération du numéro de l'étape
        journal.ajouter("Étape " + etape); // Ajout uniquement du numéro de l'étape dans le journal

        // Mise à jour des stocks de fèves
        double ajoutStock = 10;
        stock.ajouterStock(ajoutStock, ajoutStock, ajoutStock); // Ajout de 10 pour chaque type de fève

        // Mise à jour des indicateurs avec les nouvelles valeurs des stocks
        stockTotal.setValeur(this, stock.getStockTotal());
        stockFMQ.setValeur(this, stock.getStockFMQ());
        stockFBQ.setValeur(this, stock.getStockFBQ());
        stockFHQ.setValeur(this, stock.getStockFHQ());

        // Vendre 120 tonnes de fève moyenne qualité si le stock le permet
        double quantiteARechercher = 120.0; // Quantité de fève moyenne qualité à vendre

        if (stock.vendreStockFMQ(quantiteARechercher)) {
            journal.ajouter("Vente de " + quantiteARechercher + " tonnes de fève moyenne qualité en bourse");
        } else {
            journal.ajouter("Stock de fève moyenne qualité insuffisant pour vendre " + quantiteARechercher + " tonnes.");
        }
    }

    public Color getColor() {
        return new Color(243, 165, 175); 
    }

    public String getDescription() {
        return "Bla bla bla";
    }

    public List<Variable> getIndicateurs() {
        List<Variable> res = new ArrayList<>();
        res.add(stockTotal); // Indicateur du stock total
        res.add(stockFMQ); // Indicateur du stock de fève moyenne qualité
        res.add(stockFBQ); // Indicateur du stock de fève bonne qualité
        res.add(stockFHQ); // Indicateur du stock de fève haute qualité
        return res;
    }

    public List<Variable> getParametres() {
        return new ArrayList<>();
    }

    public List<Journal> getJournaux() {
        List<Journal> res = new ArrayList<>();
        res.add(journal);
        return res;
    }

    public void setCryptogramme(Integer crypto) {
        this.cryptogramme = crypto;
    }

    public void notificationFaillite(IActeur acteur) {
        journal.ajouter("Notification de faillite de " + acteur.getNom());
    }

    public void notificationOperationBancaire(double montant) {
        journal.ajouter("Opération bancaire : " + montant);
    }

    protected double getSolde() {
        return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
    }

    public List<String> getNomsFilieresProposees() {
        return new ArrayList<>();
    }

    public Filiere getFiliere(String nom) {
        return Filiere.LA_FILIERE;
    }

    public double getQuantiteEnStock(IProduit p, int cryptogramme) {
        if (this.cryptogramme == cryptogramme) { // Vérification d'accès sécurisé
            return stock.getStockTotal(); // Renvoie la valeur du stock total
        } else {
            return 0; // Accès refusé
        }
    }
}











