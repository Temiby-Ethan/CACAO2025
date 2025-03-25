package abstraction.eq5Transformateur2;

import abstraction.eqXRomu.filiere.IFabricantChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import java.util.ArrayList;
import java.util.List;

public class FabricantChocolatDeMarque extends AcheteurBourse  implements IFabricantChocolatDeMarque {

    private List<ChocolatDeMarque> chocolatsProduits;

    public FabricantChocolatDeMarque() {
        super(); 
        this.chocolatsProduits = new ArrayList<>();
        
        
        this.chocolatsProduits.add(new ChocolatDeMarque(Chocolat.C_HQ_BE, "HQ Bio-equitable",100));
        this.chocolatsProduits.add(new ChocolatDeMarque(Chocolat.C_HQ_E, "HQ Equitable",100));
        this.chocolatsProduits.add(new ChocolatDeMarque(Chocolat.C_MQ_E, "MQ Equitable",100));
        this.chocolatsProduits.add(new ChocolatDeMarque(Chocolat.C_MQ, "MQ Base",100));

    }

    @Override
    public List<ChocolatDeMarque> getChocolatsProduits() {
        return this.chocolatsProduits;
    }
}
