package Puzzle;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableroTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void tamanioTableroNegativoTest() {
		new Tablero(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tamanioTableroCeroTest() {
		new Tablero(0);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void tableroSeMueveFueraDelRangoTest() {
		Tablero tablero = new Tablero(4);
		tablero.moverCelda(1, 4);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void tableroSeMueveRangoNegativoTest() {
		Tablero tablero = new Tablero(4);
		tablero.moverCelda(-1, 2);
	}
	
	@Test
	public void tamanioTableroTest() {
        Tablero tablero = new Tablero(4);

        assertEquals(4, tablero.getTamanio());
	}
	
	@Test
	public void moverCeldaAdyacenteEnFilaTest() {
	    Tablero tablero = new Tablero(4);
	    assertTrue(tablero.moverCelda(3, 2));
	}
	
	@Test
	public void moverCeldaAdyacenteEnColumnaTest() {
	    Tablero tablero = new Tablero(4);
	    assertTrue(tablero.moverCelda(2, 3));
	}
	
	@Test
	public void moverCeldaMismaFilaPeroNoAdyacenteAVacioTest() {
	    Tablero tablero = new Tablero(4);
	    assertFalse(tablero.moverCelda(3, 0));
	}
	
	@Test
	public void moverCeldaMismaColumnaPeroNoAdyacenteAVacioTest() {
	    Tablero tablero = new Tablero(4);
	    assertFalse(tablero.moverCelda(0, 3));
	}
	
	@Test 
	public void tableroSeMueveValidoYVuelveASuAnteriorPosicionTest() {
		Tablero tablero = new Tablero(4);
		tablero.moverCelda(2, 3);
		tablero.moverCelda(3, 3);
		assertFalse(tablero.esCeldaVacia(2, 3));
	}
	
	@Test
	public void tableroNoCompletadoTest() {
        Tablero tablero = new Tablero(4);
        assertFalse(tablero.comprobarSiEstaBienResuelto());
	}

	
}
