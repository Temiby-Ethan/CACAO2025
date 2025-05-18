package abstraction.eq1Producteur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;

// AMAL MONCER

public class Producteur1arbres extends Producteur1Couts {

    private int nb_arbres_total;
    protected int temps_avant_production; // en steps
    protected int duree_de_vie = 960; // en steps
    protected int besoin_main_doeuvre;
    protected int production_par_arbre;
    protected int nb_feves_par_cabosse;
    protected double poids_feve_par_cabosse_apres_sechage;
    protected int temps_de_pousse;
    protected int temps_de_sechage;
    protected double prix_achat;
    protected int prix_replantation;
    protected Journal journal;
    protected int nombre_hectares;
    protected int nombre_feves;
    protected int nombre_feves_total;
    protected Feve typeFeve;
    protected Producteur1Parcelle parcelleBQ, parcelleMQ, parcelleHQ_E;
    protected Producteur1 producteur1;
    protected int cryptogramme;

    public Producteur1arbres() {
        this.journal = new Journal(getNom() + " - Journal arbres", this);
        this.parcelleBQ = new Producteur1Parcelle(Feve.F_BQ, this);
        this.parcelleBQ.production();
        this.parcelleMQ = new Producteur1Parcelle(Feve.F_MQ, this);
        this.parcelleMQ.production();
        this.parcelleHQ_E = new Producteur1Parcelle(Feve.F_HQ_E, this);
        this.parcelleHQ_E.production();
        this.nb_arbres_total = calculerNbArbresTotal();
    }

    public double getSolde() {
        return Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme);
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

    public HashMap<Integer, Integer> calculerMortArbre() {
        HashMap<Integer, Integer> arbre = new HashMap<>();
        int totalArbres = calculerNbArbresTotal();
        for (int i = 0; i < 40; i++) {
            arbre.put(i, (1 / (40 * 24)) * totalArbres);
        }
        for (int i = 40 * 24; i >= 1; i--) {
            arbre.put(i, arbre.get(i - 1));
        }
        return arbre;
    }

    public int arbresMortsCeStep() {
        return calculerMortArbre().get(0);
    }

    public void replanterArbres(int nbArbresVoulu) {
        if (nbArbresVoulu <= arbresMortsCeStep() && getSolde() > nbArbresVoulu * getPrixAchatParArbre()) {
            parcelleBQ.replanter_arbres(nbArbresVoulu);
            parcelleMQ.replanter_arbres(nbArbresVoulu);
            parcelleHQ_E.replanter_arbres(nbArbresVoulu);
            this.journal.ajouter("Replantation de " + nbArbresVoulu + " arbres.");
        } else {
            this.journal.ajouter("Replantation impossible : solde insuffisant ou nombre d'arbres morts insuffisant.");
        }
    }

    public void miseAJourArbres() {
        HashMap<Integer, Integer> arbre = new HashMap<>();
        List<Integer> arbresQuiProduisent = new ArrayList<>();
        int totalArbres = calculerNbArbresTotal();

        for (int i = 0; i < 40 * 24; i++) {
            arbre.put(i, (1 / (40 * 24)) * totalArbres);
            if (i >= temps_avant_production) {
                arbresQuiProduisent.add(arbre.get(i));
            }
        }
        for (int i = 40 * 24; i >= 1; i--) {
            arbre.put(i, arbre.get(i - 1));
        }

        int arbresMorts = arbre.get(0);
        int arbresVivants = totalArbres - arbresMorts;

        double solde = getSolde();
        
        if ( arbresMorts * getPrixAchatParArbre() < solde && arbresMorts > 0) {
            Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "arbres", arbresMorts * getPrixAchatParArbre());

            replanterArbres(arbresMorts);
            
        } 
        else {
            int nbArbresVoulu = (int) (solde / getPrixAchatParArbre());
            if (nbArbresVoulu > 0){
            Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "on replante un arbre", nbArbresVoulu * getPrixAchatParArbre());

            replanterArbres(nbArbresVoulu);
            }
            else {
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
        return parcelleBQ.prix_replantation(); // Exemple basé sur une parcelle
    }
}