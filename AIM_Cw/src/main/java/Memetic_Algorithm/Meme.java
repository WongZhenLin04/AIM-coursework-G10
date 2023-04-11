package Memetic_Algorithm;

import Utility.evals;
import Utility.matrix_operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Meme {
    private final int populationSize;
    private final EAX eax;
    private final int tolerance;
    private final evals evals;
    private final matrix_operators matrixOperators;
    private final int generationsSize;
    private final int substringSize;

    public Meme(int populationSize, int tolerance, int generationsSize, int substringSize){
        this.populationSize=populationSize;
        this.eax=new EAX();
        this.tolerance = tolerance;
        this.evals =new evals();
        this.matrixOperators=new matrix_operators();
        this.generationsSize=generationsSize;
        this.substringSize=substringSize;
    }

    public int[] applyMemes(){
        int x=0;
        List<Integer> X=new ArrayList<>();
        List<Double> Y=new ArrayList<>();
        System.out.println("Initialising...");
        List<int[]> population=new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(genRandomisedCities());
        }
        System.out.println("Initialised!");
        int[] bestSol = eax.pickBest(population);
        int notImproved =0;
        List<int[]> generation =new ArrayList<>();
        System.out.println("beginning training");
        int replace=1;
        while (notImproved<=tolerance){
            List<int[]> parents = selectRandom(population);
            if(matrixOperators.countUnique(parents.get(0))!=107){
                parents.set(0,genRandomisedCities());
            }
            if(matrixOperators.countUnique(parents.get(1))!=107){
                parents.set(1,genRandomisedCities());
            }
            System.out.println(Arrays.toString(parents.get(0)));
            System.out.println(Arrays.toString(parents.get(1)));
            System.out.println(evals.evalSol(bestSol));
            List<int[]> offSprings;

            try{
                offSprings = eax.applyEax(parents.get(0),parents.get(1));
            }catch (IndexOutOfBoundsException e){
                System.out.println("Haha");
                resetPop(population);
                continue;
            }

            if(offSprings.isEmpty()){
                System.out.println("Hello");
                resetPop(population);
                continue;
            }

            //parameter of 1 good offspring so just choose the best one
            List<int[]> appliedCross = new ArrayList<>();
            for (int i = 0; i < offSprings.size(); i++) {
                System.out.print(i);
                crossX crossX = new crossX();
                appliedCross.add(crossX.findBestSol(offSprings.get(i),substringSize,true));
            }
            System.out.println();
            int[] bestOffspring = eax.pickBest(appliedCross);

            if(replace==generationsSize){
                //select worst 20 and replace
                replaceWorst(population,generation);
                generation.clear();
                replace=0;
            }
            else{
                generation.add(bestOffspring);
                replace++;
            }

            if(evals.evalSol(bestOffspring)<evals.evalSol(bestSol)){
                bestSol = bestOffspring;
            }
            else{
                notImproved++;
            }
            System.out.println(notImproved);
            Y.add(evals.evalSol(bestSol));
            X.add(x);
            x++;
        }
        System.out.println();
        System.out.println("Training done!");
        System.out.println(Arrays.toString(X.toArray()));
        System.out.println(Arrays.toString(Y.toArray()));
        return bestSol;
    }

    public void resetPop(List<int[]> Pop){
        for (int i = 0; i < Pop.size(); i++) {
            Pop.set(i,genRandomisedCities());
        }
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
