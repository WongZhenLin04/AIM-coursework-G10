package Ant_Colony_Optimization_Algorithm;

public class ACOTestFrameConfig {

    /* */
    private final int numOfAnts = 50;

    /* */
    private final int numIterations = 10;

    /* */
    private final double alpha = 1.0;

    /* */
    private final double beta = 1.0;

    /* evaporation rate should be < 1 */
    private final double evaporationRate = 0.5;

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