package Ant_Colony_Optimization_Algorithm;

import java.util.Random;

public class Ant {
    private int[] tour;
    private boolean[] visited;
    private double tourLength;
    private int currentPosition;
    private int numCities;


    public Ant(int startingCity, int numCities){
        this.tour = new int[numCities];
        this.visited = new boolean[numCities];
        this.tourLength = 0;
        this.currentPosition = startingCity;
        this.numCities = numCities;
    }

    public int[] getTour() {
        return tour;
    }

    public boolean[] getVisited() {
        return visited;
    }

    public double getTourLength() {
        return tourLength;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getNumCities() {
        return numCities;
    }

    public void constructSolution(double[][] distanceMatrix, double[][] pheromoneMatrix, double alpha, double beta){
        // Journey of an ant: visits all cities
        for(int i = 0; i < this.numCities;i++){
            // Choose next city by using probabilistic rule
            int nextCity = chooseNextCity(distanceMatrix,pheromoneMatrix,alpha,beta,new Random());

            // Move to next node
            // Update path and visited node of this ant
            tour[i] = nextCity;
            visited[nextCity] = true;
            tourLength += distanceMatrix[currentPosition][nextCity];
            currentPosition = nextCity;
        }
        // Return to the starting city , from last city to starting
        tourLength += distanceMatrix[tour[numCities-1]][tour[0]];
        currentPosition = tour[0];
        tour[tour.length-1] = currentPosition;
    }

    // Choose the next city by using probabilistic rule
    public int chooseNextCity(double[][] distanceMatrix, double[][] pheromoneMatrix, double alpha, double beta, Random rand){
        int nextCity = -1;
        double[] probabilities = new double[numCities];
        double total = 0;
        //calculate the probabilities of all unvisited cities
        for(int i = 1; i < numCities; i++){
            if(!visited[i]){
                double pheromone = Math.pow(pheromoneMatrix[this.currentPosition][i], alpha);
                // heuristic = 1/Lk
                double heuristic = Math.pow(1/distanceMatrix[currentPosition][i],beta);
                probabilities[i] = pheromone*heuristic;
        //        System.out.println("Probability " + i + " = " + probabilities[i]);
                total += probabilities[i];
     //           System.out.println("total = " + total);
            }
        }
    //    System.out.println("total = " + total);
        for(int i = 0; i < numCities; i++){
            probabilities[i] /= total;
   //         System.out.println("Probability at " + i + " = " + probabilities[i]);
        }

        double r = rand.nextDouble(0,1);
        double cumulativeSum = 0;
  //      System.out.println("r = " + r);
        for(int i = 0; i < numCities; i++){
            if(!visited[i]){
                cumulativeSum += probabilities[i];
  //              System.out.println("Cumulative sum at " + i + " = " + cumulativeSum);
                if(r < cumulativeSum){
                    nextCity = i;
    //                System.out.println(nextCity);
                    return nextCity;

                }
            }
        }
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                return i;
            }
        }

        return nextCity;

    }

}
