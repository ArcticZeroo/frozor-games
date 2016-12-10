package frozor.component;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DatapointParser {

    public static Location parse(String point){
        double[] coordinates = toDoubleArray(point);
        return new Location(Bukkit.getWorlds().get(0), coordinates[0], coordinates[1], coordinates[2]);
    }

    public static String[] toStringArray(String point){
        return point.split(",");
    }

    public static int[] toIntArray(String point){
        String[] coordinates = toStringArray(point);
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int z = Integer.parseInt(coordinates[2]);

        return new int[]{x, y, z};
    }

    public static double[] toDoubleArray(String point){
        String[] coordinates = toStringArray(point);
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);
        double z = Double.parseDouble(coordinates[2]);

        return new double[]{x, y, z};
    }

}
