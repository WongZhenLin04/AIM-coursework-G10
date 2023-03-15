package Utility_funcitons;

import java.util.List;

public class evals {
    private final coordinates coorFuncs;

    public evals(){
        this.coorFuncs=new coordinates();
    }

    //function for calculating euclidean distance
    private double calcEuDist(String coords1,String coords2){
        double[] pos1 = coorFuncs.turnCoordsToDob(coords1);
        double[] pos2 = coorFuncs.turnCoordsToDob(coords2);
        double res=Math.sqrt(Math.pow((pos2[0]-pos1[0]),2)+Math.pow((pos2[1]-pos1[1]),2));
        return res;
    }

    //function takes in a list of cites in the form of an int array and calculates the total Euclidean distance
    public double evalSol(int[] Sol){
        double sum=0;
        List<String> coorList=coorFuncs.getCoordsList();
        for(int i=0;i<Sol.length-1;i++){
            sum+=calcEuDist(coorList.get(i),coorList.get(i+1));
        }
        return sum;
    }

}
