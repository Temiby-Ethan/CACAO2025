package abstraction.eq1Producteur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

// AMAL MONCER

public class Producteur1arbres extends Producteur1Bourse {

    protected int nb_arbres_total;
    protected int temps_avant_production; // en steps
    protected int duree_de_vie = 960; // en steps
    protected int besoin_main_doeuvre;
    protected int production_par_arbre;
    protected int nb_feves_par_cabosse;
    protected double poids_feve_par_cabosse_apres_sechage;
    protected double prix_achat;
    protected int prix_replantation;
    protected Journal journal;
    protected int nombre_hectares;
    protected int nombre_feves;
    protected int nombre_feves_total;
    protected Feve typeFeve;
    protected Producteur1 producteur1;
    private HashMap<Integer, Integer> arbreParStep = new HashMap<>();
    private final int dureeCycle = 960; // 40 jours en heures

    public Producteur1arbres() {
        super();
        this.journal = new Journal(getNom() + " - Journal arbres", this);

        // Initialisation des parcelles
        this.parcelleBQ = new Producteur1Parcelle(this, Feve.F_BQ, this);
        this.parcelleMQ = new Producteur1Parcelle(this, Feve.F_MQ, this);
        this.parcelleHQ_E = new Producteur1Parcelle(this, Feve.F_HQ_E, this);

        // Lancer la production initiale sur chaque parcelle si besoin
        this.parcelleBQ.production();
        this.parcelleMQ.production();
        this.parcelleHQ_E.production();

        this.nb_arbres_total = calculerNbArbresTotal();
    }

    public Producteur1Parcelle getParcelle(Feve typeFeve) {
        if (typeFeve == Feve.F_BQ) {
            return parcelleBQ;
        } else if (typeFeve == Feve.F_MQ) {
            return parcelleMQ;
        } else if (typeFeve == Feve.F_HQ_E) {
            return parcelleHQ_E;
        } else {
            return null;
        }
    }

    public List<Integer> getNombreArbresParParcelle() {
        List<Integer> nombreArbres = new ArrayList<>();
        if (parcelleBQ != null && parcelleMQ != null && parcelleHQ_E != null) {
            nombreArbres.add(parcelleBQ.getNombre_arbres());
            nombreArbres.add(parcelleMQ.getNombre_arbres());
            nombreArbres.add(parcelleHQ_E.getNombre_arbres());
        } else {
            throw new IllegalStateException("Une ou plusieurs parcelles ne sont pas initialisées.");
        }
        return nombreArbres;
    }

    public int calculerNbArbresTotal() {
        List<Integer> arbres = getNombreArbresParParcelle();
        int total = 0;
        for (int nbArbres : arbres) {
            total += nbArbres;
        }
        return total;
    }

    public void replanterArbres(int nbArbres) {
        parcelleBQ.replanter_arbres(nbArbres);
    }

    public void miseAJourArbres() {
        int totalArbres = calculerNbArbresTotal();
        int stepActuel = Filiere.LA_FILIERE.getEtape();
        int stepMod = stepActuel % dureeCycle;

        int nbArbresStep = (int) ((1.0 / dureeCycle) * totalArbres);
        arbreParStep.put(stepMod, nbArbresStep);

        int stepMort = (stepMod + 1) % dureeCycle;
        int arbresMorts = arbreParStep.getOrDefault(stepMort, 0);
        int arbresVivants = totalArbres - arbresMorts;

        double solde = getSolde();
        double prixUnitaire = getPrixAchatParArbre();

        if (arbresMorts > 0 && solde >= arbresMorts * prixUnitaire) {
            Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "arbres", arbresMorts * prixUnitaire);
            replanterArbres(arbresMorts);
        } else {
            int nbReplantables = (int) (solde / prixUnitaire);
            if (nbReplantables > 0) {
                Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "replantation partielle", nbReplantables * prixUnitaire);
                replanterArbres(nbReplantables);
            } else {
                this.journal.ajouter("Replantation impossible : solde insuffisant.");
            }
        }

        this.journal.ajouter("Il y a " + arbresMorts + " arbres morts.");
        this.journal.ajouter("Il y a " + arbresVivants + " arbres vivants.");
    }

    public void next() {
        super.next();
        miseAJourArbres();
    }

    @Override
    public List<Journal> getJournaux() {
        List<Journal> res = super.getJournaux();
        res.add(journal);
        return res;
    }

    private double getPrixAchatParArbre() {
        return parcelleBQ.prix_replantation();
    }

    public void ajouterAuStock(Feve feve, double quantite) {
        this.stock.ajouter(feve,quantite,cryptogramme);
    }


} 
