package principal;

import java.util.Scanner;

public class JuegoDeEstrategia {

    // 1. CONSTANTES DE COLOR
    public static final String ROJO = "\u001B[31m";
    public static final String AZUL = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    // 2. ATRIBUTOS
    protected Personaje[][] mapa;
    private int[][] idCasillas;
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

    // 3. CONSTRUCTOR
    public JuegoDeEstrategia(int filas, int columnas) {
        if (filas != 8 || columnas != 8) {
            throw new IllegalArgumentException("El tablero ASCII es de 8x8");
        }
        this.mapa = new Personaje[filas][columnas];
        this.idCasillas = new int[filas][columnas];

        int id = 1;
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                idCasillas[f][c] = id++;
            }
        }
    }

    // 4. LÓGICA PRINCIPAL DE PARTIDA
    public void iniciarPartida() {
        Scanner sc = new Scanner(System.in);

        System.out.println(AZUL + "========================================");
        System.out.println("   BIENVENIDO AL JUEGO DE ESTRATEGIA   ");
        System.out.println("========================================" + RESET);

        System.out.println("\nPresiona ENTER para comenzar el reclutamiento...");
        sc.nextLine();
        
        System.out.print("Nombre Jugador 1 (Equipo A): ");
        Jugador j1 = new Jugador(sc.next(), "A");
        j1.mapa = this.mapa;

        System.out.print("Nombre Jugador 2 (Equipo B): ");
        Jugador j2 = new Jugador(sc.next(), "B");
        j2.mapa = this.mapa;

        j1.comprarEjercito(sc);
        imprimir();
        j2.comprarEjercito(sc);

        boolean partidaActiva = true;
        Jugador actual = j1;
        Jugador enemigo = j2;

        while (partidaActiva) {
            imprimir();
            System.out.println("\nTURNO DE: " + actual.nombre + " (" + actual.codigo + ")");
            // CAMBIO: Ahora la opción 0 solo pasa el turno
            System.out.println("1. Mover | 2. Atacar | 3. Curar | 0. Pasar Turno");
            int accion = sc.nextInt();

            if (accion != 0) {
                procesarAccion(accion, actual, enemigo, sc);
            } else {
                System.out.println(actual.nombre + " ha pasado su turno.");
            }

            // Comprobar victoria después de la acción
            if (!enemigo.tienePersonajes()) {
                imprimir();
                System.out.println("¡EL JUGADOR " + actual.nombre + " HA GANADO!");
                partidaActiva = false;
            } else {
                // CAMBIO DE TURNO
                Jugador temp = actual;
                actual = enemigo;
                enemigo = temp;
            }
        }
        System.out.println("Fin de la partida.");
    }

    private void procesarAccion(int accion, Jugador actual, Jugador enemigo, Scanner sc) {
        System.out.println("Selecciona la unidad por su ID: ");
        int idBuscado = sc.nextInt();
        
        Personaje p = null;
        for (Personaje uni : actual.ejercito) {
            if (uni.getId() == idBuscado) {
                p = uni;
                break;
            }
        }

        if (p == null) {
            System.out.println("Esa unidad no existe en tu ejército.");
            return;
        }

        switch (accion) {
            case 1 -> { 
                System.out.print("Fila destino: ");
                int f = sc.nextInt();
                System.out.print("Columna destino: ");
                int c = sc.nextInt();
                actual.mover(p, f, c);
            }
            case 2 -> { 
                System.out.print("Fila del objetivo: ");
                int f = sc.nextInt();
                System.out.print("Columna del objetivo: ");
                int c = sc.nextInt();
                actual.atacar(p, f, c, enemigo.ejercito);
            }
            case 3 -> p.curar();
        }
    }

    // 5. RENDERIZADO DEL TABLERO
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
                String textoAbajo;

                if (mapa[f][c] != null) {
                    Personaje p = mapa[f][c];
                    String nomCorto = p.getTipo().length() >= 3
                            ? p.getTipo().substring(0, 3).toUpperCase()
                            : String.format("%-3s", p.getTipo()).toUpperCase();

                    textoArriba = String.format("%-6s", nomCorto + p.getVida() + "v").substring(0, 6);
                    textoAbajo = String.format("%-6s", "ID:" + p.getId()).substring(0, 6);
                } else {
                    textoAbajo = String.format("%-6s", idCasillas[f][c]).substring(0, 6);
                }

                tablero[filaAsciiArriba] = tablero[filaAsciiArriba].substring(0, colAscii) +
                                           textoArriba +
                                           tablero[filaAsciiArriba].substring(colAscii + 6);

                tablero[filaAsciiAbajo] = tablero[filaAsciiAbajo].substring(0, colAscii) +
                                          textoAbajo +
                                          tablero[filaAsciiAbajo].substring(colAscii + 6);
            }
        }

        System.out.print("    ");
        for (int c = 0; c < columnasMapa; c++) {
            System.out.printf("  %2d   ", c);
        }
        System.out.println();

        int filaMapa = 0;
        for (int i = 0; i < tablero.length; i++) {
            if (i % 3 == 1 && filaMapa < filasMapa) {
                System.out.printf("%2d  %s%n", filaMapa, tablero[i]);
            } else if (i % 3 == 2 && filaMapa < filasMapa) {
                System.out.printf("    %s%n", tablero[i]);
                filaMapa++;
            } else {
                System.out.println("    " + tablero[i]);
            }
        }
    }
}