package abstraction.eq1Producteur1;

public class Producteur1 extends Producteur1Acteur  {
    
    public Producteur1() {
        super();
    }

    @Override
    public void next() {
        super.next(); // Appel de la méthode next de Producteur1Acteur
        vendreEnBourse(120, "F_MQ");
    }

    private void vendreEnBourse(int quantite, String produit) {
        // Implémentation de la vente en bourse
        journal.ajouter("Vente de " + quantite + " tonnes de " + produit + " en bourse.");
        
    }
}
