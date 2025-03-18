package abstraction.eq6Transformateur3;
// @author Henri Roth & Florian Malveau
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.filiere.IFabricantChocolatDeMarque;
import abstraction.eqXRomu.general.Variable;

public class Transformateur3Fabriquant extends Transformateur3Marques implements IFabricantChocolatDeMarque{
    
    private List<ChocolatDeMarque> chocolatDeMarques;
	protected HashMap<IProduit, Variable> dicoIndicateurChoco;
    private ChocolatDeMarque fraud;
    private ChocolatDeMarque hypo;
    private ChocolatDeMarque arna;
    private ChocolatDeMarque bollo;

    public Transformateur3Fabriquant(){
        super();

        //Liste des chocolat de marque (pour matcher avec l'interface)
        this.chocolatDeMarques = new ArrayList<ChocolatDeMarque>();
        this.fraud = new ChocolatDeMarque(Chocolat.C_BQ, "Fraudolat", 30);
        this.hypo = new ChocolatDeMarque(Chocolat.C_HQ_E, "Hypocritolat", 100);
        this.arna =  new ChocolatDeMarque(Chocolat.C_MQ, "Arnaquolat", 50);
        this.bollo = new ChocolatDeMarque(Chocolat.C_BQ_E, "Bollorolat", 30);
        this.chocolatDeMarques.add(fraud);
        this.chocolatDeMarques.add(hypo);
        this.chocolatDeMarques.add(arna);
        this.chocolatDeMarques.add(bollo);

        //Liste de produit
        super.lesChocolats = new ArrayList<IProduit>();
        super.lesChocolats.add(fraud);
        super.lesChocolats.add(hypo);
        super.lesChocolats.add(arna);
        super.lesChocolats.add(bollo);

        //Dico indicateur choco
		this.dicoIndicateurChoco = new HashMap<IProduit, Variable>();
        this.dicoIndicateurChoco.put(fraud,super.eq6_Q_Fraudo);
        this.dicoIndicateurChoco.put(hypo,super.eq6_Q_Hypo);
        this.dicoIndicateurChoco.put(arna,super.eq6_Q_Arna);
        this.dicoIndicateurChoco.put(bollo,super.eq6_Q_Bollo);

        //Création du stock de chocolat
		super.stockChoco = new Transformateur3Stock(this, super.journalStock, "chocolat", 200.0, super.lesChocolats, this.dicoIndicateurChoco);
    }

    public List<ChocolatDeMarque> getChocolatsProduits(){
        return this.chocolatDeMarques;
    }
}
