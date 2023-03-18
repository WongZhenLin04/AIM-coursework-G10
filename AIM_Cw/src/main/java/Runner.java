import Memetic_Algorithm.Meme;
import Utility_funcitons.matrix_operations;

public class Runner {
    public static void main(String[] args){
        Meme meme = new Meme(30,4);
        int[] Sol=meme.findBestConfig(meme.genInitial());
        matrix_operations Matrix_operations = new matrix_operations();
        double[][] SolAd = Matrix_operations.makeAdMatrix(Sol);
        for (int i = 0; i < Sol.length; i++) {
            for (int j = 0; j < Sol.length; j++) {
                if(SolAd[i][j]==0.00){
                    System.out.print(0);
                }
                else {
                    System.out.print(SolAd[i][j]);
                }
            }
            System.out.println();
        }
    }

}
