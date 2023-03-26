package List_Based_Simulated_Annealing_Algorithm;

public class CoolingSchedule {
    private double temperature;
    private int iterations;
    private CoolingType coolingType;

    public CoolingSchedule(double initialTemperature, int iterations, CoolingType coolingType){
        this.temperature = initialTemperature;
        this.iterations = iterations;
        this.coolingType = coolingType;
    }

    public double getTemperature(int iteration) {
        switch (coolingType) {
            case LINEAR:
                temperature -= (iteration * temperature / iterations);
                break;
            case EXPONENTIAL:
                temperature *= Math.pow(0.95, iteration);
                break;
            default:
                temperature *= Math.exp(-iteration / (double) iterations);
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
