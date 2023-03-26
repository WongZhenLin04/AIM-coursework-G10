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

    public void displayBestSolution(){
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
        System.out.println("Best Solution = "+ Arrays.toString(this.getBestSolution()));
        System.out.println("Best Fitness = " + this.getBestFitness());
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

    /**
     * Generate Solution based on Inverse Operator
     * 1 2 3 4 5 -> (i = 1 , j = 4) -> 1 5 3 4 2 ->  1 5 4 3 2
     * 1 2 3 4 5 end = 4 , start = 1 -> 1 5 3 4 2 -> 1 5 4 3 2
     * @param sol
     * @param substringSize
     * @return
     */
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

//    public void runLBSA() {
//        double currentTemperature = initialTemperature;
//        for (int i = 0; i < iterations; i++) {
//            for (int j = 0; j < populationSize; j++) {
//                int[] currentSolution = initialSolution.get(j);
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
//    }
    public double calculateBadResultAcceptanceProbability(double tmax, double currentFitness, double newFitness){
        return Math.exp(-(newFitness - currentFitness) / tmax);
    }
    public double calculateNewTemperature(double r_probability, double oldTemp,double currentFitness, double newFitness){
        // t = (t - (f(y) - f(x)))/ln(r)
        return (oldTemp - (newFitness - currentFitness))/Math.log(r_probability);
    }
    public void runLBSA(){
        // produce initial temperature list
        genInitial();
        List<Double> temperatureList = createInitialTemperatureList(temperatureListLength, initialAcceptanceProbability);
        double maxTemp = Collections.max(temperatureList);
        List<int[]> evaluationResultList = new ArrayList<>();
        int[] currentSolution = initialSolution.get(0);
        double currentFitness = evalFitness(currentSolution);
        int k = 0;
        while (k <= iterations){
            k++;
            // t is used to store the total temperature calculated by formula di/ln(ri)
            double t = 0;
            int m = 0;
            int c = 0;
            while (m <= perturbationSize){
                m++;
                int[] newSolution = genNewSol(currentSolution, substringSize);


                double newFitness = evalFitness(newSolution);
                Boolean is_new_picked = false;

                if(newFitness < currentFitness){
                    currentSolution = newSolution;
                    currentFitness = newFitness;
                    is_new_picked = true;
                }
                else{
                    double p = calculateBadResultAcceptanceProbability(maxTemp, currentFitness, newFitness);
                    double r = Math.random();
                    if(r > p){
                        t = calculateNewTemperature(r, t, newFitness, currentFitness);
                        temperatureList.add(t);
                      //  temperatureList.set(temperatureList.indexOf(maxTemp), t);
                        currentSolution = newSolution;
                        currentFitness = newFitness;
                        is_new_picked = true;
                        c++;
                    }
                    if(is_new_picked){
                        evaluationResultList.add(newSolution);
                    }
                    else if(!is_new_picked){
                        evaluationResultList.add(currentSolution);
                    }
                }
            }
            if(c > 0){
                // pop maxtemp, insert t/c
                t = t/c;
                temperatureList.remove(maxTemp);
                temperatureList.add(t/c);
            }

        }
        bestSolution = currentSolution;
        bestFitness = currentFitness;
    }

    public List<Double> createInitialTemperatureList(int temperatureListLength, double intialAcceptanceProbability){
        List<Double> temperatureList = new ArrayList<>(2);
        // generate initial solution x randomly


        int[] currentSolution = initialSolution.get(0);

        for(int i = 0; i < temperatureListLength; i++){
            int[] newSolution = genNewSol(currentSolution, substringSize);

            double currentFitness = evalFitness(currentSolution);
            double newFitness = evalFitness(newSolution);
            if(newFitness < currentFitness){
                currentSolution = newSolution;
                currentFitness = newFitness;
            }
            double t = (-Math.abs(newFitness - currentFitness))/Math.log(intialAcceptanceProbability);
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