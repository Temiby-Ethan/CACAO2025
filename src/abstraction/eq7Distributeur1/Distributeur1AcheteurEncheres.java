package abstraction.eq7Distributeur1;
///Maxime GUY///
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


import abstraction.eqXRomu.encheres.Enchere;
import abstraction.eqXRomu.encheres.IAcheteurAuxEncheres;
import abstraction.eqXRomu.encheres.MiseAuxEncheres;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur1AcheteurEncheres implements IAcheteurAuxEncheres  {

	protected Integer cryptogramme;
	private List<Double> priceProduct;
	private List<Double> requiredQuantities;
	private List<Integer> succesedSell;
	private String name;
	private Color color;
	private List<Double> stock;

	public Distributeur1AcheteurEncheres(List<Double> priceProduct, List<Double> requiredQuantities, String name, Color color, List<Double> stock) {
		super();
		this.priceProduct = priceProduct;
		this.name = name ;
		this.color = color;
		this.requiredQuantities = requiredQuantities;
		this.stock = stock;
	}

	public Boolean tooManyVolume(ChocolatDeMarque product, double volume){
		int idProduct = (int) product.getChocolat().qualite();
		return(this.requiredQuantities.get(idProduct)<volume);
	}

	public double proposerPrix(MiseAuxEncheres encheres){
		IProduit product = encheres.getProduit();
		if (product instanceof ChocolatDeMarque) {
        	ChocolatDeMarque chocolat = (ChocolatDeMarque) product;
			double volume = encheres.getQuantiteT();
			int idProduct = (int) chocolat.getChocolat().qualite();
			double price = this.priceProduct.get(idProduct);
			double wantedquantity = this.requiredQuantities.get(idProduct);
			int numberSuccessedSell = this.succesedSell.get(idProduct);
			if (tooManyVolume(chocolat, volume)){
				return(price*(90+10*(1-Math.exp(-1*numberSuccessedSell/5))*(1-Math.exp((wantedquantity-volume)/1000))));
			}
		return(price*(1.1+0.02*numberSuccessedSell));
		}
		return(0);
	}
	public void initialiser(){

	}

	public void notifierAchatAuxEncheres(Enchere enchereRetenue){

	}
	public void notifierEnchereNonRetenue(Enchere enchereNonRetenue){
		
	};

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
				return(stock.get((int) chocolat.getChocolat().qualite()));
			}
			return(0);
		}
		return(0);
	}
	
}
