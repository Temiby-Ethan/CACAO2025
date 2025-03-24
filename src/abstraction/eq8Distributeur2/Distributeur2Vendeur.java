/**
	 * @author tidzzz 
	 */

package abstraction.eq8Distributeur2;

import java.util.HashMap;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IDistributeurChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.clients.ClientFinal;

import abstraction.eqXRomu.general.Journal;





public class Distributeur2Vendeur extends Distributeur2Acteur implements IDistributeurChocolatDeMarque {
    
    
    protected double capaciteDeVente;
	protected  HashMap<ChocolatDeMarque, Double> ListPrix;
	protected String[] marques;
	protected Journal journalVente;

	protected HashMap<String,Double> Coefficient;
	protected LinkedList<String> equipe;
	protected HashMap<ChocolatDeMarque,Integer> chocoVendu;
	protected HashMap<ChocolatDeMarque,Integer> aVendu;


	public Distributeur2Vendeur() {
		super();
		this.capaciteDeVente=120000.0;  //capacite de vente par step
		this.ListPrix = new HashMap<ChocolatDeMarque, Double>();
		this.marques = new String[chocolats.size()];
		this.journalVente= new Journal (this.getNom() + " journal des ventes", this);
		
		
		this.equipe = new LinkedList<String>();
		this.chocoVendu = new HashMap<ChocolatDeMarque,Integer>();
		this.aVendu = new HashMap<ChocolatDeMarque,Integer>();
	}


public void setPrix(ChocolatDeMarque choco) {
	if (Filiere.LA_FILIERE.getEtape()<1) {
			
			
		if (choco.getChocolat() == Chocolat.C_MQ_E) {
			ListPrix.put(choco, (double) 10000);
		}
		
	
		if (choco.getChocolat() == Chocolat.C_HQ_E) {
			ListPrix.put(choco, (double) 22000);
		}
		if (choco.getChocolat() == Chocolat.C_HQ_BE) {
			ListPrix.put(choco, (double) 30000);
		}
	} 
	}




    public double prix(ChocolatDeMarque choco){
        if (ListPrix.containsKey(choco)) {
			return ListPrix.get(choco);
		} 
		else { 
			return 0;
		}
    }

    public double quantiteEnVenteTG(ChocolatDeMarque choco, int crypto){
        if (crypto!=this.cryptogramme
			) {
			
			return 0.0;
		} 
		else {return 0.0;}
    }
    
    public double quantiteEnVente(ChocolatDeMarque choco, int crypto){
        if (crypto!=this.cryptogramme || !chocolats.contains(choco)) {
			journalVente.ajouter("Quelqu'un essaye de me pirater !");
			return 0.0;
		} 
		else {
			
			if (choco.toString().contains("C_MQ_E")) {
				double x = (capaciteDeVente*0.30);
				return Math.max(Math.min(x , this.getQuantiteEnStock(choco,crypto)),0.0);
			}
			
			if (choco.toString().contains("C_HQ_BE")) {
				double x = (capaciteDeVente*0.30);
				return Math.max(Math.min(x , this.getQuantiteEnStock(choco,crypto)),0.0);
			}
			if (choco.toString().contains("C_HQ_E")) {
				double x = (capaciteDeVente*0.40);
				return Math.max(Math.min(x , this.getQuantiteEnStock(choco,crypto)),0.0);
			}
			
		}
		return 0.0;
	}

    public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant, int crypto) {
		int pos= (chocolats.indexOf(choco));
		if (pos>=0) {
			stock_Choco.put(choco, this.getQuantiteEnStock(choco,crypto) - quantite) ;
			stockTotal.retirer(this, quantite, cryptogramme);
			this.aVendu.replace(choco, 1);
		}
		journalVente.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_PURPLE,client.getNom()+" a acheté "+quantite+"kg de "+choco+" pour "+montant+" d'euros ");

	}


    public void notificationRayonVide(ChocolatDeMarque choco, int crypto){
        journalVente.ajouter(" Aie... j'aurais du mettre davantage de " + choco.getNom() + " en vente");
    }


	public List<Journal> getJournaux() {
		List<Journal> jour = super.getJournaux();
		jour.add(journalVente);
		return jour;
	}

}
