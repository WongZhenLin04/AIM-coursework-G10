package List_Based_Simulated_Annealing_Algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LBSATest {

    @Test
    void genSwapNewSol() {
        int size = 6;
        int[] cities = new int[size];
        for(int i = 0; i < size; i++){
            cities[i] = i;
        }

        LBSA lbsa = new LBSA();
        int[] newSolution = lbsa.genHybridNewSol(cities);
        for(int i = 0; i < size; i++){
            System.out.print(newSolution[i] + " ");
        }
        System.out.println();
    }

    @Test
    void genNewSol() {
        int size = 6;
        int[] cities = new int[size];
        for(int i = 0; i < size; i++){
            cities[i] = i;
        }

        LBSA lbsa = new LBSA();
        int[] newSolution = lbsa.genHybridNewSol(cities);
        for(int i = 0; i < size; i++){
            System.out.print(newSolution[i] + " ");
        }
        System.out.println();
    }

    @Test
    void calculateBadResultAcceptanceProbability() {
        LBSA lbsa = new LBSA();
        double tMax = 0.1522751169663016;
        double currentFitness = 61744.345142032966;
        double newFitness = 62512.668992625724;
        double p = lbsa.calculateBadResultAcceptanceProbability( tMax, currentFitness,newFitness);
        System.out.println(p);
    }

    @Test
    void testGenNewSol() {
        LBSA lbsa = new LBSA();
        int size = 20;
        int[] solution = new int[size];
        for(int i = 0; i < size; i++){
            solution[i] = i;
        }
        solution = lbsa.genHybridNewSol(solution);
        for(int i = 0; i < size; i++){
            System.out.print(solution[i] + " ");
        }
    }

    @Test
    void genInsertNewSol() {
        LBSA lbsa = new LBSA();
        int size = 5;
        int[] solution = new int[size];
        for(int i = 0; i < size; i++){
            solution[i] = i+1;
        }
        solution = lbsa.generateInsertNewSolution(solution, 3, 1);
        for(int i = 0; i < size; i++){
            System.out.print(solution[i] + " ");
        }
    }
}