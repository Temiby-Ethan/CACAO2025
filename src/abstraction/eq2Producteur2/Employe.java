// Thomas BRUN
package abstraction.eq2Producteur2;

public class Employe {
    public enum TypeEmploye {
        ENFANT(6.0f, 0.5f), 
        ADULTE_NON_FORME(15.0f, 1.0f), 
        ADULTE_FORME(37.5f, 1.5f);

        private final float salaire;
        private final float productivite;

        TypeEmploye(float salaire, float productivite) {
                            this.salaire = salaire;
            this.productivite = productivite;
        }

        public float getSalaire() {
            return salaire;
        }

        public float getProductivite() {
            return productivite;
        }
    }

    private final TypeEmploye type;

    public Employe(TypeEmploye type) {
        this.type = type;
    }

    public float getSalaire() {
        return type.getSalaire();
    }

    public float getProductivite() {
        return type.getProductivite();
    }

    public TypeEmploye getType() {
        return type;
    }

}
