import Ant_Colony_Optimization_Algorithm.ACOTestFrameConfig;
import Ant_Colony_Optimization_Algorithm.AntColonyOptimization;
import Memetic_Algorithm.Meme;
import Memetic_Algorithm.crossX;
import Memetic_Algorithm.opt2;
import Utility.coordinates;
import Utility.evals;
import Utility.AdTuples_memes;
import Utility.coordinates;
import Utility.matrix_operators;

import java.util.List;

public class Runner {
    public static void main(String[] args){
        /*Memetic algorithm*/
        Meme meme = new Meme(30,4);
        Runner fun = new Runner();
        opt2 opt2 = new opt2();
        evals evals = new evals();
        crossX crossX = new crossX();
        List<int[]>pop=meme.genInitial();
        for (int i = 0; i < pop.size(); i++) {
            System.out.println(evals.evalSol(pop.get(i)));
        }

        /*Ant Colony Optimization*/
        matrix_operators Matrix_operators=new matrix_operators();
        ACOTestFrameConfig acoTestFrameConfig = ACOTestFrameConfig.getInstance();
        coordinates coordinates = new coordinates();
        List<String> cities = coordinates.getCoordsList();
        double[][] distanceMatrix = Matrix_operators.matrixDistancesBetweenCities(cities);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(cities.size(), distanceMatrix);
        antColonyOptimization.displayBestSolution();
    }

    public void printArray(int[]ar){
        for (int j : ar) {
            System.out.print(j + " ");
        }
    }

}
