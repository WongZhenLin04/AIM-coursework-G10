package Ant_Colony_Optimization_Algorithm;

public class ACOTestFrameConfig {

    /* */
    private final int numOfAnts = 107;

    /* */
    private final int numIterations = 1000;

    /* */
    private final double alpha = 1;

    /* */
    private final double beta = 3;

    /* evaporation rate should be < 1 */
    private final double evaporationRate = 0.3;

    private static ACOTestFrameConfig oThis;

    public static ACOTestFrameConfig getInstance(){
        if(oThis == null){
            oThis = new ACOTestFrameConfig();
        }
        return oThis;
    }

    public int getNumOfAnts() {
        return numOfAnts;
    }

    public int getNumIterations() {
        return numIterations;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getEvaporationRate() {
        return evaporationRate;
    }


}
