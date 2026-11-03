package pokemontcg;

import java.util.Scanner;
import pokemontcg.cartas.Carta;
import pokemontcg.cartas.Entrenador;
import pokemontcg.cartas.Pokemon;
import pokemontcg.combate.Jugador;
import pokemontcg.datos.CatalogoEntrenadores;
import pokemontcg.datos.CatalogoPokemon;
import pokemontcg.reglas.ValidacionMazo;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   === INICIANDO PÓKEMON TCG POCKET ===   ");
        System.out.println("==========================================");

        // 1. Inicializar Jugadores
        Jugador ash = new Jugador("Ash");
        Jugador gary = new Jugador("Gary");

        // 2. Poblar Mazos desde Catálogos
        for (Pokemon p : CatalogoPokemon.obtenerPokemon()) {
            ash.getMazo().agregarCarta(p);
            gary.getMazo().agregarCarta(p);
        }
        for (Carta e : CatalogoEntrenadores.obtenerEntrenadores()) {
            ash.getMazo().agregarCarta(e);
            gary.getMazo().agregarCarta(e);
        }

        // 3. Preparación Inicial (Mezclar y Repartir Mano)
        ValidacionMazo.repartirManoInicial(ash);
        ValidacionMazo.repartirManoInicial(gary);

        // Asignar Pokémon Activo Inicial
        ash.setPokemonActivo(CatalogoPokemon.obtenerPokemon().get(0)); // Pikachu / Mewtwo ex
        gary.setPokemonActivo(CatalogoPokemon.obtenerPokemon().get(1)); // Zapdos ex / Squirtle

        // 4. Bucle Principal de Juego (Game Loop)
        Jugador actual = ash;
        Jugador oponente = gary;
        int turno = 1;
        boolean juegoActivo = true;

        while (juegoActivo) {
            actual.robarCarta();
            actual.agregarEnergiaZona();

            boolean energiaAdjuntadaEsteTurno = false;
            boolean finTurno = false;

            while (!finTurno && juegoActivo) {
                // Imprimir el estado detallado del campo según la guía
                mostrarEstadoCampo(turno, actual, oponente, energiaAdjuntadaEsteTurno);

                System.out.println("\n¿Qué deseas hacer?");
                System.out.println("1. Adjuntar energía de zona");
                System.out.println("2. Jugar carta de entrenador");
                System.out.println("3. Colocar Pokémon en banco");
                System.out.println("4. Retirar Pokémon activo");
                System.out.println("5. Atacar");
                System.out.println("6. Pasar turno");
                System.out.println("==========================================");
                System.out.print("Selecciona una opción (1-6): ");

                String opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        if (energiaAdjuntadaEsteTurno) {
                            System.out.println("⚠️ Ya adjuntaste energía este turno.");
                        } else if (actual.getZonaEnergia() <= 0) {
                            System.out.println("⚠️ No tienes energía disponible en la zona.");
                        } else if (actual.getPokemonActivo() == null) {
                            System.out.println("⚠️ No tienes un Pokémon activo.");
                        } else {
                            actual.getPokemonActivo().adjuntarEnergia("⚡");
                            actual.gastarEnergiaZona();
                            energiaAdjuntadaEsteTurno = true;
                            System.out.println("⚡ ¡Energía adjuntada a " + actual.getPokemonActivo().getNombre() + "!");
                        }
                        break;

                    case "2":
                        boolean seJugoEntrenador = false;
                        for (int i = 0; i < actual.getMano().getCartas().size(); i++) {
                            Carta c = actual.getMano().getCartas().get(i);
                            if (c instanceof Entrenador) {
                                System.out.println("📜 Se jugó el Entrenador: " + c.getNombre());
                                actual.getMano().getCartas().remove(i);
                                seJugoEntrenador = true;
                                break;
                            }
                        }
                        if (!seJugoEntrenador) {
                            System.out.println("⚠️ No tienes cartas de Entrenador en la mano.");
                        }
                        break;

                    case "3":
                        boolean seColocoEnBanco = false;
                        for (int i = 0; i < actual.getMano().getCartas().size(); i++) {
                            Carta c = actual.getMano().getCartas().get(i);
                            if (c instanceof Pokemon) {
                                Pokemon pk = (Pokemon) actual.getMano().getCartas().remove(i);
                                actual.getBanco().agregarPokemon(pk);
                                System.out.println("🛡️ ¡" + pk.getNombre() + " fue colocado en el Banco!");
                                seColocoEnBanco = true;
                                break;
                            }
                        }
                        if (!seColocoEnBanco) {
                            System.out.println("⚠️ No tienes cartas de Pokémon en mano para enviar al banco.");
                        }
                        break;

                    case "4":
                        if (actual.getBanco().getPokemones().isEmpty()) {
                            System.out.println("⚠️ No tienes Pokémon en el banco.");
                        } else {
                            Pokemon viejoActivo = actual.getPokemonActivo();
                            Pokemon nuevoActivo = actual.getBanco().getPokemones().remove(0);
                            if (viejoActivo != null) {
                                actual.getBanco().agregarPokemon(viejoActivo);
                            }
                            actual.setPokemonActivo(nuevoActivo);
                            System.out.println("🔄 ¡Se retiró a " + (viejoActivo != null ? viejoActivo.getNombre() : "N/A") + "! Nuevo activo: " + nuevoActivo.getNombre());
                        }
                        break;

                    case "5":
                        if (actual.getPokemonActivo() != null && oponente.getPokemonActivo() != null) {
                            int danio = 20;
                            oponente.getPokemonActivo().recibirDanio(danio);
                            System.out.println("\n⚔️ ¡" + actual.getPokemonActivo().getNombre() + " ataca causando " + danio + " de daño!");

                            if (oponente.getPokemonActivo().estaDebilitado()) {
                                System.out.println("💀 ¡" + oponente.getPokemonActivo().getNombre() + " ha sido debilitado!");
                                actual.agregarPuntosPremio(1);
                                oponente.setPokemonActivo(null);

                                if (actual.ganoJuego()) {
                                    System.out.println("\n🎉🎉 ¡" + actual.getNombre() + " HA GANADO LA PARTIDA! 🎉🎉");
                                    juegoActivo = false;
                                }
                            }
                        } else {
                            System.out.println("⚠️ No hay Pokémon activo en combate para atacar.");
                        }
                        finTurno = true;
                        break;

                    case "6":
                        System.out.println("⏩ Pasando turno...");
                        finTurno = true;
                        break;

                    default:
                        System.out.println("⚠️ Opción inválida. Selecciona un número del 1 al 6.");
                        break;
                }
            }

            // Cambiar turnos
            Jugador temp = actual;
            actual = oponente;
            oponente = temp;
            turno++;
        }

        System.out.println("\n=== PARTIDA FINALIZADA ===");
    }

    // Método de renderizado del tablero según el diseño exacto de la interfaz
    private static void mostrarEstadoCampo(int turno, Jugador actual, Jugador oponente, boolean energiaUsada) {
        System.out.println("\n======================================================================");
        System.out.println("TURNO " + turno + " — " + actual.getNombre());
        System.out.println("======================================================================");

        // --- RIVAL ---
        Pokemon actRival = oponente.getPokemonActivo();
        String infoActRival = (actRival != null)
                ? "[Activo] " + actRival.getNombre() + "   Energía adjunta: " + formatoEnergias(actRival)
                : "[Activo] Ninguno";
        String psRival = (actRival != null) ? actRival.getPuntosSalud() + "/" + actRival.getPuntosSaludMaximos() : "0/0";

        System.out.printf("RIVAL (%s)        PS: %s\n", oponente.getNombre(), psRival);
        System.out.println(infoActRival);

        StringBuilder bancoRival = new StringBuilder("[Banco] ");
        if (oponente.getBanco().getPokemones().isEmpty()) {
            bancoRival.append("(vacío)");
        } else {
            for (Pokemon p : oponente.getBanco().getPokemones()) {
                bancoRival.append(p.getNombre()).append(" (").append(p.getPuntosSalud()).append(" PS) ");
            }
        }
        System.out.println(bancoRival.toString());
        System.out.println("Puntos de premio rival: " + oponente.getPuntosPremio() + "/3");

        System.out.println("----------------------------------------------------------------------");

        // --- TU CAMPO ---
        Pokemon actPropio = actual.getPokemonActivo();
        String infoActPropio = (actPropio != null)
                ? "[Activo] " + actPropio.getNombre() + "   Energía adjunta: " + formatoEnergias(actPropio)
                : "[Activo] Ninguno";
        String psPropio = (actPropio != null) ? actPropio.getPuntosSalud() + "/" + actPropio.getPuntosSaludMaximos() : "0/0";

        System.out.printf("TU CAMPO (%s)     PS: %s\n", actual.getNombre(), psPropio);
        System.out.println(infoActPropio);

        StringBuilder bancoPropio = new StringBuilder("[Banco] ");
        if (actual.getBanco().getPokemones().isEmpty()) {
            bancoPropio.append("(vacío)");
        } else {
            for (Pokemon p : actual.getBanco().getPokemones()) {
                bancoPropio.append(p.getNombre()).append(" (").append(p.getPuntosSalud()).append(" PS) ");
            }
        }
        System.out.println(bancoPropio.toString());
        System.out.println("Puntos de premio propios: " + actual.getPuntosPremio() + "/3");
        System.out.println("Zona de energía: 🔮 (" + (energiaUsada ? "usada" : "disponible") + ")");

        // --- TU MANO ---
        System.out.println("\nTu mano (" + actual.getMano().getCartas().size() + " cartas):");
        for (int i = 0; i < actual.getMano().getCartas().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + actual.getMano().getCartas().get(i).getNombre());
        }
        System.out.println("======================================================================");
    }

    private static String formatoEnergias(Pokemon p) {
        if (p == null || p.getEnergiasAdjuntas().isEmpty()) {
            return "Ninguna";
        }
        return String.join(" ", p.getEnergiasAdjuntas());
    }
}