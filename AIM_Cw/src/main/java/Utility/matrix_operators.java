package Utility;

import java.util.ArrayList;
import java.util.List;

public class matrix_operators {
    private final coordinates coords;
    private final evals evalFuncs;

    public matrix_operators(){
        this.coords=new coordinates();
        this.evalFuncs=new evals();
    }
    //given an integer array of orders of cities to traverse, make an Adjacency matrix
    public AdTuples_memes[][] makeAdMatrix(int[] cities, String matrix){
        AdTuples_memes[][] adMatrix=makeZeroMatrix(cities.length);
        for (int i = 0; i < cities.length-1; i++) {
            String coord1= coords.getCoordsList().get(cities[i]);
            String coord2= coords.getCoordsList().get(cities[i+1]);
            adMatrix[cities[i]][cities[i+1]].setDistance(evalFuncs.calcEuDist(coord1,coord2));
            adMatrix[cities[i]][cities[i+1]].setMatrixName(matrix);
        }
        return adMatrix;
    }

    /*Make a distance adjancency matrix by giving list of cities*/
    public double[][] matrixDistancesBetweenCities(List<String> cities){
        double[][] distanceMatrix = makeZeroMatrixDouble(cities.size());
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

    //make a matrix full of zeros
    public AdTuples_memes[][] makeZeroMatrix(int size){
        AdTuples_memes[][] adMatrix=new AdTuples_memes[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adMatrix[i][j]=new AdTuples_memes(Double.MAX_VALUE,"\0");
            }
        }
        return adMatrix;
    }
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
        for (int i = 0; i < ad1.length; i++) {
            for (int j = 0; j < ad1.length; j++) {
                if(ad1[i][j].getDistance()==ad2[i][j].getDistance() && ad1[i][j].getDistance()!=Double.MAX_VALUE) {
                    ad1[i][j].setMatrixName("C");
                }
                else if(ad1[i][j].getDistance()==Double.MAX_VALUE){
                    ad1[i][j].setMatrixName(ad2[i][j].getMatrixName());
                    ad1[i][j].setDistance(ad2[i][j].getDistance());
                }
            }
        }
        return ad1;
    }

    //from a llist of cities, get the possible edges
    public List<edges_memes> getEdges(int[] path){
        List<edges_memes> edges_memes =new ArrayList<>();
        for (int i = 0; i < path.length-1; i++) {
            edges_memes.add(new edges_memes(path[i],path[i+1]));
        }
        return edges_memes;
    }

    //finding AB Cycles, return the order of cities to traverse in the form of int arrays
    //Should accept a combined matrix with two parents
    //only find AB cycle using greedy approach
    public List<int[]> findABCycles(AdTuples_memes[][] combined, int[] path){
        List<int[]> ABCycles=new ArrayList<>();
        List<edges_memes> edges_memes = getEdges(path);
        //while the number of edges not empty, pick a vertex and start AB cycle
        while(!edges_memes.isEmpty()){
            //pick random vertex

        }
        return ABCycles;
    }

    // find the closest neighbour for row specified by neighbourhood with an opposite city
    public int findClosetNeighbour(AdTuples_memes[] neighbourhood, String matrixName){
        double closestNeighbourDist=Double.MAX_VALUE;
        //if no neighbour found, return -1
        int closestNeighbourIndex=-1;
        for (int i = 0; i < neighbourhood.length; i++) {
            //if the neighbour is doesn't have a 0 distance and is closer to source then make that the closest neighbour and doesn't share the same neighbour as input, then update
            if((neighbourhood[i].getDistance()!=Double.MAX_VALUE)&&(neighbourhood[i].getDistance()<closestNeighbourDist)&&(!neighbourhood[i].getMatrixName().equals(matrixName))){
                closestNeighbourDist=neighbourhood[i].getDistance();
                closestNeighbourIndex=i;
            }
        }
        return closestNeighbourIndex;
    }

    //converts an int array list to int array
    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i);
        }
        return ret;
    }
}
