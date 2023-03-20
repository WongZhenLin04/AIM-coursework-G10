package Utility;

public class AdTuples_memes {
    private double distance;
    private String matrixName;
    private boolean visited;

    public AdTuples_memes(double distance, String matrixName,boolean visited){
        this.distance=distance;
        this.matrixName=matrixName;
        this.visited=visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
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

