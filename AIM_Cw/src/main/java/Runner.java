import Memetic_Algorithm.Meme;
import Memetic_Algorithm.crossX;
import Memetic_Algorithm.opt2;
import Utility.evals;

import java.util.List;

public class Runner {
    public static void main(String[] args){
        Meme meme = new Meme(30,4);
        Runner fun = new Runner();
        opt2 opt2 = new opt2();
        evals evals = new evals();
        crossX crossX = new crossX();
        List<int[]>pop=meme.genInitial();
        for (int i = 0; i < pop.size(); i++) {
            System.out.println(evals.evalSol(pop.get(i)));
        }
    }

    public void printArray(int[]ar){
        for (int j : ar) {
            System.out.print(j + " ");
        }
    }

}
