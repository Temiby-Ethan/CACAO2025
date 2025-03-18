//Jérôme

package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;


import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur2stock extends Producteur2sechage {

    protected HashMap<Feve,Double> prodParStep;
	protected HashMap<Feve,Queue<Stock>> stock;
    protected HashMap<Feve,Variable> stockvar;
	protected Variable stockTotal;
    private Journal JournalStock;
    double cout_stockage = 7.5;


	public Producteur2stock() {

        super();
        this.stock = new HashMap<Feve, Queue<Stock>>();
        this.stockvar = new HashMap<Feve,Variable>();
		this.prodParStep = new HashMap<Feve, Double>();
        this.JournalStock = new Journal("Journal Stock Eq2",this);
        

        double totalInitialStock = 0.0;




        for(Feve f : Feve.values()){ // On initialise la prod de chaque fève à 0 car on a rien de séché au step 1
            SetProdParStep(f,0); 

        }
        
        this.stockTotal = new Variable("Stock total", "Quantité totale de fèves en stock", this, totalInitialStock);
        		
       for (Feve f : Feve.values()) {

            double initialStock = 12000; //On commence avec 12000T de chaque fèves
            this.stockvar.put(f, new VariableReadOnly(this+"Stock"+f.toString().substring(2), "<html>Stock de feves "+f+"</html>",this, 0.0, prodParStep.get(f)*24, initialStock));
            
            Queue<Stock> initStock = new LinkedList<>();
            Stock stock_f = new Stock(0, initialStock);
            initStock.add(stock_f);
            this.stock.put(f, initStock);
            totalInitialStock += initialStock;
        }
        SetTotalStock();


    }




    public void initialiser(){
        super.initialiser();

    }

	

    public void next(){
        
        super.next();
        UpdateProd();
        ProdParStep();
        Check();
        TaxeStockage();
        

    }

    class Stock {

        private int next_a;
        private double tonnes;

        public Stock(int next_a, double tonnes){
            this.next_a = next_a;
            this.tonnes = tonnes;
        }

        public int getNext_a(){
            return this.next_a;
        }

        public double getTonnes(){
            return this.tonnes;

        }

        public void setTonnes(double new_t){
            this.tonnes = new_t;
            
        }
    }

    public void SetTotalStock(){ //Met à jour la valeur du stock total si besoin

        double totalstock = 0.0;
        for(Feve f : Feve.values()){

            Queue<Stock> actualQueue = this.stock.get(f);
            double nb = 0;
            for (Stock feves : actualQueue){
                
                nb += feves.getTonnes();

            }


            totalstock += nb;

        }

        this.stockTotal.setValeur(this,totalstock);

    }


    public void SetProdParStep(Feve f, double prod){  //Permet de modifier la production par next d'une fève en particulier

        this.prodParStep.put(f, prod);

    }


    public void UpdateProd(){ //Récupère les fèves sechées et les envoie dans le stock

        for(Feve f : Feve.values()){

            double feve_seche = fevesSeches.get(f);
            this.prodParStep.put(f,feve_seche);

        }
    }
 
	public void AddStock(Feve f, double prod){ //On ajouter un stock d'une fève en particulier

        this.stockvar.get(f).ajouter(this, prod, cryptogramme);

        Stock stock = new Stock(Filiere.LA_FILIERE.getEtape(),prod);

        this.stock.get(f).offer(stock); // On met à jour le stock de la fève 
        this.stockTotal.ajouter(this,prod,cryptogramme); // On met à jour le stock 
        JournalStock.ajouter(Filiere.LA_FILIERE.getEtape()+" : On ajoute "+ prod+" T de "+f+" au stock");

	}

    public void DeleteStock(Feve f, double prod){ //On enlève le stock d'une fève en particulier

        this.stockvar.get(f).retirer(this, prod, cryptogramme);
        this.stockTotal.retirer(this,prod,cryptogramme);
        JournalStock.ajouter(Filiere.LA_FILIERE.getEtape()+" : On enlève "+ prod+" T de "+f+" au stock");

        Queue<Stock> actualStocks = stock.get(f);
        boolean fait = false;
        while(!fait){

            Stock premierStock = actualStocks.peek();
            if (premierStock == null){
                JournalStock.ajouter(Filiere.LA_FILIERE.getEtape()+" On essaie d'enlever plus de "+f+" qu'on en a de disponible !!");
            }else{
                if(premierStock.getTonnes() < prod){

                prod = prod - premierStock.getTonnes();
                actualStocks.poll();

                }else{

                    premierStock.setTonnes(premierStock.getTonnes() - prod);
                    fait = true;
                }
            }
        }



    }

    public void Check(){

        for (Feve f : Feve.values()){

            Queue<Stock> fileStock = this.stock.get(f);
            Stock premierStock = fileStock.peek();
            double next_a = premierStock.getNext_a();
            double tonnes = premierStock.getTonnes();

            if(Filiere.LA_FILIERE.getEtape() - next_a > 8 ){
                fileStock.poll();
                JournalStock.ajouter("Suppresion de "+tonnes+"T de "+f+" car date de stockage dépassées");
                this.stockvar.get(f).retirer(this, tonnes,cryptogramme);
                this.stockTotal.retirer(this,tonnes,cryptogramme);
            }



        }

    }

    public void ProdParStep(){  // On produit pour un next

        JournalStock.ajouter(Filiere.LA_FILIERE.getEtape()+" : Ajout du à la production");
        for(Feve f : Feve.values()){ 
            AddStock(f,this.prodParStep.get(f));
        }

    }

    public double getQuantiteEnStock(IProduit p, int cryptogramme) { // Permet d'avoir la quantité totale en stock
		return super.getQuantiteEnStock(p, cryptogramme);
	}

    public void TaxeStockage(){

        double tonnes = this.stockTotal.getValeur(cryptogramme);
        Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "Stockage", tonnes*cout_stockage);
        JournalBanque.ajouter("On paie "+tonnes*cout_stockage+" à la banque due au stockage");


    }

    public List<Variable> getIndicateurs() { //Modifie correctement les affichages
		List<Variable> res = super.getIndicateurs();

        res.addAll(this.stockvar.values());
		res.add(this.stockTotal);
		return res;
	}

    
	public List<Journal> getJournaux() { //Mets à jour les journaux
		List<Journal> res = super.getJournaux();
		res.add(JournalStock);
		return res;
	}

}
