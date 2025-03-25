package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.produits.Feve;

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
        this.stockFMQ = new Variable("Stock FMQ", this, stock.getStock(Feve.F_MQ));
        this.stockFBQ = new Variable("Stock FBQ", this, stock.getStock(Feve.F_BQ));
        this.stockFHQ = new Variable("Stock FHQ", this, stock.getStock(Feve.F_HQ_BE)); // 
    }

    @Override
    public void initialiser() {
        journal.ajouter("Initialisation du producteur");
    }

    @Override
    public String getNom() {
        return "EQ1";
    }

    @Override
    public void next() {
        int etape = Filiere.LA_FILIERE.getEtape();
        journal.ajouter("Étape " + etape);

        // Ajout de production fictive chaque étape
        stock.ajouter(Feve.F_BQ, 10);
        stock.ajouter(Feve.F_MQ, 10);
        stock.ajouter(Feve.F_HQ_BE, 10); //  

        // Mise à jour des indicateurs
        stockTotal.setValeur(this, stock.getStockTotal());
        stockFMQ.setValeur(this, stock.getStock(Feve.F_MQ));
        stockFBQ.setValeur(this, stock.getStock(Feve.F_BQ));
        stockFHQ.setValeur(this, stock.getStock(Feve.F_HQ_BE)); // 

        journal.ajouter("Stock mis à jour :");
        journal.ajouter("→ FMQ : " + stock.getStock(Feve.F_MQ));
        journal.ajouter("→ FBQ : " + stock.getStock(Feve.F_BQ));
        journal.ajouter("→ FHQ : " + stock.getStock(Feve.F_HQ_BE)); // 
    }

    @Override
    public Color getColor() {
        return new Color(243, 165, 175);
    }

    @Override
    public String getDescription() {
        return "Producteur de fèves de cacao simples (BQ, MQ, HQ).";
    }

    @Override
    public List<Variable> getIndicateurs() {
        List<Variable> res = new ArrayList<>();
        res.add(stockTotal);
        res.add(stockFMQ);
        res.add(stockFBQ);
        res.add(stockFHQ);
        return res;
    }

    @Override
    public List<Variable> getParametres() {
        return new ArrayList<>();
    }

    @Override
    public List<Journal> getJournaux() {
        List<Journal> res = new ArrayList<>();
        res.add(journal);
        return res;
    }

    @Override
    public void setCryptogramme(Integer crypto) {
        this.cryptogramme = crypto;
    }

    @Override
    public void notificationFaillite(IActeur acteur) {
        journal.ajouter("Faillite de " + acteur.getNom());
    }

    @Override
    public void notificationOperationBancaire(double montant) {
        journal.ajouter("Opération bancaire : " + montant);
    }

    protected double getSolde() {
        return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), cryptogramme);
    }

    @Override
    public List<String> getNomsFilieresProposees() {
        return new ArrayList<>();
    }

    @Override
    public Filiere getFiliere(String nom) {
        return Filiere.LA_FILIERE;
    }

    @Override
    public double getQuantiteEnStock(IProduit p, int cryptogramme) {
        return this.cryptogramme == cryptogramme ? stock.getStock(p) : 0;
    }
}
