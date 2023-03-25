package List_Based_Simulated_Annealing_Algorithm;

public class CoolingSchedule {
    private double initialTemperature;
    private int iterations;
    private CoolingType coolingType;

    public CoolingSchedule(double initialTemperature, int iteraitons, CoolingType coolingType){
        this.initialTemperature = initialTemperature;
        this.iterations = iterations;
        this.coolingType = coolingType;
    }

    public double getTemperature(int iteration) {
        double temperature;
        switch (coolingType) {
            case LINEAR:
                temperature = initialTemperature - (iteration * initialTemperature / iterations);
                break;
            case EXPONENTIAL:
                temperature = initialTemperature * Math.pow(0.95, iteration);
                break;
            default:
                temperature = initialTemperature * Math.exp(-iteration / (double) iterations);
                break;
        }
        return temperature;
    }

    public enum CoolingType {
        LINEAR,
        EXPONENTIAL,
        LOGARITHMIC
    }
}
