// Henri ROTH
package abstraction.eq6Transformateur3;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;

public class Transformateur3 extends Transformateur3Acteur{
	
	protected Journal jdb;
	protected int etape;
	protected Variable eq6_Q_MQ_0;
	protected Variable eq6_Q_MQ_1;
	protected Variable eq6_Q_BQ_0;
	protected Variable eq6_Q_BQ_1;
	protected Variable eq6_Q_HQ_1;
	protected Variable eq6_Q_HQ_2;
	protected Variable eq6_Q_Fraudo;
	protected Variable eq6_Q_Bollo;
	protected Variable eq6_Q_Arna;
	protected Variable eq6_Q_Hypo;
	protected Variable eq6_Q_ingre;
	protected Variable eq6_Q_machine;
	protected Variable eq6_capacite_machine;
	protected Variable eq6_nb_employe;
	protected Variable eq6_jours_decouvert;
	protected Variable eq6_cout_stockage;
	protected Variable eq6_Q_cacao_CC;
	protected Variable eq6_Q_tablette_CC;
	
	public Transformateur3() {
		super();
		this.jdb = new Journal("Journal de bord", this);
		this.eq6_Q_MQ_0 = new Variable(this.getNom()+": quantité de cacao de MQ non labellisé", this, 300);
		this.eq6_Q_MQ_1 = new Variable(this.getNom()+": quantité de cacao de MQ équitable", this, 300);
		this.eq6_Q_BQ_0 = new Variable(this.getNom()+": quantité de cacao de BQ non labellisé", this, 300);
		this.eq6_Q_BQ_1 = new Variable(this.getNom()+": quantité de cacao de BQ équitable", this, 300);
		this.eq6_Q_HQ_1 = new Variable(this.getNom()+": quantité de cacao de HQ équitable", this, 300);
		this.eq6_Q_HQ_2 = new Variable(this.getNom()+": quantité de cacao de HQ bio & équitable", this, 300);
		this.eq6_Q_Fraudo = new Variable(this.getNom()+": quantité de tablette Fraudolat", this, 300);
		this.eq6_Q_Bollo = new Variable(this.getNom()+": quantité de tablette Bollorolat", this, 300);
		this.eq6_Q_Arna = new Variable(this.getNom()+": quantité de tablette Arnaquolat", this, 300);
		this.eq6_Q_Hypo = new Variable(this.getNom()+": quantité de tablette Hypocritolat", this, 300);
		this.eq6_Q_ingre = new Variable(this.getNom()+": quantité d'ingédient secondaire", this, 300);
		this.eq6_Q_machine = new Variable(this.getNom()+": quantité de machine", this, 300);
		this.eq6_capacite_machine = new Variable(this.getNom()+": capacité de production des machines", this, 300);
		this.eq6_jours_decouvert = new Variable(this.getNom()+": nombre de jours à découvert", this, 300);
		this.eq6_nb_employe = new Variable(this.getNom()+": nombre d'employés", this, 300);
		this.eq6_cout_stockage = new Variable(this.getNom()+": coûts de stockage pour ce step", this, 300);
		this.eq6_Q_cacao_CC = new Variable(this.getNom()+": quantité de cacaco que l'on reçoit ", this, 300);
		this.eq6_Q_tablette_CC = new Variable(this.getNom()+": quantité de tablette à produire", this, 300);
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
		res.add(this.eq6_Q_MQ_0);
		res.add(this.eq6_Q_MQ_1);
		res.add(this.eq6_Q_BQ_0);
		res.add(this.eq6_Q_BQ_1);
		res.add(this.eq6_Q_HQ_1);
		res.add(this.eq6_Q_HQ_2);
		res.add(this.eq6_Q_Fraudo);
		res.add(this.eq6_Q_Bollo);
		res.add(this.eq6_Q_Hypo);
		res.add(this.eq6_Q_Arna);
		res.add(this.eq6_Q_ingre);
		res.add(this.eq6_Q_machine);
		res.add(this.eq6_capacite_machine);
		res.add(this.eq6_jours_decouvert);
		res.add(this.eq6_nb_employe);
		res.add(this.eq6_cout_stockage);
		res.add(this.eq6_Q_cacao_CC);
		res.add(this.eq6_Q_tablette_CC);
		return res;
	}
}
