package plotting;

import Utility.coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class plotGraph extends JComponent {
    private int[] X;
    private int[] Y;

    public plotGraph(int[] Sol){
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
    }

}
