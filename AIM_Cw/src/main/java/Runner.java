import Ant_Colony_Optimization_Algorithm.ACOTestFrameConfig;
import Ant_Colony_Optimization_Algorithm.AntColonyOptimization;
import List_Based_Simulated_Annealing_Algorithm.LBSA;
import Memetic_Algorithm.EAX;
import Memetic_Algorithm.Meme;
import Utility.AdTuples_memes;
import Utility.coordinates;
import Utility.evals;
import Utility.matrix_operators;

import java.util.Arrays;
import java.util.List;

import javax.swing.*;


public class Runner{
    public static void main(String[] args) {
        evals evals = new evals();
        EAX eax = new EAX();
        matrix_operators matrix_operators = new matrix_operators();
        /*Memetic algorithm*/

        Meme meme = new Meme(30, 4000,20,4);
        int [] bestSol = meme.applyMemes();
        System.out.println(Arrays.toString(bestSol));
        System.out.println(evals.evalSol(bestSol));
        System.out.println();


        /*Ant Colony Optimization*/
        ACOTestFrameConfig acoTestFrameConfig = ACOTestFrameConfig.getInstance();
        coordinates coordinates = new coordinates();
        List<String> cities = coordinates.getCoordsList();
        double[][] distanceMatrix = matrix_operators.matrixDistancesBetweenCities(cities);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(cities.size(), distanceMatrix);
        //antColonyOptimization.displayBestSolution();
        //System.out.println(evals.evalSol(antColonyOptimization.findBestSolution()));

        /*List Based Simulated Annealing*/
        int iterations = 1000;
        int perturbationSize = 30;
        int substringSize = 2;
        int temperatureListLength = 2;
        double initialAcceptanceProbability = 0.9;
        /*
        CoolingSchedule coolingSchedule = new CoolingSchedule(0.99, iterations, CoolingSchedule.CoolingType.LINEAR);
        LBSA lbsa = new LBSA(30,5,iterations,100, coolingSchedule);
        lbsa.genInitial();
        */
        LBSA lbsa = new LBSA(perturbationSize, substringSize, iterations, temperatureListLength, initialAcceptanceProbability);
        lbsa.displayBestSolution();
        /*
        System.out.println("Best Solution = "+ Arrays.toString(lbsa.getBestSolution()));
        System.out.println("Best Fitness = " + lbsa.getBestFitness());
        System.out.println(evals.evalSol(antColonyOptimization.findBestSolution()));
        */
        // creating object of JFrame(Window popup)
        JFrame window = new JFrame();

        // setting closing operation
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setting size of the pop window
        window.setBounds(30, 30, 2000, 2000);
        // setting canvas for draw
        //window.getContentPane().add(new plotGraph(bestSol));

        // set visibility
        window.setVisible(true);
    }

    public void printMatrix(AdTuples_memes[][] ar){
        for (int i=0;i<ar.length;i++) {
            for (int j = 0; j < ar.length; j++) {
                if(ar[i][j].getDistance()==Double.MAX_VALUE){
                    System.out.print(0);
                }
                else{
                    System.out.print(ar[i][j].isVisited());
                }
            }
            System.out.println();
        }
    }


}
