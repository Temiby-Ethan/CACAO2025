package abstraction.eq3Producteur3;

//Zoé
public class QualiteHQ  extends Qualite{
    public boolean bio;
    
    public QualiteHQ(int densité, int longévité, int croissance, int mainOeuvre, int nbCabosses, int pousse, int sechage, int achat, int replanter, int vente, boolean equitable, boolean bio) {
        super(densité, longévité, croissance, mainOeuvre, nbCabosses, pousse, sechage, achat, replanter, vente, equitable);
        this.bio=bio;
    }
    
}
