import Ant_Colony_Optimization_Algorithm.ACOTestFrameConfig;
import Ant_Colony_Optimization_Algorithm.AntColonyOptimization;
import Memetic_Algorithm.Meme;
import Utility_funcitons.AdTuples;
import Utility_funcitons.coordinates;
import Utility_funcitons.matrix_operations;

import java.util.List;

public class Runner {
    public static void main(String[] args){
        Meme meme = new Meme(30,4);
        Runner fun = new Runner();
        List<int[]> pop=meme.genInitial();
        int[] Sol=meme.findBestConfig(pop);
        int[] Sol1=pop.get(0);
        matrix_operations Matrix_operations = new matrix_operations();
        AdTuples[][] SolAd1 = Matrix_operations.makeAdMatrix(Sol,"A");
        AdTuples[][] SolAd2 = Matrix_operations.makeAdMatrix(Sol1,"B");
        AdTuples[][] SolAd =Matrix_operations.combineAd(SolAd1,SolAd2);
        List<int[]> cycles=Matrix_operations.findABCycles(SolAd);
        for (int i = 0; i < cycles.size(); i++) {
            fun.printArray(cycles.get(i));
            System.out.println();
        }

        /*Ant Colony Optimization*/
        ACOTestFrameConfig acoTestFrameConfig = ACOTestFrameConfig.getInstance();
        coordinates coordinates = new coordinates();
        List<String> cities = coordinates.getCoordsList();
        double[][] distanceMatrix = Matrix_operations.matrixDistancesBetweenCities(cities);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(cities.size(), distanceMatrix);
        antColonyOptimization.displayBestSolution();
    }

    public void printArray(int[]ar){
        for (int j : ar) {
            System.out.print(j + " ");
        }
    }

}
