import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class JuegoGUI {
    private JFrame ventana;
    private JTextArea registroCombate;
    private JLabel labelJugador, labelEnemigo, imagenJugador, imagenEnemigo;
    private JProgressBar barraVidaJugador, barraVidaEnemigo;
    private JButton botonAtacar, botonCurar;
    private Jugador jugador;
    private Enemigo enemigoActual;
    private List<Enemigo> enemigos;

    public JuegoGUI() {
        inicializarJugador();
        inicializarEnemigos();
        inicializarVentana();
        actualizarEstado();
    }
// opcones para escoger el jugador 
    private void inicializarJugador() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de tu personaje:");
        Object[] opciones = {"Guerrero", "Mago", "Arquero"};
        String tipo = (String) JOptionPane.showInputDialog(
                null,
                "Elige tu clase:",
                "Clase del jugador",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        jugador = new Jugador(nombre, tipo);
    }

    private void inicializarEnemigos() {
        enemigos = new ArrayList<>();
        enemigos.add(new Enemigo("Orco", 50, 15, 5));
        enemigos.add(new Enemigo("Esqueleto", 40, 10, 3));
        enemigoActual = enemigos.get(0);
    }

    private void inicializarVentana() {
        ventana = new JFrame("Juego RPG - Barras de Vida Mejoradas");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setLayout(new BorderLayout());

        // Registro de combate
        registroCombate = new JTextArea();
        registroCombate.setEditable(false);
        registroCombate.setFont(new Font("Arial", Font.PLAIN, 14));
        registroCombate.setBackground(new Color(230, 230, 250));
        JScrollPane scrollPane = new JScrollPane(registroCombate);

        // Panel de estadísticas
        JPanel panelEstado = new JPanel(new GridLayout(2, 3));
        panelEstado.setBackground(new Color(200, 200, 255));

        labelJugador = new JLabel();
        labelJugador.setFont(new Font("Arial", Font.BOLD, 16));
        labelJugador.setForeground(new Color(34, 139, 34));

        imagenJugador = new JLabel(new ImageIcon("jugador.png")); // Imagen opcional

        barraVidaJugador = crearBarraDeVida();

        labelEnemigo = new JLabel();
        labelEnemigo.setFont(new Font("Arial", Font.BOLD, 16));
        labelEnemigo.setForeground(new Color(178, 34, 34));

        imagenEnemigo = new JLabel(new ImageIcon("enemigo.png")); // Imagen opcional

        barraVidaEnemigo = crearBarraDeVida();

        panelEstado.add(imagenJugador);
        panelEstado.add(barraVidaJugador);
        panelEstado.add(labelJugador);
        panelEstado.add(imagenEnemigo);
        panelEstado.add(barraVidaEnemigo);
        panelEstado.add(labelEnemigo);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(255, 239, 213));
        botonAtacar = crearBoton("Atacar", new Color(255, 69, 0));
        botonCurar = crearBoton("Curarse", new Color(30, 144, 255));

        panelBotones.add(botonAtacar);
        panelBotones.add(botonCurar);

        // Agregar componentes a la ventana
        ventana.add(panelEstado, BorderLayout.NORTH);
        ventana.add(scrollPane, BorderLayout.CENTER);
        ventana.add(panelBotones, BorderLayout.SOUTH);

        // Acciones de los botones
        botonAtacar.addActionListener(e -> realizarAtaque());
        botonCurar.addActionListener(e -> realizarCuracion());

        ventana.setVisible(true);
    }

    private JProgressBar crearBarraDeVida() {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(100);
        barra.setStringPainted(true);
        barra.setFont(new Font("Arial", Font.BOLD, 14));
        barra.setForeground(Color.GREEN);
        return barra;
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }

    private void actualizarEstado() {
        labelJugador.setText("<html><strong>Jugador: </strong>" + jugador.getNombre() +
                " | Vida: " + jugador.getPuntosDeVida() +
                " | Ataque: " + jugador.getAtaque() +
                " | Defensa: " + jugador.getDefensa() + "</html>");

        labelEnemigo.setText("<html><strong>Enemigo: </strong>" + enemigoActual.getNombre() +
                " | Vida: " + enemigoActual.getPuntosDeVida() +
                " | Ataque: " + enemigoActual.getAtaque() +
                " | Defensa: " + enemigoActual.getDefensa() + "</html>");

        actualizarBarraDeVida(barraVidaJugador, jugador.getPuntosDeVida());
        actualizarBarraDeVida(barraVidaEnemigo, enemigoActual.getPuntosDeVida());
    }

    private void actualizarBarraDeVida(JProgressBar barra, int vida) {
        barra.setValue(vida);

        // Cambiar color según el nivel de vida
        if (vida > 60) {
            barra.setForeground(Color.GREEN);
        } else if (vida > 30) {
            barra.setForeground(Color.YELLOW);
        } else {
            barra.setForeground(Color.RED);
        }
    }

    private void realizarAtaque() {
        registroCombate.append(jugador.getNombre() + " ataca a " + enemigoActual.getNombre() + "\n");
        jugador.atacar(enemigoActual);

        if (enemigoActual.getPuntosDeVida() <= 0) {
            registroCombate.append(enemigoActual.getNombre() + " ha sido derrotado.\n");
            enemigos.remove(enemigoActual);

            if (enemigos.isEmpty()) {
                registroCombate.append("¡Felicidades! .\n");
                botonAtacar.setEnabled(false);
                botonCurar.setEnabled(false);
                mostrarVictoria();
                return;
            } else {
                enemigoActual = enemigos.get(0);
                registroCombate.append("Aparece un nuevo enemigo: " + enemigoActual.getNombre() + "\n");
            }
        } else {
            enemigoActual.atacar(jugador);
            registroCombate.append(enemigoActual.getNombre() + " ataca a " + jugador.getNombre() + "\n");

            if (jugador.getPuntosDeVida() <= 0) {
                registroCombate.append("Has sido derrotado. Fin del juego.\n");
                botonAtacar.setEnabled(false);
                botonCurar.setEnabled(false);
                return;
            }
        }

        actualizarEstado();
    }

    private void realizarCuracion() {
        registroCombate.append(jugador.getNombre() + " se cura 20 puntos de vida.\n");
        jugador.curar(20);
        enemigoActual.atacar(jugador);
        registroCombate.append(enemigoActual.getNombre() + " ataca mientras te curas.\n");

        actualizarEstado();
    }

    private void mostrarVictoria() {
        // Crear ventana emergente de victoria
        JDialog victoriaDialog = new JDialog(ventana, "¡Has Ganado!", true);
        victoriaDialog.setSize(400, 300);
        victoriaDialog.setLayout(new BorderLayout());
        victoriaDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
        // Texto de victoria
        JLabel mensaje = new JLabel("¡Felicidades! Has ganado el juego.", JLabel.CENTER);
        mensaje.setFont(new Font("Arial", Font.BOLD, 24));
        mensaje.setForeground(Color.ORANGE);
    
        // Animación: Parpadeo del texto
        Timer timer = new Timer(500, e -> {
            Color colorActual = mensaje.getForeground();
            mensaje.setForeground(colorActual == Color.ORANGE ? Color.RED : Color.ORANGE);
        });
        timer.start();
    
        // Panel con imagen de fondo
        JLabel imagen = new JLabel(new ImageIcon("celebracion.png")); // Usa una imagen como fondo
        imagen.setLayout(new BorderLayout());
        imagen.add(mensaje, BorderLayout.CENTER);
    
        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        JButton botonSalir = new JButton("Salir");
        JButton botonReiniciar = new JButton("Reiniciar");
    
        botonSalir.addActionListener(e -> System.exit(0)); // Cierra el juego
        botonReiniciar.addActionListener(e -> {
            victoriaDialog.dispose();
            ventana.dispose();
            new JuegoGUI(); // Reinicia el juego
        });
    
        panelBotones.add(botonSalir);
        panelBotones.add(botonReiniciar);
    
        // Añadir componentes
        victoriaDialog.add(imagen, BorderLayout.CENTER);
        victoriaDialog.add(panelBotones, BorderLayout.SOUTH);
    
        // Centrar la ventana de victoria
        victoriaDialog.setLocationRelativeTo(ventana);
        victoriaDialog.setVisible(true);
    }
    

    public static void main(String[] args) {
        new JuegoGUI();
    }
}



