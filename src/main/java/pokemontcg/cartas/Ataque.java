package pokemontcg.cartas;

public class Ataque {

    private String nombre;
    private String tipoEnergia;
    private int cantidadEnergia;
    private int danioBase;

    public Ataque(String nombre, String tipoEnergia,
                  int cantidadEnergia, int danioBase) {

        this.nombre = nombre;
        this.tipoEnergia = tipoEnergia;
        this.cantidadEnergia = cantidadEnergia;
        this.danioBase = danioBase;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoEnergia() {
        return tipoEnergia;
    }

    public int getCantidadEnergia() {
        return cantidadEnergia;
    }

    public int getDanioBase() {
        return danioBase;
    }
}