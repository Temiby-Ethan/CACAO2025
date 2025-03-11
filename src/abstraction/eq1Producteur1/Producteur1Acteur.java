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
    
	// Adrien BUECHER --> Stocks ; Adam SEBIANE --> journal
	protected Journal journal; // Journal pour enregistrer les étapes
    private Variable stockTotal; // Indicateur du volume total du stock
    private Variable stockFMQ; // Indicateur du stock de fève moyenne qualité
    private Variable stockFBQ; // Indicateur du stock de fève bonne qualité
    private Variable stockFHQ; // Indicateur du stock de fève haute qualité
    

    public Producteur1Acteur() {
		// Adrien BUECHER --> Stocks ; Adam SEBIANE --> journal
		this.journal = new Journal(this.getNom() + " Journal", this);
        this.stockTotal = new Variable("Stock Total", this, 0.0); // Initialisation du stock total à 0
		this.stockFBQ = new Variable("Stock FBQ", this, 0.0); // Initialisation du stock de fève basse qualité à 0
        this.stockFMQ = new Variable("Stock FMQ", this, 0.0); // Initialisation du stock de fève moyenne qualité à 0
        this.stockFHQ = new Variable("Stock FHQ", this, 0.0); // Initialisation du stock de fève haute qualité à 0
    }
    
    public void initialiser() {
		// Adam SEBIANE
        journal.ajouter("Initialisation du producteur");
    }

    public String getNom() {
        return "EQ1";
    }
    
    public String toString() {
        return this.getNom();
    }

    public void next() {
		// Adrien BUECHER --> Étape ; adam SEBIANE --> Journal
        int etape = Filiere.LA_FILIERE.getEtape(); // Récupération du numéro de l'étape
        journal.ajouter("Étape " + etape); // Ajout uniquement du numéro de l'étape dans le journal

        // Mise à jour des stocks de fèves
        double ajoutStock = 10;
        stockFMQ.setValeur(this, stockFMQ.getValeur() + ajoutStock); // Augmentation de 10 du stock FMQ
        stockFBQ.setValeur(this, stockFBQ.getValeur() + ajoutStock); // Augmentation de 10 du stock FBQ
        stockFHQ.setValeur(this, stockFHQ.getValeur() + ajoutStock); // Augmentation de 10 du stock FHQ

        // Mise à jour du stock total en tant que somme des trois stocks
        double totalStock = stockFMQ.getValeur() + stockFBQ.getValeur() + stockFHQ.getValeur();
        stockTotal.setValeur(this, totalStock); // Mise à jour du stock total

        // Vendre 120 tonnes de fève moyenne qualité si le stock le permet
        double quantiteARechercher = 120.0; // Quantité de fève moyenne qualité à vendre

        if (stockFMQ.getValeur() >= quantiteARechercher) {
            // Effectuer la vente
            stockFMQ.setValeur(this, stockFMQ.getValeur() - quantiteARechercher); // Réduire le stock de fève moyenne qualité
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
        res.add(stockTotal); // Ajout de l'indicateur du stock total
        res.add(stockFMQ); // Ajout de l'indicateur du stock de fève moyenne qualité
        res.add(stockFBQ); // Ajout de l'indicateur du stock de fève bonne qualité
        res.add(stockFHQ); // Ajout de l'indicateur du stock de fève haute qualité
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
            return stockTotal.getValeur(); // Renvoie la valeur du stock total
        } else {
            return 0; // Accès refusé
        }
    }
}










