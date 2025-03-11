package abstraction.eq9Distributeur3;

import abstraction.eqXRomu.clients.ClientFinal;
import abstraction.eqXRomu.filiere.IDistributeurChocolatDeMarque;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariablePrivee;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Distributeur3Distributeur extends Distributeur3Acteur implements IDistributeurChocolatDeMarque {
    // Implémenter par Héloïse
    protected HashMap<ChocolatDeMarque, Double> stocks;
    protected HashMap<ChocolatDeMarque, Float> prix;
    private VariablePrivee stockTotal;


    public Distributeur3Distributeur() {
        super();
        stocks = new HashMap<>();
        prix = new HashMap<>();
        this.stockTotal = new VariablePrivee("stockTotal",this);
        this.stockTotal.setValeur(this,300.0);
    }


    @Override
    public double prix(ChocolatDeMarque choco) {
        return prix.get(choco);
    }


    @Override
    public double quantiteEnVente(ChocolatDeMarque choco, int crypto) {
        if (this.cryptogramme==crypto && this.stocks.containsKey(choco)) {
            if(this.stocks.get(choco)>=100) {
                return 100;
            }else{
                return this.stocks.get(choco);
            }
        } else {
            return 0.0;
        }
    }

    @Override
    public double quantiteEnVenteTG(ChocolatDeMarque choco, int crypto) {
        return this.quantiteEnVente(choco,crypto)*ClientFinal.POURCENTAGE_MAX_EN_TG;
    }

    @Override
    public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant, int crypto) {
        if(crypto==this.cryptogramme){
            stocks.put(choco,Double.valueOf(this.stocks.get(choco)-quantite));
            this.MAJStocksTotal();
            journalActeur.ajouter("Vente de "+quantite+" tonnes de "+choco.toString()+" à "+client.getNom()+" pour "+montant+" euros");
        }
    }

    public void MAJStocksTotal(){
        double total = 0.0;
        for(ChocolatDeMarque choco : stocks.keySet()){
            total+=stocks.get(choco);
        }
        this.stockTotal.setValeur(this,total);
    }

    public List<Variable> getIndicateurs() {
        List<Variable> res = super.getIndicateurs();
        res.add(this.stockTotal);
        return res;
    }

    @Override
    public void notificationRayonVide(ChocolatDeMarque choco, int crypto) {
        this.journalActeur.ajouter("Le rayon de "+choco.toString()+" est vide ");
    }
}
