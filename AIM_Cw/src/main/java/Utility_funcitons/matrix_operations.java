package Utility_funcitons;

import Utility_funcitons.coordinates;
import Utility_funcitons.evals;

public class matrix_operations {
    private final coordinates coords;
    private final evals evalFuncs;

    public matrix_operations(){
        this.coords=new coordinates();
        this.evalFuncs=new evals();
    }
    //given an integer array of orders of cities to traverse, make an Adjacency matrix
    public double[][] makeAdMatrix(int[] cities){
        double[][] adMatrix=makeZeroMatrix(cities.length);
        for (int i = 0; i < cities.length-1; i++) {
            String coord1= coords.getCoordsList().get(cities[i]);
            String coord2= coords.getCoordsList().get(cities[i+1]);
            adMatrix[cities[i]][cities[i+1]]=evalFuncs.calcEuDist(coord1,coord2);
        }
        return adMatrix;
    }

    //make a matrix full of zeros
    public double[][] makeZeroMatrix(int size){
        double[][] adMatrix=new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adMatrix[i][j]=0;
            }
        }
        return adMatrix;
    }

    //combining 2 adjacency matrices
    public double[][] combineAd(double[][] ad1, double[][] ad2){
        double[][] com = new double[ad1.length][ad1.length];
        for (int i = 0; i < ad1.length; i++) {
            for (int j = 0; j < ad1.length; j++) {
                if(ad1[i][j]==0.00 && ad2[i][j]!=0.00){
                    com[i][j]=ad2[i][j];
                }
                if(ad1[i][j]!=0.00 && ad2[i][j]==0.00){
                    com[i][j]=ad1[i][j];
                }
            }
        }
        return com;
    }
}
