import Memetic_Algorithm.Meme;
import Utility_funcitons.evals;

public class Runner {
    public static void main(String[] args){
        Meme meme = new Meme();
        int[] Sol= meme.genRandomisedCities();
        evals evalFunc=new evals();
        System.out.println(evalFunc.evalSol(Sol));
    }
}
