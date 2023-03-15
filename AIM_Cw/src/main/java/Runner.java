import Utility_funcitons.evals;

public class Runner {
    public static void main(String[] args){
        int[] Sol= new int[107];
        evals evalFunc = new evals();
        for (int i=0;i<Sol.length;i++){
            Sol[i]=i;
        }
        System.out.println(evalFunc.evalSol(Sol));
    }
}
