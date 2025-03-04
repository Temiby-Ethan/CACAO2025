package abstraction.eq6Transformateur3;
// @author Henri Roth
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.filiere.IFabricantChocolatDeMarque;

public class Transformateur3_fabriquant extends Transformateur3_marques implements IFabricantChocolatDeMarque{
    
    private List<ChocolatDeMarque> chocolatDeMarques;
    private ChocolatDeMarque fraud;
    private ChocolatDeMarque hypo;
    private ChocolatDeMarque arna;
    private ChocolatDeMarque bollo;

    public Transformateur3_fabriquant(){
        this.chocolatDeMarques = new ArrayList<ChocolatDeMarque>();
        this.fraud = new ChocolatDeMarque(Chocolat.C_BQ, "Fraudolat", 30);
        this.hypo = new ChocolatDeMarque(Chocolat.C_HQ_E, "Hypocritolat", 100);
        this.arna =  new ChocolatDeMarque(Chocolat.C_MQ, "Arnaquolat", 50);
        this.bollo = new ChocolatDeMarque(Chocolat.C_BQ_E, "Bollorolat", 30);
        this.chocolatDeMarques.add(fraud);
        this.chocolatDeMarques.add(hypo);
        this.chocolatDeMarques.add(arna);
        this.chocolatDeMarques.add(bollo);
    }

    public List<ChocolatDeMarque> getChocolatsProduits(){
        return this.chocolatDeMarques;
    }
}
