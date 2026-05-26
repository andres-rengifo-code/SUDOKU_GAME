package Model;

public class Celda {

    private int valor;
    private boolean editable;


    //Constructores
    public Celda() {
        valor=0;
        editable=true;
    }

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

    // Configura la celda de tal manera que solo se pueden ingresar numeros del 1 al 9
    private void configurarCelda(){
        if (valor<1 || valor>9){
            valor=0;
        }
    }
}
