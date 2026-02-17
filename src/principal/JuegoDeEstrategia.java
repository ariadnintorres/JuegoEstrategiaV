package principal;

import java.util.Scanner;


public class JuegoDeEstrategia {

    public static final String ROJO = "\u001B[31m";
    public static final String AZUL = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    protected Personaje[][] mapa;
    private int[][] idCasillas;
    
    public void iniciarPartida() {
        Scanner sc = new Scanner(System.in);
        
        
        this.mapa = new Personaje[8][8];

        System.out.println(AZUL + "========================================");
        System.out.println("   BIENVENIDO AL JUEGO DE ESTRATEGIA   ");
        System.out.println("========================================" + RESET);

        System.out.print("Nombre Jugador 1 (Equipo A): ");
        Jugador j1 = new Jugador(sc.next(), "A");
        j1.mapa = this.mapa; // Asignar el mapa del juego al jugador

        System.out.print("Nombre Jugador 2 (Equipo B): ");
        Jugador j2 = new Jugador(sc.next(), "B");
        j2.mapa = this.mapa; // Asignar el mapa del juego al jugador

        // 3. COMPRA Y COLOCACIÓN 
        j1.comprarEjercito(sc);
        imprimir(); // Mostrar como quedó el equipo A
        j2.comprarEjercito(sc);

        // 4. BUCLE DE TURNOS (Se mantiene igual, la acción ya pasa el turno al final)
        boolean partidaActiva = true;
        Jugador actual = j1;
        Jugador enemigo = j2;

        while (partidaActiva) {
            imprimir();
            System.out.println("\nTURNO DE: " + actual.nombre + " (" + actual.codigo + ")");
            System.out.println("1. Mover | 2. Atacar | 3. Curar | 0. Rendirse");
            int accion = sc.nextInt();

            if (accion == 0) {
                System.out.println(actual.nombre + " se ha rendido.");
                break;
            }

            procesarAccion(accion, actual, enemigo, sc);

            if (!enemigo.tienePersonajes()) {
                imprimir();
                System.out.println("¡EL JUGADOR " + actual.nombre + " HA GANADO!");
                partidaActiva = false;
            } else {
                // Cambio de turno automático tras la acción
                Jugador temp = actual;
                actual = enemigo;
                enemigo = temp;
            }
        }
    }


	private void procesarAccion(int accion, Jugador actual, Jugador enemigo, Scanner sc) {
        if (accion == 0) return; // Rendirse o salir

        System.out.println("Selecciona la unidad por su ID (número en la casilla): ");
        int id = sc.nextInt();
        
        // Buscamos el personaje en el mapa por su ID
        Personaje p = buscarPorId(id, mapa);

        if (p == null || !p.getJugador().equals(actual.codigo)) {
            System.out.println("Esa unidad no existe o no es tuya.");
            return;
        }

        switch (accion) {
            case 1 -> { // 
                System.out.print("Fila destino: ");
                int f = sc.nextInt();
                System.out.print("Columna destino: ");
                int c = sc.nextInt();
                Jugador.mover(p, f, c, this.mapa);
               // no static
            }
            case 2 -> { // ATACAR
                System.out.print("Fila del objetivo: ");
                int f = sc.nextInt();
                System.out.print("Columna del objetivo: ");
                int c = sc.nextInt();
                Jugador.atacar(p, f, c, this.mapa, enemigo.ejercito);
            }
            case 3 -> p.curar();
        }
    }
	

    

	// Función auxiliar para encontrar la unidad en el tablero
	public static Personaje buscarPorId(int id, Personaje[][] mapa) {
	    for (int f = 0; f < mapa.length; f++) {
	        for (int c = 0; c < mapa[f].length; c++) {
	            // Si hay un personaje y su ID coincide
	            if (mapa[f][c] != null && mapa[f][c].getId() == id) {
	                return mapa[f][c];
	            }
	        }
	    }
	    return null;
	}
    // 🔹 TABLERO ASCII 8x8
    private String[] baseTablero = {
            "╔══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╗",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "║      ║      ║      ║      ║      ║      ║      ║      ║",
            "╚══════╩══════╩══════╩══════╩══════╩══════╩══════╩══════╝",
    };

    public JuegoDeEstrategia(int filas, int columnas) {

        if (filas != 8 || columnas != 8) {
            throw new IllegalArgumentException("El tablero ASCII es de 8x8");
        }

        mapa = new Personaje[filas][columnas];
        idCasillas = new int[filas][columnas];

        int id = 1;
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                idCasillas[f][c] = id++;
            }
        }
    }

   
    private boolean enLimites(int f, int c) {
        return f >= 0 && f < mapa.length && c >= 0 && c < mapa[0].length;
    }

  

    // 🔹 IMPRIMIR TABLERO
    public void imprimir() {

        String[] tablero = baseTablero.clone();

        int filasMapa = mapa.length;
        int columnasMapa = mapa[0].length;

        for (int f = 0; f < filasMapa; f++) {
            for (int c = 0; c < columnasMapa; c++) {

                int filaAsciiArriba = 1 + f * 3;
                int filaAsciiAbajo = 2 + f * 3;
                int colAscii = 1 + c * 7;

                String textoArriba = "      ";
                String textoAbajo = String.format("%-6s", idCasillas[f][c]).substring(0, 6);

                if (mapa[f][c] != null) {

                    Personaje p = mapa[f][c];

                    String nom = p.getTipo();
                    String nomCorto = nom.length() >= 3
                            ? nom.substring(0, 3).toUpperCase()
                            : String.format("%-3s", nom).toUpperCase();

                    String vida = String.valueOf(p.getVida());

                    textoArriba = (nomCorto + vida + "v");
                    textoArriba = String.format("%-6s", textoArriba).substring(0, 6);
                }

                String linea1 = tablero[filaAsciiArriba];
                tablero[filaAsciiArriba] =
                        linea1.substring(0, colAscii) +
                        textoArriba +
                        linea1.substring(colAscii + 6);

                String linea2 = tablero[filaAsciiAbajo];
                tablero[filaAsciiAbajo] =
                        linea2.substring(0, colAscii) +
                        textoAbajo +
                        linea2.substring(colAscii + 6);
            }
        }

        //  Columnas
        System.out.print("    ");
        for (int c = 0; c < columnasMapa; c++) {
            System.out.printf("  %2d   ", c);
        }
        System.out.println();

        int filaMapa = 0;

        for (int i = 0; i < tablero.length; i++) {

            if (i % 3 == 1 && filaMapa < filasMapa) {
                System.out.printf("%2d  %s%n", filaMapa, tablero[i]);
            }
            else if (i % 3 == 2 && filaMapa < filasMapa) {
                System.out.printf("    %s%n", tablero[i]);
                filaMapa++;
            }
            else {
                System.out.println("    " + tablero[i]);
            }
        }
    }
}



