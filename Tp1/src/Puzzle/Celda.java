package Puzzle;

public class Celda {
	private Integer valor;
	
	public Celda(Integer valor) {
		this.valor=valor;
	}

	public Integer getValor() {
		return valor;
	}
	
	public boolean esVacia() {
		return valor == null;
	}
}
