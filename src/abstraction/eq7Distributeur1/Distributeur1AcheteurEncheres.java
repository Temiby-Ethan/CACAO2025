package abstraction.eq7Distributeur1;
///Maxime GUY///

import java.util.List;

public class Distributeur1AcheteurEncheres extends Distributeur1Acteur  {

	protected Integer cryptogramme;
	private List<Integer> priceProduct;
	private List<Integer> requiredQuantities;
	private List<Integer> succesedSell;

	public Distributeur1AcheteurEncheres(List<Integer> priceProduct, List<Integer> requiredQuantities) {
		super();
		this.priceProduct = priceProduct;
		this.requiredQuantities = requiredQuantities;
	}

	public Boolean tooManyVolume(int product, int volume){
		return(this.requiredQuantities.get(product)<volume);
	}

	public double priceOffered(int product, int volume){
		int price = this.priceProduct.get(product);
		int numberSuccessedSell = this.succesedSell.get(product);
		Boolean soldVolumeTooHigh = tooManyVolume(product, volume);
		if (soldVolumeTooHigh){
			return(price*(90+10*(1-Math.exp(-1*numberSuccessedSell/5))));
		}
	return(price*(1.1+0.02*numberSuccessedSell));
	}
}
