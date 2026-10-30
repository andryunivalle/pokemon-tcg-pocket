package pokemontcg.cartas;

public abstract class Entrenador extends Carta implements Activable {

    public Entrenador(String nombre, String categoria) {
        super(nombre, categoria);
    }
}