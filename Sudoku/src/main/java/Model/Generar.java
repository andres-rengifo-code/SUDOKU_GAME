package Model;

import java.util.Random;

public class Generar {

    private int[] numerosSudoku = {1, 2, 3, 4, 5, 6};
    private Validar validar = new Validar();
    private Random random = new Random();

    public boolean generarSudoku(Tablero tablero, int posicion) {

        if (posicion == 36) {
            return true;
        }
        int fila = posicion / 6;
        int columna = posicion % 6;


        for (int i = 0; i < numerosSudoku.length; i++) {
            int aleatorio = random.nextInt(6);
            int temp = numerosSudoku[i];
            numerosSudoku[i] = numerosSudoku[aleatorio];
            numerosSudoku[aleatorio] = temp;
        }

        for (int i = 0; i < numerosSudoku.length; i++) {
            Celda celdaTemporal = new Celda();
            celdaTemporal.setValor(numerosSudoku[i]);
            if (validar.validarFila(tablero, celdaTemporal, fila) && validar.validarColumna(tablero, celdaTemporal, columna) && validar.validarSubcuadro(fila, columna, tablero, celdaTemporal)) {
                tablero.getCelda(fila, columna).setValor(numerosSudoku[i]);
                if (generarSudoku(tablero, posicion + 1)) {
                    return true;
                } else {
                    tablero.getCelda(fila, columna).setValor(0);
                }


            }

        }
        return false;
    }

    public void eliminarCeldasPorSubcuadro(Tablero tablero) {

        int filasBloque = 2;
        int columnasBloque = 3;

        int totalFilas = 6;
        int totalColumnas = 6;

        for (int inicioFila = 0; inicioFila < totalFilas; inicioFila += filasBloque) {
            for (int inicioCol = 0; inicioCol < totalColumnas; inicioCol += columnasBloque) {

                int eliminadas = 0;

                while (eliminadas < 4) {

                    int fila = inicioFila + random.nextInt(filasBloque);
                    int columna = inicioCol + random.nextInt(columnasBloque);

                    Celda celda = tablero.getCelda(fila, columna);

                    if (celda.getValor() != 0) {
                        celda.setValor(0);
                        celda.setEditable(true);
                        eliminadas++;
                    }
                }
            }
        }
    }
}
