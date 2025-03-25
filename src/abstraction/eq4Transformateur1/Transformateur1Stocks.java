package abstraction.eq4Transformateur1;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.acteurs.Romu;
import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.contratsCadres.ContratCadre;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IFabricantChocolatDeMarque;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;

public class Transformateur1Stocks extends Transformateur1Acteur implements IFabricantChocolatDeMarque {

	private double coutStockage; 
	protected double STOCK_MAX_TOTAL_FEVES = 1000000;

	protected List<ExemplaireContratCadre> mesContratEnTantQuAcheteur;
	protected List<ExemplaireContratCadre> mesContratEnTantQueVendeur;//Contient tous les contrats de vente

	private List<ChocolatDeMarque> chocosProduits; // la liste de toutes les sortes de ChocolatDeMarque que l'acteur produit et peut vendre
	protected HashMap<Feve, HashMap<Chocolat, Double>> pourcentageTransfo; // pour les differentes feves, le chocolat qu'elles peuvent contribuer a produire avec le ratio
	protected List<ChocolatDeMarque> chocolatsLimDt; // la liste des chocolats de marque "LimDt" que l'acteur produit

	protected HashMap<Feve, Double> qttEntrantesFeve;//Contient les quantités entrant dans le stock à la période actuelle
	protected HashMap<Feve, Double> prixTFeveStockee;//Contient les prix moyens des fèves en stock
	protected HashMap<Chocolat, Double> prixTChocoBase;//Contient les prix des chocolats produits en s'appuyant sur le prix du stock de fèves

	public Transformateur1Stocks() {
		super();
		this.chocosProduits = new LinkedList<ChocolatDeMarque>();
		this.pourcentageTransfo = new HashMap<Feve, HashMap<Chocolat, Double>>();
		this.chocolatsLimDt=new LinkedList<ChocolatDeMarque>();

		this.qttEntrantesFeve = new HashMap<Feve, Double>();
		this.prixTFeveStockee = new HashMap<Feve, Double>();
		this.prixTChocoBase = new HashMap<Chocolat, Double>();
	}
	
