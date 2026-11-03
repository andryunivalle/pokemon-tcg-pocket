package pokemontcg.reglas;


import pokemontcg.cartas.Carta;
import pokemontcg.cartas.Pokemon;
import pokemontcg.combate.Mazo;
import pokemontcg.combate.Jugador;

public class ValidacionMazo {

    public static boolean validarMazo(Mazo mazo) {
        if (mazo.getCartas().size() != 20){
            System.out.println("El mazo debe contener exactamente 20 cartas.");
            return false;
        }

        int contieneBasico = 0;
        java.util.List<Carta> listaCartas = mazo.getCartas();
        
        for (int i = 0; i < listaCartas.size(); i++){
            Carta cartaActual = listaCartas.get(i);

            if (cartaActual.getCategoria().equalsIgnoreCase("Pokemon Basico")){
                contieneBasico++;
            }

            String nombreActual = cartaActual.getNombre();
            int repeticiones = 0;

            for (int j = 0; j < listaCartas.size(); j++){
                if (listaCartas.get(j).getNombre().equals(nombreActual)){
                    repeticiones++;
                }

            }    

            if (repeticiones > 2){
                System.out.println("La carta " + nombreActual + " se repite más de 2 veces en el mazo.");
                return false;
            }
        }    

        if (contieneBasico < 6){
            System.out.println("El mazo debe contener al menos 6 Pokemon basicos.");
            return false;
        }
        System.out.println("¡Mazo validado exitosamente!");
        return true;
    }

    public static void repartirManoInicial(Jugador jugador) {
        if (jugador == null || jugador.getMazo() == null) return;

        boolean tieneBasico = false;

        while (!tieneBasico) {
            // 1. Devolver cartas de la mano al mazo si no había básico
            while (!jugador.getMano().getCartas().isEmpty()) {
                Carta cartaDevuelta = jugador.getMano().getCartas().remove(0);
                jugador.getMazo().agregarCarta(cartaDevuelta);
            }

            // 2. Barajar mazo completo
            jugador.getMazo().barajar();

            // 3. Robar 5 cartas iniciales
            for (int i = 0; i < 5; i++) {
                if (!jugador.getMazo().estaVacio()) {
                    Carta cartaRobada = jugador.getMazo().robarCarta();
                    jugador.getMano().agregarCarta(cartaRobada);
                }
            }

            // 4. Verificar si la nueva mano contiene al menos 1 Pokémon Básico
            for (Carta carta : jugador.getMano().getCartas()) {
                if (carta instanceof Pokemon) {
                    Pokemon p = (Pokemon) carta;
                    if ("Basico".equalsIgnoreCase(p.getEtapa()) || "Básico".equalsIgnoreCase(p.getEtapa())) {
                        tieneBasico = true;
                        break;
                    }
                }
            }
            
            if (!tieneBasico) {
                System.out.println("¡" + jugador.getNombre() + " no tiene Pokémon básicos. Rebarajando mano!");
            }
        }
    }

}
