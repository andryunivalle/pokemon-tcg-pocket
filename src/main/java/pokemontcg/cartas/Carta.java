package pokemontcg.cartas;

public abstract class Carta {

    private String nombre;
    private String categoria;

    public Carta(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }
}