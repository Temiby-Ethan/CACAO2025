//Jérôme

package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur2stock extends Producteur2sechage {

    protected HashMap<Feve,Double> prodParStep;
	protected HashMap<Feve,Variable> stock;
	protected Variable stockTotal;
    private Journal JournalStock;

	public Producteur2stock() {

        super();
        this.stock = new HashMap<Feve, Variable>();
		this.prodParStep = new HashMap<Feve, Double>();
        this.JournalStock = new Journal("Journal Stock Eq2",this);

        double totalInitialStock = 0.0;

        for(Feve f : Feve.values()){ // On initialise la prod de chaque fève
            SetProdParStep(f,2000.0); // A MODIFIER
        }
        
        this.stockTotal = new Variable("Stock total", "Quantité totale de fèves en stock", this, totalInitialStock);
        		
        for (Feve f : Feve.values()) {
            double initialStock = prodParStep.get(f) * 6;
            this.stock.put(f, new VariableReadOnly(this+"Stock"+f.toString().substring(2), "<html>Stock de feves "+f+"</html>",this, 0.0, prodParStep.get(f)*24, initialStock));
            totalInitialStock += initialStock;
        }
        SetTotalStock();


    }


    public void initialiser(){
        super.initialiser();

    }

	

    public void next(){

        ProdParStep();
        super.next();


    }

    public void SetTotalStock(){ //Met à jour la valeur du stock total si besoin

        double totalstock = 0.0;
        for(Feve f : Feve.values()){

            totalstock += this.stock.get(f).getValeur(cryptogramme);

        }

        this.stockTotal.setValeur(this,totalstock);

    }

    public void SetProdParStep(Feve f, double prod){  //Permet de modifier la production par next d'une fève en particulier

        this.prodParStep.put(f, prod);

    }


 
	public void AddStock(Feve f, double prod){ //On ajouter un stock d'une fève en particulier
        

        this.stock.get(f).ajouter(this,prod,cryptogramme); // On met à jour le stock de la fève 
        this.stockTotal.ajouter(this,prod,cryptogramme); // On met à jour le stock 
        JournalStock.ajouter(Filiere.LA_FILIERE.getEtape()+" : On ajoute "+ prod+" T de "+f+" au stock");

	}

    public void DeleteStock(Feve f, double prod){ //On enlève le stock d'une fève en particulier

        this.stock.get(f).retirer(this, prod, cryptogramme);
        this.stockTotal.retirer(this,prod,cryptogramme);
        JournalStock.ajouter(Filiere.LA_FILIERE.getEtape()+" : On enlève "+ prod+" T de "+f+" au stock");

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

    public List<Variable> getIndicateurs() { //Modifie correctement les affichages
		List<Variable> res = super.getIndicateurs();
        //System.out.println(this.stock.values());
		res.addAll(this.stock.values());
		res.add(this.stockTotal);
		return res;
	}

    
	public List<Journal> getJournaux() { //Mets à jour les journaux
		List<Journal> res = super.getJournaux();
		res.add(JournalStock);
		return res;
	}

}
