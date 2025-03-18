package abstraction.eq3Producteur3;

//Zoé
public class QualiteBQ extends Qualite{
    
    public QualiteBQ(int densité, int longevite, int croissance, int mainOeuvre, int nbCabosses, int pousse, int sechage, int achat, int replanter, int vente, boolean equitable) {
        super(densité, longevite, croissance, mainOeuvre, nbCabosses, pousse, sechage, achat, replanter, vente, equitable);
    }

    public QualiteBQ(boolean equitable){
        super(95000, 960, 72, 8, 32, 12, 2, 225000, 800000, 145000, equitable);
    }

    
    
    
}
