package abstraction.eq6Transformateur3;

import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.bourseCacao.IAcheteurBourse;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
//de quoi hérite notre classe ? aucun héritage mais une interface : IActeur 
//on importe cette interface : 
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;

// @author Eric SCHILTZ

//notre objectif est donc de créer un fils des programmes crées précédemment
//le fait de faire de faire des achats en bourse sera une méthode
//le but étant que la suite hérite de nous et de nos méthodes et donc puisse faire des achats en bourse
//on va aller chercher parfois si besoin dans le code fait précédemment Père si besoin
//Mais globalement il faut recommencer à 0
//Notre entreprise finale sera une instance de notre classe. 
//On va s'inspirer de ce qu'a fait Henri
//on doit pouvoir respecter la nomenclature commune et donc correspondre à l'interface IAcheteur
public class Transformateur3AcheteurBoursef extends Transformateur3ContratCadreAcheteur implements IAcheteurBourse{
    //il faut voir le pb comme si on était la classe principale de notre boîte, la dernière. 
    //il faut que l'instance qu'elle créée soit notre entreprise
    //des attributs
    //il faut que les attributs de notre instance que l'on rajoute soit donc la liste des achats en 
    //bourses faits :soit une liste avec les achats en bourse fait : liste de fèves et liste de stocks
    //voulus
    //et une liste avec les achats en bourse courants :liste de fèves et liste de stocks
    //voulus
	protected List<Variable> StockFevevouluencours;
	protected List<Feve> Fevevoulueencours;
    protected List<Variable> Stockvoulufaits;
    protected List<Feve> Fevevouluefaites;
    //des constructeurs
    //notre contrainte et que l'on veut rien en variable 
    public Transformateur3AcheteurBoursef(){
        this.StockFevevouluencours = new LinkedList<Variable>();
        this.Fevevoulueencours = new LinkedList<Feve>();
        this.Stockvoulufaits = new LinkedList<Variable>();
        this.Fevevouluefaites = new LinkedList<Feve>();
    }

    //des méthodes

    //méthode next. Réaliser à chaque step. 
    public void next() {
        
    }

    //méthodes pour correspondre à l'interface IAcheteur

    //permet de savoir quelle fève l'on demande selon quel cours en cours
    @Override
    public double demande(Feve f, double cours) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'demande'");
    }
    @Override
    public void notificationAchat(Feve f, double quantiteEnT, double coursEnEuroParT) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificationAchat'");
    }
    @Override
    public void notificationBlackList(int dureeEnStep) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificationBlackList'");
    }
}
