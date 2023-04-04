import Ant_Colony_Optimization_Algorithm.ACOTestFrameConfig;
import Ant_Colony_Optimization_Algorithm.AntColonyOptimization;
import List_Based_Simulated_Annealing_Algorithm.LBSA;
import Memetic_Algorithm.EAX;
import Memetic_Algorithm.Meme;
import Utility.AdTuples_memes;
import Utility.coordinates;
import Utility.evals;
import Utility.matrix_operators;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Runner extends Application{
    static int[] solutionToDisplay;
    coordinates coords = new coordinates();

    @Override
    public void start(Stage stage) throws Exception {
        List<Double> coordsX=new ArrayList<>();
        List<Double> coordsY=new ArrayList<>();
        List<String> stringCoords = coords.getCoordsList();
        for (String coord: stringCoords) {
            coordsX.add(coords.turnCoordsToDob(coord)[0]);
            coordsY.add(coords.turnCoordsToDob(coord)[1]);
        }
        NumberAxis x = new NumberAxis(Collections.min(coordsX,null)-1000, Collections.max(coordsX,null)+1000,10);
        NumberAxis y = new NumberAxis(Collections.min(coordsY,null)-1000, Collections.max(coordsY,null)+1000, 10);
        LineChart linechart = new LineChart(x, y);
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < solutionToDisplay.length; i++) {
            series.getData().add(new XYChart.Data(coordsX.get(solutionToDisplay[i]),coordsY.get(solutionToDisplay[i])));
        }
        linechart.getData().add(series);
        Group root = new Group(linechart);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        evals evals = new evals();
        EAX eax = new EAX();
        matrix_operators matrix_operators = new matrix_operators();
        /*Memetic algorithm*/
<<<<<<< HEAD
//        Meme meme = new Meme(30,4,40,20);
//        int [] bestSol = meme.applyMemes();
//        System.out.println(Arrays.toString(bestSol));
//        System.out.println(evals.evalSol(bestSol));

=======
        Meme meme = new Meme(30,4,40,20);
        int [] bestSol = meme.applyMemes();
        System.out.println(Arrays.toString(bestSol));
        System.out.println(evals.evalSol(bestSol));
>>>>>>> 27332c5ea6df0073c859618a64d2a840d057f7b3
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
        int iterations = 10;
        int perturbationSize = 30;
        int substringSize = 5;
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
        solutionToDisplay = bestSol;
        launch(args);
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
