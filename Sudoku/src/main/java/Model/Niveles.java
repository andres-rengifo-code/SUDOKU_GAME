package Model;

import java.util.Random;

public class Niveles {

    private int celdasAEliminar;
    private Random random = new Random();

    public int nivelFacil(){
       return celdasAEliminar=random.nextInt(6)+31;
    }

    public int nivelMedio(){
        return celdasAEliminar=random.nextInt(6)+44;
    }

    public int nivelDificil(){
        return celdasAEliminar=random.nextInt(6)+56;
    }


}
