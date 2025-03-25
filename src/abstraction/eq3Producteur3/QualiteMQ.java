package abstraction.eq3Producteur3;

//Zoé
public class QualiteMQ extends Qualite {
    
    
    public QualiteMQ(int densité, int longévité, int croissance, int mainOeuvre, int nbCabosses, int pousse, int sechage, int achat, int replanter, int vente, boolean equitable) {
        super(densité, longévité, croissance, mainOeuvre, nbCabosses, pousse, sechage, achat, replanter, vente, equitable);
    }

    public QualiteMQ(boolean equitable){
        super(75000,960,96,6,30,12,1,425000,140000,285000, equitable);
    }

}
