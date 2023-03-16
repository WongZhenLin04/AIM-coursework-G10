package Memetic_Algorithm;

import Utility_funcitons.evals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Meme {
    private final evals evalFunc;
    public Meme(){
        this.evalFunc=new evals();
    }

    public int[] memeAlgo(){
        return null;
    }

    //randomly generate a population
    private List<int[]> genInitial(int populationSize){
        List<int[]> res = new ArrayList<>();
        for(int i=0;i<populationSize;i++){
            res.add(genRandomisedCities());
        }
        return res;
    }

    public int[] genRandomisedCities(){
        int[] res=new int[107];
        for (int i=0;i<res.length;i++){
            res[i]=i;
        }
        Random rand = new Random();
        for (int i = 0; i < 107; i++) {
            int j = rand.nextInt(107);
            int temp = res[i];
            res[i] = res[j];
            res[j] = temp;
        }
        return res;
    }
}
