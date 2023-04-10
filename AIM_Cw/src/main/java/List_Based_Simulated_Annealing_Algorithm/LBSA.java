package List_Based_Simulated_Annealing_Algorithm;

import java.util.*;

import Utility.evals;

public class LBSA {

    private int perturbationSize;
    private int substringSize;
    private int iterations;
    private double initialTemperature;
    private CoolingSchedule coolingSchedule;
    private List<int[]> initialSolution;
    private int[] bestSolution;
    private double bestFitness;
    private evals evalFuncs;
    private int temperatureListLength;
    private double initialAcceptanceProbability;

    public LBSA() {

    }

    public LBSA(int perturbationSize, int substringSize, int iterations, double initialTemperature, CoolingSchedule coolingSchedule) {
        this.perturbationSize = perturbationSize;
        this.substringSize = substringSize;
        this.iterations = iterations;
        this.initialTemperature = initialTemperature;
        this.coolingSchedule = coolingSchedule;
        this.initialSolution = new ArrayList<>();
        this.evalFuncs = new evals();
        this.bestSolution = null;
        this.bestFitness = Double.MAX_VALUE;
    }

    public LBSA(int perturbationSize, int substringSize, int iterations, int temperatureListLength, double initialAcceptanceProbability) {
        this.perturbationSize = perturbationSize;
        this.substringSize = substringSize;
        this.iterations = iterations;
        this.temperatureListLength = temperatureListLength;
        this.initialAcceptanceProbability = initialAcceptanceProbability;
        this.initialSolution = new ArrayList<>();
        this.evalFuncs = new evals();
        this.bestSolution = null;
        this.bestFitness = Double.MAX_VALUE;

    }

    public void displayBestSolution() {
        System.out.println("List-Based Simulated Annealing ALgorithm: ");
        /**
         * temperature list length is determined based on the size of the s
         * earch space and the desired level of exploration versus exploitation. A longer
         * temperature list allows the algorithm to explore more of the
         * search space and find a global optimum, but it also increases
         * the computational cost. A shorter temperature list focuses more on exploiting
         * promising solutions and may converge faster, but it may get stuck in local optima.
         */
        System.out.printf("Parameter: numOfIterations = %d, perturbationSize = %d, temperatureListLength = %d, initialAcceptanceProbability = %.1f\n",
                iterations, perturbationSize, temperatureListLength, initialAcceptanceProbability
        );
        runLBSA();
        System.out.println("Best Solution = " + Arrays.toString(this.getBestSolution()));
        System.out.println("Best Fitness = " + evalFitness(this.getBestSolution()));
    }

