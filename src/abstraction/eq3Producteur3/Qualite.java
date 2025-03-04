package abstraction.eq3Producteur3;

//Zoé
public abstract class Qualite {

    protected final int densité;
    protected final int longevite;
    protected final int croissance;
    protected  final int mainOeuvre;
    protected final int nbCabosses;
    protected final int pousse;
    protected final int sechage;
    protected final int achat;
    protected final int replanter;
    protected final int vente;
    protected boolean equitable;


    public Qualite(int densité, int longevite, int croissance, int mainOeuvre, int nbCabosses, int pousse, int sechage, int achat, int replanter, int vente, boolean equitable) {
        this.densité = densité;
        this.longevite = longevite;
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
