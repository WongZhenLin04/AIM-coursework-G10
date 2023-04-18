package List_Based_Simulated_Annealing_Algorithm;

import java.util.*;

import Memetic_Algorithm.opt2;
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

    public LBSA(int perturbationSize, int iterations, int temperatureListLength, double initialAcceptanceProbability) {
        this.perturbationSize = perturbationSize;
        this.iterations = iterations;
        this.temperatureListLength = temperatureListLength;
        this.initialAcceptanceProbability = initialAcceptanceProbability;
        this.initialSolution = new ArrayList<>();
        this.evalFuncs = new evals();
        this.bestSolution = null;
        this.bestFitness = Double.MAX_VALUE;

    }

    int randomNum1 = 0;
    int randomNum2 = 0;

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
        System.out.println("Best Fitness = " +getBestFitness());
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
        int[] res = new int[108];
        for (int i = 0; i < 107; i++) {
            res[i] = i;
        }
        return res;
    }
    private int[] generateIntialSolution(){
        bestSolution = genCities();
        return bestSolution;
    }
    /**
     * Generate Solution based on Inverse Operator
     * 1 2 3 4 5 -> (i = 1 , j = 4) -> 1 5 3 4 2 ->  1 5 4 3 2
     * 1 2 3 4 5 end = 4 , start = 1 -> 1 5 3 4 2 -> 1 5 4 3 2
     *
     * @param sol
     * @return
     */
    public int[] genInverseNewSol(int[] sol, int i, int j) {
        int[] newSol = sol.clone();
//        Random rand = new Random();
//        do{
//            i = rand.nextInt(1,  newSol.length-2);
//            j = rand.nextInt(1, newSol.length-2);
//        }while(j - i < 1 && i -j >= newSol.length-3);
        while (j > i) {
            int temp = newSol[i];
            newSol[i] = newSol[j];
            newSol[j] = temp;
            i++;
            j--;
        }
        return newSol;
    }

    public int[] generateCandidateSolution(int[] currentSolution) {
        int[] candidateSolution = currentSolution.clone();
        Random rand = new Random();

        // Select two positions
        int position1 = rand.nextInt(candidateSolution.length);
        int position2 = rand.nextInt(candidateSolution.length);

        // Ensure positions are distinct
        while (position2 == position1) {
            position2 = rand.nextInt(candidateSolution.length);
        }

        // Apply inverse operator
        int start = Math.min(position1, position2);
        int end = Math.max(position1, position2);
        while (end > start) {
            int temp = candidateSolution[start];
            candidateSolution[start] = candidateSolution[end];
            candidateSolution[end] = temp;
            start++;
            end--;
        }

        // Apply insert operator
        int insertPosition = rand.nextInt(candidateSolution.length - 1);
        int elementToInsert = candidateSolution[start];
        for (int i = start; i > insertPosition; i--) {
            candidateSolution[i] = candidateSolution[i - 1];
        }
        candidateSolution[insertPosition] = elementToInsert;

        // Apply swap operator
        int swapPosition = rand.nextInt(candidateSolution.length);
        int temp = candidateSolution[start];
        candidateSolution[start] = candidateSolution[swapPosition];
        candidateSolution[swapPosition] = temp;

        // Evaluate the three neighbor solutions and select the best one
        // based on the evaluation function or objective function

        return candidateSolution;
    }

    /**
     * Generat solution base on insert operator
     * 1 2 3 4 5 -> (i = 1, j = 3) -> 1 4 2 3 5
     * @param sol
     * @return
     */
    public int[] genInsertNewSol(int[] sol, int i, int j){
        int[] newSol = sol.clone();
        Random rand = new Random();

        if(i > j){
            int temp = i;
            i = j;
            j = temp;
        }
        if(i < j){
            int temp = newSol[i];
            newSol[i] = newSol[j];
            newSol[j] = temp;
            i++;
            while(i <= j){
                newSol = swap(newSol, i, j);
                i++;

            }
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
    public int[] genSwapNewSol(int[] sol, int i, int j) {
        int[] newSol = sol.clone();
        int temp = newSol[i];
        newSol[i] = sol[j];
        newSol[j] = temp;
        return newSol;
    }
    public int[] genHybridNewSol(int sol[]){
        int[] newSol = sol.clone();

        Random rand = new Random();
        int i = 0, j =0;
        do {
            i = rand.nextInt(1, sol.length - 2);
            j = rand.nextInt(1, sol.length - 2);
        } while (j - i < 1 && i -j >= newSol.length-3);

        double inverseSolutionFitness = evalFitness(genInverseNewSol(sol, i, j));
        double swapSolutionFitness = evalFitness(genSwapNewSol(sol, i, j));
        double insertSolutionFitness =evalFitness(genInsertNewSol(sol, i, j));
        if(inverseSolutionFitness <= swapSolutionFitness && inverseSolutionFitness <= insertSolutionFitness){
            return genInverseNewSol(sol, i, j);
        }
        else if(swapSolutionFitness <= inverseSolutionFitness && swapSolutionFitness <= insertSolutionFitness){
            return genSwapNewSol(sol, i, j);
        }
        else {
            return genInsertNewSol(sol,i,j);
        }


    }
    private int[] swap(int[] sol , int i, int j){
        int[] swappedSol = sol.clone();
        int temp = swappedSol[i];
        swappedSol[i] = swappedSol[j];
        swappedSol[j] = temp;
        return swappedSol;
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

    opt2 opt2 = new opt2();
    Random random = new Random();

    public double calculateNewTemperature(double r_probability, double oldTemp, double currentFitness, double newFitness) {
        // t = (t - (f(y) - f(x)))/ln(r)
        return (oldTemp - (newFitness - currentFitness)) / Math.log(r_probability);
    }

    public void runLBSA() {
        // produce initial temperature list
        generateIntialSolution();
        List<Double> temperatureList = createInitialTemperatureList(temperatureListLength, initialAcceptanceProbability);
        double maxTemp = Collections.max(temperatureList);
        List<int[]> evaluationResultList = new ArrayList<>();
        int[] solution = getBestSolution();
        double currentFitness = 0;
        int k = 0;

        while (k <= iterations) {
            k++;
            System.out.print(k);
            // t is used to store the total temperature calculated by formula di/ln(ri)
            double t = 0;
            int m = 0;
            int c = 0;


            while (m <= perturbationSize) {
                m++;
                int[] currentSolution = solution;

                while (randomNum1 == randomNum2) {
                    randomNum1 = random.nextInt(107);
                    randomNum2 = random.nextInt(107);
                }
//                System.out.println("Current Solution: " + Arrays.toString(currentSolution));
                currentFitness = evalFitness(currentSolution);

                int[] newSolution = genHybridNewSol(currentSolution);

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
        /* After find best solution, go back to the starting point */
        solution[solution.length-1] = solution[0];
        bestSolution = solution;
        bestFitness = currentFitness;
    }

    public List<Double> createInitialTemperatureList(int temperatureListLength, double intialAcceptanceProbability) {
        List<Double> temperatureList = new ArrayList<>(temperatureListLength);
        // generate initial solution x randomly


        int[] currentSolution = getBestSolution();

        for (int i = 0; i < temperatureListLength; i++) {

            int[] newSolution = genHybridNewSol(currentSolution);

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