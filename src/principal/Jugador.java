package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jugador {

    // 1. ATRIBUTOS
    String nombre;
    String codigo; // A o B
    int creditos = 50;
    List<Personaje> ejercito = new ArrayList<>();
    protected Personaje[][] mapa;

    // 2. CONSTRUCTOR
    public Jugador(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    // 3. ACCIONES DE TURNO (Mover, Atacar, Curar)
    public void mover(Personaje p, int nf, int nc) {
        // Validar que el destino esté dentro del rango 0-7 y vacío
        if (nf >= 0 && nf < 8 && nc >= 0 && nc < 8 && mapa[nf][nc] == null) {
            // Limpiar posición anterior
            mapa[p.getFila()][p.getColumna()] = null;
            
            // ACTUALIZAR EL ID SEGÚN LA NUEVA POSICIÓN (f*8 + c + 1)
            int nuevoId = (nf * 8) + nc + 1;
            p.setId(nuevoId); 
            
            // Colocar en nueva posición
            colocarEnMapa(p, nf, nc);
            System.out.println("Movimiento realizado. Nuevo ID: " + nuevoId);
        } else {
            System.out.println("Movimiento inválido (Casilla ocupada o fuera de rango).");
        }
    }

    public void atacar(Personaje atk, int f, int c, List<Personaje> listaEnemiga) {
        Personaje def = mapa[f][c];
        
        // Validación de objetivo válido
        if (def == null || def.getJugador().equals(this.codigo)) {
            System.out.println("No hay enemigo en esa posición.");
            return;
        }

        // Lógica de combate
        int valorAtaque = atk.calcularAtaque(def); 
        int valorDefensa = def.calcularDefensa();
        int daño = Math.max(0, valorAtaque - valorDefensa); // Evita valores negativos

        def.setVida(def.getVida() - daño);

        // Mostrar reporte de combate
        System.out.println("\n--- RESULTADO DEL COMBATE ---");
        System.out.println(atk.getTipo() + " ataca con: " + valorAtaque);
        System.out.println(def.getTipo() + " se defiende con: " + valorDefensa);
        System.out.println("Daño final infligido: " + daño);
        System.out.println("Vida restante del defensor: " + Math.max(0, def.getVida()));
        
        // Eliminar si la vida llega a 0
        if (def.getVida() <= 0) {
            System.out.println("¡Unidad eliminada!");
            mapa[f][c] = null;
            listaEnemiga.remove(def);
        }
    }

    public void curarEjercito(Jugador j) {
        for (Personaje p : j.ejercito) {
            p.curar();
        }
    }

    // 4. GESTIÓN Y RECLUTAMIENTO
    public void comprarEjercito(Scanner sc) {
        System.out.println("\n--- RECLUTAMIENTO: " + nombre + " ---");
        while (creditos >= 5) {
            System.out.println("Créditos: " + creditos + " | 1.Soldado(10) 2.Caballero(20) 3.Arquero(15) 4.Lancero(15) 0.Fin");
            int opcion = sc.nextInt();
            if (opcion == 0) break;

            int coste = (opcion == 2) ? 20 : (opcion == 1) ? 10 : 15;
            if (creditos < coste) {
                System.out.println("Créditos insuficientes.");
                continue;
            }

            System.out.print("Fila (rango 0 a 7): ");
            int f = sc.nextInt();
            
            if (codigo.equals("A")) {
                System.out.print("Columna (solo puedes en 0 y 1): ");
            } else {
                System.out.print("Columna (solo puedes en 6 y 7): ");
            }
            int c = sc.nextInt();
            
            if (validarPosicionInicial(f, c)) {
                // ID sincronizado con el número de casilla del tablero
                int idTablero = (f * 8) + c + 1; 
                int numEq = codigo.equals("A") ? 1 : 2;

                Personaje p = switch(opcion) {
                    case 1 -> new Soldado(idTablero, numEq);
                    case 2 -> new Caballero(idTablero, numEq);
                    case 3 -> new Arquero(idTablero, numEq);
                    case 4 -> new Lancero(idTablero, numEq);
                    default -> null;
                };

                if (p != null) {
                    colocarEnMapa(p, f, c);
                    ejercito.add(p);
                    creditos -= coste;
                }
            } else {
                System.out.println("Posición inválida.");
            }
        }
    }

    // 5. MÉTODOS DE APOYO Y VALIDACIÓN
    public void colocarEnMapa(Personaje p, int f, int c) {
        mapa[f][c] = p;
        p.setFila(f);
        p.setColumna(c);
    }

    public boolean tienePersonajes() {
        return !ejercito.isEmpty();
    }

    private boolean validarPosicionInicial(int f, int c) {
        boolean zonaCorrecta = codigo.equals("A") ? (c == 0 || c == 1) : (c == 6 || c == 7);
        return f >= 0 && f < 8 && zonaCorrecta && mapa[f][c] == null;
    }


}