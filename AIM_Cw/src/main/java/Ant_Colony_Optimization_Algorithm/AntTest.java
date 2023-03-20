package Ant_Colony_Optimization_Algorithm;

import Utility_funcitons.coordinates;
import Utility_funcitons.matrix_operations;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AntTest {


    @org.junit.jupiter.api.Test
    void chooseNextCity() {
        coordinates coordinates = new coordinates();
        List<String> cities = coordinates.getCoordsList();
        matrix_operations matrixOperations = new matrix_operations();
        double[][] distanceMatrix = matrixOperations.matrixDistancesBetweenCities(cities);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(cities.size(),distanceMatrix);
        double[][] phemoroneMatrix = antColonyOptimization.getPheromoneMatrix();
        Ant ant = new Ant(0, cities.size());
        Assertions.assertNotEquals(-1,ant.chooseNextCity(distanceMatrix,phemoroneMatrix,antColonyOptimization.getAlpha(),antColonyOptimization.getBeta(),new Random()) );
  //     ant.chooseNextCity(distanceMatrix,phemoroneMatrix,antColonyOptimization.getAlpha(),antColonyOptimization.getBeta(),new Random());

    }
}