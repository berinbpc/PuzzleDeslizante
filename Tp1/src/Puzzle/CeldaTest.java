package Puzzle;

import static org.junit.Assert.*;

import org.junit.Test;

public class CeldaTest {

	@Test
	public void celdaConValorSeCreaTest() {
		Celda celda = new Celda(1);
		assertEquals(Integer.valueOf(1),celda.getValor());
	} 
	
	@Test
	public void celdaNulaSeCreaTest() {
		Celda celda = new Celda(null);
		assertEquals(null,celda.getValor());
	}

	@Test
	public void celdaNulaDevuelveQueEstaVaciaTest() {
		Celda celda = new Celda(null);
		assertTrue(celda.esVacia());
	}
	
	@Test
	public void celdaNoNulaDevuelveQueNoEstaVaciaTest() {
		Celda celda = new Celda(1);
		assertFalse(celda.esVacia());
	}
}
