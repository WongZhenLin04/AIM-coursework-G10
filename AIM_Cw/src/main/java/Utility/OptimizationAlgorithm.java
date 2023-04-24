package Utility;

public abstract class OptimizationAlgorithm {
    private int[] bestSolution;
    private double bestSolutionFitness;

    public OptimizationAlgorithm() {
    }

    public int[] getBestSolution() {
        return bestSolution;
    }

    public double getBestSolutionFitness() {
        return bestSolutionFitness;
    }

    public void setBestSolution(int[] bestSolution) {
        this.bestSolution = bestSolution;
    }

    public void setBestSolutionFitness(double bestSolutionFitness) {
        this.bestSolutionFitness = bestSolutionFitness;
    }
}
