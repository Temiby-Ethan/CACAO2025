package abstraction.eq4Transformateur1;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IFabricantChocolatDeMarque;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.general.Variable;

public class Transformateur1Stocks extends Transformateur1Acteur implements IFabricantChocolatDeMarque {

	//Des variables qui ne seront au final que des constantes lors de la simulation
	private double coutStockage; 
	private double coutProd;
	protected double STOCK_MAX_TOTAL_FEVES = 1000000;

	//Listes regroupant les contrats cadres actifs
	protected List<ExemplaireContratCadre> mesContratEnTantQuAcheteur;
	protected List<ExemplaireContratCadre> mesContratEnTantQueVendeur;

	private List<ChocolatDeMarque> chocosProduits; // la liste de toutes les sortes de ChocolatDeMarque que l'acteur produit et peut vendre
	protected HashMap<Feve, HashMap<Chocolat, Double>> pourcentageTransfo; // pour les differentes feves, le chocolat qu'elles peuvent contribuer a produire avec le ratio qttChocoProduit/qttFevesUtilisée

	//Des tables de hachages pour connaître l'état des chocolats à une période précise
	protected HashMap<Feve, Double> qttEntrantesFeve;//Contient les quantités entrant dans le stock à la période actuelle
	protected HashMap<Feve, Double> prixTFeveStockee;//Contient les prix moyens des fèves en stock
	protected HashMap<Chocolat, Double> prixTChocoBase;//Contient les prix des chocolats produits en s'appuyant sur le prix du stock de fèves
	protected HashMap<Chocolat, Double> qttSortantesChoco;
	protected HashMap<Chocolat, Double> marges;


	public Transformateur1Stocks() {
		super();

		this.chocosProduits = new LinkedList<ChocolatDeMarque>();
		this.pourcentageTransfo = new HashMap<Feve, HashMap<Chocolat, Double>>();
		

		this.qttEntrantesFeve = new HashMap<Feve, Double>();
		this.prixTFeveStockee = new HashMap<Feve, Double>();
		this.prixTChocoBase = new HashMap<Chocolat, Double>();
		this.qttSortantesChoco = new HashMap<Chocolat, Double>();


		this.stockChocoMarque=new HashMap<ChocolatDeMarque,Double>();

		this.marges = new HashMap<Chocolat, Double>();

	}





	
	public void initialiser() {
		super.initialiser();

		//Initialisation des prix de nos stocks de fève
		this.prixTFeveStockee.put(Feve.F_BQ, 2000.);
		this.prixTFeveStockee.put(Feve.F_BQ_E, 2000.);
		this.prixTFeveStockee.put(Feve.F_MQ_E, 2000.);
		this.prixTFeveStockee.put(Feve.F_HQ_BE, 2000.);

		this.coutStockage = Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur()*4;
		this.coutProd = 4000; //A MODIFIER il s'agit du cout de la production d'une tonne de chocolat, valeur arbitraire censée contenir salaires, ingrédients secondaires, et autres couts fixes

		//Initialisation des prix de base des chocolats que l'on veut produire
		this.prixTChocoBase.put(Chocolat.C_BQ, 2000.);
		this.prixTChocoBase.put(Chocolat.C_BQ_E, 2000.);
		this.prixTChocoBase.put(Chocolat.C_HQ_BE, 2000.);
		this.prixTChocoBase.put(Chocolat.C_MQ_E, 2000.);
		

		//Initialisation des marges que l'on va faire sur les différents produits
		this.marges.put(Chocolat.C_BQ, 1.5);
		this.marges.put(Chocolat.C_BQ_E, 1.16);
		this.marges.put(Chocolat.C_MQ_E, 1.16);
		this.marges.put(Chocolat.C_HQ_BE, 1.3);


		//Initialisation des pourcentage de conversion fèves vers chocolat
		this.pourcentageTransfo.put(Feve.F_HQ_BE, new HashMap<Chocolat, Double>());
		double conversion = 1.0 + (100.0 - Filiere.LA_FILIERE.getParametre("pourcentage min cacao HQ").getValeur())/100.0;
		this.pourcentageTransfo.get(Feve.F_HQ_BE).put(Chocolat.C_HQ_BE, conversion);// la masse de chocolat obtenue est plus importante que la masse de feve vue l'ajout d'autres ingredients

		this.pourcentageTransfo.put(Feve.F_MQ_E, new HashMap<Chocolat, Double>());
		conversion = 1.0 + (100.0 - Filiere.LA_FILIERE.getParametre("pourcentage min cacao MQ").getValeur())/100.0;
		this.pourcentageTransfo.get(Feve.F_MQ_E).put(Chocolat.C_MQ_E, conversion);

		this.pourcentageTransfo.put(Feve.F_BQ, new HashMap<Chocolat, Double>());
		conversion = 1.0 + (100.0 - Filiere.LA_FILIERE.getParametre("pourcentage min cacao BQ").getValeur())/100.0;
		this.pourcentageTransfo.get(Feve.F_BQ).put(Chocolat.C_BQ, conversion);

		this.pourcentageTransfo.put(Feve.F_BQ_E, new HashMap<Chocolat, Double>());
		this.pourcentageTransfo.get(Feve.F_BQ_E).put(Chocolat.C_BQ_E, conversion);


		this.journalStock.ajouter(Romu.COLOR_LLGRAY, Color.PINK, "Stock initial chocolat de marque : ");


		//Initialisation des quantités de fève entrantes
		this.qttEntrantesFeve.put(Feve.F_BQ, 0.);
		this.qttEntrantesFeve.put(Feve.F_BQ_E, 0.);
		this.qttEntrantesFeve.put(Feve.F_HQ_BE, 0.);
		this.qttEntrantesFeve.put(Feve.F_MQ_E, 0.);
	}





