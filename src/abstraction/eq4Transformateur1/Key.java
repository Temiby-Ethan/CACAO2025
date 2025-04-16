package abstraction.eq4Transformateur1;
import abstraction.eqXRomu.produits.ChocolatDeMarque;

public class Key {
    private Integer someInt;
    private ChocolatDeMarque chocolat;

    public Key(Integer someInt, ChocolatDeMarque chocolat) {
        this.someInt = someInt;
        this.chocolat = chocolat;
    }

    // Getters, equals() et hashCode() sont essentiels pour que ça marche dans une HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return someInt.equals(key.someInt) && chocolat.equals(key.chocolat);
    }

    @Override
    public int hashCode() {
        return 31 * someInt.hashCode() + chocolat.hashCode();
    }
}
