package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur1Acteur implements IActeur {
    
    protected int cryptogramme;

    protected Journal journal;
    protected Stock stock;

    private Variable stockTotal;
    private Variable stockFMQ;
    private Variable stockFBQ;
    private Variable stockFHQ;

    public Producteur1Acteur() {
        this.journal = new Journal(getNom() + " Journal", this);
        this.stock = new Stock();

        // Initialisation du stock
        stock.ajouter(Feve.F_BQ, 1000);
        stock.ajouter(Feve.F_MQ, 1000);
        stock.ajouter(Feve.F_HQ_BE, 1000); //  corrigé ici

        // Initialisation des indicateurs
        this.stockTotal = new Variable("Stock Total", this, stock.getStockTotal());
        this.stockFMQ = new Variable("Stock FMQ", this, stock.getStockFMQ());
        this.stockFBQ = new Variable("Stock FBQ", this, stock.getStockFBQ());
        this.stockFHQ = new Variable("Stock FHQ", this, stock.getStockFHQ()); // 
    }

    public void initialiser() {
        journal.ajouter("Initialisation du producteur");
    }

    @Override
    public String getNom() {
        return "EQ1";
    }
    
    public String toString() {
        return this.getNom();
    }

    public void next() {
        int etape = Filiere.LA_FILIERE.getEtape();
        journal.ajouter("Étape " + etape);

        // Ajout de production fictive chaque étape
        stock.ajouter(Feve.F_BQ, 10);
        stock.ajouter(Feve.F_MQ, 10);
        stock.ajouter(Feve.F_HQ_BE, 10); //  

        // Mise à jour des indicateurs avec les nouvelles valeurs des stocks
        stockTotal.setValeur(this, stock.getStockTotal());
        stockFMQ.setValeur(this, stock.getStockFMQ());
        stockFBQ.setValeur(this, stock.getStockFBQ());
        stockFHQ.setValeur(this, stock.getStockFHQ()); // 

        journal.ajouter("Stock mis à jour :");
        journal.ajouter("→ FMQ : " + stock.getStockFMQ());
        journal.ajouter("→ FBQ : " + stock.getStockFBQ());
        journal.ajouter("→ FHQ : " + stock.getStockFHQ()); // 
    }

    @Override
    public Color getColor() {
        return new Color(243, 165, 175); 
    }

    public String getDescription() {
        return "Producteur de fèves de cacao simples (BQ, MQ, HQ).";
    }

    public List<Variable> getIndicateurs() {
        List<Variable> res = new ArrayList<>();
        res.add(stockTotal); // Indicateur du stock total
        res.add(stockFMQ); // Indicateur du stock de fève moyenne qualité
        res.add(stockFBQ); // Indicateur du stock de fève basse qualité
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
        journal.ajouter("Faillite de " + acteur.getNom());
    }

    public void notificationOperationBancaire(double montant) {
        journal.ajouter("Opération bancaire : " + montant + " €");
    }

    protected double getSolde() {
        return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), cryptogramme);
    }

    public List<String> getNomsFilieresProposees() {
        return new ArrayList<>();
    }

    public Filiere getFiliere(String nom) {
        return Filiere.LA_FILIERE;
    }

    @Override
    public double getQuantiteEnStock(IProduit p, int cryptogramme) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQuantiteEnStock'");
    }


}











