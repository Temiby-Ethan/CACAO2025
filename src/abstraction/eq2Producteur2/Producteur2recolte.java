//Maxime Philippon
package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Feve;

public class Producteur2recolte extends Producteur2stock {

    private List<Plantation> plantations;
    protected HashMap<Feve,Variable> feve_recolte;
    private Journal Journalterrains;
    private Journal JournalRecolte;

    public Producteur2recolte() {
        super();
        this.plantations = new ArrayList<>();
        this.feve_recolte = new HashMap<Feve, Variable>();
        this.JournalRecolte = new Journal("Journal Recolte Eq2",this);
        this.Journalterrains = new Journal("Journal Terrains Eq2",this);

        for (Feve f : Feve.values()) {
            this.feve_recolte.put(f, new VariableReadOnly(this+"Recolte"+f.toString().substring(2), "<html>Recolte de feves "+f+"</html>",this, 0.0, Double.MAX_VALUE, 0.0));
        }
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
    public void recolteParStep() {
        double Prod_BQ = 0;
        double Prod_BQ_E = 0;
        double Prod_MQ = 0;
        double Prod_MQ_E = 0;
        double Prod_HQ_E = 0;
        double Prod_HQ_BE = 0;
        for (Plantation p : plantations) {
            if (p.prodPlantation() > 0) {
                switch (p.getTypeFeve()) {
                    case F_BQ:
                        Prod_BQ += p.prodPlantation();
                        break;
                    case F_BQ_E:
                        Prod_BQ_E += p.prodPlantation();
                        break;
                    case F_MQ:
                        Prod_MQ += p.prodPlantation();
                        break;
                    case F_MQ_E:
                        Prod_MQ_E += p.prodPlantation();
                        break;
                    case F_HQ_E:
                        Prod_HQ_E += p.prodPlantation();
                        break;
                    case F_HQ_BE:
                        Prod_HQ_BE += p.prodPlantation();
                        break;
                    default:
                        throw new IllegalArgumentException("Type de fève non reconnu !");
                }
            }
        }
        this.feve_recolte.get(Feve.F_BQ).ajouter(this,Prod_BQ,cryptogramme);
        this.feve_recolte.get(Feve.F_BQ_E).ajouter(this,Prod_BQ_E,cryptogramme);
        this.feve_recolte.get(Feve.F_MQ).ajouter(this,Prod_MQ,cryptogramme);
        this.feve_recolte.get(Feve.F_MQ_E).ajouter(this,Prod_MQ_E,cryptogramme);
        this.feve_recolte.get(Feve.F_HQ_E).ajouter(this,Prod_HQ_E,cryptogramme);
        this.feve_recolte.get(Feve.F_HQ_BE).ajouter(this,Prod_HQ_BE,cryptogramme);
        JournalRecolte.ajouter(Filiere.LA_FILIERE.getEtape()+" : Recolte de "+Prod_BQ+" feves de BQ, "+Prod_BQ_E+" feves de BQ_E, "+Prod_MQ+" feves de MQ, "+Prod_MQ_E+" feves de HQ_E, "+Prod_HQ_E+" feves de HQ_E et "+Prod_HQ_BE+" feves de HQ_BE");
    }
 
    /**
     * Retourne le nombre de parcelles par type de feve.
     */
    public void get_nb_plantations() {
        double nb_BQ = 0;
        double nb_BQ_E = 0;
        double nb_MQ = 0;
        double nb_MQ_E = 0;
        double nb_HQ_E = 0;
        double nb_HQ_BE = 0;
        for (Plantation p : plantations) {
            switch (p.getTypeFeve()) {
                case F_BQ:
                    nb_BQ+=p.getParcelles();
                    break;
                case F_BQ_E:
                    nb_BQ_E+=p.getParcelles();
                    break;
                case F_MQ:
                    nb_MQ+=p.getParcelles();
                    break;
                case F_MQ_E:
                    nb_MQ_E+=p.getParcelles();
                    break;
                case F_HQ_E:
                    nb_HQ_E+=p.getParcelles();
                    break;
                case F_HQ_BE:
                    nb_HQ_BE+=p.getParcelles();
                    break;
                default:
                    throw new IllegalArgumentException("Type de fève non reconnu !");
            }
        }
        Journalterrains.ajouter(Filiere.LA_FILIERE.getEtape()+" Notre producteur possede "+nb_BQ+" parcelles de BQ, "+nb_BQ_E+" parcelles de BQ_E, "+nb_MQ+" parcelles de MQ, "+nb_MQ_E+" parcelles de MQ_E, "+nb_HQ_E+" parcelles de HQ_E et "+nb_HQ_BE+" parcelles de HQ_BE");
    }

    public void initialiser() {
        super.initialiser();
        //Ajoute les parcelles de depart
        ajouterPlantation(new Plantation(Feve.F_BQ,10,100));
        ajouterPlantation(new Plantation(Feve.F_BQ_E,10,100));
    }

    public void next() {
        for (Plantation p : plantations) {
            p.add_age();
        }
        recolteParStep();
        get_nb_plantations();
        super.next();
    }

    public List<Journal> getJournaux() {
		List<Journal> res = super.getJournaux();
		res.add(JournalRecolte);
        res.add(Journalterrains);
		return res;
	}
}