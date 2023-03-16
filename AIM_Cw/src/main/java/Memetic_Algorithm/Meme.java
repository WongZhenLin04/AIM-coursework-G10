package Memetic_Algorithm;

import Utility_funcitons.evals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Meme {
    private final evals evalFunc;
    private final VNDHue vndHue;

    public Meme(){
        this.evalFunc=new evals();
        this.vndHue=new VNDHue();
    }

    public int[] memeAlgo(){
        return null;
    }

    //randomly generate a population
    public List<int[]> genInitial(int populationSize,int subStringSize){
        List<int[]> res = new ArrayList<>();
        for(int i=0;i<populationSize;i++){
            //perform VND on each city
            int[]bestOne= vndHue.applyVHD(genRandomisedCities(),subStringSize);
            res.add(bestOne);
            System.out.println(evalFunc.evalSol(bestOne));
        }
        return res;
    }

    private int[] genRandomisedCities(){
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
