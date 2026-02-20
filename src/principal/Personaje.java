package principal;

import java.util.Random;

public abstract class Personaje {

    // 1. ATRIBUTOS
    protected String tipo;
    protected String jugador; // "A" o "B"
    protected int equipo;    // 1 o 2
    protected int id;
    protected int fila;
    protected int columna;
    protected int vida;
    protected int vidaMax;
    protected int ataqueMax;
    protected int defensaMax;
    protected int radioAtaque;
    protected int movimientoMax;
    
    protected static Random random = new Random();

    // 2. CONSTRUCTOR
    public Personaje(String tipo, int id, int equipo) {
        this.tipo = tipo.toLowerCase();
        this.id = id;
        this.equipo = equipo;
        this.jugador = (equipo == 1) ? "A" : "B";
        // Nota: La vida se inicializa en las clases hijas tras definir vidaMax
    }

    // 3. MÉTODOS DE COMBATE Y LÓGICA
    public int calcularAtaque(Personaje enemigo) {
        double bono = 1.0;
        String tAtk = this.tipo.toLowerCase();
        String tDef = enemigo.getTipo().toLowerCase();

        // Lógica de ventajas tácticas (Bonificaciones)
        if (tAtk.equals("arquero") && tDef.equals("lancero")) bono = 3.0;
        else if (tAtk.equals("soldado") && tDef.equals("arquero")) bono = 2.0;
        else if (tAtk.equals("caballero") && (tDef.equals("arquero") || tDef.equals("soldado"))) bono = 2.0;
        else if (tAtk.equals("lancero") && tDef.equals("caballero")) bono = 5.0;

        return (int) (random.nextInt(ataqueMax + 1) * bono);
    }

    public int calcularDefensa() {
        return random.nextInt(defensaMax + 1);
    }

    public boolean enRango(Personaje enemigo) {
        int dist = Math.max(
                Math.abs(fila - enemigo.fila),
                Math.abs(columna - enemigo.columna)
        );
        return dist <= radioAtaque;
    }

    // 4. ACCIONES
    public void curar() {
        int cura = random.nextInt(vidaMax + 1);
        vida = Math.min(vida + cura, vidaMax);
        System.out.println(tipo + " cura " + cura + " de vida");
    }

    // 5. MÉTODOS DE INTERFAZ (TABLERO)
    public String getEtiqueta() {
        String corto = tipo.length() >= 3
                ? tipo.substring(0, 3).toUpperCase()
                : String.format("%-3s", tipo).toUpperCase();

        return corto + vida + "v";
    }

    // 6. GETTERS Y SETTERS
    public String getTipo() { return tipo; }
    
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
    
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getJugador() { return jugador; }
}
