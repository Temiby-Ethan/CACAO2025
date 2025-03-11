package abstraction.eq3Producteur3;

//Zoé
public class QualiteHQ  extends Qualite{
    public boolean bio;
    
    public QualiteHQ(int densité, int longévité, int croissance, int mainOeuvre, int nbCabosses, int pousse, int sechage, int achat, int replanter, int vente, boolean equitable, boolean bio) {
        super(densité, longévité, croissance, mainOeuvre, nbCabosses, pousse, sechage, achat, replanter, vente, equitable);
        this.bio=bio;
    }

    public QualiteHQ(boolean equitable){
        super(50000,960,120,4,20,12,1,700000,235000,465000, equitable);
    }

    public QualiteHQ(boolean equitable, boolean bio){
        super(35000,960,120,4,20,12,1,700000,235000,465000, equitable);
        this.bio = bio;
    }
    
}
