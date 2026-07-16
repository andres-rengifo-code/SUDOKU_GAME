package Model;

/**
 * Adaptador que envuelve la clase {@link Juego} y la expone
 * a través de la interfaz {@link IJuego}.
 *
 * Patrón utilizado: Adapter (de objeto).
 *
 * Permite al controlador depender únicamente de la abstracción
 * {@link IJuego} sin conocer la implementación concreta {@link Juego}.
 * Si en el futuro se necesita una variante del juego (por ejemplo,
 * con dificultad distinta o modo sin errores), basta con crear
 * otro adaptador sin modificar el controlador.
 */
public class JuegoAdapter implements IJuego {

    /**
     * Instancia concreta del juego que este adaptador envuelve.
     */
    private final Juego juego;

    /**
     * Construye el adaptador creando internamente una nueva instancia de {@link Juego}.
     */
    public JuegoAdapter() {
        this.juego = new Juego();
    }

    /**
     * Construye el adaptador a partir de una instancia de {@link Juego} ya existente.
     *
     * @param juego Instancia concreta a adaptar.
     */
    public JuegoAdapter(Juego juego) {
        this.juego = juego;
    }

    /** {@inheritDoc} */
    @Override
    public void iniciarJuego() {
        juego.iniciarJuego();
    }

    /** {@inheritDoc} */
    @Override
    public boolean getionarAccion(int fila, int columna, int valor) {
        return juego.getionarAccion(fila, columna, valor);
    }

    /** {@inheritDoc} */
    @Override
    public boolean verificarJugadorGano() {
        return juego.verificarJugadorGano();
    }

    /** {@inheritDoc} */
    @Override
    public boolean contadorErrores() {
        return juego.contadorErrores();
    }

    /** {@inheritDoc} */
    @Override
    public int[] darPista() {
        return juego.darPista();
    }

    /** {@inheritDoc} */
    @Override
    public Tablero getTablero() {
        return juego.getTablero();
    }

    /** {@inheritDoc} */
    @Override
    public int getErrores() {
        return juego.getErrores();
   }

    /** {@inheritDoc} */
    @Override
    public boolean isJuegoTermino() {
        return juego.isJuegoTermino();
    }

    /** {@inheritDoc} */
    @Override
    public int getPistasRestantes() {
        return juego.getPistasRestantes();
    }
}
