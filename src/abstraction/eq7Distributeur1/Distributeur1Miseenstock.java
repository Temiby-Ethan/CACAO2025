package abstraction.eq7Distributeur1;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import abstraction.eqXRomu.acteurs.DistributeurXActeur;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IDistributeurChocolatDeMarque;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;

/* @Ethan */
public class Distributeur1Miseenstock extends Distributeur1 {
    private int etape;
    protected Integer cryptogramme;
    private Map<ChocolatDeMarque,Variable> stockVenteChocolat;

    public Distributeur1Miseenstock(){
        super();
        this.etape = Filiere.LA_FILIERE.getEtape();
        //Toute cette initialisation sera à refaire plus tard
        stockVenteChocolat.put(ChocolatDeMarque.C_HQ_BE, new Variable("7stockVenteHQBE", this)); // CHOCOLAT HAUTE QUALITE BIO EQUITABLE
        stockVenteChocolat.put(ChocolatDeMarque.C_HQ_E, new Variable("7stockVenteHQE", this));  // CHOCOLAT HAUTE QUALITE EQUITABLE
        stockVenteChocolat.put(ChocolatDeMarque.C_MQ_E, new Variable("7stockVenteMQE", this));  // CHOCOLAT MOYENNE QUALITE EQUITABLE
        stockVenteChocolat.put(ChocolatDeMarque.C_MQ, new Variable("7stockVenteMQ", this));   // CHOCOLAT MOYENNE QUALITE (NI BIO NI EQUITABLE)
        stockVenteChocolat.put(ChocolatDeMarque.C_BQ_E, new Variable("7stockVenteBQE", this));  // CHOCOLAT BASSE QUALITE EQUITABLE
        stockVenteChocolat.put(ChocolatDeMarque.C_BQ, new Variable("7stockVenteBQ", this));   // CHOCOLAT BASSE QUALITE (NI BIO NI EQUITABLE)

    }

    public Boolean stockdisponible(ChocolatDeMarque produit){
        return(this.stockChocolats.get(produit).getValeur() > 100000);
    }

    public double QuantiteMiseEnVente(ChocolatDeMarque choco, int crypto) {
		if (this.cryptogramme==crypto && stockdisponible(choco)==true) {
            this.stockVenteChocolat.get(choco).setValeur(this, this.stockVenteChocolat.get(choco).getValeur()+ 100000);
			return this.stockVenteChocolat.get(choco).getValeur()/10;
		} else {
			return 0.0;
		}
	}

    public double restock (ChocolatDeMarque choco, int crypto){
        if (etape != Filiere.LA_FILIERE.getEtape()) {
            etape = Filiere.LA_FILIERE.getEtape();
            return QuantiteMiseEnVente(choco, crypto);
        } else {
            return 0.0;
        }
    }
}
