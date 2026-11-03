package pokemontcg.combate;

import java.util.ArrayList;
import java.util.List;

import pokemontcg.cartas.Pokemon;

public class Banco {

    private List<Pokemon> pokemonEnBanco;
    private int limite = 2;

    public Banco() {
        this.pokemonEnBanco = new ArrayList<>();
    }

    public boolean agregarPokemon(Pokemon pokemon) {
        if (pokemonEnBanco.size() < limite) {
            pokemonEnBanco.add(pokemon);
            System.out.println(pokemon.getNombre() + " ha sido agregado al banco.");
            return true;
        } else{
            System.out.println("¡Error! El banco está lleno (maximo 2 Pokemon).");
            return false;
        }
    }

    public Pokemon removerPokemon(int indice){
        if (indice >= 0 && indice < pokemonEnBanco.size()){
            return pokemonEnBanco.remove(indice);
        }
        return null;
    }

    public boolean tieneEspacio() {
        return pokemonEnBanco.size() < limite;
    }
    public boolean estaVacio() {
        return pokemonEnBanco.isEmpty();
    }
    public void mostrarBanco(){
        System.out.println("Banco(" + pokemonEnBanco.size() + "/2):");
        if (pokemonEnBanco.isEmpty()){
            System.out.println("[ Vacío ]");
        }else{
            for (int i = 0; i < pokemonEnBanco.size(); i++){
                Pokemon p = pokemonEnBanco.get(i);
                System.out.println(" " + (i + 1) + ". " + p.getNombre() + " (PS: " + p.getPuntosSalud() + ")");
            }
        }
    }
    public List<Pokemon> getPokemonEnBanco() {
        return pokemonEnBanco;
    }
    
    
}
