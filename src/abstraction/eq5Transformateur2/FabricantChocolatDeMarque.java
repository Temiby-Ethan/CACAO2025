package abstraction.eq5Transformateur2;

import java.util.List;
import java.util.ArrayList;

import abstraction.eqXRomu.filiere.IFabricantChocolatDeMarque;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.clients.ClientFinal;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

public class FabricantChocolatDeMarque extends AcheteurBourse implements IFabricantChocolatDeMarque {
    private List<ChocolatDeMarque> chocolatsProduits;

    public FabricantChocolatDeMarque() {
        super();
    }

    @Override
    public List<ChocolatDeMarque> getChocolatsProduits() {
        return this.chocolatsProduits;
    }
}