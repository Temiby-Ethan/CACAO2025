package abstraction.eq6Transformateur3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.filiere.Filiere;

// @author Eric SCHILTZ

//l'objectif est de créer une classe acheteur. 
//On réalise le code : il faut se baser sur notre stock et y ajouter ce que l'on y a acheté
//On réalise et vérifie les bonnes implémentations comme dans l'exemple

//de quoi hérite notre classe ? aucun héritage mais une interface : IActeur 
//on importe cette interface : 
import abstraction.eqXRomu.filiere.IActeur;

//on répond à l'interface.Comme dans la méthode certaines méthodes par exemple seront vides. 
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;

//cette classe sera en réalité le père de la classe créeant réellement des instances d'achat.
public class Transformateur3AcheteurBoursePère implements IActeur{
    //des attributs
    //ici on nous donne :  
    private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
    //on créer cette variable statique à la classe NB_INSTANCES qui compte le nombre d'achats 
    //en bourse que l'on a faiit depuis le début. intérêt ?
    //notre première instance démarrera à 1
	protected Journal journal; //pour avoir un journal rendant compte du déroulement
    //de notre achat en bourse
	protected Variable stockFeve; //le stock de feve que l'on acheter
	protected Feve feve; //le type de feve que l'on veut acheter 
    //des constructeurs
    //ou plutôt un constructeur ici, qui nous permettra de :A chaque que l'on veut acheter en bourse
    //on va créer une instance de cette classe acheteur qui va nous servir à acheter en bourse
    //chaque instance d'achat va donc prendre en variables le stock que l'on veut acheter et 
    //le type de fève que l'on veut acheter
    public Transformateur3AcheteurBoursePère(Feve feve, double stock) {	
		if (feve==null ||stock<0) {//si on ne remplit pas un type de feve ou si le stock demandé est 
            //négatif : on retourne qu'il y a un problème. La manière on ne va pas chercher à la comprendre
			throw new IllegalArgumentException("creation d'une instance d'achat en bourse avec des arguments non valides");
		}
        //sinon c'est donc que l'on peut créer notre instance d'achat		
		NB_INSTANCES++; //on rajoute alors 1 à notre compteur d'instances d'achat
        //on créer notre instance: 
        //on crée notre variable de stock de feve, de feve et de journal; 
        //la manière ne nous intéresse pas. On a bien les bonnes importations. 
		this.stockFeve=new Variable(this.getNom()+"Stock"+feve, this, 0.0, 1000000.0,stock);
		this.feve = feve;
		this.journal = new Journal(this.getNom()+" activites", this);
	}
    //des méthodes
    @Override
    //vide
    public void initialiser() {
    }

    //check
    protected Feve getFeve() {
		return this.feve;
	} 

    //check
    public String toString() {
		return this.getNom();
	}

    //check
    @Override
	public String getNom() {
		return "A.Bourse"+this.numero;
	}

    //check
    @Override
	public String getDescription() {
		return "Acheteur de feve a la bourse du cacao "+this.numero;
	}

    /*à faire */
    //y mettre à jour : notre journal le cours actuel de la bourse si on est au delà de l'étape 1
    //sinon aussi plus généralement l'étape courante de la filière dans ce journal. 
    //on s'en fout d'où vont les journaux balec
    //BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
    // permet d'accéder à l'unique instance valable pour tout le jeu responsable de la bourse de cacao
    //Filiere.LA_FILIERE.getEtape()
    //la filière c'est le jeu global.
    @Override
    public void next() {
        journal.ajouter("Etape="+Filiere.LA_FILIERE.getEtape());
		if (Filiere.LA_FILIERE.getEtape()>=1) {
			BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
			journal.ajouter("le cours de "+feve+" est : "+Journal.doubleSur(bourse.getCours(feve).getValeur(), 2));
		}
    }

    //on retourne une liste des indicateurs de notre instance d'achat
    //Il faut retourner une liste de mm éléments or ici théoriquement on a deux indicateurs:
    // Feve et StockFeve mais de deux types différents. On ne rend ici en réalité pas une liste 
    //d'indicateurs mais juste le stockFeve
    @Override
    public List<Variable> getIndicateurs() {
        List<Variable> res=new ArrayList<Variable>();
		res.add(stockFeve);
		return res;
    }

    @Override
    public List<Variable> getParametres() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getParametres'");
    }

    @Override
    public List<Journal> getJournaux() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJournaux'");
    }

    @Override
    public void setCryptogramme(Integer crypto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCryptogramme'");
    }

    //vide
    @Override
    public void notificationFaillite(IActeur acteur) {
    }

    //vide
    @Override
    public void notificationOperationBancaire(double montant) {
    }

    @Override
    public List<String> getNomsFilieresProposees() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNomsFilieresProposees'");
    }

    @Override
    public Filiere getFiliere(String nom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFiliere'");
    }

    @Override
    public double getQuantiteEnStock(IProduit p, int cryptogramme) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQuantiteEnStock'");
    }
    
}
