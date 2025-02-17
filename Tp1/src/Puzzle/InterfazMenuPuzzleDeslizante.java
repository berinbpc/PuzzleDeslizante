package Puzzle;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class InterfazMenuPuzzleDeslizante {

	private JFrame ventanaJuego;
	private Dimension tamanioPantallaJugador;
    private JButton[] botonesDificultad;
    private JButton[] botonesImagenes;
    private JButton botonSubirImagen;
    private JButton botonJugarConNumeros;
	private BufferedImage[] imagenesPorDefecto;
    private BufferedImage imagenPuzzle;
    private boolean flagImagen;
    private boolean flagNumeros;
	private int tamanioTablero;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazMenuPuzzleDeslizante menu = new InterfazMenuPuzzleDeslizante();
					menu.ventanaJuego.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public InterfazMenuPuzzleDeslizante() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tamanioPantallaJugador = Toolkit.getDefaultToolkit().getScreenSize();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		crearVentanaMenu();

		inicializarBotonesDificultad();
		
		crearBotonIniciar();
		
		crearBotonDeJuegoConNumeros();
		
		cargarImagenesPorDefecto();
		
		inicializarBotonesImagen();
		
		crearBotonSeleccionarImagen();
		
		crearSeleccionDificultadText();
		crearSeleccionImagenText();
		crearTituloJuegoText();
		crearOtraImagenText();
	}

/*
 * -------------- Metodos auxiliares de initializate --------------
 */

	private void crearVentanaMenu() {
		ventanaJuego = new JFrame();
		ventanaJuego.getContentPane().setBackground(new Color(200, 250, 255));
		ventanaJuego.setBounds(tamanioPantallaJugador.width/3, tamanioPantallaJugador.height/5, 458, 469);
		ventanaJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaJuego.getContentPane().setLayout(null);
		ventanaJuego.setVisible(true);
	}
	
	
/*
 * Botón para jugar con numeros
 */
	
	private void crearBotonDeJuegoConNumeros() {		
        // Boton para cargar la imagen
		botonJugarConNumeros = new JButton("Jugar con números");
		botonJugarConNumeros.setFocusable(false);
		botonJugarConNumeros.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
		            activarFlagNumerosSeleccionados();
		            desactivarFlagImagenSeleccionada();
		            resaltarBordeBoton(botonJugarConNumeros);
		            
		            for(int i = 0; i < botonesImagenes.length; i++) {
		            	quitarResaltado(botonesImagenes[i]);
		            }
		            
		            quitarResaltado(botonSubirImagen);
		        }
		    });
		botonJugarConNumeros.setBounds(250, 229, 135, 33);
		ventanaJuego.getContentPane().add(botonJugarConNumeros);
	}

/*
 * Botones de dificultad y resaltado
 */
	private void inicializarBotonesDificultad() {
	    String[] niveles = {"FÁCIL", "NORMAL", "DIFÍCIL"};
	    int[] tamaniosTablero = {3, 4, 5};  // Tamaños correspondientes a cada nivel
	    int[] posicionesX = {13, 153, 293};  // Posiciones X correspondientes
	    botonesDificultad = new JButton[niveles.length];
	    
	    for (int i = 0; i < niveles.length; i++) {
	        crearBotonDificultad(i, niveles[i], tamaniosTablero[i], posicionesX[i]);
	    }
	}

	private void crearBotonDificultad(int indice, String textoNivel, int tamanioTablero, int x) {
	    botonesDificultad[indice] = new JButton(textoNivel);
	    botonesDificultad[indice].setBounds(x, 311, 135, 33);
	    ventanaJuego.getContentPane().add(botonesDificultad[indice]);
	    
	    botonesDificultad[indice].setFocusable(false);
	    botonesDificultad[indice].addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            setTamanioTablero(tamanioTablero);
	            resaltarBordeBoton(botonesDificultad[indice]);
	            quitarResaltadoOtrosBotonesDificultad(botonesDificultad[indice]);
	        }
	    });
	}
	
	private void quitarResaltadoOtrosBotonesDificultad(JButton boton) {
		//Ahora que tengo un array de botonesDificultad puedo usar foreach y evitar tener 3 if
		for (JButton botonDificultad : botonesDificultad) {
	        if (!botonDificultad.equals(boton)) 
	            quitarResaltado(botonDificultad);
	    }
	}
	
	private void resaltarBordeBoton(JButton boton) {
	    boton.setBorder(new LineBorder(Color.GREEN, 3));  
	}

	
	private void quitarResaltado(JButton boton) {
	    boton.setBorder(new LineBorder(Color.GRAY, 0));  
	}

