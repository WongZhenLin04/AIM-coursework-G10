package Memetic_Algorithm;

import Utility.evals;

public class VNDHue {
    private final opt2 opt2Algo;
    private final crossX crossXAlgo;
    private final evals evals;

    public VNDHue(){
        this.opt2Algo=new opt2();
        this.crossXAlgo=new crossX();
        this.evals=new evals();
    }

    //apply respective heuristic until local optima is reached
    public int[] applyVHD(int[] cities,int windowSize){
        //apply opt-2
        int[] sol1=opt2Algo.twoOpt(cities);
        while(true) {
            double evaluated=evals.evalSol(sol1);
            sol1=opt2Algo.twoOpt(sol1);
            if(evaluated== evals.evalSol(sol1)){
                break;
            }
        }
        //apply cross exchange without reversing
        int[] sol2 = crossXAlgo.applyCross(sol1,windowSize,false);
        while(true) {
            double evaluated=evals.evalSol(sol2);
            sol2=opt2Algo.twoOpt(sol2);
            if(evaluated== evals.evalSol(sol2)){
                break;
            }
        }
        //apply cross with reversing and return
        int[] sol3 = crossXAlgo.applyCross(sol2,windowSize,true);
        while(true) {
            double evaluated=evals.evalSol(sol3);
            sol3=opt2Algo.twoOpt(sol3);
            if(evaluated== evals.evalSol(sol3)){
                break;
            }
        }
        return sol3;
    }
}
