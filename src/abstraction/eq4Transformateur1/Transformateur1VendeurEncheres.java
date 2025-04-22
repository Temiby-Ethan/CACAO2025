package abstraction.eq4Transformateur1;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.encheres.Enchere;
import abstraction.eqXRomu.encheres.IVendeurAuxEncheres;
import abstraction.eqXRomu.encheres.SuperviseurVentesAuxEncheres;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque; 

/**
 * @author YAOU Reda
 */

public class Transformateur1VendeurEncheres extends Transformateur1VendeurAppelDoffre implements IVendeurAuxEncheres {

    protected SuperviseurVentesAuxEncheres superviseur;
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected String marque;
	protected HashMap<Chocolat, Double> prixTmin = new HashMap<Chocolat, Double>();


    public Transformateur1VendeurEncheres() {	
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.marque = "LimDt";
	}





	public void initialiser() {

		super.initialiser();

		for (Chocolat choco : lesChocolats) {
			this.prixTmin.put(choco, prixTChocoBase.get(choco)*1.8);
			journalTransactions.ajouter(Color.white, Color.darkGray,  "E " +choco+" : PrixMin : " + this.prixTmin.get(choco));
		}
		journalTransactions.ajouter("\n");

		this.superviseur = (SuperviseurVentesAuxEncheres)(Filiere.LA_FILIERE.getActeur("Sup.Encheres"));
	}


	protected ChocolatDeMarque getChocolatDeMarque(Chocolat choco) {
		return new ChocolatDeMarque(choco, marque,  (int)(Filiere.LA_FILIERE.getParametre("pourcentage min cacao "+choco.getGamme()).getValeur()));
	}


	public void next() {

		super.next();

		if (Filiere.LA_FILIERE.getEtape()>=1) {
			for (Chocolat choco : lesChocolats){
				if (this.getQuantiteEnStock(getChocolatDeMarque(choco), this.cryptogramme)>200) {
					//On veut vendre 10% de notre stock de ce chocolat de marque
					Enchere retenue = superviseur.vendreAuxEncheres(this, cryptogramme, getChocolatDeMarque(choco), this.getQuantiteEnStock(getChocolatDeMarque(choco), this.cryptogramme)*0.1);
					if (retenue!=null) {
	
	
						this.retirerDuStock(retenue.getProduit(),  retenue.getMiseAuxEncheres().getQuantiteT(), this.cryptogramme);
						this.qttSortantesTransactions.put(choco, this.qttSortantesTransactions.get(choco)+retenue.getMiseAuxEncheres().getQuantiteT());
							
						journalTransactions.ajouter(Romu.COLOR_LLGRAY, Color.darkGray, "--> E "+choco+" : vente de "+retenue.getMiseAuxEncheres().getQuantiteT()+" T de à "+retenue.getAcheteur().getNom());
						this.journalTransactions.ajouter("\n");
					} else {
						journalTransactions.ajouter(Color.pink, Color.darkGray, "--> E "+choco+" : pas d'offre retenue");
						this.journalTransactions.ajouter("\n");
					}
				}
			}
		}
	}

	@Override
	public Enchere choisir(List<Enchere> propositions) {
		this.journalTransactions.ajouter(Color.white, Color.darkGray, "E : encheres: "+propositions);
		if (propositions == null) {
			this.journalTransactions.ajouter(Color.pink, Color.darkGray, "--> E : pas de propositions");
			return null;
		} else {
			Enchere retenue = propositions.get(0);
			Chocolat choco = ((ChocolatDeMarque) retenue.getProduit()).getChocolat();
			if (retenue.getPrixTonne()>this.prixTmin.get(choco)) {
				this.journalTransactions.ajouter(Color.white, Color.darkGray, "E : je choisis "+retenue);
				return retenue;
			} else {
				this.journalTransactions.ajouter(Color.pink, Color.darkGray, "--> E "+choco+": je ne retiens rien");
				this.journalTransactions.ajouter("\n");
				return null;
			}
		}		
	}
}