    // generates the initial solution
    public void genInitial() {
        for (int i = 0; i < perturbationSize; i++) {
            initialSolution.add(genRandomisedCities());
        }
        //      runLBSA();
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

    private int[] genCities() {
        int[] res = new int[107];
        for (int i = 0; i < 107; i++) {
            res[i] = i;
        }

        return res;
    }

    /**
     * Generate Solution based on Inverse Operator
     * 1 2 3 4 5 -> (i = 1 , j = 4) -> 1 5 3 4 2 ->  1 5 4 3 2
     * 1 2 3 4 5 end = 4 , start = 1 -> 1 5 3 4 2 -> 1 5 4 3 2
     *
     * @param sol
     * @param substringSize
     * @return
     */
    public int[] genNewSol(int[] sol, int substringSize) {
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

    /**
     * Generate Solution based on swap 2 cities Operator
     * 1 2 3 4 5 -> (i = 1 , j = 4) -> 1 5 3 4 2
     *
     * @param sol cities array
     * @return new solution of cities array
     */
    public int[] genSwapNewSol(int[] sol) {
        int[] newSol = sol.clone();
        Random rand = new Random();
        int i = 0, j = 0;

        // make sure i and j diffrent random numbers
        do {
            i = rand.nextInt(0, sol.length - 1);
            j = rand.nextInt(0, sol.length - 1);
        } while (i == j);

        int temp = newSol[i];
        newSol[i] = sol[j];
        newSol[j] = temp;
        return newSol;
    }

    // evaluate the fitness of a solution
    private double evalFitness(int[] solution) {
        return evalFuncs.evalSol(solution);
    }

    /*
    //    public void runLBSA() {
    //
    //        double currentTemperature = initialTemperature;
    //        for (int i = 0; i < iterations; i++) {
    //            for (int j = 0; j < perturbationSize; j++) {
    //                int[] currentSolution = initialSolution.get(j);
    ////                int[] currentSolution = bestSolution;
    //                int[] newSolution = genNewSol(currentSolution, substringSize);
    //                double currentFitness = evalFitness(currentSolution);
    //                double newFitness = evalFitness(newSolution);
    //                double deltaFitness = newFitness - currentFitness;
    //                if (deltaFitness < 0 || Math.exp(-deltaFitness / currentTemperature) > Math.random()) {
    //                    currentSolution = newSolution;
    //                    currentFitness = newFitness;
    //                }
    //                if (currentFitness < bestFitness) {
    //                    bestSolution = currentSolution;
    //                    bestFitness = currentFitness;
    //                }
    //            }
    //            currentTemperature = coolingSchedule.getTemperature(i);
    //        }
    //
    //    }

     */
    public double calculateBadResultAcceptanceProbability(double tmax, double currentFitness, double newFitness) {
        return Math.exp(-(newFitness - currentFitness) / tmax);
    }

    public double calculateNewTemperature(double r_probability, double oldTemp, double currentFitness, double newFitness) {
        // t = (t - (f(y) - f(x)))/ln(r)
        return (oldTemp - (newFitness - currentFitness)) / Math.log(r_probability);
    }

    public void runLBSA() {
        // produce initial temperature list
        List<Double> temperatureList = createInitialTemperatureList(temperatureListLength, initialAcceptanceProbability);
        double maxTemp = Collections.max(temperatureList);
        List<int[]> evaluationResultList = new ArrayList<>();
        int[] solution = genCities();
        double currentFitness = 0;
        int k = 0;

        while (k <= iterations) {
            k++;
            // t is used to store the total temperature calculated by formula di/ln(ri)
            double t = 0;
            int m = 0;
            int c = 0;

            while (m <= perturbationSize) {
                m++;
                int[] currentSolution = solution;
//                System.out.println("Current Solution: " + Arrays.toString(currentSolution));
                currentFitness = evalFitness(currentSolution);
                int[] newSolution = genNewSol(currentSolution, substringSize);
//                int[] newSolution = genSwapNewSol(currentSolution);
//                System.out.println("New Solution: " + Arrays.toString(newSolution));
                double newFitness = evalFitness(newSolution);
                Boolean is_new_picked = false;
//                System.out.println("Curent fitness = " + currentFitness + " , new finess = " + newFitness);
                if (newFitness < currentFitness) {
                    solution = newSolution;
                    is_new_picked = true;
                } else {
                    maxTemp = Collections.max(temperatureList);
                    double p = calculateBadResultAcceptanceProbability(Collections.max(temperatureList), currentFitness, newFitness);
//                    System.out.println("Tmax = " + Collections.max(temperatureList));
                    double r = Math.random();
//                    System.out.println("P = " + p + " , random = " + r);
                    if (r < p) {
//                        System.out.println("T = " + t);
                        t = calculateNewTemperature(r, t, currentFitness, newFitness); // t = (t-delta)/ln(r)
//                        System.out.println("New T = " + t);
                        temperatureList.remove(Collections.max(temperatureList));
                        temperatureList.add(t);
//                        System.out.println("temperature list size = " + temperatureList.size());
                        //  temperatureList.set(temperatureList.indexOf(maxTemp), t);
                        solution = newSolution;
                        is_new_picked = true;
                        c++;
                    }
                    if (is_new_picked) {
                        evaluationResultList.add(newSolution);
                    } else if (!is_new_picked) {
                        evaluationResultList.add(currentSolution);
                    }
                }
            }
            if (c > 0) {
                // pop maxtemp, insert t/c
                t = t / c;
                temperatureList.remove(Collections.max(temperatureList));
                temperatureList.add(t / c);
            }

        }
        bestSolution = solution;
        bestFitness = currentFitness;
    }

    public List<Double> createInitialTemperatureList(int temperatureListLength, double intialAcceptanceProbability) {
        List<Double> temperatureList = new ArrayList<>(temperatureListLength);
        // generate initial solution x randomly


        int[] currentSolution = genCities();

        for (int i = 0; i < temperatureListLength; i++) {
            int[] newSolution = genNewSol(currentSolution, substringSize);
//            int[] newSolution = genSwapNewSol(currentSolution);
            double currentFitness = evalFitness(currentSolution);
            double newFitness = evalFitness(newSolution);
            if (newFitness < currentFitness) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }
            double t = (-Math.abs(newFitness - currentFitness)) / Math.log(intialAcceptanceProbability);
            temperatureList.add(t);
        }

        return temperatureList;
    }

    public int[] getBestSolution() {
        return bestSolution;
    }

    public double getBestFitness() {
        return bestFitness;
    }
}