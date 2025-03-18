package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transformateur2Acteur implements IActeur {
    protected HashMap<Chocolat, Variable> stockChoco;
    protected HashMap<Feve, Variable> stockFeve;
    protected HashMap<ChocolatDeMarque, Variable> stockChocoMarque;
    protected int cryptogramme;
    protected Journal journal;
    protected Variable stockChocoTotal;
    protected Variable stockFeveTotal;
    protected Feve feve;

    public Transformateur2Acteur() {
        this.stockChoco = new HashMap<Chocolat,Variable>();
        this.stockFeve = new HashMap<Feve,Variable>();
        
        for (Chocolat c : Chocolat.values()) {
            this.stockChoco.put(c, new VariableReadOnly("stockChoco " + c.toString(), this,0.0));
        }

        for (Feve f : Feve.values()) {
            this.stockFeve.put(f, new VariableReadOnly("stockFeve " + f.toString(), this,0.0));
        }

        this.journal = new Journal("Journal Equipe 5", this);
        this.stockChocoTotal = new Variable("EQ5StockChocolat", this, 0.0);
        this.stockFeveTotal = new Variable("EQ5StockFeve", this, 0.0);
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
        // Mise à jour du stock total de chocolat
        stockChocoTotal.setValeur(this, 0.0);
        for (Chocolat c : Chocolat.values()) {
                this.stockChocoTotal.ajouter(this, stockChoco.get(c).getValeur(), cryptogramme);
            }
        

        // Mise à jour du stock total de fèves
        stockFeveTotal.setValeur(this, 0.0);
        for (Feve f : Feve.values()) {
                this.stockFeveTotal.ajouter(this, stockFeve.get(f).getValeur(), cryptogramme);
            }
		
        
        
    }

    public Color getColor() {
        return new Color(165, 235, 195); 
    }

    public String getDescription() {
        return "Bla bla bla";
    }

    public List<Variable> getIndicateurs() {
        List<Variable> res = new ArrayList<>();
        res.add(stockChocoTotal);
        res.add(stockFeveTotal);
		res.add(stockFeve.get(Feve.F_MQ));
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
