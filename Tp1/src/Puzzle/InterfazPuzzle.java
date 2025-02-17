package Puzzle;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class InterfazPuzzle {

    private JFrame ventanaJuego;
    private Tablero tablero;
    private JButton[][] botones;
    private JLabel contMovimientosJugador;
    private BufferedImage imagenPuzzle;
    private Font fuenteNumerosDelTablero = new Font("Tahoma", Font.PLAIN, 15);
    ImageIcon[] imagenDividida;

    /**
     * Create the application.
     */
    public InterfazPuzzle(int tamanioTablero, BufferedImage imagenUsuario) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initialize(tamanioTablero, imagenUsuario);
    }
    
    public InterfazPuzzle(int tamanioTablero) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initialize(tamanioTablero);
    }
    
    

    /**
     * Initialize the contents of the frame.
     */
    
    //Para jugar con imagenes
    private void initialize(int tamanioTablero, BufferedImage imagenUsuario) {
        crearVentanaJuego(tamanioTablero);
        permitirMoverConFlechitasDelTeclado();
        movimientosText();
        tituloDelJuegoText();
        crearTableroYBotones(tamanioTablero);
        contadorMovimientosText();
        contructorDeBotones();
        recortarImagen(imagenUsuario, tamanioTablero);
        pistaImagen();
        actualizarTablero();
    }
    
    //Para jugar con numeros
    private void initialize(int tamanioTablero) {
        crearVentanaJuego(tamanioTablero);
        permitirMoverConFlechitasDelTeclado();
        movimientosText();
        tituloDelJuegoText();
        crearTableroYBotones(tamanioTablero);
        contadorMovimientosText();
        contructorDeBotones();
        actualizarTablero();
    }

/*
* ------- Metodos auxiliares initialize -------
*/

    private void crearVentanaJuego(int tamanioTablero) {
        ventanaJuego = new JFrame();
        int alturaVentana = tamanioTablero * 100 + 150;
        ventanaJuego.setBounds(70, 80, 937, alturaVentana);
        ventanaJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaJuego.setVisible(true);
        ventanaJuego.getContentPane().setLayout(null);
        ventanaJuego.setFocusable(true); // Para poder permitir que el JFrame capture las flechas / eventos del teclado
    }

    private void crearTableroYBotones(int tamanioTablero) {
        tablero = new Tablero(tamanioTablero);
        int dimensionTablero = tablero.getTamanio();
        botones = new JButton[dimensionTablero][dimensionTablero];
    }
    
    private void recortarImagen(BufferedImage imagenUsuario, int tamanioTablero) {
      	 this.imagenPuzzle = imagenUsuario;
      	 BufferedImage[] imagenDividida = ImagenDividida.dividirImagenEnArreglo(imagenPuzzle, tamanioTablero, tamanioTablero);
      	 this.imagenDividida = ImagenDividida.convertirYEscalarEnArregloImageIcon(imagenDividida, 
          		 															botones[0][0].getWidth() + 14,
          		 															botones[0][0].getHeight());	
   	}

    private void pistaImagen() {
        Image imagenEscalada = imagenPuzzle.getScaledInstance(205, 181, Image.SCALE_SMOOTH);
        JLabel imagenArmar = new JLabel(new ImageIcon(imagenEscalada));
        imagenArmar.setBounds(668, 65, 205, 181);
        ventanaJuego.getContentPane().add(imagenArmar);
    }

	
/*
 * Movimiento celdas
 */
	private void permitirMoverConFlechitasDelTeclado() {
		ventanaJuego.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moverCeldaConFlechas(e);
            }
        });
	}

	private void permitirMoverCeldaAlTocarBoton(JButton boton, final int fila, final int columna) {
		boton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	moverCeldaEnIntefaz(fila, columna);
		    }
		});
	}
	
	private void moverCeldaConFlechas(KeyEvent e) {
        int filaVacia = tablero.getFilaVacia(); // Obtener la posición de la celda vacía
        int columnaVacia = tablero.getColumnaVacia();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (filaVacia < tablero.getTamanio() - 1) {
                    moverCeldaEnIntefaz(filaVacia + 1, columnaVacia); // Mover celda hacia arriba
                }
                break;
            case KeyEvent.VK_DOWN:
                if (filaVacia > 0) {
                    moverCeldaEnIntefaz(filaVacia - 1, columnaVacia); // Mover celda hacia abajo
                }
                break;
            case KeyEvent.VK_LEFT:
                if (columnaVacia < tablero.getTamanio() - 1) {
                    moverCeldaEnIntefaz(filaVacia, columnaVacia + 1); // Mover celda hacia la izquierda
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (columnaVacia > 0) {
                    moverCeldaEnIntefaz(filaVacia, columnaVacia - 1); // Mover celda hacia la derecha
                }
                break;
        }
    }

	private void moverCeldaEnIntefaz(int fila, int columna) {
        boolean realizarMovimiento = tablero.moverCelda(fila, columna);
        if (realizarMovimiento) {
            actualizarTablero();
            
        }
        
        if (tablero.comprobarSiEstaBienResuelto()) {
            felicitarJugador();
            abrirMenu();
        }

    }