	////////////////////////////////////////////////////////
	//      En lien avec la comptabilité et production    //
	////////////////////////////////////////////////////////




	/**
	 * @author ABBASSI Rayenne
	 * Cette méthode transforme une partie du stock de fève en chacun des chocolats que l'on a décidé de produire.
	 * Elle détermine et place dans la HashMap prixTChocoBase le prix sans marge des chocolats produits en se basant sur le prix du stock de fèves
	 * @param None
	 * @return None
	 */
	protected void transformation(){

		for (Feve f : lesFeves) {
			for (Chocolat c : lesChocolats) {

				double transfo;
				if (this.stocksFevesVar.get(f) != null && this.pourcentageTransfo.get(f).get(c) != null){
					//On transforme une partie de nos fèves
					transfo = 0.9 * this.stocksFevesVar.get(f).getValeur();

					//On s'assure que l'on produit quelque chose pour faire nos opérations
					if (transfo<=this.getQuantiteEnStock(f, this.cryptogramme) && transfo >0) {

						//OBSOLETE
						this.totalStocksFeves.retirer(this, transfo, this.cryptogramme);

						double pourcentageMarque = 0.8;  //Modifiable
						// La Pourcentage ainsi definie sera stockee sous forme de marquee, la quantité restante sera alors stockee comme non marquee

	
						
						//A MODIFIER
						int pourcentageCacao =  (int) (Filiere.LA_FILIERE.getParametre("pourcentage min cacao "+c.getGamme()).getValeur());
						ChocolatDeMarque cm= new ChocolatDeMarque(c, "LimDt", pourcentageCacao);
						double scm = this.stocksMarqueVar.keySet().contains(cm) ?this.stockChocoMarque.get(cm) : 0.0;
						
						//calcul du stock de chocolat après les transformations
						double nouveauStock = transfo*this.pourcentageTransfo.get(f).get(c);

						//Détermination du prix de base des chocolats à la tonne en pondérant avec les coûts de la période précédente
						if(prixTChocoBase.containsKey(c) && nouveauStock > 0){
							double ancienPrix = prixTChocoBase.get(c);
							double nouveauPrix = ancienPrix * ((getQuantiteEnStock(c, this.cryptogramme) + getQuantiteEnStock(cm, this.cryptogramme))/ (nouveauStock+getQuantiteEnStock(c, this.cryptogramme) + getQuantiteEnStock(cm, this.cryptogramme))) + (prixTFeveStockee.get(f) + coutProd + coutStockage) * (pourcentageTransfo.get(f).get(c) * transfo / (nouveauStock+ getQuantiteEnStock(c, this.cryptogramme) + getQuantiteEnStock(cm, this.cryptogramme)));

							prixTChocoBase.put(c, nouveauPrix);
						}

						//Ajout des chocolats produits au stock
						this.stocksFevesVar.get(f).retirer(this, transfo, this.cryptogramme);
						this.stocksChocoVar.get(c).ajouter(this, transfo * (1-pourcentageMarque), this.cryptogramme);
						this.stocksMarqueVar.get(cm).ajouter(this, transfo * pourcentageMarque, this.cryptogramme);


						//OBSOLETE
						this.stockChocoMarque.put(cm, scm+pourcentageMarque*nouveauStock);
						this.totalStocksChoco.ajouter(this, nouveauStock, this.cryptogramme);
						this.totalStocksChocoMarque.ajouter(this, pourcentageMarque*nouveauStock, this.cryptogramme);
						this.totalStocksChocoNonMarquee.ajouter(this, (1-pourcentageMarque)*nouveauStock, this.cryptogramme);
						this.stockChoco.put(c, stockChoco.get(c) + nouveauStock);
						
						//Notification dans le journal
						this.journal.ajouter(Romu.COLOR_LLGRAY, Color.PINK, "Transfo de "+(transfo<10?" "+transfo:transfo)+" T de "+f+" en "+Journal.doubleSur(transfo*this.pourcentageTransfo.get(f).get(c),3,2)+" T de "+c);
						this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN," stock("+f+")->"+this.stockFeves.get(f));
						this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN," stock("+c+")->"+this.stockChoco.get(c));
						this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN," stock("+cm+")->"+this.stockChocoMarque.get(cm));
						this.journal.ajouter("\n");
					}
				}
			}
		}
	}










	/**
	 * @author MURY Julien
	 * Une méthode qui permet de déterminer la quantitié de fèves entrant dans le stock à la période actuelle selon les contrats négociés et achats en bourse
	 * Les résultats sont stockés dans la HashMap qttEntrantes
	 * @param None
	 * @return None
	 */
	public void determinerQttEntrantFeves(){

		//Quantité entrante de fèves f par contrat cadre
		for (Feve f : pourcentageTransfo.keySet()){
			for (ExemplaireContratCadre cc : mesContratEnTantQuAcheteur){
				if (cc.getProduit() == f){
					if (this.qttEntrantesFeve.containsKey(f)){
						this.qttEntrantesFeve.put(f, cc.getPrix()+qttEntrantesFeve.get(f));
					}
					else {
						this.qttEntrantesFeve.put(f, cc.getPrix());
					}
				}
			}
		}

		//Quantité entrante de fèves par achat en bourse
		qttEntrantesFeve.put(Feve.F_BQ, 80.);



	}




