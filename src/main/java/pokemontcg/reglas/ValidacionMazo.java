package pokemontcg.reglas;


import pokemontcg.cartas.Carta;
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

    public static void repartirManoInicial(Jugador jugador){
        boolean tieneBasico = false;

        while (!tieneBasico){
            jugador.getMazo().barajar();
            jugador.getMano().getCartas().clear();
            for (int i = 0; i < 5; i++){
                Carta cartaRobada = jugador.getMazo().robarCarta();
                jugador.getMano().agregarCarta(cartaRobada);
            }
            for (int i = 0; i < jugador.getMano().getCartas().size(); i++){
                Carta cartaEnMano = jugador.getMano().getCartas().get(i);
                if (cartaEnMano.getCategoria().equalsIgnoreCase("Pokemon Basico")){
                    tieneBasico = true;
                    break;
                }
            }
            if (!tieneBasico){
                System.out.println("¡"+ jugador.getNombre() + " no tiene Pokemon basicos. Barajando de nuevo...");
            }
        }
    }
    
}
