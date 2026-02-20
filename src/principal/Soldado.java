package principal;

	public class Soldado extends Personaje {
	    public Soldado(int id, int equipo) {
	        super("Soldado", id, equipo);
	        this.vidaMax = 10; this.ataqueMax = 10; this.defensaMax = 10;
	        this.radioAtaque = 1; this.movimientoMax = 1;
	        this.vida = vidaMax;
	    }
	}

	