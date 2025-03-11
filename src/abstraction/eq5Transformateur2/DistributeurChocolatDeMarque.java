package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.IDistributeurChocolatDeMarque;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.clients.ClientFinal;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

public class DistributeurChocolatDeMarque extends AcheteurBourse implements IDistributeurChocolatDeMarque {
    public DistributeurChocolatDeMarque() {
        super();
    }

    @Override
    public double prix(ChocolatDeMarque choco) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prix'");
    }

    @Override
    public double quantiteEnVente(ChocolatDeMarque choco, int crypto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'quantiteEnVente'");
    }

    @Override
    public double quantiteEnVenteTG(ChocolatDeMarque choco, int crypto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'quantiteEnVenteTG'");
    }

    @Override
    public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant, int crypto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vendre'");
    }

    @Override
    public void notificationRayonVide(ChocolatDeMarque choco, int crypto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notificationRayonVide'");
    }
}
