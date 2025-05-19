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

    // Parcelles de fèves
    protected Producteur1Parcelle parcelleBQ;
    protected Producteur1Parcelle parcelleMQ;
    protected Producteur1Parcelle parcelleHQ_E;

    // Indicateurs de stock
    private Variable stockTotal;
    private Variable stockFMQ;
    private Variable stockFBQ;
    private Variable stockFHQ_E;
    private Producteur1arbres arbres;

    // Setter pour le cryptogramme, appelé après création
    @Override
    public void setCryptogramme(Integer crypto) {
        this.cryptogramme = crypto;
        this.stock = new Stock(this, cryptogramme); // Initialisation du stock

    }

    public Producteur1Acteur() {
        this.journal = new Journal(getNom() + " Journal", this);

        // On initialise les indicateurs à partir du stock 
        this.stockTotal = new Variable("Stock Total", this, 0);
        this.stockFMQ = new Variable("Stock FMQ", this, 0);
        this.stockFBQ = new Variable("Stock FBQ", this, 0);
        this.stockFHQ_E = new Variable("Stock FHQ", this, 0);
    }




    // Mise à jour des indicateurs à chaque étape
    public void next() {

        int etape = Filiere.LA_FILIERE.getEtape();
        journal.ajouter("Étape " + etape);

        // Mise à jour des indicateurs avec les valeurs actuelles du stock
        stockTotal.setValeur(this, stock.getStockTotal());
        stockFMQ.setValeur(this, getQuantiteEnStock(Feve.F_MQ,cryptogramme));
        stockFBQ.setValeur(this, getQuantiteEnStock(Feve.F_BQ,cryptogramme));
        stockFHQ_E.setValeur(this, getQuantiteEnStock(Feve.F_HQ_E,cryptogramme));

        // Journalisation des niveaux de stock
        journal.ajouter("Stock mis à jour :");
        journal.ajouter("→ FMQ : " + stock.getStock(Feve.F_MQ));
        journal.ajouter("→ FBQ : " + stock.getStock(Feve.F_BQ));
        journal.ajouter("→ FHQ : " + stock.getStock(Feve.F_HQ_E));
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
        return "Producteur de fèves de cacao simples (BQ, MQ, HQ).";
    }

    @Override
    public List<Variable> getIndicateurs() {
        List<Variable> res = new ArrayList<>();
        res.add(stockTotal);
        res.add(stockFMQ);
        res.add(stockFBQ);
        res.add(stockFHQ_E);
        return res;
    }

    @Override
    public List<Variable> getParametres() {
        return new ArrayList<>();
    }

    @Override
    public void notificationFaillite(IActeur acteur) {
        journal.ajouter("Faillite de " + acteur.getNom());
    }

    @Override
    public void notificationOperationBancaire(double montant) {
        journal.ajouter("Opération bancaire : " + montant + " €");
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
    public double getQuantiteEnStock(IProduit p, int crypto) {
        if (this.cryptogramme == crypto && p instanceof Feve) {
            return stock.getStock((Feve) p);
        }
        return 0.0;
    }

    public List<Journal> getJournaux() {
        List<Journal> res = new ArrayList<>();
        res.add(journal);
        return res;
    }

    @Override
    public void initialiser() {
		
	}

}
