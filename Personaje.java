// Clase abstracta que sirve como base para personajes (jugador, enemigos, etc.)
public abstract class Personaje {

    // Atributos comunes para todos los personajes
    protected String nombre;
    protected int puntosDeVida;
    protected int ataque;
    protected int defensa;

    // Constructor que inicializa los atributos del personaje
    public Personaje(String nombre, int puntosDeVida, int ataque, int defensa) {
        this.nombre = nombre;
        this.puntosDeVida = puntosDeVida;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    // Métodos para obtener información del personaje
    public String getNombre() {
        return nombre;
    }

    public int getPuntosDeVida() {
        return puntosDeVida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    // Método para que el personaje reciba daño (no permite vida negativa)
    public void recibirDaño(int daño) {
        puntosDeVida -= daño;
        if (puntosDeVida < 0) {
            puntosDeVida = 0;
        }
    }

    // Método abstracto que define cómo un personaje ataca a otro
    public abstract void atacar(Personaje enemigo);
}


