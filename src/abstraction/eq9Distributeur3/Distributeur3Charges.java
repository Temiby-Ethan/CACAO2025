package abstraction.eq9Distributeur3;

import abstraction.eqXRomu.filiere.Filiere;

public class Distributeur3Charges extends Distributeur3ContratCadre {
    Distributeur3Charges(){
        super();
    }

    @Override
    public void next() {
        super.next();

        int nbEmployes = 3;
        //System.out.println("paiement des frais de stockage : "+this.stockTotal.getValeur(Filiere.LA_FILIERE.getEtape(),this.cryptogramme)+" tonnes ");
        Filiere.LA_FILIERE.getBanque().payerCout(this,this.cryptogramme,"Stockage",Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur()*16*this.stockTotal.getValeur(this.cryptogramme));
        double totalEnRayon = 0.0;

        //on itère sur les chocolats en vente chez nous
        //Filiere.LA_FILIERE.getBanque().payerCout(this,this.cryptogramme,"Mise en rayon",Filiere.LA_FILIERE.getParametre("cout mise en rayon").getValeur()*this.quantiteEnVente());
        Filiere.LA_FILIERE.getBanque().payerCout(this,this.cryptogramme,"Salaires",700*nbEmployes);

    }
}
