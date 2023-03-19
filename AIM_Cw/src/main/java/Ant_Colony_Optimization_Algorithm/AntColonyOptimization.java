package Ant_Colony_Optimization_Algorithm;

import java.util.Random;

public class AntColonyOptimization {
    private int numOfAnts;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private int numIterations;
    private int numCities;
    private double[][] distanceMatrix;
    private double[][] pheromoneMatrix;
    private ACOTestFrameConfig acoTestFrameConfig = ACOTestFrameConfig.getInstance();

    public AntColonyOptimization(int numCities, double[][] distanceMatrix) {
        this.numOfAnts = acoTestFrameConfig.getNumOfAnts();
        this.alpha = acoTestFrameConfig.getAlpha();
        this.beta = acoTestFrameConfig.getBeta();
        this.evaporationRate = acoTestFrameConfig.getEvaporationRate();
        this.numIterations = acoTestFrameConfig.getNumIterations();
        this.numCities = numCities;
        this.distanceMatrix = distanceMatrix;
        this.pheromoneMatrix = new double[this.numCities][this.numCities];
        generatePheromoneMatrix();
    }


    public int getNumOfAnts() {
        return numOfAnts;
    }

    public double getAlpha() {
        return alpha;
    }


    public double getBeta() {
        return beta;
    }
    public double getEvaporationRate() {
        return evaporationRate;
    }
    public int getNumIterations() {
        return numIterations;
    }
    public int getNumCities() {
        return numCities;
    }
    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }
    public double[][] getPheromoneMatrix() {
        return pheromoneMatrix;
    }
    public void generatePheromoneMatrix(){
       for(int i = 0; i < numCities; i++){
           for(int j = 0; j < numCities; j++){
               pheromoneMatrix[i][j] = 0.1;
           }
       }
    }
    public void displayBestSolution(){
        int[] solution = findBestSolution();
        System.out.println("Ant Colony Optimization Algorithm: ");
        System.out.printf("Parameter: numOfAnts = %d, alpha = %.1f, beta = %.1f, evalorationRate = %.1f ,numOfIterations = %d\n",
                this.numOfAnts, this.alpha, this.beta, this.evaporationRate, this.numIterations
                );
        for(int i = 0; i < solution.length; i++){
            System.out.printf("%d " , solution[i]+1);
        }
        System.out.println();
    }
    public int[] findBestSolution(){
        int[] bestSolution = new int[numCities];
        double bestSolutionLength = Double.MAX_VALUE;
        Random rand = new Random();
        for(int i = 0; i < numIterations; i++){
            Ant[] ants = new Ant[numOfAnts];
            //Generate ants and let it explore all the cities by starting a random city
            for(int j = 0; j < numOfAnts; j++){
                ants[j] = new Ant(rand.nextInt(numCities), numCities);
                ants[j].constructSolution(distanceMatrix,pheromoneMatrix,alpha,beta);
                if(ants[j].getTourLength() < bestSolutionLength){
                    bestSolutionLength = ants[j].getTourLength();
                    System.arraycopy(ants[j].getTour(), 0, bestSolution, 0, numCities);
                }
            }
            updatePheromones(ants);
        }

        return bestSolution;
    }
    public void updatePheromones(Ant[] ants){
        double[][] deltaPheromones = new double[numCities][numCities];
        // calculate delta pheromone of the ants
        for(Ant ant : ants){
            double pheromoneDelta =(double) 1/ant.getTourLength();
            for(int i = 0; i < numCities; i++){
                int city1 = ant.getTour()[i];
                int city2 = ant.getTour()[(i+1)%numCities];
                deltaPheromones[city1][city2] += pheromoneDelta;
                deltaPheromones[city2][city1] += pheromoneDelta;
            }
        }
        // update pheromone level
        for(int i = 0; i < numCities; i++){
            for(int j = 0; j < numCities; j++){
                pheromoneMatrix[i][j] = (1-evaporationRate)*pheromoneMatrix[i][j] + deltaPheromones[i][j];
            }
        }
    }

}

