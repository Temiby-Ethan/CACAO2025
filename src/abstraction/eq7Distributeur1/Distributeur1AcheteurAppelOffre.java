package abstraction.eq7Distributeur1;
///Maxime GUY///


import java.util.List;

import abstraction.eqXRomu.appelDOffre.AppelDOffre;
import abstraction.eqXRomu.appelDOffre.IAcheteurAO;
import abstraction.eqXRomu.produits.IProduit;

public class Distributeur1AcheteurAppelOffre extends Distributeur1Acteur  {

	protected Integer cryptogramme;
	private IAcheteurAO identity;
	private List<Double> requiredQuantities;

	public Distributeur1AcheteurAppelOffre(List<Double> requiredQuantities, IAcheteurAO identity) {
		super();
		this.requiredQuantities = requiredQuantities;
		this.identity = identity;
	}

	public AppelDOffre appelOffre(IProduit product, int idProduit){
		AppelDOffre proposition = new AppelDOffre(this.identity, product, this.requiredQuantities.get(idProduit));
		return(proposition);
	}
	
}
