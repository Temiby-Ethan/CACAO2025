package abstraction.eq7Distributeur1;
///Maxime GUY///

import java.util.List;

public class Distributeur1AcheteurEncheres extends Distributeur1Acteur  {

	protected Integer cryptogramme;
	private List<Double> priceProduct;
	private List<Double> requiredQuantities;
	private List<Integer> succesedSell;

	public Distributeur1AcheteurEncheres(List<Double> priceProduct, List<Double> requiredQuantities) {
		super();
		this.priceProduct = priceProduct;
		this.requiredQuantities = requiredQuantities;
	}

	public Boolean tooManyVolume(int product, double volume){
		return(this.requiredQuantities.get(product)<volume);
	}

	public double proposerPrix(int product, double volume){
		double price = this.priceProduct.get(product);
		double wantedquantity = this.requiredQuantities.get(product);
		int numberSuccessedSell = this.succesedSell.get(product);
		Boolean soldVolumeTooHigh = tooManyVolume(product, volume);
		if (soldVolumeTooHigh){
			return(price*(90+10*(1-Math.exp(-1*numberSuccessedSell/5))*(1-Math.exp((wantedquantity-volume)/1000))));
		}
	return(price*(1.1+0.02*numberSuccessedSell));
	}
}
