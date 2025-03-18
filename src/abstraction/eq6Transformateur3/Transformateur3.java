// Henri ROTH, Florian MALVEAU
package abstraction.eq6Transformateur3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

public class Transformateur3 extends Transformateur3ContratCadreAcheteur{

	public Transformateur3() {
	}

	public void initialiser(){
		super.initialiser();
	}

	public void next(){
		super.next();
		stockChoco.addToStock(super.lesChocolats.get(0), 500.0);
		stockChoco.addToStock(lesChocolats.get(1), 800.0);

		stockFeves.remove(lesFeves.get(0), 100.0);

		stockFeves.display();
		stockChoco.display();
	}

	public List<Journal> getJournaux() {
		ArrayList<Journal> res = new ArrayList<Journal>();
		res.add(super.jdb);
		res.add(super.journalStock);
		res.add(super.journalTransac);
		return res;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res =  new ArrayList<Variable>();
		res.add(super.eq6_Q_BQ_0);
		res.add(super.eq6_Q_BQ_1);
		res.add(super.eq6_Q_MQ_0);
		res.add(super.eq6_Q_MQ_1);
		res.add(super.eq6_Q_HQ_1);
		res.add(super.eq6_Q_HQ_2);
		res.add(super.eq6_Q_Fraudo);
		res.add(super.eq6_Q_Bollo);
		res.add(super.eq6_Q_Hypo);
		res.add(super.eq6_Q_Arna);
		res.add(super.eq6_Q_ingre);
		res.add(super.eq6_Q_machine);
		res.add(super.eq6_capacite_machine);
		res.add(super.eq6_jours_decouvert);
		res.add(super.eq6_nb_employe);
		res.add(super.eq6_cout_stockage);
		res.add(super.eq6_Q_cacao_CC);
		res.add(super.eq6_Q_tablette_CC);
		return res;
	}
}
