package Memetic_Algorithm;

import Utility.AdTuples_memes;
import Utility.edges_memes;
import Utility.matrix_operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EAX {

    private final matrix_operators matrixOperators;

    public EAX(){
        this.matrixOperators=new matrix_operators();
    }
    /*
    // function to get the int array form of an E set solution, by turning E set into inter sols
    public void generateSolution(int[] parentA, List<AdTuples_memes[][]> ESet){
        AdTuples_memes[][] AMatrix = matrixOperators.makeAdMatrix(parentA,"A");
        List<AdTuples_memes[][]> interSet = genIntermediateSet(AMatrix,ESet);
        for (int i = 0; i < interSet.size(); i++) {
            List<edges_memes> edges=matrixOperators.getEdges(interSet.get(i));
            edges_memes startingEdge=edges.get(findFirstInstance(parentA[0],edges));

        }
    }
     */

    //generate intermediate solution by removing edges εi ∩ εA from φA and adding εi ∩ εB to φA

    public List<AdTuples_memes[][]> genIntermediateSet(AdTuples_memes[][] parentA,AdTuples_memes[][] parentB, List<AdTuples_memes[][]> ESet){
        List<AdTuples_memes[][]> finalRes=new ArrayList<>();
        for (int i = 0; i < ESet.size(); i++) {
            AdTuples_memes[][] commonA = matrixOperators.returnCommonEdges(parentA, ESet.get(i));
            AdTuples_memes[][] commonB = matrixOperators.returnCommonEdges(parentB,ESet.get(i));

            AdTuples_memes[][]removedCommon = matrixOperators.removeEdges(parentA,commonA);
            AdTuples_memes[][]addedCommon = matrixOperators.addEdges(removedCommon,commonB);
            finalRes.add(addedCommon);
        }
        return finalRes;
    }


    //function that takes in a list of AB cycles, if any two has vertices in common, then combine the two graphs and save it into the second entry of comparison
    public List<AdTuples_memes[][]> makeESet(List<int[]> cycles){
        //list of cycles in adjacency matrices form
        List<AdTuples_memes[][]> comparingList =makeListAd(cycles);
        for (int j = 1; j < cycles.size(); j++) {
            if(matrixOperators.hasCommonElements(cycles.get(0),cycles.get(j))){
                comparingList.set(j,matrixOperators.combineAd(comparingList.get(0),comparingList.get(j)));
            }
        }
        return comparingList;
    }

    //function that takes in a list of cycles and turns them into adjacency matrices
    public List<AdTuples_memes[][]> makeListAd(List<int[]> cycles){
        List<AdTuples_memes[][]> finalList = new ArrayList<>();
        for (int i = 0; i < cycles.size(); i++) {
            finalList.add(matrixOperators.makeAdMatrix(cycles.get(i),"\0"));
        }
        return finalList;
    }

    //finding AB Cycles, return the order of cities to traverse in the form of int arrays
    //Should accept a combined matrix with two parents
    //only find AB cycle using greedy approach
    public List<int[]> findABCycles(AdTuples_memes[][] combined){
        List<Integer> cycle= new ArrayList<>();
        List<int[]> ABCycles=new ArrayList<>();
        int max=0;
        //repeat until all nodes been visited(will have break statement for that)
        while(true){
            //random start point
            int start = pickRandomPoint(combined);
            //add start to cycle
            cycle.add(start);
            //pick any edge from any parent
            int tolerance =0;
            edges_memes connectingEdge = findNeighbour(combined[start],start,"\0");
            //if cant find a neighbour for starting point, pick a different starting point 100 times or until an edge can be found, else just cancel entire thing
            while(connectingEdge.getTo()==-1&&connectingEdge.getFrom()==-1){
                start=pickRandomPoint(combined);
                connectingEdge = findNeighbour(combined[start],start,"\0");
                if(connectingEdge.getTo()==-1&&connectingEdge.getFrom()==-1){
                    connectingEdge.setStatus(true);
                }
                tolerance++;
                if(tolerance==100){
                    break;
                }
            }
            if(tolerance==100){
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
                    cycle.add(connectingEdge.getTo());
                    ABCycles.add(matrixOperators.convertIntegers(cycle));
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

    // a function that takes in the from destination and returns the position of the edge in the list
    public int findFirstInstance(int from, List<edges_memes> edges){
        int index=-1;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getFrom() == from) {
                index=i;
            }
        }
        return index;
    }
}
