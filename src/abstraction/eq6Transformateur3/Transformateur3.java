package abstraction.eq6Transformateur3;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.bourseCacao.IAcheteurBourse;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;


public class Transformateur3 extends Transformateur3Acteur implements IAcheteurBourse  {
	
	private Journal jdb;
	private int etape;
	private Variable totalstock;
	private Feve feve;
	
	public Transformateur3() {
		super();
		this.jdb = new Journal("Journal de bord", this);
		this.totalstock = new Variable(this.getNom()+" volume total de notre stock", this, 300);
		this.feve = Feve.F_MQ;
	}

	protected Feve getFeve() {
		return this.feve;
	}

	public void next(){
		etape = Filiere.LA_FILIERE.getEtape();
		jdb.ajouter("Etape " + etape);
	}

	public List<Journal> getJournaux() {
	ArrayList<Journal> res = new ArrayList<Journal>();
	res.add(this.jdb);
	return res;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res =  new ArrayList<Variable>();
		res.add(this.totalstock);
		return res;
	}

	public double demande(Feve f, double cours) {
		if (this.getFeve().equals(f)) {
			return 80;
		} else {
			return 0.0;
		}
	}
	public void notificationAchat(Feve f, double quantiteEnT, double coursEnEuroParT) {
		this.totalstock.setValeur(this, this.totalstock.getValeur()+quantiteEnT);
	}
	public void notificationBlackList(int dureeEnStep) {
		this.jdb.ajouter("Aie... je suis blackliste... j'aurais du verifier que j'avais assez d'argent avant de passer une trop grosse commande en bourse...");
	}
}
