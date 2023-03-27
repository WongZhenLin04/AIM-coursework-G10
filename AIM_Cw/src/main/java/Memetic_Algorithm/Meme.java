package Memetic_Algorithm;

import Utility.evals;
import Utility.matrix_operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Meme {
    private final int populationSize;
    private final int subStringSize;
    private final EAX eax;
    private int tolerance;
    private int generationSize;
    private VNDHue vndHue;
    private final evals evals;
    private final matrix_operators matrixOperators;

    public Meme(int populationSize,int subStringSize, int tolerance,int generationsSize){
        this.populationSize=populationSize;
        this.subStringSize=subStringSize;
        this.eax=new EAX();
        this.tolerance = tolerance;
        this.vndHue=new VNDHue();
        this.evals =new evals();
        this.generationSize=generationsSize;
        this.matrixOperators=new matrix_operators();
    }

    public int[] applyMemes(){
        System.out.println("Initialising...");
        List<int[]> population=genInitial();
        System.out.println("Initialised!");
        int[] bestSol = eax.pickBest(population);
        int notImproved =0;
        List<int[]> generation =new ArrayList<>();
        System.out.println("beginning training");
        int replace=1;
        while (notImproved<=tolerance){
            List<int[]> parents = selectRandom(population);
            List<int[]> offSprings = eax.applyEax(parents.get(0),parents.get(1));
            //parameter of 1 good offspring so just choose the best one
            int[] bestOffspring = eax.pickBest(offSprings);
            VNDHue vndHue=new VNDHue();
            int[] improvedOffspring = vndHue.applyVHD(bestOffspring,subStringSize);

            if(replace==20){
                //select worst 20 and replace
                replaceWorst(population,generation);
                generation.clear();
                replace=0;
            }
            else{
                generation.add(bestOffspring);
                replace++;
            }

            if(evals.evalSol(improvedOffspring)<evals.evalSol(bestSol)){
                bestSol = improvedOffspring;
            }
            else{
                notImproved++;
            }
            System.out.print("*");
        }
        System.out.println("Training done!");
        return bestSol;
    }

    //function that selects the worst solutions for a population specified by the generation size  and replaces them
    public void replaceWorst(List<int[]> population, List<int[]> generation){
        for (int i = 0; i < generation.size(); i++) {
            if(!checkPresentInPop(population,generation.get(i))){
                int inWorst = eax.pickWorst(population);
                population.remove(inWorst);
                population.add(generation.get(i));
            }
        }
    }

    public boolean checkPresentInPop(List<int[]> population,int[] solution){
        for (int i = 0; i < population.size(); i++) {
            if(Arrays.equals(population.get(i),solution)){
                return true;
            }
        }
        return false;
    }

    //perform random parent selection
    public List<int[]> selectRandom(List<int[]> population){
        Random random = new Random();
        int parent1=random.nextInt(population.size());
        int parent2=random.nextInt(population.size());
        while(parent1==parent2){
            parent2=random.nextInt(population.size());
        }
        List<int[]> parents = new ArrayList<>();
        parents.add(population.get(parent1));
        parents.add(population.get(parent2));
        return parents;
    }

    //randomly generate a population
    public List<int[]> genInitial(){
        List<int[]> res = new ArrayList<>();
        for(int i=0;i<populationSize;i++){
            //perform VND on each city
            int[] randomConfig =genRandomisedCities();
            VNDHue vndHue=new VNDHue();
            int[]bestOne= vndHue.applyVHD(randomConfig,subStringSize);
            if(!checkPresentInPop(res,bestOne)){
                res.add(bestOne);
            }
            else{
                i--;
            }
            if((i+1) % 3 == 0) {
                System.out.print("*");
            }
        }
        System.out.println();
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