/*
* Texto en pantalla
*/
    private JLabel movimientosText() {
        JLabel movimientosText = new JLabel("Movimientos: ");
        movimientosText.setFont(new Font("Tahoma", Font.PLAIN, 26));
        movimientosText.setBounds(560, 350, 170, 45);
        ventanaJuego.getContentPane().add(movimientosText);
        return movimientosText;
    }

    private JLabel tituloDelJuegoText() {
        JLabel tituloJuego = new JLabel("Puzzle");
        tituloJuego.setForeground(new Color(128, 128, 128));
        tituloJuego.setFont(new Font("Tahoma", Font.PLAIN, 48));
        tituloJuego.setBounds(10, 10, 675, 45);
        ventanaJuego.getContentPane().add(tituloJuego);
        return tituloJuego;
    }

    private JLabel contadorMovimientosText() {
        contMovimientosJugador = new JLabel(String.valueOf(tablero.getMovimientos()));
        contMovimientosJugador.setFont(new Font("Tahoma", Font.PLAIN, 26));
        contMovimientosJugador.setBounds(745, 350, 115, 45);
        ventanaJuego.getContentPane().add(contMovimientosJugador);
        return contMovimientosJugador;
    }

/*
* Botones
*/
    private void contructorDeBotones() {
        int tamanio = tablero.getTamanio();
        int anchoVentana = ventanaJuego.getWidth();
        for (int f = 0; f < tamanio; f++) {
            for (int c = 0; c < tamanio; c++) {
               
                JButton boton = crearBotonDeTablero(anchoVentana, f, c); // Pasamos fila y columna en lugar de la celda
                botones[f][c] = boton;

                final int fila = f;
                final int columna = c;

                permitirMoverCeldaAlTocarBoton(boton, fila, columna);
            }
        }
    }
    private JButton crearBotonDeTablero(int anchoVentana, int fila, int columna) {
        JButton boton = new JButton();
        
        // Calcula la posición del botón basado en su fila y columna
        int tamanioBoton = anchoVentana / 10; // El tamaño de los botones (se puede ajustar según sea necesario)
        int xPos =columna * tamanioBoton + 70; // Calcula la posición en X
        int yPos = fila * tamanioBoton + 70;    // Calcula la posición en Y
        
        // Coloca las dimensiones y la posición del botón en la ventana
        boton.setBounds(xPos, yPos, tamanioBoton, tamanioBoton);
        boton.setFocusable(false);
        ventanaJuego.getContentPane().add(boton);
        
        return boton;
    }

    private void asignarImagenAlBoton(JButton boton, ImageIcon porcionImagen) {
    	// Si la imagen pasada por parametro es nula, se borra la imagen que tenga el boton.
        boton.setIcon(porcionImagen);
    }

/*
* Estado del juego
*/
    private void actualizarTablero() {
        for (int i = 0; i < tablero.getTamanio(); i++) {
            for (int j = 0; j < tablero.getTamanio(); j++) {
            	
                Celda celda = tablero.getElemento(i, j);
                
                if (imagenPuzzle == null) {
                	botones[i][j].setText(celda.esVacia() ? "" : String.valueOf(celda.getValor()));
                	botones[i][j].setFont(fuenteNumerosDelTablero);
                } else {
                	// Si es el casillero vacio, se borra la imagen
                	if (celda.esVacia()) {
                		asignarImagenAlBoton(botones[i][j], null);
                	} else {
                		asignarImagenAlBoton(botones[i][j], imagenDividida[celda.getValor() - 1]);
                }
            }
           }
        }
        contMovimientosJugador.setText(String.valueOf(tablero.getMovimientos()));
    }

    private void felicitarJugador() {
        JOptionPane.showMessageDialog(this.ventanaJuego, "¡Felicidades! Has completado el rompecabezas en " + tablero.getMovimientos() + " movimientos.");
    }

    private void abrirMenu() {
        this.ventanaJuego.dispose(); // Cierra el juego

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new InterfazMenuPuzzleDeslizante();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
