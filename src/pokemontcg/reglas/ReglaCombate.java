package pokemontcg.reglas;

import pokemontcg.cartas.Ataque;
import pokemontcg.cartas.Pokemon;
import pokemontcg.combate.Jugador;

public class ReglaCombate {

    /**
     * Valida si el Pokémon activo posee las energías necesarias para ejecutar el ataque.
     */
    public boolean puedeAtacar(Pokemon atacante, Ataque ataque) {
        if (atacante == null || ataque == null) {
            return false;
        }
        // Compara la cantidad de energías adjuntas contra el costo del ataque
        return atacante.getEnergiasAdjuntas().size() >= ataque.getCostoEnergia();
    }

    public void atacar(Pokemon atacante, Pokemon defensor, Ataque ataque, Jugador rival) {
        // 1. Validación de energías[cite: 5]
        if (!puedeAtacar(atacante, ataque)) {
            System.out.println("No se puede realizar el ataque: " + atacante.getNombre() + 
                               " no tiene suficientes energías adjuntas (" + 
                               atacante.getEnergiasAdjuntas().size() + "/" + ataque.getCostoEnergia() + ").");
            return;
        }

        int danioBase = ataque.getDanioBase();
        int danioFinal = danioBase;

        // 2. Aplicar bono de +20 por debilidad según la tabla oficial[cite: 5]
        if (esDebil(atacante.getTipo(), defensor.getTipo())) {
            danioFinal += 20;
            System.out.println("¡Golpe crítico! Debilidad (+20 de daño).");
        }    
            
        // 3. Aplicar el daño siempre al defensor[cite: 5]
        defensor.recibirDanio(danioFinal);
        System.out.println(atacante.getNombre() + " usó " + ataque.getNombre() + " e hizo " + danioFinal + " de daño.");

        // 4. Verificar si el Pokémon fue derrotado[cite: 5]
        if (defensor.getPuntosSalud() <= 0) {
            System.out.println("¡El Pokémon " + defensor.getNombre() + " ha sido derrotado!");
            int puntosPremio = defensor.esEx() ? 2 : 1;
            rival.agregarPuntosPremio(puntosPremio);
            System.out.println("El rival gana " + puntosPremio + " punto(s) de premio.");
        }
    }

    /**
     * Tabla de debilidades del documento oficial[cite: 5]:
     * - Fuego ataca a Agua (Agua es débil a Fuego)[cite: 5]
     * - Agua ataca a Fuego (Fuego es débil a Agua)[cite: 5]
     * - Eléctrico ataca a Agua (Agua es débil a Eléctrico)[cite: 5]
     * - Psíquico ataca a Incoloro (Incoloro es débil a Psíquico)[cite: 5]
     */
    private boolean esDebil(String tipoAtacante, String tipoDefensor) {
        if (tipoAtacante.equalsIgnoreCase("Fuego") && tipoDefensor.equalsIgnoreCase("Agua")) {
            return true;
        } else if (tipoAtacante.equalsIgnoreCase("Agua") && tipoDefensor.equalsIgnoreCase("Fuego")) {
            return true;
        } else if (tipoAtacante.equalsIgnoreCase("Electrico") && tipoDefensor.equalsIgnoreCase("Agua")) {
            return true;
        } else if (tipoAtacante.equalsIgnoreCase("Psiquico") && tipoDefensor.equalsIgnoreCase("Incoloro")) {
            return true;
        }
        return false;
    }

    public boolean verificarGanador(Jugador jugadorActual, Jugador rival) {
        if (jugadorActual.getPuntosPremio() >= 3) {
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