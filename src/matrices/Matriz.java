/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    public static Matriz MultiplicarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] * b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    } 
  // Empieza el calculo de la inversa
  public static void multiplicarMatriz(double n, double[][] matriz) {
    for(int i=0;i<matriz.length;i++)
        for(int j=0;j<matriz.length;j++)
            matriz[i][j]*=n;
}


public static double[][] matrizInversa(double[][] matriz) {
    double det=1/determinante(matriz);
    double[][] nmatriz=matrizAdjunta(matriz);
    multiplicarMatriz(det,nmatriz);
    return nmatriz;
}
public static double[][] matrizAdjunta(double [][] matriz){
    return matrizTranspuesta(matrizCofactores(matriz));
}
public static double[][] matrizCofactores(double[][] matriz){
    double[][] nm=new double[matriz.length][matriz.length];
    for(int i=0;i<matriz.length;i++) {
        for(int j=0;j<matriz.length;j++) {
            double[][] det=new double[matriz.length-1][matriz.length-1];
            double detValor;
            for(int k=0;k<matriz.length;k++) {
                if(k!=i) {
                    for(int l=0;l<matriz.length;l++) {
                        if(l!=j){
                            int indice1=k<i ? k : k-1 ;
                            int indice2=l<j ? l : l-1 ;
                            det[indice1][indice2]=matriz[k][l];
                        }
                    }
                }
            }
            detValor=determinante(det);
            nm[i][j]=detValor * (double)Math.pow(-1, i+j+2);
        }
    }
    return nm;
}

public static double[][] matrizTranspuesta(double [][] matriz){
    double[][]nuevam=new double[matriz[0].length][matriz.length];
    for(int i=0; i<matriz.length; i++){
        for(int j=0; j<matriz.length; j++)
            nuevam[i][j]=matriz[j][i];
    }
    return nuevam;
}

public static double determinante(double[][] matriz){
    double det;
    if(matriz.length==2){
        det=(matriz[0][0]*matriz[1][1])-(matriz[1][0]*matriz[0][1]);
        return det;
    }
    double suma=0;
    for(int i=0; i<matriz.length; i++){
    double[][] nm=new double[matriz.length-1][matriz.length-1];
        for(int j=0; j<matriz.length; j++){
            if(j!=i){
                for(int k=1; k<matriz.length; k++){
                    int indice=-1;
                    if(j<i)
                        indice=j;
                    else if(j>i)
                        indice=j-1;
                        nm[indice][k-1]=matriz[j][k];
                }
            }
        }
        if(i%2==0)
            suma+=matriz[i][0] * determinante(nm);
        else
            suma-=matriz[i][0] * determinante(nm);
    }
    return suma;
}

public void printMatriz(double[][] mat) {
    for (int i = 0; i < mat.length; i++) {
        for (int j = 0; j < mat[i].length; j++) {
            System.out.print(mat[i][j] + " ");
        }

        System.out.println();
    }

}

// Aquí termina el calculo de la inversa

    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
}
