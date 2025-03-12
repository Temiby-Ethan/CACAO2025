package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Transformateur2Acteur implements IActeur {
    protected int cryptogramme;
    protected Journal journal;
    protected Feve feve;
    protected Stock stock;

    public Transformateur2Acteur() {
        this.stock = new Stock();
        this.journal = new Journal("Journal Equipe 5", this);
    }
    
    public void initialiser() {}

    public String getNom() {
        return "EQ5";
    }
    
    public String toString() {
        return this.getNom();
    }

    public void next() {
        int etape = Filiere.LA_FILIERE.getEtape();
        this.journal.ajouter("Etape numéro : " + etape);
        
    }

    public Color getColor() {
        return new Color(165, 235, 195); 
    }

    public String getDescription() {
        return "Bla bla bla";
    }

    public List<Variable> getIndicateurs() {
        List<Variable> res = new ArrayList<>();
        res.add(this.stock.getstockChocoTotal());
        res.add(this.stock.getstockFeveTotal());
		res.add(stock.getstockFeve(Feve.F_MQ));
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

    public void notificationFaillite(IActeur acteur) {}

    public void notificationOperationBancaire(double montant) {}
    
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
        if (this.cryptogramme == cryptogramme) {
            return 0;
        } else {
            return 0;
        }
    }
}
