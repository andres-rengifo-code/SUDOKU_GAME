package Model;

public class Tablero  {
    private Celda[][] celda ;
    private static final int tamanioMatriz = 6;

    // craa el tablero de la matriz siendo un tablero de celdas 6x6
    public Tablero() {
        celda = new Celda[tamanioMatriz][tamanioMatriz];
        crearMatriz();
    }

    //recorre toda la matriz de celda y le aigna un objeto de la clase Celda  asignandole un objeto a cada celda
    public void crearMatriz(){
        for(int i=0; i<tamanioMatriz; i++){
            for(int j=0; j<tamanioMatriz; j++){
                celda[i][j] = new Celda();
            }

        }
    }

    // retorna la matriz
    public Celda[][] getCelda() {
        return celda;
    }

    //retorna un celda de la matriz
    public Celda getCelda(int fil, int column) {
        return celda[fil][column];
    }


}
