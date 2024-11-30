// Clase Enemigo que extiende la clase abstracta Personaje
public class Enemigo extends Personaje {

    // Constructor que inicializa un enemigo con nombre, puntos de vida, ataque y defensa
    public Enemigo(String nombre, int puntosDeVida, int ataque, int defensa) {
        super(nombre, puntosDeVida, ataque, defensa);
    }

    // Implementación del método abstracto atacar
    // Define cómo el enemigo inflige daño al jugador
    @Override
    public void atacar(Personaje jugador) {
        int daño = Math.max(0, ataque - jugador.getDefensa()); // Daño mínimo es 0
        jugador.recibirDaño(daño); // Aplica el daño al jugador
    }
}




