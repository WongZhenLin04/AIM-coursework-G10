package Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class matrix_operators {
    private final coordinates coords;
    private final evals evalFuncs;

    public matrix_operators(){
        this.coords=new coordinates();
        this.evalFuncs=new evals();
    }
    //given an integer array of orders of cities to traverse, make an Adjacency matrix
    public AdTuples_memes[][] makeAdMatrix(int[] cities, String matrix){
        AdTuples_memes[][] adMatrix= makeMaxMatrix(cities.length);
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
    public List<int[]> findABCycles(AdTuples_memes[][] combined){
        List<Integer> cycle= new ArrayList<>();
        List<int[]> ABCycles=new ArrayList<>();
        int max=0;
        //repeat until all nodes been visited(will have break statement for that)
        while(max!=10000){
            //random start point
            int start = pickRandomPoint(combined);
            //add start to cycle
            cycle.add(start);
            //pick any edge from any parent
            int tolarence =0;
            edges_memes connectingEdge = findNeighbour(combined[start],start,"\0");
            //if cant find a neighbour for starting point, pick a different starting point 100 times or until an edge can be found, else just cancel entire thing
            while(connectingEdge.getTo()==-1&&connectingEdge.getFrom()==-1){
                start=pickRandomPoint(combined);
                connectingEdge = findNeighbour(combined[start],start,"\0");
                tolarence++;
                if(tolarence==100){
                    break;
                }
            }
            if(tolarence==100){
                break;
            }
            //set visited for connecting edge
            combined[connectingEdge.getFrom()][connectingEdge.getTo()].setVisited(true);
            cycle.add(connectingEdge.getTo());
            while(true){
                String name = combined[connectingEdge.getFrom()][connectingEdge.getTo()].getMatrixName();
                connectingEdge = findNeighbour(combined[connectingEdge.getTo()],connectingEdge.getTo(),name);
                if (connectingEdge.getFrom()==-1||connectingEdge.getTo()==-1){
                    //reset combined
                    resetVisited(combined,cycle);
                    cycle.clear();
                    break;
                }
                if(connectingEdge.getTo()==start){
                    ABCycles.add(convertIntegers(cycle));
                    cycle.clear();
                    break;
                }
                combined[connectingEdge.getFrom()][connectingEdge.getTo()].setVisited(true);
                cycle.add(connectingEdge.getTo());
            }
            max++;
        }
        return ABCycles;
    }

    //reset the visited nodes to not visited
    public void resetVisited(AdTuples_memes[][] combined,List<Integer> cycle){
        for (int i = 0; i < cycle.size()-1; i++) {
            combined[cycle.get(i)][cycle.get(i+1)].setVisited(false);
        }
    }

    //picking a vertex
    public int pickRandomPoint(AdTuples_memes[][] combined){
        // Find all non-empty points in the matrix
        List<int[]> nonZeroPoints = new ArrayList<>();
        for (int i = 0; i < combined.length; i++) {
            for (int j = 0; j < combined[0].length; j++) {
                if (!combined[i][j].isVisited()) {
                    nonZeroPoints.add(new int[]{i,j});
                }
            }
        }
        Random random = new Random();
        return nonZeroPoints.get(random.nextInt(nonZeroPoints.size()))[0];
    }

    // find the closest neighbour for row specified by neighbourhood with an opposite city
    public edges_memes findNeighbour(AdTuples_memes[] neighbourhood,int from, String matrixName){
        edges_memes edge=new edges_memes(-1,-1);
        for (int i = 0; i < neighbourhood.length; i++) {
            //if the neighbour is doesn't have an unknown distance and doesn't share the same neighbour as input, then update
            if((neighbourhood[i].getDistance()!=Double.MAX_VALUE)&&(!neighbourhood[i].getMatrixName().equals(matrixName))&&(!neighbourhood[i].isVisited())){
                edge=new edges_memes(from,i);
                break;
            }
        }
        return edge;
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
