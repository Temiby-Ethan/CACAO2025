package abstraction.eq6Transformateur3;
// @author Henri Roth & Florian Malveau
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IFabricantChocolatDeMarque;
import abstraction.eqXRomu.general.Variable;

public class Transformateur3Fabriquant extends Transformateur3Marques implements IFabricantChocolatDeMarque{
    
    //Gestion des chocolats de marque
    private List<ChocolatDeMarque> chocolatDeMarques;
	protected HashMap<IProduit, Variable> dicoIndicateurChoco;
    private ChocolatDeMarque fraud;
    private ChocolatDeMarque hypo;
    private ChocolatDeMarque arna;
    private ChocolatDeMarque bollo; 

    //Gestion de la production de chocolat
    private double nbOuvrier = 85400;
    private double nbMachine = 128;
    
    private double capacite_machine = 10e7; //tablette/step
    private double capacite_ouvrier = 15000; // tab/step

    private double coutIngredient = 450.0; // €/tonnes
    private double salaireOuvrier = 4500.0; // €
    private double coutStockage;

    private double productionMax = nbMachine*capacite_machine;

    //Demande de production
    protected HashMap<IProduit, Double> DemandeProdChoco; //Demande pour chaque choco en tonnes
    

    public Transformateur3Fabriquant(){
        super();

        //Liste des chocolat de marque (pour matcher avec l'interface)
        this.chocolatDeMarques = new ArrayList<ChocolatDeMarque>();
        this.fraud = new ChocolatDeMarque(Chocolat.C_BQ, "Fraudolat", 30);
        this.hypo = new ChocolatDeMarque(Chocolat.C_HQ_E, "Hypocritolat", 100);
        this.arna =  new ChocolatDeMarque(Chocolat.C_MQ, "Arnaquolat", 50);
        this.bollo = new ChocolatDeMarque(Chocolat.C_BQ_E, "Bollorolat", 30);
        this.chocolatDeMarques.add(fraud);
        this.chocolatDeMarques.add(hypo);
        this.chocolatDeMarques.add(arna);
        this.chocolatDeMarques.add(bollo);

        //Liste de produit
        super.lesChocolats = new ArrayList<IProduit>();
        super.lesChocolats.add(fraud);
        super.lesChocolats.add(hypo);
        super.lesChocolats.add(arna);
        super.lesChocolats.add(bollo);

        //Dico indicateur choco
		this.dicoIndicateurChoco = new HashMap<IProduit, Variable>();
        this.dicoIndicateurChoco.put(fraud,super.eq6_Q_Fraudo);
        this.dicoIndicateurChoco.put(hypo,super.eq6_Q_Hypo);
        this.dicoIndicateurChoco.put(arna,super.eq6_Q_Arna);
        this.dicoIndicateurChoco.put(bollo,super.eq6_Q_Bollo);

        //Création du stock de chocolat
		super.stockChoco = new Transformateur3Stock(this, super.journalStock, "chocolat", 200.0, super.lesChocolats, this.dicoIndicateurChoco);
    
        //Initialisation de la demande
        this.DemandeProdChoco = new HashMap<IProduit, Double>();
        this.DemandeProdChoco.put(fraud,productionMax/4);
        this.DemandeProdChoco.put(hypo,productionMax/4);
        this.DemandeProdChoco.put(arna,productionMax/4);
        this.DemandeProdChoco.put(bollo,productionMax/4);
    }

    public void initialiser(){
        super.initialiser();
        coutStockage = 4*Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur();
    }

    public void next(){
        super.next();
        super.jdb.ajouter("NEXT - FABRIQUANT");
    }

    public List<ChocolatDeMarque> getChocolatsProduits(){
        return this.chocolatDeMarques;
    }

    public void payerIngredient(double quantity){
        double prixIngredient = this.coutIngredient*quantity;
        super.LaBanque.payerCout(LaBanque, cryptogramme, "Coût ingrédient additionnels", prixIngredient);
    }

    public void produceChocolate(ChocolatDeMarque choco, double quantity){
        //Récupération de la liste des fèves triées dans l'ordre décroissant de qualité
        List<IProduit> FevesSorted = super.stockFeves.getListProduitSorted();
        
        //Récupération des caractéristique du chocolat à produire
        boolean feveIsEquitable = choco.isEquitable();
        boolean feveIsBio = choco.isBio();

        // Étape 1 : Détermine les fèves utilisable pour la production
        int indexLastFeve=-1;

        //Si chocolat est Hypocritolat - C_HQ_E
        if(choco.getGamme()==Gamme.HQ){
            //Indice de la dernière fève compatible pour faire ce chocolat
            indexLastFeve = 1;
        }
        //Si chocolat est Arnaquolat - C_MQ
        else if(choco.getGamme()==Gamme.MQ){
            //Indice de la dernière fève compatible pour faire ce chocolat
            indexLastFeve = 3;
        }
        //Si chocolat est Bollorolat - C_BQ_E ou Fraudolat C_BQ 
        else if(choco.getGamme()==Gamme.BQ){
            //Indice de la dernière fève compatible pour faire ce chocolat
            indexLastFeve = 5;
        }

        //Quantité de chocolat à produire
        double quantityToProduce = this.DemandeProdChoco.get(choco);

        //Étape 2 : Production
        //Énumération des fèves en allant de la plus basse qualité vers la plus haute
        for(int i=indexLastFeve; 0<=i; i--){
            Feve feveUtilisee  = (Feve)FevesSorted.get(i);
            //On ne produit du chocolat équitable qu'avec des fèves équitables et inversement
            if(feveUtilisee.isBio()==feveIsBio && feveUtilisee.isEquitable()==feveIsBio){
                int pourcentageCacao = choco.getPourcentageCacao();
                double quantityFeve = super.stockFeves.getQuantityOf(feveUtilisee);
                double quantityFeveDemandee = quantityToProduce*pourcentageCacao/100;

                //Si on dispose d'assez de fèves en stock => on produit
                if(quantityFeve > quantityFeveDemandee){
                    double quantityIngredient = quantityToProduce*(1-pourcentageCacao)/100;
                    
                    this.stockFeves.remove(feveUtilisee, quantityFeve);
                    this.payerIngredient(quantityIngredient);


                    super.jdb.ajouter("Production de "+choco.getNom());
                }
                else{
                    super.jdb.ajouter("ERROR "+choco.getNom()+" : demande de production supérieure au stock");
                }


            }

        }

    }
}
