package pokemontcg.cartas;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Carta {

    private String tipo;
    private int puntosSalud;
    private int puntosSaludMaximos;
    private int costoRetirada;
    private String debilidad;
    private List<Ataque> ataques;
    private List<String> energiasAdjuntas;
    private boolean esEx;

    // Constructor
    public Pokemon(String nombre, String tipo, int puntosSalud,
                   int costoRetirada, boolean esEx) {

        this(nombre, tipo, puntosSalud, costoRetirada, "", esEx);
    }

    // Constructor con debilidad
    public Pokemon(String nombre, String tipo, int puntosSalud,
                   int costoRetirada, String debilidad, boolean esEx) {

        super(nombre, "Pokemon");

        this.tipo = tipo;
        this.puntosSalud = puntosSalud;
        this.puntosSaludMaximos = puntosSalud;
        this.costoRetirada = costoRetirada;
        this.debilidad = debilidad;
        this.esEx = esEx;

        this.ataques = new ArrayList<>();
        this.energiasAdjuntas = new ArrayList<>();
    }

    // Obtener el tipo
    public String getTipo() {
        return tipo;
    }

    // Obtener los puntos de salud actuales
    public int getPuntosSalud() {
        return puntosSalud;
    }

    // Obtener los puntos de salud máximos
    public int getPuntosSaludMaximos() {
        return puntosSaludMaximos;
    }

    // Obtener el costo de retirada
    public int getCostoRetirada() {
        return costoRetirada;
    }

    // Obtener la debilidad
    public String getDebilidad() {
        return debilidad;
    }

    // Obtener los ataques
    public List<Ataque> getAtaques() {
        return new ArrayList<>(ataques);
    }

    // Obtener las energías adjuntas
    public List<String> getEnergiasAdjuntas() {
        return new ArrayList<>(energiasAdjuntas);
    }

    // Comprobar si es un Pokémon ex
    public boolean esEx() {
        return esEx;
    }

    // Obtener los puntos de premio
    public int getPuntosPremio() {
        if (esEx) {
            return 2;
        }

        return 1;
    }

    // Agregar un ataque
    public void agregarAtaque(Ataque ataque) {
        if (ataque != null) {
            ataques.add(ataque);
        }
    }

    // Recibir daño
    public void recibirDanio(int danio) {
        if (danio > 0) {
            puntosSalud -= danio;

            if (puntosSalud < 0) {
                puntosSalud = 0;
            }
        }
    }

    // Curar puntos de salud
    public void curar(int cantidad) {
        if (cantidad > 0) {
            puntosSalud += cantidad;

            if (puntosSalud > puntosSaludMaximos) {
                puntosSalud = puntosSaludMaximos;
            }
        }
    }

    // Adjuntar una energía
    public void adjuntarEnergia(String tipoEnergia) {
        if (tipoEnergia != null && !tipoEnergia.isBlank()) {
            energiasAdjuntas.add(tipoEnergia);
        }
    }

    // Descartar una energía
    public boolean descartarEnergia(String tipoEnergia) {
        return energiasAdjuntas.remove(tipoEnergia);
    }
}