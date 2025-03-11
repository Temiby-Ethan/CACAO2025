package abstraction.eq7Distributeur1;
///Maxime GUY///

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.appelDOffre.IAcheteurAO;
import abstraction.eqXRomu.appelDOffre.IVendeurAO;
import abstraction.eqXRomu.contratsCadres.ContratCadre;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.encheres.Enchere;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur1AcheteurContratCadre implements IAcheteurContratCadre  {

	protected Integer cryptogramme;
	private Integer product;
	private List<Double> priceProduct;
	private IAcheteurAO identity;
	private List<Double> requiredQuantities;
	private String name;
	private Color color;
	private List<Double> predictionsVentesPourcentage;
	private HashMap<ChocolatDeMarque,Variable> stock;

	public Distributeur1AcheteurContratCadre(List<Double> predictionsVentesPourcentage, List<Double> priceProduct, List<Double> requiredQuantities, int cryptogramme, int step, IAcheteurAO identity, int product,String name,Color color) {
		super();
		this.predictionsVentesPourcentage = predictionsVentesPourcentage;
		this.product = product;
		this.identity = identity;
		this.priceProduct = priceProduct;
		this.requiredQuantities = requiredQuantities;
		this.cryptogramme = cryptogramme;
		this.name = name;
		this.color = color;
		this.stock = stock;
	}

	    public int getInt(ChocolatDeMarque product){
        int idProduct = 0;
        switch(product.getGamme()){
            case BQ : idProduct=0;
            case MQ : idProduct=2;
            case HQ : idProduct=4;
        }
        if (product.isBio()){
            idProduct++;
        }
        if (product.isEquitable()){
            idProduct++;
        }
        return(idProduct);
    }
	
	public boolean achete(IProduit produit){
		if (produit instanceof ChocolatDeMarque){
			ChocolatDeMarque chocolat = (ChocolatDeMarque) produit;
			return(requiredQuantities.get(getInt(chocolat))>0); 
		}
		return(false);
	}

	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat){
		List<Echeancier> listeEcheancier = contrat.getEcheanciers();
		int tour = listeEcheancier.size();
		ChocolatDeMarque chocolat = (ChocolatDeMarque) contrat.getProduit();
		Echeancier echeancierActuel = contrat.getEcheancier();
		for (int step = echeancierActuel.getStepDebut(); step<=echeancierActuel.getStepFin() ; step++){
			double quantiteDemandee = echeancierActuel.getQuantite(step);
			double quantiteVoulue = requiredQuantities.get(getInt(chocolat));
			if (quantiteDemandee > quantiteVoulue*(1+0.01*tour)){
				echeancierActuel.set(step, quantiteVoulue*(1+0.01*tour));
			}
			if (quantiteDemandee < quantiteVoulue*(1-0.01*tour)){
				echeancierActuel.set(step, quantiteVoulue*(1-0.01*tour));
			}
		}
		return(echeancierActuel);
	}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat){
		List<Double> listePrix = contrat.getListePrix();
		int tour = listePrix.size();
		ChocolatDeMarque chocolat = (ChocolatDeMarque) contrat.getProduit();
		double prixProposé = priceProduct.get(getInt(chocolat))*(0.87+0.04*tour);
		if (tour<6 && listePrix.getLast()>prixProposé){
			return(prixProposé);
		}
		if (listePrix.getLast()<=prixProposé){
			return(listePrix.getLast());
		}
		return(priceProduct.get(getInt(chocolat)));
	}

	public void initialiser(){

	}

	public String getNom(){
		return(this.name);
	}

	public Color getColor(){
		return(this.color);
	}

	public String getDescription(){
		return("Acheteur aux encheres de l'equipe 7");
	}

	public void next(){

	}

	public List<Variable> getIndicateurs(){
		List<Variable> indicateurs = new ArrayList<Variable>();
		return(indicateurs);
	}

	public List<Variable> getParametres(){
		List<Variable> parametres = new ArrayList<Variable>();
		return(parametres);
	}

	public List<Journal> getJournaux(){
		List<Journal> journaux = new ArrayList<Journal>();
		return(journaux);
	}

	public void setCryptogramme(Integer crypto){
		this.cryptogramme = crypto;
	}

	public void notificationFaillite(IActeur acteur){

	}

	public void notificationOperationBancaire(double montant){

	}

	public List<String> getNomsFilieresProposees(){
		List<String> noms = new ArrayList<String>();
		return(noms);
	}

	public Filiere getFiliere(String nom){
		Filiere test = new Filiere(0);
		return(test);
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme ){
		if (this.cryptogramme == cryptogramme){
			if (p instanceof ChocolatDeMarque){
				ChocolatDeMarque chocolat = (ChocolatDeMarque) p;
				return(this.stock.get((chocolat)).getValeur());
			}
			return(0);
		}
		return(0);
	}

	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat){

	}
	
	public void receptionner(IProduit p, double quantiteEnTonnes, ExemplaireContratCadre contrat){

	}
}

