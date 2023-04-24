package plotting;

import Utility.coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class plotGraph extends JComponent {
    private int[] X;
    private int[] Y;
    private double bestSolutionFitness;
    private String algorithmName;
    private int[] bestSolution;

    public plotGraph(int[] Sol, double bestSolFitness, String algorithmName){
        this.bestSolution = Sol;
        this.bestSolutionFitness = bestSolFitness;
        this.algorithmName = algorithmName;
        coordinates coords = new coordinates();
        List<String> stringCoords = coords.getCoordsList();
        List<Double> coordsX = new ArrayList<>();
        List<Double> coordsY = new ArrayList<>();
        for (String coord: stringCoords) {
            coordsX.add(coords.turnCoordsToDob(coord)[0]);
            coordsY.add(coords.turnCoordsToDob(coord)[1]);
        }
        X=new int[Sol.length];
        Y=new int[Sol.length];


        for (int i = 0; i < Sol.length; i++) {
            X[i]=coordsX.get(Sol[i]).intValue()/15;
            Y[i]=coordsY.get(Sol[i]).intValue()/15;

        }

    }

    public void paint(Graphics g) {
        int z = X.length;
        g.drawPolygon(X,Y,z);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        String optimalSolutionFitnessString = algorithmName + " : "  + bestSolutionFitness;
        g.drawString(optimalSolutionFitnessString, 450,200);
        for(int i =0; i < X.length; i++){
            g.drawOval(X[i]-2,Y[i]-2,5,5);
            g.setColor(Color.RED);
            g.fillOval(X[i]-2,Y[i]-2,5,5);
        }


    }


}
