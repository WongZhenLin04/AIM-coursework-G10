import Memetic_Algorithm.EAX;
import Memetic_Algorithm.Meme;
import Memetic_Algorithm.crossX;
import Memetic_Algorithm.opt2;
import Utility.*;

import java.util.List;

public class Runner {
    public static void main(String[] args){
        evals evals = new evals();
        matrix_operators Matrix_operators=new matrix_operators();
        EAX eax = new EAX();
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
        List<int[]> cycles= eax.findABCycles(SolAd);
        List<AdTuples_memes[][]> ESet= eax.makeESet(cycles);



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

    public void printMatrix(AdTuples_memes[][] ar){
        for (int i=0;i<ar.length;i++) {
            for (int j = 0; j < ar.length; j++) {
                if(ar[i][j].getDistance()==Double.MAX_VALUE){
                    System.out.print(0);
                }
                else{
                    System.out.print(ar[i][j].getDistance());
                }
            }
            System.out.println();
        }
    }

}
