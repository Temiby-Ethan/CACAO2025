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
// @author Henri Roth & Eric Schiltz
public class Transformateur3ContratCadreVendeur extends Transformateur3Fabriquant implements IVendeurContratCadre {
    //des attributs
    protected LinkedList<ExemplaireContratCadre> ContratsVendeur;
    //des constructeurs
    //on utilise à chaque fois des instances de cette classe quand on fait des contrats cadres
    public Transformateur3ContratCadreVendeur() {
        this.ContratsVendeur=new LinkedList<ExemplaireContratCadre>();
	}
    //des méthodes
    //à chaque next on va proposer des contrats cadres pour vendre du chocolat
    @Override //@author Henri Roth
    public void next(){
        //on récupère le next de tous les pères
        super.next();
        //on crée un contrat cadre
        SuperviseurVentesContratCadre supCCadre = (SuperviseurVentesContratCadre) Filiere.LA_FILIERE.getActeur("Sup.CCadre");
        //on parcourt tous les types de chocolat
        for(IProduit choco : super.lesChocolats){
            //on met en vente dès que on a plus de 1000 unités de ce chocolat
            if(stockChoco.getQuantityOf(choco)>1000){
                //on parcourt les acteurs de la filière
                for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
                    //si l'acteur n'est pas nous et si l'acteur achète des contrats cadres et s'il achète
                    //du chocolat par contrat cadre
			        if (acteur!=this && acteur instanceof IAcheteurContratCadre && ((IAcheteurContratCadre)acteur).achete(choco)) {
                        //on propose un contrat cadre à l'acteur en question qui démarre à l'étape
                        //suivante de la filière, qui dure 10 step avec une quantité livrée de 100 unités
                        //par step et avec ou non tête de gondole ici en fonction du chocolat
                        // pour hypocritolat on le demande pas 
                        if (choco==hypo){
                            supCCadre.demandeVendeur((IAcheteurContratCadre)acteur, (IVendeurContratCadre)this,(IProduit) choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 1000.0), cryptogramme, false);
                        }
                        //pour tous les autres chocolats on le demande
                        else {
                            supCCadre.demandeVendeur((IAcheteurContratCadre)acteur, (IVendeurContratCadre)this,(IProduit) choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 1000.0), cryptogramme, true);
                        }
                    }
                }
            }
        }
    }

    @Override //@author Eric Schiltz
    public boolean vend(IProduit produit) {
        //quand un acheteur parcours les transformateurs à la recherche de quelqu'un qui vend en 
        //contrat cadre tel type de chocolat. C'est la méthode de réponse à cette demande.
        //On considère que l'on est disponible pour un contrat cadre quand notre notre stock du produit en
        //question est supérieur à 100. Problème : si notre stock est au dessus de 100 on passe donc tous
        //les contrats cadres qui nous sont demandé. Cette condition ne reflète pas correctement ce que
        //l'on veut
        //On va chercher à savoir combien de ce produit on doit livrer pour le step d'après 
        //Pour cela on parcourt les contrats cadres déja passés et on regarde si ils correspondent au
        // produit demandé, et combien il demande chaque step et si il faut on l'ajoute à un compteur
        // et in fine on regarde si ce notre stock - ça est supérieur à 100. 
        double a = 0;
        for (ExemplaireContratCadre contrat : ContratsVendeur){
            if (contrat.getProduit()==produit){
                a += contrat.getQuantiteALivrerAuStep();
            }
        }
        if(super.lesChocolats.contains(produit)){
            if(stockChoco.getQuantityOf(produit)-a>100){
                return true;
            }
        }
        return false;
    }

    @Override //@author Eric Schiltz
    public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
        IProduit p = contrat.getProduit();
        if(super.lesChocolats.contains(p)){
            // Calcul de la quantite par step de chocolat à livrer
            double capa = capacite_vente.get(p);
            Echeancier e = contrat.getEcheancier();
            int stepdebut = e.getStepDebut();
            double QuantiteStep = e.getQuantite(stepdebut);
            if(QuantiteStep < capa){
                return contrat.getEcheancier();
            }
            else{
                if(capa < 100){
                    return null;
                }
                if(capa >100 && capa<1000){
                    int nb = e.getNbEcheances();
                    return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nb, capa);
                }
            }
        }
        return null;
    }

    @Override //@author Henri Roth
    public double propositionPrix(ExemplaireContratCadre contrat) {
        // Calcul de la quantite par step de chocolat à livrer
        IProduit choco = contrat.getProduit();
        if(choco.equals((IProduit)fraud)){
            return 150000;
        }
        if(choco.equals((IProduit)bollo)){
            return 200000;
        }
        if(choco.equals((IProduit)arna)){
            return 300000;
        }
        else{
            return 500000;
        }
    }

    @Override //@author Henri Roth
    public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
        return contrat.getPrix()*1.2;
    }

    @Override //@author Henri Roth
    public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
        journalCC.ajouter("Nouveau contrat cadre" +contrat);
        ContratsVendeur.add(contrat);
        IProduit produit = contrat.getProduit();
        double QuantiteAuStep = contrat.getQuantiteALivrerAuStep();
        double c_v = capacite_vente.get(produit);
        c_v -= QuantiteAuStep;
        capacite_vente.replace(produit, c_v);
    }

    @Override //@author Henri Roth
    public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
        double stockActuel = stockChoco.getQuantityOf(produit);
		double aLivre = Math.min(quantite, stockActuel);
		journalCC.ajouter("   Livraison de "+aLivre+" T de "+produit+" sur "+quantite+" exigees pour contrat "+contrat.getNumero());
		stockChoco.remove(produit, aLivre);
		return aLivre;
	}
    
}