/**
 * @author MURY Julien
 */
	public void determinerQttSortantChoco(){
		//Qtt sortante par contrat cadre
		for (Chocolat c : lesChocolats){
			for (ExemplaireContratCadre cc : mesContratEnTantQueVendeur){
				if (cc.getProduit().equals(c) || ((ChocolatDeMarque)cc.getProduit()).getChocolat().equals(c)){
					if (this.qttSortantesChoco.containsKey(c)){
						this.qttSortantesChoco.put(c, cc.getPrix()+qttSortantesChoco.get(c));
					}
					else {
						this.qttSortantesChoco.put(c, cc.getPrix());
					}
				}
			}
		}

		//Quantité sortante par enchère
		

		//Quantité sortante par appel d'offre
	}





	/**
	 * @author MURY Julien
	 * Une méthode qui permet de déterminer les cout en fèves entrantes dans le stock à la période actuelle selon les contrats négociés et achats en bourse
	 * Les résultats sont stockés dans la HashMap qttEntrantes
	 * On amalgame le prix moyen total du stock avec le prix à la tonne des fèves qui entrent dans notre stock
	 * @param None 
	 * @return None
	 */
	public void determinerPrixTFevesStockees(){
		determinerQttEntrantFeves();
		
		for (Feve f : lesFeves){

			//On calcule le prix pour les fèves issues des contrats cadres
			double prix = 0;
			for (ExemplaireContratCadre cc : mesContratEnTantQuAcheteur){
				if (cc.getProduit() == f){

					//On calcule le prix moyen de ce qui entre en pondérant par la part que représente ce contrat dans la qtt totale entrante
					prix += cc.getPrix() * (cc.getQuantiteALivrerAuStep()/qttEntrantesFeve.get(f));

				}
			}
			//On calcule le prix pour les ajouts par bourse
			if (f == Feve.F_BQ){
				BourseCacao bourse = (BourseCacao) Filiere.LA_FILIERE.getActeur("BourseCacao");	
				prix += bourse.getCours(f).getValeur() * (80. / qttEntrantesFeve.get(f));
			}

			double ancienPrixPondere =prixTFeveStockee.keySet().contains(f)? prixTFeveStockee.get(f)*(getQuantiteEnStock(f, this.cryptogramme)/(getQuantiteEnStock(f, this.cryptogramme)+ qttEntrantesFeve.get(f))) : 0.;
			prixTFeveStockee.put(f, ancienPrixPondere + prix*(qttEntrantesFeve.get(f)/(qttEntrantesFeve.get(f)+getQuantiteEnStock(f, this.cryptogramme))));
		}
	}











	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
		super.next();

		this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_LBLUE, "N° Etape " + Filiere.LA_FILIERE.getEtape());
		this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_LBLUE, "N° Etape " + Filiere.LA_FILIERE.getEtape());
		this.journalCC.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_LBLUE, "N° Etape " + Filiere.LA_FILIERE.getEtape());
		this.journalTransactions.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_LBLUE, "N° Etape " + Filiere.LA_FILIERE.getEtape());

		
		this.journalStock.ajouter("Stock de fèves : " + this.totalStocksFeves.getValeur(this.cryptogramme));
		this.journalStock.ajouter("Stock de chocolat : " + this.totalStocksChoco.getValeur(this.cryptogramme));
		this.journalStock.ajouter("\n");

		this.journal.ajouter("=== STOCKS === ");
		for (Feve f : this.lesFeves) {
			this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN,"Stock de "+Journal.texteSurUneLargeurDe(f+"", 15)+" = "+this.stockFeves.get(f));
		}
		this.journalStock.ajouter("\n");

		for (Chocolat c : this.lesChocolats) {
			this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN,"Stock de "+Journal.texteSurUneLargeurDe(c+"", 15)+" = "+this.stockChoco.get(c));
		}
		this.journalStock.ajouter("\n");

		if (this.stockChocoMarque.keySet().size()>0) {
			for (ChocolatDeMarque cm : this.stockChocoMarque.keySet()) {
				this.journalStock.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN,"Stock de "+Journal.texteSurUneLargeurDe(cm+"", 15)+" = "+this.stockChocoMarque.get(cm));
			}
		    this.journalStock.ajouter("\n");
		}

		this.determinerPrixTFevesStockees();

		this.transformation();

		Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "Stockage", (this.totalStocksFeves.getValeur(cryptogramme)+this.totalStocksChoco.getValeur(cryptogramme))*this.coutStockage);


		System.out.println("Voici nos prix : " + prixTChocoBase);
	}







	public List<Variable> getIndicateurs(){
		List<Variable> res = super.getIndicateurs();

		res.add(this.stock_F_BQ);
		res.add(this.stock_F_BQ_E);
		res.add(this.stock_F_MQ_E);
		res.add(this.stock_F_HQ_BE);
		res.add(this.stock_C_BQ);
		res.add(this.stock_C_BQ_E);
		res.add(this.stock_C_MQ_E);
		res.add(this.stock_C_HQ_BE);
		res.add(this.stock_C_BQ_Limdt);
		res.add(this.stock_C_BQ_E_Limdt);
		res.add(this.stock_C_MQ_E_Limdt);
		res.add(this.stock_C_HQ_BE_Limdt);


		return res;
	}



	////////////////////////////////////////////////////////
	//        Pour la creation de filieres de test        //
	////////////////////////////////////////////////////////


	public List<String> getMarquesChocolat() {
		LinkedList<String> marques = new LinkedList<String>();
		marques.add("LimDt");
		return marques;
	}

	public List<ChocolatDeMarque> getChocolatsProduits() {
		if (this.chocosProduits.size()==0) {
			for (Chocolat c : Chocolat.values()) {
				int pourcentageCacao =  (int) (Filiere.LA_FILIERE.getParametre("pourcentage min cacao "+c.getGamme()).getValeur());
				this.chocosProduits.add(new ChocolatDeMarque(c, "LimDt", pourcentageCacao));
			}
		}
		return this.chocosProduits;
	}

}
