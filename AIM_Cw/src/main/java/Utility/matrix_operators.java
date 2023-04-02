package Utility;

import java.util.*;

public class matrix_operators {
    private final coordinates coords;
    private final evals evalFuncs;

    public matrix_operators(){
        this.coords=new coordinates();
        this.evalFuncs=new evals();
    }
    //given an integer array of orders of cities to traverse, make an Adjacency matrix
    public AdTuples_memes[][] makeAdMatrix(int[] cities, String matrix){
        AdTuples_memes[][] adMatrix= makeMaxMatrix(107);
        for (int i = 0; i < cities.length-1; i++) {
            String coord1= coords.getCoordsList().get(cities[i]);
            String coord2= coords.getCoordsList().get(cities[i+1]);
            adMatrix[cities[i]][cities[i+1]].setDistance(evalFuncs.calcEuDist(coord1,coord2));
            adMatrix[cities[i]][cities[i+1]].setMatrixName(matrix);
        }
        return adMatrix;
    }

    /*Make a distance adjacency matrix by giving list of cities*/
    public double[][] matrixDistancesBetweenCities(List<String> cities){
        double[][] distanceMatrix = makeZeroMatrixDouble(107);
        for(int i = 0; i < cities.size(); i++){
            for(int j = 0; j < cities.size(); j++){
                String coord1 = cities.get(i);
                String coord2 = cities.get(j);
                distanceMatrix[i][j] = evalFuncs.calcEuDist(coord1,coord2);
                distanceMatrix[j][i] = evalFuncs.calcEuDist(coord1,coord2);
            }
        }
        return distanceMatrix;
    }

    //make a matrix full of Max values
    public AdTuples_memes[][] makeMaxMatrix(int size){
        AdTuples_memes[][] adMatrix=new AdTuples_memes[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adMatrix[i][j]=new AdTuples_memes(Double.MAX_VALUE,"\0",false);
            }
        }
        return adMatrix;
    }

    //make a matrix full of zeros
    private double[][] makeZeroMatrixDouble(int size){
        double[][] matrix = new double[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    //combining 2 adjacency matrices
    public AdTuples_memes[][] combineAd(AdTuples_memes[][] ad1, AdTuples_memes[][] ad2){
        AdTuples_memes[][] finalRes= copyMatrices(ad1);
        for (int i = 0; i < ad1.length; i++) {
            for (int j = 0; j < ad1.length; j++) {
                if(ad1[i][j].getDistance()==ad2[i][j].getDistance() && ad1[i][j].getDistance()!=Double.MAX_VALUE) {
                    finalRes[i][j].setMatrixName("C");
                }
                else if(ad2[i][j].getDistance()!=Double.MAX_VALUE){
                    finalRes[i][j].setMatrixName(ad2[i][j].getMatrixName());
                    finalRes[i][j].setDistance(ad2[i][j].getDistance());
                }
            }
        }
        return finalRes;
    }

    //converts an int array list to int array
    public int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i);
        }
        return ret;
    }

    //functon returns true if two arrays have the same element
    public boolean hasCommonElements(int[] a1, int[] a2)
    {
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a2.length; j++) {
                if (a1[i] == a2[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //given two adjacency matrices, return the common edges
    public AdTuples_memes[][] returnCommonEdges(AdTuples_memes[][] Ad1,AdTuples_memes[][] Ad2){
        AdTuples_memes[][] copy=copyMatrices(Ad1);
        for (int i = 0; i < Ad1.length; i++) {
            for (int j = 0; j < Ad1.length; j++) {
                if((Ad1[i][j].getDistance()!=Double.MAX_VALUE&&Ad2[i][j].getDistance()==Double.MAX_VALUE)||(Ad1[i][j].getDistance()==Double.MAX_VALUE&&Ad2[i][j].getDistance()!=Double.MAX_VALUE)){
                    copy[i][j].setDistance(Double.MAX_VALUE);
                }
            }
        }
        return copy;
    }

    //given two E matrices and a parent, remove the common edges of E matrices from parent
    public AdTuples_memes[][] removeEdges(AdTuples_memes[][] parent, AdTuples_memes[][] Ad1){
        AdTuples_memes[][] finalRes=copyMatrices(parent);
        for (int i = 0; i < Ad1.length; i++) {
            for (int j = 0; j < Ad1.length; j++) {
                if(parent[i][j].getDistance()!=Double.MAX_VALUE&&Ad1[i][j].getDistance()!=Double.MAX_VALUE){
                    finalRes[i][j].setDistance(Double.MAX_VALUE);
                }
            }
        }
        return finalRes;
    }

    //add edges from Ad1
    public AdTuples_memes[][] addEdges(AdTuples_memes[][] offSpring, AdTuples_memes[][] Ad1){
        AdTuples_memes[][] finalRes=copyMatrices(offSpring);
        for (int i = 0; i < Ad1.length; i++) {
            for (int j = 0; j < Ad1.length; j++) {
                if(offSpring[i][j].getDistance()==Double.MAX_VALUE&&Ad1[i][j].getDistance()!=Double.MAX_VALUE){
                    finalRes[i][j].setDistance(Ad1[i][j].getDistance());
                }
            }
        }
        return finalRes;
    }

    //from an Ad matrix, get the edges
    public List<edges_memes> getEdges(AdTuples_memes[][] ad){
        List<edges_memes> edges =new ArrayList<>();
        for (int i = 0; i < ad.length; i++) {
            for (int j = 0; j < ad.length; j++) {
                if(ad[i][j].getDistance()!=Double.MAX_VALUE){
                    edges.add(new edges_memes(i,j));
                }
            }
        }
        return edges;
    }

    //function to copy matrices of AdTuples
    public AdTuples_memes[][] copyMatrices(AdTuples_memes[][] a){
        AdTuples_memes[][] res = makeMaxMatrix(a.length);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                if(a[i][j].getDistance()!=Double.MAX_VALUE){
                    res[i][j].setDistance(a[i][j].getDistance());
                    res[i][j].setMatrixName(a[i][j].getMatrixName());
                }
            }
        }
        return res;
    }

}
