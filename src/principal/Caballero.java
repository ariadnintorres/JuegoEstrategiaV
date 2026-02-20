package principal;

public class Caballero extends Personaje {
    public Caballero(int id, int equipo) {
        super("Caballero", id, equipo);
        this.vidaMax = 20; this.ataqueMax = 15; this.defensaMax = 15;
        this.radioAtaque = 2; this.movimientoMax = 3;
        this.vida = vidaMax;
    }
}