package Memetic_Algorithm;

import Utility.evals;

import java.util.Arrays;

public class opt2 {
    private final Utility.evals eval;
    private double bestCost = Integer.MAX_VALUE;
    public opt2(){
        this.eval=new evals();
    }

    public int[] apply2OptAlgo(int[] cities){
        int[] sol1=twoOpt(cities);
        while(true) {
            double evaluated=eval.evalSol(sol1);
            sol1=twoOpt(sol1);
            if(evaluated== eval.evalSol(sol1)){
                break;
            }
        }
        return sol1;
    }

    //perform Opt-2 optimisation until we hit a local optima
    public int[] twoOpt(int[] cities) {
        int size = cities.length;
        int[] bestConfig = cities;
        int i, j;
        for (i = 1; i < size - 1; i++) {
            for (j = i + 1; j < size; j++) {
                if (j - i == 1) continue;
                opt2Swap(cities, i, j);
                if (eval.evalSol(cities) < bestCost) {
                    bestConfig = Arrays.copyOf(cities,cities.length);
                    this.bestCost=eval.evalSol(bestConfig);
                }
                opt2Swap(cities, i, j);
            }
        }
        return bestConfig;
    }

    //helper for opt-2
    private int[] opt2Swap(int[] cities,int i,int j){
        int temp= cities[i];
        cities[i]= cities[j];
        cities[j]=temp;
        return cities;
    }
}
