package abstraction.eq3Producteur3;

public abstract class Qualite {

    public final int densité;
    public final int longévité;
    public final int croissance;
    public final int mainOeuvre;
    public final int nbCabosses;
    public final int pousse;
    public final int sechage;
    public final int achat;
    public final int replanter;
    public final int vente;
    public boolean equitable;


    public Qualite(int densité, int longévité, int croissance, int mainOeuvre, int nbCabosses, int pousse, int sechage, int achat, int replanter, int vente, boolean equitable) {
        this.densité = densité;
        this.longévité = longévité;
        this.croissance = croissance;
        this.mainOeuvre = mainOeuvre;
        this.nbCabosses = nbCabosses;
        this.pousse = pousse;
        this.sechage = sechage;
        this.achat = achat;
        this.replanter = replanter;
        this.vente = vente;
        this.equitable = equitable;
    }

    
    
}
