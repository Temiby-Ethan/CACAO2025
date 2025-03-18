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

public class Producteur2recolte extends Producteur2Acteur {

    private List<Plantation> plantations;
    protected HashMap<Feve, Double> feve_recolte;
    private Journal Journalterrains;
    private Journal JournalRecolte;

    public Producteur2recolte() {
        super();
        this.plantations = new ArrayList<>();
        this.feve_recolte = new HashMap<Feve, Double>();
        this.JournalRecolte = new Journal("Journal Recolte Eq2",this);
        this.Journalterrains = new Journal("Journal Terrains Eq2",this);

        for (Feve f : Feve.values()) {
            this.feve_recolte.put(f, 0.0);
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
        this.feve_recolte.put(Feve.F_BQ,Prod_BQ);
        this.feve_recolte.put(Feve.F_BQ_E,Prod_BQ_E);
        this.feve_recolte.put(Feve.F_MQ,Prod_MQ);
        this.feve_recolte.put(Feve.F_MQ_E,Prod_MQ_E);
        this.feve_recolte.put(Feve.F_HQ_E,Prod_HQ_E);
        this.feve_recolte.put(Feve.F_HQ_BE,Prod_HQ_BE);
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
        for (int i = 1; i < 40; i++) {
            int age_init = 1 + (i * 24);
            ajouterPlantation(new Plantation(Feve.F_BQ,257,age_init));
            ajouterPlantation(new Plantation(Feve.F_BQ_E,24,age_init));
            ajouterPlantation(new Plantation(Feve.F_MQ,94,age_init));
            ajouterPlantation(new Plantation(Feve.F_MQ_E,24,age_init));
            ajouterPlantation(new Plantation(Feve.F_HQ_BE,24,age_init));
        }
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