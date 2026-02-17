package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Jugador {
	 String nombre;
	    String codigo; // A o B
	    int creditos = 50;
	    List<Personaje> ejercito = new ArrayList<>();
	    protected Personaje[][] mapa;

	    public Jugador(String nombre, String codigo) {
	        this.nombre = nombre;
	        this.codigo = codigo;
	    }
	    
	    

	    public boolean tienePersonajes() {
	        return !ejercito.isEmpty();
	    }
	    
	    public void comprarEjercito(Scanner sc) {
	        System.out.println("\n--- RECLUTAMIENTO: " + nombre + " ---");
	        while (creditos >= 5) {
	            System.out.println("Créditos actuales: " + creditos);
	            System.out.println("1. Soldado(10) 2. Caballero(20) 3. Arquero(15) 4. Lancero(15) 0. Fin");
	            int opcion = sc.nextInt();
	            if (opcion == 0) break;

	            String tipo = switch(opcion) {
	                case 1 -> "soldado";
	                case 2 -> "caballero";
	                case 3 -> "arquero";
	                case 4 -> "lancero";
	                default -> "";
	            };

	            int coste = (opcion == 2) ? 20 : (opcion == 1) ? 10 : 15;

	            if (!tipo.isEmpty() && creditos >= coste) {
	                boolean posValida = false;
	                while (!posValida) {
	                    System.out.println("Indique posición de colocación:");
	                    System.out.print("Fila (0-7): ");
	                    int f = sc.nextInt();
	                    System.out.print("Columna " + (codigo.equals("A") ? "(0-1): " : "(6-7): "));
	                    int c = sc.nextInt();

	                   
	                    boolean columnaCorrecta = codigo.equals("A") ? (c == 0 || c == 1) : (c == 6 || c == 7);

	                    if (enLimites(f, c) && columnaCorrecta && mapa[f][c] == null) {
	                        Personaje p = new Personaje(tipo, (int)(Math.random()*1000), codigo.equals("A") ? 1 : 2);
	                        colocarPersonaje(p, f, c, mapa);
	                        ejercito.add(p);
	                        creditos -= coste;
	                        posValida = true;
	                    } else {
	                        System.out.println("Posición inválida, ocupada o fuera de tu zona permitida. Reintenta.");
	                    }
	                }
	            } else if (creditos < coste) {
	                System.out.println("No tienes suficientes créditos.");
	            }
	        }
	    }
	                
	                
	                private boolean enLimites(int f, int c) {
	                    return f >= 0 && f < mapa.length && c >= 0 && c < mapa[0].length;
	                }

	   
	                
	                            
	                
	                public static void colocarPersonaje(Personaje p, int fila, int columna, Personaje[][] mapa) {
	                    
	                    // 1. Validación de límites
	                    if (fila < 0 || fila >= mapa.length || columna < 0 || columna >= mapa[0].length) {
	                        System.out.println("Fuera del mapa");
	                        return; // Sale del método si está fuera
	                    }

	                    // 2. Validación de ocupación
	                    if (mapa[fila][columna] != null) {
	                        System.out.println("Ejército ya colocado"); 
	                        return; 
	                    }
	                    
	                    mapa[fila][columna] = p; 
	                    p.setFila(fila);         
	                    p.setColumna(columna);
	                    
	                    System.out.println("Ejércitos colocados con éxito."); 
	                }
	                
	             
	                public static void mover(Personaje p, int nFila, int nCol, Personaje[][] mapa) {
	                   
	                    if (nFila < 0 || nFila >= mapa.length || nCol < 0 || nCol >= mapa[0].length) {
	                        System.out.println("Movimiento fuera del mapa.");
	                        return;
	                    }

	                   if (mapa[nFila][nCol] != null) {
	                        System.out.println("Casilla ocupada.");
	                        return;
	                    }

	                    
	                    mapa[p.getFila()][p.getColumna()] = null; // Borra posición vieja
	                    p.setFila(nFila);
	                    p.setColumna(nCol);
	                    mapa[nFila][nCol] = p; // Ocupa posición nueva
	                }

	                // Atacar a una pieza enemiga
	                public static void atacar(Personaje atk, int f, int c, Personaje[][] mapa, List<Personaje> listaEnemiga) {
	                    Personaje def = mapa[f][c];

	                    if (def == null) {
	                        System.out.println("No hay nadie a quien atacar.");
	                        return;
	                    }

	                    // --- CÁLCULO DE DAÑO INTEGRADO ---
	                    double bono = 1.0;
	                    String tAtk = atk.getTipo().toLowerCase();
	                    String tDef = def.getTipo().toLowerCase();

	                    // Aplicamos las ventajas tácticas directamente aquí
	                    if (tAtk.equals("arquero") && tDef.equals("lancero")) bono = 3.0;
	                    else if (tAtk.equals("soldado") && tDef.equals("arquero")) bono = 2.0;
	                    else if (tAtk.equals("caballero") && (tDef.equals("arquero") || tDef.equals("soldado"))) bono = 2.0;
	                    else if (tAtk.equals("lancero") && tDef.equals("caballero")) bono = 5.0;

	                    // Lanzamos los dados (Random)
	                    Random rnd = new Random();
	                    int fuerzaAtk = (int) (rnd.nextInt(atk.ataqueMax + 1) * bono);
	                    int fuerzaDef = rnd.nextInt(def.defensaMax + 1);
	                    
	                    int daño = Math.max(0, fuerzaAtk - fuerzaDef);
	                    // ---------------------------------

	                    def.setVida(def.getVida() - daño);
	                    System.out.println("¡" + atk.getTipo() + " golpea con " + daño + " puntos a " + def.getTipo() + "!");

	                    if (def.getVida() <= 0) {
	                        System.out.println("¡Unidad " + def.getTipo() + " eliminada!");
	                        mapa[f][c] = null;
	                        listaEnemiga.remove(def);
	                    }
	                }
	              
	                public void curarEjercito(Jugador j) {
	                    for (Personaje p : j.ejercito) {
	                        p.curar();
	                    }
	                
	            }
	            
}
