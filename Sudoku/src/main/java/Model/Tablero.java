package Model;

public class Tablero  {
    private Celda[][] celda ;
    private static final int tamanioMatriz = 9;

    public Tablero() {
        celda = new Celda[tamanioMatriz][tamanioMatriz];
        crearMatriz();
    }

    public void crearMatriz(){
        for(int i=0; i<tamanioMatriz; i++){
            for(int j=0; j<tamanioMatriz; j++){
                celda[i][j] = new Celda();
            }

        }
    }

    public Celda[][] getCelda() {
        return celda;
    }

    public Celda getCelda(int fil, int column) {
        return celda[fil][column];
    }


}
