package Utility;

public class AdTuples_memes {
    private double distance;
    private String matrixName;

    public AdTuples_memes(double distance, String matrixName){
        this.distance=distance;
        this.matrixName=matrixName;
    }

    public double getDistance() {
        return distance;
    }

    public String getMatrixName() {
        return matrixName;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setMatrixName(String matrixName) {
        this.matrixName = matrixName;
    }
}
