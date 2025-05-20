// Adrien BUECHER

package abstraction.eq1Producteur1;

import java.util.List;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

public class Producteur1Couts extends Producteur1sechage {

    private Journal journalCouts;

    
    protected int employesBQ = 8; 
    protected int employesMQ = 6;
    protected int employesHQ = 4;
    protected int employesPermanents = 2;

    protected double coutEnfant = 2.0*100;
    protected double coutAdulteNonForme = 5.0*100;
    protected double coutAdulteForme = 12.5*100;

    public Producteur1Couts() {
        super();
        this.journalCouts = new Journal(getNom() + " Journal Coûts", this);
    }



    public double calculerCoutEntretien( Feve feve) {
        switch (feve) {
            case F_BQ:
                return coutAdulteNonForme * 2 + coutEnfant * 6; // on choisit de prendre 2 adultes non formés et 6 enfants
            case F_MQ:
                return coutAdulteNonForme * 2 + coutEnfant * 4; // on choisit de prendre 2 adultes non formés et 4 enfants
            case F_HQ_E:
                return coutAdulteForme * 4; // on choisit de prendre 2 adultes formés et 2 enfants puisqu'il s'agit d'un type équitable
            default:
                return 0.0;

    
    }
}

    // === Coûts ===
    public double calculerCoutEmployes() {
        double cout = calculerCoutEntretien(Feve .F_BQ)+
                      calculerCoutEntretien(Feve.F_MQ) +
                      calculerCoutEntretien(Feve.F_HQ_E) +
                      (coutAdulteNonForme * employesPermanents);
        try {
            Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "Salaires", cout);
            journalCouts.ajouter("Coût des employés payé : " + cout + " € (ligne budgétaire : Salaires)");
        } catch (Exception e) {
            journalCouts.ajouter("Échec du paiement des salaires : " + e.getMessage());
        }
        return cout;
    }

    public double calculerCoutStockage() {
        double cout = coutStockage(); // Appel direct à la méthode de la classe mère
        try {
            if (cout >0){
            Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "Stockage", cout);
            journalCouts.ajouter("Coût de stockage payé : " + cout + " € (ligne budgétaire : Stockage)");
        } 
    }
        catch (Exception e) {
            journalCouts.ajouter("Échec du paiement du stockage : " + e.getMessage());
    }
    return cout;
}


    @Override
    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journalCouts);
        return res;
    }

  
    @Override
    public void next() {
        super.next();
        calculerCoutEmployes();
        calculerCoutStockage();
        stock.nettoyageStock(); 
    }
}

// nous n'avons pas reussi à faire la transistion vers une production équitable sans travail infantile
