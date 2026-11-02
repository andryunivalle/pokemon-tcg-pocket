package pokemontcg.reglas;

import pokemontcg.cartas.Ataque;
import pokemontcg.cartas.Pokemon;
import pokemontcg.combate.Jugador;

public class ReglaCombate {

    public void atacar (Pokemon atacante, Pokemon defensor, Ataque ataque, Jugador rival){

        int danioBase = ataque.getDanioBase();
        int danioFinal = danioBase;

        if (esDebil(atacante.getTipo(), defensor.getTipo())){
            danioFinal += 20;
            System.out.println("¡Golpe crítico! Debilidad (+20 de dano).");

            defensor.recibirDanio(danioFinal);
            System.out.println(atacante.getNombre() + " uso " + ataque.getNombre() + " e hizo " + danioFinal + " de danio.");
        }    
            
        if (defensor.getPuntosSalud() <= 0){
            System.out.println("¡El Pokemon " + defensor.getNombre() + " ha sido derrotado!");
            int puntosPremio = defensor.esEx() ? 2 : 1;
            rival.agregarPuntosPremio(puntosPremio);
            System.out.println("El rival gana " + puntosPremio + " punto(s) de premio.");
        }
    }

    private boolean esDebil(String tipoAtacante, String tipoDefensor){
        if (tipoAtacante.equalsIgnoreCase("Fuego") && tipoDefensor.equalsIgnoreCase("Agua")){
            return true;
        } else if (tipoAtacante.equalsIgnoreCase("Agua") && tipoDefensor.equalsIgnoreCase("Planta")){
            return true;
        } else if (tipoAtacante.equalsIgnoreCase("Electrico") && tipoDefensor.equalsIgnoreCase("Agua")){
            return true;
        } else if (tipoAtacante.equalsIgnoreCase("Psiquico") && tipoDefensor.equalsIgnoreCase("Incoloro")){
            return true;
        }
        return false;
    }

    public boolean verificarGanador (Jugador jugadorActual, Jugador rival) {
        if (jugadorActual.getPuntosPremio() >= 3){
            System.out.println("¡" + jugadorActual.getNombre() + " ha ganado la partida!");
            return true;
        }

        if (rival.getMazo().estaVacio()) {
        System.out.println("¡" + jugadorActual.getNombre() + " gana porque el rival se quedó sin cartas en el mazo!");
        return true;
        }
        
        if (rival.getPokemonActivo() == null && rival.getBanco().estaVacio()) {
            System.out.println("¡" + jugadorActual.getNombre() + " gana porque el rival se quedó sin Pokémon en juego!");
            return true;
        }

        return false;
        
    }
}    
