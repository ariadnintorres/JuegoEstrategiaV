package principal;


public class Lancero extends Personaje {
    public Lancero(int id, int equipo) {
        super("Lancero", id, equipo);
        this.vidaMax = 10; this.ataqueMax = 10; this.defensaMax = 5;
        this.radioAtaque = 2; this.movimientoMax = 2;
        this.vida = vidaMax;
    }
}

