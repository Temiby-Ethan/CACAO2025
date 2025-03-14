//Maxime Philippon
package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur2prod extends Producteur2stock {

    private List<Plantation> plantations;
    protected HashMap<Feve,Double> prod;
    protected HashMap<Feve,Variable> stock_feve_recolte;
    protected int cryptogramme;
    private Journal Journalterrains;
    private Journal JournalProd;

    public Producteur2prod() {
        
        this.plantations = new ArrayList<>();
        this.stock_feve_recolte = new HashMap<Feve, Variable>();
        this.prod = new HashMap<Feve, Double>();
        this.JournalProd = new Journal(this.getNom()+" journal Prod Eq2", this);
    }

    /**
     * Ajoute une plantation à la liste.
     */
    public void ajouterPlantation(Plantation p) {
        plantations.add(p);
    }

    /**
     * Produit des fèves à partir de toutes les plantations.
     */
    public void prodParStep() {
        double Prod_BQ = 0;
        double Prod_MQ = 0;
        double Prod_HQ_E = 0;
        for (Plantation p : plantations) {
            if (p.prodPlantation() > 0) {
                switch (p.getTypeFeve()) {
                    case F_BQ:
                        Prod_BQ += p.prodPlantation();
                    case F_MQ:
                        Prod_MQ += p.prodPlantation();
                    case F_HQ_E:
                        Prod_HQ_E += p.prodPlantation();
                    default:
                        throw new IllegalArgumentException("Type de fève non reconnu !");
                }
            }
        }
        this.prod.put(Feve.F_BQ, Prod_BQ);
        this.prod.put(Feve.F_MQ, Prod_MQ);
        this.prod.put(Feve.F_HQ_E, Prod_HQ_E);
        JournalProd.ajouter(Filiere.LA_FILIERE.getEtape()+" : Production de "+Prod_BQ+" T de BQ, "+Prod_MQ+" T de MQ et "+Prod_HQ_E+" T de HQ_E");
    }
 
    /**
     * Retourne la liste des plantations.
     */
    public List<Plantation> getPlantations() {
        return plantations;
    }

    public void next() {
        for (Plantation p : plantations) {
            p.add_age();
        }
        super.next();
    }
}