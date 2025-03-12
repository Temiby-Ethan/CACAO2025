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
        
        
        this.chocolatsProduits.add(new ChocolatDeMarque(Chocolat.C_MQ, "EQ5Marque1",100));
    }

    @Override
    public List<ChocolatDeMarque> getChocolatsProduits() {
        return this.chocolatsProduits;
    }
}
