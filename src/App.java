import java.util.Scanner;

public class App {
    private Scanner scanner;

    public App() {
        this.scanner = new Scanner(System.in);
    }

    public String[] mostrarMenuInicial() {
        System.out.println("=== BIENVENIDO A POKÉMON TCG POCKET ===");
        System.out.print("Ingrese el nombre del Jugador 1: ");
        String jugador1 = scanner.nextLine();

        System.out.print("Ingrese el nombre del Jugador 2: ");
        String jugador2 = scanner.nextLine();

        System.out.println("\n¡Listos para el combate, " + jugador1 + " y " + jugador2 + "!\n");
        return new String[]{jugador1, jugador2};
    }

    public static void main(String[] args) {
        App app = new App();

        String[] jugadores = app.mostrarMenuInicial(); // Captura los nombres ingresados
        app.ejecutarJuego(jugadores[0], jugadores[1]); // Inicia el flujo con los nombres reales
    }

    public int leerOpcionMenu() {
        System.out.println("--- MENÚ DE ACCIONES ---");
        System.out.println("1. Adjuntar energía de la zona");
        System.out.println("2. Jugar carta de entrenador");
        System.out.println("3. Colocar Pokémon en el banco");
        System.out.println("4. Retirar Pokémon activo");
        System.out.println("5. Atacar");
        System.out.println("6. Pasar turno");
        System.out.println("7. Salir del juego");
        
        int opcion = -1;
        while (opcion < 1 || opcion > 7) {
            System.out.print("¿Qué deseas hacer? (1-7): ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                if (opcion < 1 || opcion > 7) {
                    System.out.println("Opción no válida. Ingrese un número del 1 al 7.");
                }
            } else {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // Limpiar entrada incorrecta
            }
        }
        return opcion;
    }

    public void mostrarCampo(String jugadorActivo, String jugadorRival, int numeroTurno) {
        System.out.println("\n=============================================");
        System.out.println("          TURNO " + numeroTurno + " - " + jugadorActivo.toUpperCase());
        System.out.println("=============================================");
        
        System.out.println("\n--- RIVAL: " + jugadorRival + " ---");
        System.out.println("  [Activo] Pikachu (PS: 60/60) | Energía: 1");
        System.out.println("  [Banco]  Bulbasaur (PS: 70/70)");
        System.out.println("  Puntos de premio rival: 0/3");
        System.out.println("---------------------------------------------");

        System.out.println("\n--- TU CAMPO (" + jugadorActivo + ") ---");
        System.out.println("  [Activo] Charmander (PS: 70/70) | Energía: 1");
        System.out.println("  [Banco]  Squirtle (PS: 60/60)");
        System.out.println("  Puntos de premio propios: 0/3");
        System.out.println("  Zona de Energía: [Disponible]");
        System.out.println("---------------------------------------------");

        System.out.println("\nTu mano (2 cartas):");
        System.out.println("  1. Potion (Entrenador)");
        System.out.println("  2. Charmander (Pokémon)");
        System.out.println("=============================================\n");
    }

    public void ejecutarJuego(String jugador1, String jugador2) {
        boolean turnoJugador1 = true;
        int numeroTurno = 1;
        boolean juegoActivo = true;

        while (juegoActivo) {
            String activo = turnoJugador1 ? jugador1 : jugador2;
            String rival = turnoJugador1 ? jugador2 : jugador1;

            mostrarCampo(activo, rival, numeroTurno);
            int opcion = leerOpcionMenu();

            if (opcion == 6) {
                System.out.println("\n>>> " + activo + " ha pasado el turno. <<<\n");
                turnoJugador1 = !turnoJugador1; // Cambia el turno al otro jugador
                numeroTurno++;
            } else if (opcion == 7) {
                System.out.println("\n¡Gracias por jugar! Partida finalizada.\n");
                juegoActivo = false; // Rompe el bucle while limpiamente
            } else {
                System.out.println("\nEjecutando opción " + opcion + "...\n");
            }
        }
    }
}
