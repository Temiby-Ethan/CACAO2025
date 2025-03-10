package abstraction.eq3Producteur3;

//Zoé
public class Terrain {

    protected Qualite qualite;
    protected int dateDebut;

    //Un terrain = 100ha


    public Terrain(Qualite qualite, int dateDebut){
        this.qualite = qualite;
        this.dateDebut = dateDebut;
    }


    public int getNextRestants(){
        return 0;
    }



    
}
