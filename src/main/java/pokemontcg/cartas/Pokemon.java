package pokemontcg.cartas;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Carta {

    private String tipo;
    private int puntosSalud;
    private int puntosSaludMaximos;
    private int costoRetirada;
    private List<Ataque> ataques;
    private boolean esEx;

    public Pokemon(String nombre, String tipo, int puntosSalud,
                   int costoRetirada, boolean esEx) {

        super(nombre, "Pokemon");

        this.tipo = tipo;
        this.puntosSalud = puntosSalud;
        this.puntosSaludMaximos = puntosSalud;
        this.costoRetirada = costoRetirada;
        this.esEx = esEx;
        this.ataques = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public int getPuntosSalud() {
        return puntosSalud;
    }

    public int getPuntosSaludMaximos() {
        return puntosSaludMaximos;
    }

    public int getCostoRetirada() {
        return costoRetirada;
    }

    public List<Ataque> getAtaques() {
        return ataques;
    }

    public boolean esEx() {
        return esEx;
    }

    public int getPuntosPremio() {
        if (esEx) {
            return 2;
        }

        return 1;
    }

    public void agregarAtaque(Ataque ataque) {
        ataques.add(ataque);
    }
}