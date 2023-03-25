package List_Based_Simulated_Annealing_Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

import Utility_funcitons.evals;

public class LBSA {

    private int populationSize;
    private int substringSize;
    private int iterations;
    private double initialTemperature;
    private CoolingSchedule coolingSchedule;
    private List<int[]> initialSolution;
    private int[] bestSolution;
    private double bestFitness;
    private evals evalFuncs;

    public LBSA(int populationSize, int substringSize, int iterations, double initialTemperature, CoolingSchedule coolingSchedule) {
        this.populationSize = populationSize;
        this.substringSize = substringSize;
        this.iterations = iterations;
        this.initialTemperature = initialTemperature;
        this.coolingSchedule = coolingSchedule;
        this.initialSolution = new ArrayList<>();
        this.evalFuncs = new evals();
        this.bestSolution = null;
        this.bestFitness = Double.MAX_VALUE;
    }

    // generates the initial solution
    public void genInitial() {
        for (int i = 0; i < populationSize; i++) {
            initialSolution.add(genRandomisedCities());
        }
        runLBSA();
    }

    private int[] genRandomisedCities() {
        int[] res = new int[107];
        for (int i = 0; i < res.length; i++) {
            res[i] = i;
        }
        Random rand = new Random();
        for (int i = 0; i < 107; i++) {
            int j = rand.nextInt(107);
            int temp = res[i];
            res[i] = res[j];
            res[j] = temp;
        }
        return res;
    }

    private int[] genNewSol(int[] sol, int substringSize) {
        int[] newSol = sol.clone();
        Random rand = new Random();
        int start = rand.nextInt(newSol.length - substringSize + 1);
        int end = start + substringSize - 1;
        while (end > start) {
            int temp = newSol[start];
            newSol[start] = newSol[end];
            newSol[end] = temp;
            start++;
            end--;
        }
        return newSol;
    }

    // evaluate the fitness of a solution
    private double evalFitness(int[] solution) {
        return evalFuncs.evalSol(solution);
    }

    public void runLBSA() {
        double currentTemperature = initialTemperature;
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < populationSize; j++) {
                int[] currentSolution = initialSolution.get(j);
                int[] newSolution = genNewSol(currentSolution, substringSize);
                double currentFitness = evalFitness(currentSolution);
                double newFitness = evalFitness(newSolution);
                double deltaFitness = newFitness - currentFitness;
                if (deltaFitness < 0 || Math.exp(-deltaFitness / currentTemperature) > Math.random()) {
                    currentSolution = newSolution;
                    currentFitness = newFitness;
                }
                if (currentFitness < bestFitness) {
                    bestSolution = currentSolution;
                    bestFitness = currentFitness;
                }
            }
            currentTemperature = coolingSchedule.getTemperature(i);
        }
    }

    public int[] getBestSolution() {
        return bestSolution;
    }

    public double getBestFitness() {
        return bestFitness;
    }
}