	public void initialiser() {
		super.initialiser();


		this.coutStockage = Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur()*4;

		this.lesFeves = new LinkedList<Feve>();
		this.lesFeves.add(Feve.F_HQ_BE);
		this.lesFeves.add(Feve.F_MQ_E);
		this.lesFeves.add(Feve.F_BQ_E);
		this.lesFeves.add(Feve.F_BQ);
		
		for (Feve f : this.lesFeves) {
			this.stockFeves.put(f, 20000.0);
			this.totalStocksFeves.ajouter(this, 20000.0, this.cryptogramme);
			this.journal.ajouter("ajout de 20000 de "+f+" au stock de feves --> total="+this.totalStocksFeves.getValeur(this.cryptogramme));
		}
		
		for (Chocolat c : Chocolat.values()) {
			this.stockChoco.put(c, 0.0);
			this.totalStocksChoco.ajouter(this, 0.0, this.cryptogramme);
			this.journal.ajouter("Initialisation de 0 de "+c+" au stock de chocolat --> total="+this.totalStocksChoco.getValeur(this.cryptogramme));
		}

		this.stockChocoMarque=new HashMap<ChocolatDeMarque,Double>();
		this.pourcentageTransfo = new HashMap<Feve, HashMap<Chocolat, Double>>();


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
		
		for (Feve f : Feve.values()) {
			if (this.pourcentageTransfo.keySet().contains(f)) {
				for (Chocolat c : this.pourcentageTransfo.get(f).keySet()) {
					int pourcentageCacao =  (int) (Filiere.LA_FILIERE.getParametre("pourcentage min cacao "+c.getGamme()).getValeur());

					ChocolatDeMarque cm= new ChocolatDeMarque(c, "LimDt", pourcentageCacao);

					this.chocolatsLimDt.add(cm);
					this.stockChocoMarque.put(cm, 40000.0);
					this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN," stock("+cm+")->"+this.stockChocoMarque.get(cm));
				}
			}
		}
	}


	////////////////////////////////////////////////////////
	//      En lien avec la comptabilité et production    //
	////////////////////////////////////////////////////////

	/**
	 * Cette méthode transforme une partie du stock de fève en chacun des chocolats que l'on a décidé de produire.
	 * Elle détermine et place dans la HashMap prixTChocoBase le prix sans marge des chocolats produits en se basant sur le prix du stock de fèves
	 * @param None
	 * @return None
	 */
	protected void transformation(){

		for (Feve f : this.pourcentageTransfo.keySet()) {
			for (Chocolat c : this.pourcentageTransfo.get(f).keySet()) {

				double transfo;
				if (this.stockFeves.get(f) != null ){
					//On transforme toutes nos fèves
					transfo = this.stockFeves.get(f);

					if (transfo<=this.stockFeves.get(f) && transfo >0) {

						this.stockFeves.put(f, this.stockFeves.get(f)-transfo);
						this.totalStocksFeves.retirer(this, transfo, this.cryptogramme);

						double PourcentageMarque = 0.8;  //Modifiable
						// La Pourcentage ainsi definie sera stockee sous forme de marquee, la quantité restante sera alors stockee comme non marquee

						this.stockChoco.put(c, this.stockChoco.get(c)+((transfo*PourcentageMarque)*this.pourcentageTransfo.get(f).get(c)));

						int pourcentageCacao =  (int) (Filiere.LA_FILIERE.getParametre("pourcentage min cacao "+c.getGamme()).getValeur());
						ChocolatDeMarque cm= new ChocolatDeMarque(c, "LimDt", pourcentageCacao);
						double scm = this.stockChocoMarque.keySet().contains(cm) ?this.stockChocoMarque.get(cm) : 0.0;
						
						//calcul du stock de chocolat après les transformations
						double nouveauStock = (transfo)*this.pourcentageTransfo.get(f).get(c);

						//Détermination du prix de base des chocolats en pondérant avec les coûts de la période précédente
						if(prixTChocoBase.containsKey(c) && nouveauStock > 0){
							double ancienPrix = prixTChocoBase.get(c);
							double nouveauPrix = ancienPrix * (stockChoco.get(c)/ nouveauStock) +  pourcentageTransfo.get(f).get(c) * transfo * prixTFeveStockee.get(f) / nouveauStock;

							prixTChocoBase.put(c, nouveauPrix);
						}

						//Conversion des chocolat
						this.stockChocoMarque.put(cm, scm+PourcentageMarque*nouveauStock);

						this.totalStocksChoco.ajouter(this, nouveauStock, this.cryptogramme);
						this.totalStocksChocoMarque.ajouter(this, PourcentageMarque*nouveauStock, this.cryptogramme);
						this.totalStocksChocoNonMarquee.ajouter(this, (1-PourcentageMarque)*nouveauStock, this.cryptogramme);


						
						
						this.journal.ajouter(Romu.COLOR_LLGRAY, Color.PINK, "Transfo de "+(transfo<10?" "+transfo:transfo)+" T de "+f+" en "+Journal.doubleSur(transfo*this.pourcentageTransfo.get(f).get(c),3,2)+" T de "+c);
						this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN," stock("+f+")->"+this.stockFeves.get(f));
						this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN," stock("+c+")->"+this.stockChoco.get(c));
						this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN," stock("+cm+")->"+this.stockChocoMarque.get(cm));
					}
				}
			}
		}
	}

	/**
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
					if (qttEntrantesFeve.containsKey(f)){
						qttEntrantesFeve.put(f, cc.getPrix()+qttEntrantesFeve.get(f));
					}
					else {
						qttEntrantesFeve.put(f, cc.getPrix());
					}
				}
			}
		}

		//Quantité entrante de fèves par achat en bourse
		qttEntrantesFeve.put(Feve.F_BQ, 80.);



	}


	/**
	 * Une méthode qui permet de déterminer les cout en fèves entrantes dans le stock à la période actuelle selon les contrats négociés et achats en bourse
	 * Les résultats sont stockés dans la HashMap qttEntrantes
	 * On amalgame le prix moyen total du stock avec le prix à la tonne des fèves qui entrent dans notre stock
	 * @param None 
	 * @return None
	 */
	public void determinerPrixTFevesStockees(){
		determinerQttEntrantFeves();
		
		for (Feve f : pourcentageTransfo.keySet()){

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


			prixTFeveStockee.put(f, prix);
		}
	}




	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
		super.next();


		this.journal.ajouter("N° Etape " + Filiere.LA_FILIERE.getEtape());
		this.journal.ajouter("Solde : " + this.getSolde());

		this.journalStock.ajouter("N° Etape " + Filiere.LA_FILIERE.getEtape());
		this.journalCC.ajouter("N° Etape " + Filiere.LA_FILIERE.getEtape());
		this.journalTransactions.ajouter("N° Etape " + Filiere.LA_FILIERE.getEtape());

		
		this.journal.ajouter("Stock de fèves : " + this.totalStocksFeves.getValeur(this.cryptogramme));
		this.journal.ajouter("Stock de chocolat : " + this.totalStocksChoco.getValeur(this.cryptogramme));

		this.journal.ajouter("=== STOCKS === ");
		for (Feve f : this.lesFeves) {
			this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN,"Stock de "+Journal.texteSurUneLargeurDe(f+"", 15)+" = "+this.stockFeves.get(f));
		}
		for (Chocolat c : Chocolat.values()) {
			this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN,"Stock de "+Journal.texteSurUneLargeurDe(c+"", 15)+" = "+this.stockChoco.get(c));
		}
		if (this.stockChocoMarque.keySet().size()>0) {
			for (ChocolatDeMarque cm : this.stockChocoMarque.keySet()) {
				this.journal.ajouter(Romu.COLOR_LLGRAY, Romu.COLOR_BROWN,"Stock de "+Journal.texteSurUneLargeurDe(cm+"", 15)+" = "+this.stockChocoMarque.get(cm));
			}
		}

		this.determinerPrixTFevesStockees();

		this.transformation();

		Filiere.LA_FILIERE.getBanque().payerCout(this, cryptogramme, "Stockage", (this.totalStocksFeves.getValeur(cryptogramme)+this.totalStocksChoco.getValeur(cryptogramme)+this.totalStocksChocoMarque.getValeur(cryptogramme))*this.coutStockage);

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
