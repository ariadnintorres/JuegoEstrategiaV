package principal;

import java.util.Random;


public class Personaje {

    String tipo;
    String jugador; 
    int fila;
    int columna;
    int vida;
    int vidaMax;
    int ataqueMax;
    int defensaMax;
    int radioAtaque;
    int movimientoMax;
    int equipo; 
	private int id;

    static Random random = new Random();

    //  CONSTRUCTOR PRINCIPAL
    
    public Personaje(String tipo, int id, int equipo) {
        this.tipo = tipo.toLowerCase();
        this.id = id;
        this.equipo = equipo;
        this.jugador = (equipo == 1) ? "A" : "B"; 

        configurarTipo();
        this.vida = vidaMax;
    }

   
   
    private void configurarTipo() {
//here
        switch (tipo) {

            case "soldado":
                vidaMax = 10;
                ataqueMax = 10;
                defensaMax = 10;
                radioAtaque = 1;
                movimientoMax = 1;
                break;

            case "caballero":
                vidaMax = 20;
                ataqueMax = 15;
                defensaMax = 15;
                radioAtaque = 2;
                movimientoMax = 3;
                break;

            case "arquero":
                vidaMax = 10;
                ataqueMax = 10;
                defensaMax = 5;
                radioAtaque = 3;
                movimientoMax = 2;
                break;

            case "lancero":
                vidaMax = 10;
                ataqueMax = 10;
                defensaMax = 5;
                radioAtaque = 2;
                movimientoMax = 2;
                break;

            default:
                throw new IllegalArgumentException("Tipo inválido: " + tipo);
        }
    }

    //  GETTERS
    public int getnumUnidad() { return this.id; }
    public int getVida() { return vida; }
    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public String getTipo() { return tipo; }

    // SET POSICIÓN
    public void setFila(int fila) { this.fila = fila; }
    public void setColumna(int columna) { this.columna = columna; }

    //  RANGO DE ATAQUE
    public boolean enRango(Personaje enemigo) {
        int dist = Math.max(
                Math.abs(fila - enemigo.fila),
                Math.abs(columna - enemigo.columna)
        );
        return dist <= radioAtaque;
    }

    //  CURAR
    public void curar() {
        int cura = random.nextInt(vidaMax + 1);
        vida = Math.min(vida + cura, vidaMax);
        System.out.println(tipo + " cura " + cura + " de vida");
    }

    //  ATAQUE
    public int calcularAtaque(Personaje enemigo) {

        int ataque = random.nextInt(ataqueMax + 1);

        // BONIFICACIONES
        if (tipo.equals("arquero") && enemigo.tipo.equals("lancero")) ataque *= 3;
        if (tipo.equals("soldado") && enemigo.tipo.equals("arquero")) ataque *= 2;
        if (tipo.equals("caballero") && enemigo.tipo.equals("arquero")) ataque *= 2;
        if (tipo.equals("caballero") && enemigo.tipo.equals("soldado")) ataque *= 2;
        if (tipo.equals("lancero") && enemigo.tipo.equals("caballero")) ataque *= 5;

        return ataque;
    }

    //  DEFENSA
    public int calcularDefensa() {
        return random.nextInt(defensaMax + 1);
    }

    //  ETIQUETA PARA TABLERO
    public String getEtiqueta() {

        String corto =tipo.length() >= 3
                ? tipo.substring(0, 3).toUpperCase()
                : String.format("%-3s", tipo).toUpperCase();

        return corto + vida + "v";
    }

	public Object getJugador() {
		
		return this.jugador;
	}



	public int setVida(int i) {
		return this.vida;
		
	}



	public int getId() {
		return this.id;
	}

}
