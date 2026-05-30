package Model;

public class Celda {

    private int valor;
    private boolean editable;


    //Constructores basio : genera celdas con valor de cero y con estado editable
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
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
        configurarCelda();
    }

    // Configura la celda de tal manera que solo se pueden ingresar numeros del 1 al 6
    private void configurarCelda(){
        if (valor<1 || valor>6){
            valor=0;
        }
    }
}
