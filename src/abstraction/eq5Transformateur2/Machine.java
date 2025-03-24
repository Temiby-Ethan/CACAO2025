package abstraction.eq5Transformateur2;


import abstraction.eqXRomu.filiere.Filiere;


public class Machine extends Employes{
    
    private double nbrMachineTotal;
    private double capaciteProduction;
    private double prixMachine;
    private double pourcentageRevente;
    
    public Machine(){
        super();
        this.nbrMachineTotal=128;
        this.capaciteProduction= 10000000;
        this.prixMachine =500000;
        this.pourcentageRevente= 0.6;
    }
    
    public void achatMachine(double nbrMachine){
        this.nbrMachineTotal += nbrMachine;
        Filiere.LA_FILIERE.getBanque().payerCout(this ,super.cryptogramme,  "Machine",nbrMachineTotal*prixMachine);

    }
}
