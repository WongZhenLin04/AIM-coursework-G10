package Utility_funcitons;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class coordinates {
    private List<String> coords;
    InputStream inputStream = this.getClass().getResourceAsStream("/TSP_107.txt");

    public coordinates(){
        this.coords=getEntries();
    }

    //code for getting cities from the resource file into a list
    private List<String> getEntries(){
        ArrayList<String> cities = new ArrayList<>();
        Scanner reader = new Scanner(inputStream);
        while (reader.hasNextLine()){
            cities.add(splitONFirstBlank(reader.nextLine()));
        }
        reader.close();
        return cities.subList(6,cities.size()-1);
    }

    private String splitONFirstBlank(String word){
        return word.substring(word.indexOf(" ")+1);
    }

    //takes in a set of coordinates and turning it into a double array
    public double[] turnCoordsToDob(String coords){
        String[] pos=coords.split(" ");
        double[] res=new double[2];
        double x=Double.parseDouble(pos[0]);
        double y=Double.parseDouble(pos[1]);
        res[0]=x;
        res[1]=y;
        return res;
    }

    public List<String> getCoordsList() {
        return coords;
    }
}
