package Utility_funcitons;

import java.util.ArrayList;
import java.util.List;

public class matrix_operations {
    private final coordinates coords;
    private final evals evalFuncs;

    public matrix_operations(){
        this.coords=new coordinates();
        this.evalFuncs=new evals();
    }
    //given an integer array of orders of cities to traverse, make an Adjacency matrix
    public AdTuples[][] makeAdMatrix(int[] cities, String matrix){
        AdTuples[][] adMatrix=makeZeroMatrix(cities.length);
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
    public AdTuples[][] makeZeroMatrix(int size){
        AdTuples[][] adMatrix=new AdTuples[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adMatrix[i][j]=new AdTuples(Double.MAX_VALUE,"\0");
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
    public AdTuples[][] combineAd(AdTuples[][] ad1, AdTuples[][] ad2){
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

    //finding AB Cycles, return the order of cities to traverse in the form of int arrays
    //Should accept a combined matrix with two parents
    //only find AB cycle using greedy approach
    // should start with vertices from A
    public List<int[]> findABCycles(AdTuples[][] combined){
        List<int[]> ABCycles=new ArrayList<>();
        //for each starting node check, start the search there
        for (int i = 0; i < combined.length; i++) {
            int alt=2;
            //create cycle list
            List<Integer> cycle=new ArrayList<Integer>();
            cycle.add(i);
            //start wit vertices from A or AB
            int nextNearest=findClosetNeighbour(combined[i],"B");
            while(true){
                if(nextNearest== i){
                    //if forming a complete cycle
                    ABCycles.add(convertIntegers(cycle));
                    //clear the list to facilitate the next cycle
                    cycle.clear();
                    break;
                }
                if (nextNearest==-1){
                    //give up on searching if no nearest neighbour
                    cycle.clear();
                    break;
                }
                //alternate between cities
                else {
                    //before adding must ensure that the new closest node doesnt go back to the old node
                    int tabu = nextNearest;
                    if(alt%2==0){
                        cycle.add(nextNearest);
                        nextNearest=findClosetNeighbour(combined[nextNearest],"A");
                    }
                    else {
                        cycle.add(nextNearest);
                        nextNearest=findClosetNeighbour(combined[nextNearest],"B");
                    }
                    alt++;
                }
            }

        }
        return ABCycles;
    }

    // find the closest neighbour for row specified by neighbourhood with an opposite city
    public int findClosetNeighbour(AdTuples[] neighbourhood,String matrixName){
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
