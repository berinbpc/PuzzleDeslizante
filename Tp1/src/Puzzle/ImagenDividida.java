package Puzzle;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;


public class ImagenDividida {
	
	/**
	 * Divide la imagen pasada por parametro y aloja las partes recortadas en un arreglo.
	 * @param imagen imagen a recortar.
	 * @param cantFilas cantidad de cortes en horizontal de la imagen.
	 * @param cantColumnas cantidad de cortes en vertical de la imagen
	 */
	public static BufferedImage[] dividirImagenEnArreglo(BufferedImage imagen, int cantFilas, int cantColumnas) {
		BufferedImage[] imagenDividida = new BufferedImage[cantFilas*cantColumnas]; //Array para almacenar las sub-imagenes
		
		//Calcula el tamaño de cada imagen recortada
		int anchoImagenRecortada = imagen.getWidth() / cantFilas;
		int alturaImagenRecortada = imagen.getHeight() / cantColumnas;
		cortarImagen(imagenDividida, imagen, anchoImagenRecortada, alturaImagenRecortada, cantFilas, cantColumnas);
		return imagenDividida;
	}
	
	/**
	 * Convierte un arreglo de una clase de tipo Image a un arreglo de ImageIcon y escala las imagenes
	 * según el tamaño que se solicite.
	 * @param arreglo arreglo de imagenes a convertir.
	 * @param ancho deseado para escalar las imagenes.
	 * @param altura deseada para escalar las imagenes.
	 */
	public static ImageIcon[] convertirYEscalarEnArregloImageIcon(Image[] arreglo, int ancho, int altura) {
		ImageIcon[] arregloImageIcon = new ImageIcon[arreglo.length];
		for (int indice=0; indice<arreglo.length; indice++) {
			arregloImageIcon[indice] = new ImageIcon(escalarImagen(arreglo[indice], ancho, altura));
		}
		return arregloImageIcon;
	}
	
	 /*
     * Corta la imagen en partes más pequeñas y almacena los resultados en el arreglo proporcionado.
     */
	
	private static void cortarImagen(BufferedImage[] imagenDividida, BufferedImage imagen, 
			int anchoImagenRecortada, int alturaImagenRecortada, int cantFilas, int cantColumnas) {
		int posicion = 0;
		for (int fila = 0; fila < cantFilas; fila++) {
			for (int columna = 0; columna < cantColumnas; columna++) {
				  // Calcular la posición y el tamaño de la subimagen
				BufferedImage imagenRecortada = imagen.getSubimage(columna*anchoImagenRecortada, 
																fila*alturaImagenRecortada, 
																anchoImagenRecortada, 
																alturaImagenRecortada);
				imagenDividida[posicion] = imagenRecortada;
				posicion++;
				}
			}
	}
	
	private static Image escalarImagen(Image imagen, int ancho, int altura) {
		Image imagenEscalada = imagen.getScaledInstance(ancho, altura, Image.SCALE_SMOOTH);
		return imagenEscalada;
	}
}
