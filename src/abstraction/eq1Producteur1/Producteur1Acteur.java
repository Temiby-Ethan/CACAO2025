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

    protected Stock stock;
    protected Journal journalPrincipal;

    private Variable stockTotal;
    private Variable stockFMQ;
    private Variable stockFBQ;
    private Variable stockFHQ;

    public Producteur1Acteur() {
        this.journalPrincipal = new Journal("Journal EQ1", this);
        this.stock = new Stock();

        // Initialisation du stock avec des valeurs par défaut
        stock.ajouter(Feve.F_BQ, 1000);
        stock.ajouter(Feve.F_MQ, 1000);
        stock.ajouter(Feve.F_HQ_BE, 1000);

        // Initialisation des indicateurs
        this.stockTotal = new Variable("EQ1 Stock Total", this, stock.getStockTotal());
        this.stockFMQ = new Variable("EQ1 Stock FMQ", this, stock.getStock(Feve.F_MQ));
        this.stockFBQ = new Variable("EQ1 Stock FBQ", this, stock.getStock(Feve.F_BQ));
        this.stockFHQ = new Variable("EQ1 Stock FHQ", this, stock.getStock(Feve.F_HQ_BE));
    }

    @Override
    public void initialiser() {
        journalPrincipal.ajouter("Initialisation du producteur EQ1");
    }

    @Override
    public void next() {
        int step = Filiere.LA_FILIERE.getEtape();

        // Simule une production de fèves
        stock.ajouter(Feve.F_BQ, 10);
        stock.ajouter(Feve.F_MQ, 10);
        stock.ajouter(Feve.F_HQ_BE, 10);

        // Mise à jour des indicateurs
        stockTotal.setValeur(this, stock.getStockTotal());
        stockFMQ.setValeur(this, stock.getStock(Feve.F_MQ));
        stockFBQ.setValeur(this, stock.getStock(Feve.F_BQ));
        stockFHQ.setValeur(this, stock.getStock(Feve.F_HQ_BE));

        journalPrincipal.ajouter("Étape " + step + " : production ajoutée et stocks mis à jour.");
    }

    @Override
    public String getNom() {
        return "EQ1";
    }

    @Override
    public String toString() {
        return this.getNom();
    }

    @Override
    public Color getColor() {
        return new Color(243, 165, 175);
    }

    @Override
    public String getDescription() {
        return "Producteur modulaire de fèves EQ1";
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
        res.add(journalPrincipal);
        return res;
    }

    @Override
    public void setCryptogramme(Integer crypto) {
        this.cryptogramme = crypto;
    }

    @Override
    public void notificationFaillite(IActeur acteur) {
        journalPrincipal.ajouter("Faillite de l'acteur : " + acteur.getNom());
    }

    @Override
    public void notificationOperationBancaire(double montant) {
        journalPrincipal.ajouter("Opération bancaire : " + montant + " €");
    }

    protected double getSolde() {
        return Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme);
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
    public double getQuantiteEnStock(IProduit produit, int cryptogramme) {
        if (this.cryptogramme == cryptogramme) {
            return stock.getStock(produit);
        } else {
            return 0.0;
        }
    }

    public Journal getJournalPrincipal() {
        return journalPrincipal;
    }
}
