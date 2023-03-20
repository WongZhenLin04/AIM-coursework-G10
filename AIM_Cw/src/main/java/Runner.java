import Ant_Colony_Optimization_Algorithm.ACOTestFrameConfig;
import Ant_Colony_Optimization_Algorithm.AntColonyOptimization;
import Memetic_Algorithm.Meme;
import Memetic_Algorithm.crossX;
import Memetic_Algorithm.opt2;
import Utility.*;

import java.util.List;

public class Runner {
    public static void main(String[] args){
        evals evals = new evals();
        matrix_operators Matrix_operators=new matrix_operators();
        /*Memetic algorithm*/
        Meme meme = new Meme(30,4);
        Runner fun = new Runner();
        opt2 opt2 = new opt2();
        crossX crossX = new crossX();
        int[] Sol=meme.genRandomisedCities();
        int[] Sol1= meme.genRandomisedCities();
        AdTuples_memes[][] SolAd1 = Matrix_operators.makeAdMatrix(Sol,"A");
        AdTuples_memes[][] SolAd2 = Matrix_operators.makeAdMatrix(Sol1,"B");
        AdTuples_memes[][] SolAd =Matrix_operators.combineAd(SolAd1,SolAd2);
        List<int[]> cycles=Matrix_operators.findABCycles(SolAd);
        for (int i = 0; i < cycles.size(); i++) {
            fun.printArray(cycles.get(i));
            System.out.println();
        }

        /*Ant Colony Optimization*//*

        ACOTestFrameConfig acoTestFrameConfig = ACOTestFrameConfig.getInstance();
        coordinates coordinates = new coordinates();
        List<String> cities = coordinates.getCoordsList();
        double[][] distanceMatrix = Matrix_operators.matrixDistancesBetweenCities(cities);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(cities.size(), distanceMatrix);
        antColonyOptimization.displayBestSolution();
        System.out.println(evals.evalSol(antColonyOptimization.findBestSolution()));
        */
    }

    public void printArray(int[]ar){
        for (int j : ar) {
            System.out.print(j + " ");
        }
    }

}
