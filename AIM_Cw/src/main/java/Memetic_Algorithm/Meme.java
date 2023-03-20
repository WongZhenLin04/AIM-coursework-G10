package Memetic_Algorithm;

import Utility.evals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Meme {
    private final evals evalFunc;
    private final int populationSize;
    private final int subStringSize;

    public Meme(int populationSize,int subStringSize){
        this.evalFunc=new evals();
        this.populationSize=populationSize;
        this.subStringSize=subStringSize;
    }

    public int[] memeAlgo(){
        List<int[]> population=genInitial();
        int[]bestChild=findBestConfig(population);

        return null;
    }

    //randomly generate a population
    public List<int[]> genInitial(){
        List<int[]> res = new ArrayList<>();
        for(int i=0;i<populationSize;i++){
            //perform VND on each city
            int[] randomConfig =genRandomisedCities();
            VNDHue vndHue=new VNDHue();
            int[]bestOne= vndHue.applyVHD(randomConfig,subStringSize);
            res.add(bestOne);
        }
        return res;
    }
    
    //finds the best solution in a given population
    public int[] findBestConfig(List<int[]> population){
        int[] best=population.get(0);
        for (int i = 1; i < populationSize; i++) {
            if(evalFunc.evalSol(population.get(i))<evalFunc.evalSol(best)){
                best=population.get(i);
            }
        }
        return best;
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
