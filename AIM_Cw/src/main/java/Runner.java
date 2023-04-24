import Ant_Colony_Optimization_Algorithm.ACOTestFrameConfig;
import Ant_Colony_Optimization_Algorithm.AntColonyOptimization;
import List_Based_Simulated_Annealing_Algorithm.LBSA;
import Memetic_Algorithm.Meme;
import Utility.AdTuples_memes;
import Utility.OptimizationAlgorithm;
import Utility.coordinates;
import Utility.matrix_operators;
import plotting.plotGraph;

import java.util.List;

import javax.swing.*;


public class Runner{
    public static void main(String[] args) {
        matrix_operators matrix_operators = new matrix_operators();

        /*Ant Colony Optimization*/
        ACOTestFrameConfig acoTestFrameConfig = ACOTestFrameConfig.getInstance();
        coordinates coordinates = new coordinates();
        List<String> cities = coordinates.getCoordsList();
        double[][] distanceMatrix = matrix_operators.matrixDistancesBetweenCities(cities);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(cities.size(), distanceMatrix);
        antColonyOptimization.displayBestSolution();

        /*List Based Simulated Annealing*/
        int iterations = 1000;
        int perturbationSize = 20;
        int temperatureListLength = 120;
        double initialAcceptanceProbability = 0.9;

        LBSA lbsa = new LBSA(perturbationSize, iterations, temperatureListLength, initialAcceptanceProbability);
        lbsa.displayBestSolution();

        /*Memetic algorithm*/

        Meme meme = new Meme(30, 4000,20,4);
        meme.printOpt();


        createJFrame(antColonyOptimization);
        createJFrame(lbsa);
        createJFrame(meme);
    }
    public static void createJFrame(OptimizationAlgorithm algorithm){

        // creating object of JFrame(Window popup)
        JFrame window = new JFrame();

        // setting closing operation
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setting size of the pop window
        window.setBounds(0, 0, 2000, 2000);
        // setting canvas for draw
        window.getContentPane().add(new plotGraph(algorithm.getBestSolution(), algorithm.getBestSolutionFitness(), algorithm.getClass().getSimpleName()));

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