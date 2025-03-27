package abstraction.eq6Transformateur3;

import java.util.LinkedList;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.produits.IProduit;

public class Transformateur3ContratCadreVendeur extends Transformateur3Fabriquant implements IVendeurContratCadre {

    protected LinkedList<ExemplaireContratCadre> ContratsVendeur;
    
    public Transformateur3ContratCadreVendeur() {
        this.ContratsVendeur=new LinkedList<ExemplaireContratCadre>();
	}
    @Override
    public void next(){
        super.next();
        SuperviseurVentesContratCadre supCCadre = (SuperviseurVentesContratCadre) Filiere.LA_FILIERE.getActeur("Sup.CCadre");
        for(IProduit choco : super.lesChocolats){
            if(stockChoco.getQuantityOf(choco)>1000){
                for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			        if (acteur!=this && acteur instanceof IAcheteurContratCadre && ((IAcheteurContratCadre)acteur).achete(choco)) {
				        supCCadre.demandeVendeur((IAcheteurContratCadre)acteur, (IVendeurContratCadre)this,(IProduit) choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 100.0), cryptogramme, false);
                    }
                }
            }
        }
    }

    @Override
    public boolean vend(IProduit produit) {
        if(super.lesChocolats.contains(produit)){
            if(stockChoco.getQuantityOf(produit)>100){
                return true;
            }
        }
        return false;
    }

    @Override
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        IProduit p = contrat.getProduit();
        if(super.lesChocolats.contains(p)){
            return contrat.getEcheancier();
        }
        return null;
    }

    @Override
    public double propositionPrix(ExemplaireContratCadre contrat) {
        return 3000;
    }

    @Override
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        return contrat.getPrix();
    }

    @Override
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        journalCC.ajouter("Nouveau contrat cadre" +contrat);
        ContratsVendeur.add(contrat);
    }

    @Override
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        double stockActuel = stockChoco.getQuantityOf(produit);
		double aLivre = Math.min(quantite, stockActuel);
		journalCC.ajouter("   Livraison de "+aLivre+" T de "+produit+" sur "+quantite+" exigees pour contrat "+contrat.getNumero());
		stockChoco.remove(produit, aLivre);
		return aLivre;
	}
    
}
