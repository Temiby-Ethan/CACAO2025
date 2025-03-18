package abstraction.eq3Producteur3;

//Zoé
public class Parcelle {

    protected Qualite qualite;
    protected int dateDebut;

    //Une parcelle = 100ha


    public Parcelle(Qualite qualite, int dateDebut){
        this.qualite = qualite;
        this.dateDebut = dateDebut;
    }


    public int getNextRestants(){
        return 0;
    }



    
}
