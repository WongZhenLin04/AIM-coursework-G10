package Memetic_Algorithm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class VND {
    Random random;

    public VND(){
        this.random=new Random();
    }

    public void opt2Swap(int[] cities){
        int i = random.nextInt(cities.length);
        int j = random.nextInt(cities.length);
        if (i== cities.length-1){
            i--;
        }
        if (j== cities.length-1){
            j--;
        }
        int temp=cities[i+1];
        cities[i+1]=cities[j+1];
        cities[j+1]=temp;
    }

    public void crossX(int[] cities,int subSize){
        //choose random cities
        int windowSize = subSize-1;
        int i= random.nextInt(cities.length-windowSize);
        int j= random.nextInt(cities.length-windowSize);
        int[] a1 = takeSegmentOfCities(cities,subSize,i);
        int[] a2 = takeSegmentOfCities(cities,subSize,j);
        while (hasSame(a1,a2)){
            j= random.nextInt(cities.length-windowSize);
            a2 = takeSegmentOfCities(cities,subSize,j);
        }
        for (int k = 0; k < subSize; k++) {
            cities[i+k]=a2[k];
            cities[j+k]=a1[k];
        }
    }

    private int[] takeSegmentOfCities(int[] cities,int size,int startIndex){
        int[] a = new int[size];
        System.arraycopy(cities, startIndex, a, 0, a.length);
        return a;
    }

    //function to check if the arrays contain a sharing element
    public boolean hasSame(int[]a1,int[]a2){
        // create hashsets
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        // Adding elements from array1
        for (int i : a1) {
            set1.add(i);
        }
        // Adding elements from array2
        for (int i : a2) {
            set2.add(i);
        }
        set1.retainAll(set2);
        return !set1.isEmpty();
    }
}