/*
* Botones de seleccion de imagenes
*/
	
	private void cargarImagenesPorDefecto() {
		String[] nombresImagenesDefault = new String[] {"perrito.jpg", "gatito.jpeg", "monito.jpg"};
		this.imagenesPorDefecto = new BufferedImage[nombresImagenesDefault.length];

		for (int indice = 0; indice < nombresImagenesDefault.length; indice++) {
			try {
				imagenesPorDefecto[indice] = ImageIO.read(InterfazMenuPuzzleDeslizante.class.getResource(nombresImagenesDefault[indice]));
			} catch (IOException e1) {
			    e1.printStackTrace();
			}
		}
	}
	
	private void inicializarBotonesImagen() {
	    int cantidadBotones = imagenesPorDefecto.length;
	    botonesImagenes = new JButton[cantidadBotones];
	    
	    for (int i = 0; i < cantidadBotones; i++) {
	        crearBotonImagen(i);
	    }
	}
	
	private void crearBotonImagen(int posImagen) {
		botonesImagenes[posImagen] = new JButton("");
		
		// Posicion en pantalla de los botones
		int x = 30 + (posImagen * 142);
	    int y = 115;
	    
	    botonesImagenes[posImagen].setBounds(x, y, 98, 74);
	    
	    Image imagenEscaladaParaBoton = imagenesPorDefecto[posImagen].getScaledInstance(98, 74, java.awt.Image.SCALE_SMOOTH);
	    botonesImagenes[posImagen].setIcon(new ImageIcon(imagenEscaladaParaBoton));
	    ventanaJuego.getContentPane().add(botonesImagenes[posImagen]);
	    
	    botonesImagenes[posImagen].setFocusable(false);
	    botonesImagenes[posImagen].addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            activarFlagImagenSeleccionada();
	            desactivarFlagNumerosSeleccionados();
	            guardarImagenSeleccionada(imagenesPorDefecto[posImagen]);
	            resaltarBordeBoton(botonesImagenes[posImagen]);
	            quitarResaltadoOtrosBotonesImagen(botonesImagenes[posImagen]);
	            quitarResaltado(botonJugarConNumeros);
	        }
	    });
	}
	

	private void crearBotonSeleccionarImagen() {		
        // Boton para cargar la imagen
		botonSubirImagen = new JButton("Buscar imagen");
		botonSubirImagen.setFocusable(false);
		botonSubirImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Hace que al apretar el boton, se abra la ventana de cargar la imagen
				JFileChooser seleccionadorDeArchivo = new JFileChooser();
                int resultado = seleccionadorDeArchivo.showOpenDialog(ventanaJuego);
                
                // Si se selecciono una imagen con la ventana...
                if (resultado == JFileChooser.APPROVE_OPTION) {
                	if (obtenerImagenSeleccionada(seleccionadorDeArchivo)) {
                		activarFlagImagenSeleccionada();
                		desactivarFlagNumerosSeleccionados();
                		resaltarBordeBoton(botonSubirImagen);
                    	quitarResaltadoOtrosBotonesImagen(botonSubirImagen);
                    	quitarResaltado(botonJugarConNumeros);
                	}
                }
			}
		});
		botonSubirImagen.setBounds(50, 229, 135, 33);
		ventanaJuego.getContentPane().add(botonSubirImagen);
	}
	
	private void quitarResaltadoOtrosBotonesImagen(JButton boton) {
		for (JButton botonImagen : botonesImagenes) {
	        if (!botonImagen.equals(boton)) {
	            quitarResaltado(botonImagen);
	        }
	    }
		
		if (! boton.equals(botonSubirImagen)) {
			quitarResaltado(botonSubirImagen);
		}
	}
	
	private boolean obtenerImagenSeleccionada(JFileChooser seleccionadorDeArchivo) {
		BufferedImage imagenObtenida = null;
		// Guarda la direccion (ej: "documentos/imagen.png") de la imagen seleccionada
		String direccionImagen = seleccionadorDeArchivo.getSelectedFile().getAbsolutePath();
		
		// Intenta obtener la imagen y la guarda en un objeto tipo BufferedImage si lo logra
		if (verificarQueArchivoSeaTipoImagen(direccionImagen)) {
			try {
				imagenObtenida = ImageIO.read(new File(direccionImagen));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
			guardarImagenSeleccionada(imagenObtenida);
			
			return true;
			
			//Si no puede obtenerla, muestra mensaje de error y con un llamado recursivo intenta abrirlo hasta que se seleccione el formato correcto.
		} else {
			JOptionPane.showMessageDialog(null, "Formato de archivo no válido. Solo se aceptan .jpg, .jpeg, o .png", "Error de formato", JOptionPane.ERROR_MESSAGE);
			int resultado = seleccionadorDeArchivo.showOpenDialog(null);
			
			if (resultado == JFileChooser.APPROVE_OPTION) {
				obtenerImagenSeleccionada(seleccionadorDeArchivo);
			}
			
			return false;
		}
	}
	
	private void guardarImagenSeleccionada(BufferedImage imagen) {
		this.imagenPuzzle = imagen;
	}
	
	private boolean verificarQueArchivoSeaTipoImagen(String nombreArchivo) {
	    String nombreEnMinusculas = nombreArchivo.toLowerCase();
	    return nombreEnMinusculas.endsWith(".jpg") || nombreEnMinusculas.endsWith(".jpeg") || nombreEnMinusculas.endsWith(".png");
	}

/*
 * Texto en pantalla
 */

	private void crearTituloJuegoText() {
		JLabel tituloJuego = new JLabel("PUZZLE DESLIZANTE");
		tituloJuego.setFont(new Font("Yu Gothic", Font.PLAIN, 41));
		tituloJuego.setBounds(10, 21, 421, 63);
		ventanaJuego.getContentPane().add(tituloJuego);
		
		JLabel creditosText = new JLabel("Programacion III - Grupo 9 ");
		creditosText.setBounds(308, 419, 126, 13);
		ventanaJuego.getContentPane().add(creditosText);
	}
	
	private void crearSeleccionImagenText() {
		JLabel seleccionImangenText = new JLabel("Seleccione la imagen");
		seleccionImangenText.setFont(new Font("Yu Gothic", Font.PLAIN, 22));
		seleccionImangenText.setHorizontalAlignment(SwingConstants.CENTER);
		seleccionImangenText.setVerticalAlignment(SwingConstants.CENTER);
		seleccionImangenText.setBounds(115, 76, 220, 36);
		ventanaJuego.getContentPane().add(seleccionImangenText);
	}
	
	private void crearOtraImagenText() {
		JLabel seleccionImangenText = new JLabel("Ó...");
		seleccionImangenText.setFont(new Font("Yu Gothic", Font.PLAIN, 22));
		seleccionImangenText.setHorizontalAlignment(SwingConstants.CENTER);
		seleccionImangenText.setVerticalAlignment(SwingConstants.CENTER);
		seleccionImangenText.setBounds(115, 196, 213, 36);
		ventanaJuego.getContentPane().add(seleccionImangenText);
	}
	
	private void crearSeleccionDificultadText() {
		JLabel seleccionDificultadText = new JLabel("Seleccione la dificultad");
		seleccionDificultadText.setFont(new Font("Yu Gothic", Font.PLAIN, 22));
        seleccionDificultadText.setHorizontalAlignment(SwingConstants.CENTER);
        seleccionDificultadText.setVerticalAlignment(SwingConstants.CENTER);
		seleccionDificultadText.setBounds(95, 278, 282, 36);
		ventanaJuego.getContentPane().add(seleccionDificultadText);
	}
	
/*
 * Boton iniciar
 */

	private void crearBotonIniciar() {
		JButton botonIniciar = new JButton("INICIAR");
		botonIniciar.setFocusable(false);
		botonIniciar.setBounds(153, 376, 135, 33);
		ventanaJuego.getContentPane().add(botonIniciar);
		botonIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getTamanioTablero()>=3 && getTamanioTablero()<=5 && (getFlagImagenSeleccionada() || getFlagNumerosSeleccionados()))
					iniciarJuego(getTamanioTablero());
			}
		});
	}

/*
 * Otros
 */
	private void setTamanioTablero(int tamanio) {
        this.tamanioTablero = tamanio;
    }
	
	private int getTamanioTablero() {
		return tamanioTablero;
	}
	
	private boolean getFlagImagenSeleccionada() {
		return flagImagen;
	}
	
	private void activarFlagImagenSeleccionada() {
		this.flagImagen=true;
	}
	
	private void desactivarFlagImagenSeleccionada() {
		this.flagImagen=false;
	}
	
	private boolean getFlagNumerosSeleccionados() {
		return flagNumeros;
	}
	
	private void activarFlagNumerosSeleccionados() {
		this.flagNumeros=true;
	}
	
	private void desactivarFlagNumerosSeleccionados() {
		this.flagNumeros=false;
	}
	
	 private void iniciarJuego(int tamanioTablero) {
		 	ventanaJuego.dispose();  // Cierra el menú
		 	
		 	EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						if(getFlagNumerosSeleccionados() && !getFlagImagenSeleccionada()) {
							new InterfazPuzzle(tamanioTablero);
						}else if(getFlagImagenSeleccionada() && !getFlagNumerosSeleccionados()) {
							new InterfazPuzzle(tamanioTablero, imagenPuzzle);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	    }
}
