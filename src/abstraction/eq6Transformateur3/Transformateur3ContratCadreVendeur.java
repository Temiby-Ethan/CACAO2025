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
    @Override //@author Henri Roth & Eric SCHILTZ
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
                            double capa = capacite_vente.get(hypo);
                            supCCadre.demandeVendeur((IAcheteurContratCadre)acteur, (IVendeurContratCadre)this,(IProduit) choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, capa), cryptogramme, false);
                        }
                        //pour tous les autres chocolats on les demande en tête de gondole 
                        //et il faut aussi demander pour les mêmes chocolats en non tête de gondole 
                        //car on nous le refusera dans 90% des cas et donc il faut aussi le demander 
                        //sans tête de gondole
                        else {
                            double capa = capacite_vente.get(choco);
                            supCCadre.demandeVendeur((IAcheteurContratCadre)acteur, (IVendeurContratCadre)this,(IProduit) choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, capa), cryptogramme, true);
                            supCCadre.demandeVendeur((IAcheteurContratCadre)acteur, (IVendeurContratCadre)this,(IProduit) choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, capa), cryptogramme, false);
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
        //quand le vendeur propose une tête de gondole on dit toujours oui. On ne le tient 
        //pas en compte. On pourrait les refuser une fois sur 10 par ex mais en fait : 
        //On ne fais pas la différene entre les propositions du vendeur et ses contrepropositions
        //à nos propositions. 
        //Sinon on aurait des problèmes, on risquerait de refuser des contrats 
        //que nous avons nous même émis sans tête de gondole. 
        //on regarde quel produit est concerné par le contrat
        IProduit p = contrat.getProduit();
        //on regarde si on vend/produit bien de ce chocolat
        if(super.lesChocolats.contains(p)){
            // si oui on calcule de la quantite par step de chocolat que l'on veut livrer
            //on regarde quelle est notre capacité de vente du produit 
            double capa = capacite_vente.get(p);
            //on récupère l'échéancier
            Echeancier e = contrat.getEcheancier();
            //on récupère le premier step
            int stepdebut = e.getStepDebut();
            //on rearde la quantité à livrer proposée
            double QuantiteStep = e.getQuantite(stepdebut);
            // si la quantité à livrer proposé est dans nos capacités on accepte
            if(QuantiteStep < capa){
                return contrat.getEcheancier();
            }
            //sinon
            else{
                //si elle est vrmt trop petite : on abandonne 
                if(capa < 100){
                    return null;
                }
                //sinon on refait l'échéancier avec ce que l'on a; 
                if(capa >100 && capa<1000){
                    int nb = e.getNbEcheances();
                    return new Echeancier(Filiere.LA_FILIERE.getEtape()+1, nb, capa);
                }
            }
        }
        //si on ne le vend/produit pas on ne fait rien 
        return null;
    }

    @Override //@author Henri Roth
    public double propositionPrix(ExemplaireContratCadre contrat) {
        // on retourne notre proposition de prix pour chaque 
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
        //à chaque fois on propose 1.2 fois le prix proposé par l'acheteur
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
