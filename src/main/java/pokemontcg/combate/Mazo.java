package pokemontcg.combate;

import java.util.ArrayList;
import java.util.List;

import pokemontcg.cartas.Carta;

public class Mazo {

    private List<Carta> cartas;

    public Mazo(List<Carta> cartasIniciales) {
        this.cartas = new ArrayList<>(cartasIniciales);
    }

    public void barajar(){
        int n = cartas.size();
        for (int i= n-1; i>0; i--){
            int indiceManual = (int) (Math.random() * (i + 1));
            Carta temp = this.cartas.get(i);
            this.cartas.set(i, this.cartas.get(indiceManual));
            this.cartas.set(indiceManual, temp);
        } 
    }

    public Carta robarCarta(){
        if (!cartas.isEmpty()) {
            return cartas.remove(0);
        }
        return null;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public boolean estaVacio() {
        return cartas.isEmpty();
    }


    
}
