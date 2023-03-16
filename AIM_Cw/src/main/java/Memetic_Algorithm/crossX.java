package Memetic_Algorithm;

import Utility_funcitons.evals;

import java.util.Arrays;
import java.util.Collections;

public class crossX {
    evals evalFuncs;
    private double bestCost = Integer.MAX_VALUE;

    public crossX(){
        this.evalFuncs=new evals();
    }

    //type 1: normal, type 2: reverse
    public int[] applyCross(int[] cities, int windowSize,boolean rev){
        int[] bestConfig = cities;
        for (int i = 0; i <= 2*(cities.length-windowSize); i++) {
            for (int j = i+windowSize; j <= cities.length-windowSize; j++) {
                int[] a1 = takeSegmentOfCities(cities,windowSize,i);
                int[] a2 = takeSegmentOfCities(cities,windowSize,j);
                //reverse swap
                if(rev) {
                    revArray(a2);
                }
                swapSeg(cities, a1, a2, i, j);
                if(evalFuncs.evalSol(cities)<bestCost){
                    bestConfig= Arrays.copyOf(cities,cities.length);
                    bestCost=evalFuncs.evalSol(cities);
                }
                if(rev) {
                    revArray(a2);
                }
                swapSeg(cities, a2, a1, i, j);
            }
        }
        return bestConfig;
    }

    private void revArray(int[] array){
        for(int i = 0; i < array.length / 2; i++) {
            // Swapping the elements
            int j = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = j;
        }
    }

    private void swapSeg(int[] ori,int[] a, int[] b,int pos1,int pos2){
        for (int i = 0; i < a.length; i++) {
            ori[pos1+i]=b[i];
            ori[pos2+i]=a[i];
        }
    }

    private int[] takeSegmentOfCities(int[] cities,int size,int startIndex){
        int[] a = new int[size];
        System.arraycopy(cities, startIndex, a, 0, a.length);
        return a;
    }
}
