package pokemontcg.cartas;

import java.util.Iterator;
import pokemontcg.combate.Jugador;

public class Objeto extends Entrenador {

    private Pokemon pokemonAfectado;
    private boolean turboActivo;

    public Objeto(String nombre) {
        super(nombre, "Objeto");
        this.pokemonAfectado = null;
        this.turboActivo = false;
    }

    @Override
    public void activarEfecto(Jugador jugador, Pokemon objetivo) {

        if (jugador == null) {
            return;
        }

        switch (getNombre()) {

            case "Poción":
                Pokemon pokemonActivo = jugador.getPokemonActivo();

                if (pokemonActivo != null) {
                    pokemonActivo.curar(20);
                    System.out.println("El Pokémon activo recuperó 20 puntos de salud.");
                } else {
                    System.out.println("No hay un Pokémon activo para curar.");
                }
                break;

            case "Poké Ball":
                Iterator<Carta> iterador =
                        jugador.getMazo().getCartas().iterator();

                while (iterador.hasNext()) {
                    Carta carta = iterador.next();

                    if (carta instanceof Pokemon) {
                        iterador.remove();
                        jugador.getMano().agregarCarta(carta);

                        System.out.println("Se agregó " + carta.getNombre()
                                + " a la mano.");
                        return;
                    }
                }

                System.out.println("No se encontraron Pokémon en el mazo.");
                break;

            case "Turbo Velocidad":
                pokemonAfectado = jugador.getPokemonActivo();

                if (pokemonAfectado != null) {
                    turboActivo = true;
                    System.out.println("El costo de retirada se redujo en 1 durante este turno.");
                } else {
                    System.out.println("No hay un Pokémon activo.");
                }
                break;

            default:
                System.out.println("Objeto no reconocido.");
                break;
        }
    }

    public int getCostoRetiradaTemporal() {

        if (turboActivo && pokemonAfectado != null) {
            return Math.max(0, pokemonAfectado.getCostoRetirada() - 1);
        }

        return -1;
    }

    public void finalizarTurno() {
        turboActivo = false;
        pokemonAfectado = null;
    }
}