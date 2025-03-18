package abstraction.eq6Transformateur3;

import abstraction.eqXRomu.bourseCacao.IAcheteurBourse;
//de quoi hérite notre classe ? aucun héritage mais une interface : IActeur 
//on importe cette interface : 
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;

public class Transformateur3AcheteurBoursef extends Transformateur3ContratCadreAcheteur implements IAcheteurBourse{
    //des attributs
	protected Journal journal; //pour avoir un journal rendant compte du déroulement
    //de notre achat en bourse
	protected Variable stockFeve; //le stock de feve que l'on acheter
	protected Feve feve; //le type de feve que l'on veut acheter 
    //des constructeurs
    public Transformateur3AcheteurBoursef(){
        
    }
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
