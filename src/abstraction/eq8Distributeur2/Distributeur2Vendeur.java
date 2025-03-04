package abstraction.eq8Distributeur2;

import java.util.HashMap;

import abstraction.eqXRomu.filiere.IDistributeurChocolatDeMarque;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

import java.util.LinkedList;



import abstraction.eqXRomu.clients.ClientFinal;

import abstraction.eqXRomu.general.Journal;





public class Distributeur2Vendeur extends Distributeur2Acteur implements IDistributeurChocolatDeMarque {
    
    
    protected double capaciteDeVente;
	protected  HashMap<ChocolatDeMarque, Double> ListPrix;
	protected String[] marques;
	protected Journal journalVente;
	protected HashMap<String,Double> Fidele;
	protected HashMap<String,Double> Coefficient;
	protected LinkedList<String> equipe;
	protected HashMap<ChocolatDeMarque,Integer> chocoVendu;
	protected HashMap<ChocolatDeMarque,Integer> aVendu;
	protected LinkedList<ChocolatDeMarque> banni;






    public double prix(ChocolatDeMarque choco){
        if (ListPrix.containsKey(choco)) {
			return ListPrix.get(choco);
		} 
		else { 
			return 0;
		}
    }

    public double quantiteEnVente(ChocolatDeMarque choco, int crypto){
        if (crypto!=this.cryptogramme
			) {
			
			return 0.0;
		} 
		else {return 0.0;}
    }
    
    public double quantiteEnVenteTG(ChocolatDeMarque choco, int crypto){
        if (crypto!=this.cryptogramme
			) {
			
			return 0.0;
		} 
		else {return 0.0;}
    }

    public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant, int crypto) {
		
    }

    public void notificationRayonVide(ChocolatDeMarque choco, int crypto){
        journalVente.ajouter(" Aie... j'aurais du mettre davantage de "+choco.getNom()+" en vente");
    }

}
