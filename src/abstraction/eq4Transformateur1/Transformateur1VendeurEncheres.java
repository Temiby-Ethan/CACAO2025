package abstraction.eq4Transformateur1;

import java.util.List;

import abstraction.eqXRomu.encheres.Enchere;
import abstraction.eqXRomu.encheres.IVendeurAuxEncheres;
import abstraction.eqXRomu.encheres.SuperviseurVentesAuxEncheres;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

/**
 * @author YAOU Reda
 */

public class Transformateur1VendeurEncheres extends Transformateur1VendeurAppelDoffre implements IVendeurAuxEncheres {

    protected SuperviseurVentesAuxEncheres superviseur;
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Variable stock; // Ce sera stockChocoMarqueBQ, BQ_E ....
	protected Chocolat choco;
	protected String marque;
	protected double prixMin;

    public Transformateur1VendeurEncheres() {	
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.choco = Chocolat.C_BQ;
		this.marque = "LimDt";
		this.prixMin = 2400.0;
		this.stock=new Variable(this.getNom()+"Stock"+choco, this, 0.0, 1000000000.0, 500);
	}

	public void initialiser() {
		this.superviseur = (SuperviseurVentesAuxEncheres)(Filiere.LA_FILIERE.getActeur("Sup.Encheres"));
		journalTransactions.ajouter("PrixMin== " + this.prixMin);
	}

	protected ChocolatDeMarque getChocolatDeMarque() {
		return new ChocolatDeMarque(choco,marque,  (int)(Filiere.LA_FILIERE.getParametre("pourcentage min cacao "+choco.getGamme()).getValeur()));
	}

	public void next() {
		journalTransactions.ajouter("Etape="+Filiere.LA_FILIERE.getEtape());
		if (Filiere.LA_FILIERE.getEtape()>=1) {
			if (this.stock.getValeur()>200) {
				Enchere retenue = superviseur.vendreAuxEncheres(this, cryptogramme, getChocolatDeMarque(), 200.0);
				if (retenue!=null) {
					this.stock.setValeur(this, this.stock.getValeur()-retenue.getMiseAuxEncheres().getQuantiteT());
					journalTransactions.ajouter("vente de "+retenue.getMiseAuxEncheres().getQuantiteT()+" T a "+retenue.getAcheteur().getNom());
				} else {
					journalTransactions.ajouter("pas d'offre retenue");
				}
			}
		}
	}

	@Override
	public Enchere choisir(List<Enchere> propositions) {
		this.journalTransactions.ajouter("encheres : "+propositions);
		if (propositions == null) {
			return null;
		} else {
			Enchere retenue = propositions.get(0);
			if (retenue.getPrixTonne()>this.prixMin) {
				this.journal.ajouter("  --> je choisis "+retenue);
				return retenue;
			} else {
				this.journal.ajouter("  --> je ne retiens rien");
				return null;
			}
		}		
	}
}


