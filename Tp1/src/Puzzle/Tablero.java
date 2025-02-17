package Puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tablero {
	private Celda[][] tablero;
	private int tamanio;
	private static int filaEnQueEstaCeldaVacio;
	private static int columnaEnQueEstaCeldaVacio;
	private int cantMovimientos=0;
	
	public Tablero(int tamanio){
		verificarTamanioTableroValido(tamanio);
        
		this.tamanio=tamanio;
		tablero = new Celda[tamanio][tamanio];
		
		boolean tableroTieneSolucion = false;
		while (! tableroTieneSolucion) {
			iniciarTableroMezclado();
			tableroTieneSolucion = verificarSiTableroTieneSolucion();
		}
	}
	
	 private void iniciarTableroMezclado() {
	        int contadorCeldas = 0;
	        List<Integer> valores = crearArrayDeValoresMezclados();
	        for (int fila = 0; fila < tamanio; fila++) {
	            for (int columna = 0; columna < tamanio; columna++) {
	                if (contadorCeldas < valores.size()) {
	                    tablero[fila][columna] = new Celda(valores.get(contadorCeldas));
	                } else {
	                    tablero[fila][columna] = new Celda(null);
	                    actualizarFilaColumnaVacia(fila, columna);
	                }
	                contadorCeldas++;
	            }
	        }
	    }
	
	public boolean comprobarSiEstaBienResuelto() {
		int cont=1;
		int celdasTotales = tamanio*tamanio;
		for(int fila = 0; fila<tamanio; fila++) {
			for (int columna = 0; columna<tamanio; columna++) {
				// Verifica si la celda es la vacía al final
				if(cont == celdasTotales) {
					return (esCeldaVacia(fila,columna));
				}
				// Verifica si el valor de la celda coincide con el número esperado
				if (!esCeldaVacia(fila,columna) ) {
					if (tablero[fila][columna].getValor() == cont) {
						cont++;
					}
					else {
						return false;	
					}
				}
			}
		}
		return false;
	}
		
	public boolean moverCelda(int fila, int columna) {
		verificarPosValida(fila);
		verificarPosValida(columna);
		if (esAdyacenteACeldaVacia(fila, columna)) {
			intercambiarConCeldaVacia(fila, columna);
			 sumarMovimiento();
			return true;
		}
		return false;
	}
	
	public boolean esCeldaVacia(int fila, int columna) {
	    return tablero[fila][columna].esVacia();
	}
	
	public int getTamanio() {
		return tamanio;
	}
	
	public Celda getElemento(int fila, int columna) {
        return tablero[fila][columna];
    }

	public void sumarMovimiento() {
		this.cantMovimientos++;
	}
	
	public int getMovimientos() {
		return this.cantMovimientos;
	}
	
	public int getFilaVacia() {
		return filaEnQueEstaCeldaVacio;
	}
	
	public int getColumnaVacia() {
		return columnaEnQueEstaCeldaVacio;
	}
	
	private boolean verificarSiTableroTieneSolucion() {
		/* 
		 * Para conocer si un tablero tiene solución, se debe saber la cantidad
		 * de inversiones que tiene. Y para saber la cantidad de inversiones, se
		 * debe convertir la matriz en un arreglo.
		 */
		Celda[] tableroComoArreglo = convertirMatrizEnArreglo(tablero);
		
		/*
		 * Dado un par de elementos (a, b) de un arreglo, se forma una
		 * INVERSIÓN si el elemento a aparece antes que b pero a es mayor
		 * que b.
		 */
		
		int contadorInversiones = cuantasInversiones(tableroComoArreglo);
		
		
		/*
		 * Un tablero tiene solución si:
		 * 	-El tamaño del tablero es impar:
		 * 		-La cantidad de inversiones es par.
		 * 	-El tamaño del tablero es par:
		 * 		-El casillero vacio esta en una fila par contando desde abajo y
		 * 		el numero de inversiones es par.
		 * 		-El casillero vacio esta en una fila impar contando desde abajo y
		 * 		el numero de inversiones es par.
		 */
		
		if (tablero.length % 2 == 1) {
			return contadorInversiones % 2 == 0;
		} else {
			if ((tablero.length - filaEnQueEstaCeldaVacio) % 2 == 1) {
				return contadorInversiones % 2 == 0;
			} else {
				return contadorInversiones % 2 == 1;
			}
		}
	}
    
    private int cuantasInversiones(Celda[] arreglo) {
		int contadorInversiones = 0;
		for (int primerIndice=0; primerIndice < arreglo.length/arreglo.length; primerIndice++) {
			for (int segundoIndice=0; segundoIndice < arreglo.length/arreglo.length; segundoIndice++) {
				if (arreglo[segundoIndice].getValor() != null && arreglo[primerIndice].getValor() != null && 
						arreglo[primerIndice].getValor() > arreglo[segundoIndice].getValor()) {
					contadorInversiones++;
				}
			}
		}
		return contadorInversiones;
	}
    
    private Celda[] convertirMatrizEnArreglo(Celda[][] matriz) {
    	Celda[] arreglo = new Celda[matriz.length*matriz.length];
		int indiceArreglo = 0;
		
		for (int fila=0; fila<matriz.length; fila++) {
			for (int columna=0; columna<matriz[0].length; columna++) {
				arreglo[indiceArreglo] = matriz[fila][columna];
			}
		}
		return arreglo;
    }
    
    
    private List<Integer> crearArrayDeValoresMezclados() {
    	List<Integer> valores = new ArrayList<>();
    	int celdasTotales = tamanio*tamanio;
    	for (int i = 1; i < celdasTotales; i++) {
    		valores.add(i);
    	}
    	Collections.shuffle(valores);	// Mezcla los valores del array
    	return valores;
    }
    
    private boolean esAdyacenteACeldaVacia(int fila, int columna) {
    	// Si la diferencia absoluta con columna o fila es exactamente 1, y está en la misma fila o misma col, significa que las dos celdas están adyacentes 
    	   
    	    boolean mismaFila = (fila == filaEnQueEstaCeldaVacio) && (Math.abs(columna - columnaEnQueEstaCeldaVacio) == 1);
    	    boolean mismaColumna = (columna == columnaEnQueEstaCeldaVacio) && (Math.abs(fila - filaEnQueEstaCeldaVacio) == 1);
    	    
    	    // Si es adyacente en fila o columna, devuelve true
    	    return mismaFila || mismaColumna;
    	}
    
    private void intercambiarConCeldaVacia(int fila, int columna) {
    	Celda celdaTemporal = tablero[fila][columna];
    	tablero[fila][columna] = tablero[filaEnQueEstaCeldaVacio][columnaEnQueEstaCeldaVacio];
    	tablero[filaEnQueEstaCeldaVacio][columnaEnQueEstaCeldaVacio] = celdaTemporal;
    	actualizarFilaColumnaVacia(fila, columna);
    }

	
	private void actualizarFilaColumnaVacia(int fila, int columna) {
		filaEnQueEstaCeldaVacio = fila;
		columnaEnQueEstaCeldaVacio = columna;
	}
     
    private void verificarPosValida(int pos) {
    	if (pos<0)
			throw new IllegalArgumentException("La posición no puede ser negativa: " + pos);
		
		if (pos>= tablero.length)
			throw new IllegalArgumentException("La posición deben estar entre 0 y" + tablero.length + ": " + pos);
	
    }
  
    private void verificarTamanioTableroValido(int tamanio) {
    	if (tamanio<=0)
    		throw new IllegalArgumentException("El tamaño del tablero no puede ser negativo o nulo");
    }
}
