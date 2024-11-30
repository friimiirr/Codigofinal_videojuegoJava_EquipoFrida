// Clase Jugador, que extiende la clase abstracta Personaje
public class Jugador extends Personaje {

    // Constructor que inicializa un jugador con nombre y tipo (Mago o Arquero)
    public Jugador(String nombre, String tipo) {
        super(nombre, 100, 20, 10); // Valores base para vida, ataque y defensa
        if (tipo.equals("Mago")) { // Ajustes específicos para el tipo "Mago"
            this.ataque = 25;
            this.defensa = 5;
        } else if (tipo.equals("Arquero")) { // Ajustes específicos para el tipo "Arquero"
            this.ataque = 15;
            this.defensa = 15;
        }
    }

    // Implementación del método abstracto atacar
    @Override
    public void atacar(Personaje enemigo) {
        int daño = Math.max(0, ataque - enemigo.getDefensa()); // Daño no puede ser negativo
        enemigo.recibirDaño(daño);
    }

    // Método para curar al jugador, con un máximo de vida de 100
    public void curar(int cantidad) {
        puntosDeVida = Math.min(puntosDeVida + cantidad, 100);
    }
}


