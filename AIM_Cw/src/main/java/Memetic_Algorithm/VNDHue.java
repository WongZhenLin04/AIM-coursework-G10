package Memetic_Algorithm;

public class VNDHue {
    private final opt2 opt2Algo;
    private final crossX crossXAlgo;

    public VNDHue(){
        this.opt2Algo=new opt2();
        this.crossXAlgo=new crossX();
    }

    public int[] applyVHD(int[] cities,int windowSize){
        //apply opt-2
        int[] sol1 = opt2Algo.twoOpt(cities);
        //apply cross exchange without reversing
        int[] sol2 = crossXAlgo.applyCross(sol1,windowSize,false);
        //apply cross with reversing and return
        return crossXAlgo.applyCross(sol2,windowSize,true);
    }
}
