package abstraction.eq7Distributeur1;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.filiere.IActeur;

public class Distributeur1Stock {
    protected Map<ChocolatDeMarque, Variable> stocksChocolats;
    protected List<ChocolatDeMarque> chocolats;

    public Distributeur1Stock(IActeur IAct)
    {
        this.stocksChocolats = new HashMap<>();

        this.chocolats = new ArrayList<ChocolatDeMarque>();
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_HQ_BE, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_HQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_MQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_MQ, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_BQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_BQ, "Hexafridge", 50));

        for (int i=0; i<this.chocolats.size(); i++) {
			this.stocksChocolats.put(chocolats.get(i), new Variable("Stock"+chocolats.get(i).getNom(), IAct, 1000.0));
		}
    }
}
