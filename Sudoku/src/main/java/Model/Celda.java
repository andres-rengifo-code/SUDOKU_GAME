package Model;

public class Celda {

    /**
     * Valor almacenado en la celda.
     * Los valores válidos son del 1 al 6.
     * El valor 0 representa una celda vacía.
     */
    private int valor;

    /**
     * Indica si la celda puede ser modificada por el usuario.
     * true = editable.
     * false = bloqueada.
     */
    private boolean editable;


    /**
     * Constructor por defecto.
     * Crea una celda vacía y editable.
     */
    public Celda() {
        valor=0;
        editable=true;
    }

    //Constructor
    public Celda(boolean editable, int valor) {
        this.editable = editable;
        this.valor = valor;
        configurarCelda();
    }


    // Getters and setters
    /**
     * Obtiene el estado de edición de la celda.
     *
     * @return true si la celda es editable, false en caso contrario.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Modifica el estado de edición de la celda.
     *
     * @param editable Nuevo estado de edición.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Obtiene el valor almacenado en la celda.
     *
     * @return Valor actual de la celda.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Asigna un nuevo valor a la celda.
     * Después de asignarlo, se valida para asegurar
     * que esté dentro del rango permitido.
     *
     * @param valor Nuevo valor de la celda.
     */
    public void setValor(int valor) {
        this.valor = valor;
        configurarCelda();
    }

    /**
     * Valida el valor de la celda.
     * Si el valor no está entre 1 y 6,
     * se asigna automáticamente 0.
     *
     * El valor 0 representa una celda vacía.
     */
    private void configurarCelda(){
        if (valor<1 || valor>6){
            valor=0;
        }
    }
}
