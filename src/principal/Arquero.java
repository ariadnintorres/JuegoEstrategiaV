package principal;

public class Arquero extends Personaje {
    public Arquero(int id, int equipo) {
        super("Arquero", id, equipo);
        this.vidaMax = 10; this.ataqueMax = 10; this.defensaMax = 5;
        this.radioAtaque = 3; this.movimientoMax = 2;
        this.vida = vidaMax;
    }
}
