package Algorithms;

import Utility_funcitons.evals;

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

    private List<int[]> genInitial(int populationSize){

        return null;
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
