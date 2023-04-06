package Memetic_Algorithm;

import Utility.AdTuples_memes;
import Utility.edges_memes;
import Utility.evals;
import Utility.matrix_operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EAX {

    private final matrix_operators matrixOperators;
    private final evals evals;

    public EAX(){
        this.matrixOperators=new matrix_operators();
        this.evals=new evals();
    }

    // function to get the int array form of an E set solution, by turning E set into inter sols
    public List<int[]> applyEax(int[] parentA,int[] parentB){
        AdTuples_memes[][] AMatrix = matrixOperators.makeAdMatrix(parentA,"A");
        AdTuples_memes[][] BMatrix = matrixOperators.makeAdMatrix(parentB,"B");
        AdTuples_memes[][] ABMatrix =matrixOperators.combineAd(AMatrix,BMatrix);
        List<int[]>cycles=findABCycles(ABMatrix);
        List<AdTuples_memes[][]>Markiplier=makeESet(cycles);
        List<AdTuples_memes[][]> interSet = genIntermediateSet(AMatrix,BMatrix,Markiplier);
        //use greedy approach to get the shortest path
        //offSprings
        List<int[]> offSpring = new ArrayList<>();
        for (int i = 0; i < interSet.size(); i++) {
            offSpring.add(construction(interSet.get(i)));
        }
        return offSpring;
    }

    //from a list of offsprings, pick the best offspring
    public int[] pickBest(List<int[]> offSprings) {
        int[] bestTour = offSprings.get(0);
        double bestScore = evals.evalSol(bestTour);
        for (int[] ints : offSprings) {
            double currentScore = evals.evalSol(ints);
            if (currentScore < bestScore) {
                bestScore = currentScore;
                bestTour = ints;
            }
        }
        return bestTour;
    }

    //from a population, pick the worst candidate and return its index
    public int pickWorst(List<int[]> offSprings) {
        double worstScore = Double.MIN_VALUE;
        int index = -1;
        for (int i = 0;i<offSprings.size();i++) {
            double currentScore = evals.evalSol(offSprings.get(i));
            if (currentScore > worstScore) {
                worstScore = currentScore;
                index=i;
            }
        }
        return index;
    }

    //function that takes in an intermediate solution and forms a solution offspring
    public int[] construction(AdTuples_memes[][] distanceMatrix) {
        List<int[]> tours = new ArrayList<>();
        List<Integer> tour = new ArrayList<>();
        //start from all edges
        List<edges_memes> edges = matrixOperators.getEdges(distanceMatrix);
        for (int i = 0; i < edges.size(); i++) {
            int start = edges.get(i).getFrom();
            tour.add(start);
            int step = edges.get(i).getTo();
            distanceMatrix[start][step].setVisited(true);
            while (tour.size()<107) {
                edges_memes neighbour = findNeighbour(distanceMatrix[step], step, "Any");
                if (neighbour.getFrom() == -1 && neighbour.getTo() == -1) {
                    tours.add(matrixOperators.convertIntegers(tour));
                    resetVisited(distanceMatrix, tour);
                    break;
                } else {
                    distanceMatrix[neighbour.getFrom()][neighbour.getTo()].setVisited(true);
                    tour.add(step);
                    step = neighbour.getTo();
                }
            }
        }
        List<int[]> completeTours = new ArrayList<>();
        for (int[] ints : tours) {
            completeTours.add(completeSolution(ints));
        }
        return pickBest(completeTours);
    }

    //given an incomplete solution fill in the rest of the cities greedily
    public int[] completeSolution (int[] incomplete){
        List<Integer> remainingCities = findMissingCities(incomplete);
        int citiesLeft = remainingCities.size();
        List<Integer> intList = new ArrayList<>();
        for (int i : incomplete)
        {
            intList.add(i);
        }
        for (int i = 0; i < citiesLeft; i++) {
            double bestScore = Double.MAX_VALUE;
            int bestCityIndex = -1;
            for (int j = 0; j < remainingCities.size(); j++) {
                int city = remainingCities.get(j);
                intList.add(city);
                double currentScore = evals.evalSol(matrixOperators.convertIntegers(intList));
                if(currentScore<bestScore){
                    bestCityIndex=j;
                    bestScore = currentScore;
                }
                intList.remove(intList.size()-1);
            }
            intList.add(remainingCities.get(bestCityIndex));
            remainingCities.remove(bestCityIndex);
        }
        return matrixOperators.convertIntegers(intList);
    }

    //given an incomplete solution, find the remaining cities
    public List<Integer> findMissingCities (int[] incomplete){
        boolean[] present = new boolean[107];
        List<Integer> missing = new ArrayList<>();
        for (int i = 0; i < incomplete.length; i++) {
            present[incomplete[i]]=true;
        }
        for (int i = 0; i < present.length; i++) {
            if(!present[i]){
                missing.add(i);
            }
        }
        return missing;
    }

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
            if((neighbourhood[i].getDistance()!=Double.MAX_VALUE)&&(!neighbourhood[i].getMatrixName().equals(matrixName))&&(!neighbourhood[i].isVisited())&&(i!=from)){
                edge=new edges_memes(from,i);
                break;
            }
        }
        return edge;
    }

}
