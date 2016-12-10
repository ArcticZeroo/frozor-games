package frozor.component;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DatapointParser {

    public static Location parse(String point){
        String[] stringCoordinates = toStringArray(point);

        double[] coordinates = toDoubleArray(stringCoordinates);

        Location location = new Location(Bukkit.getWorlds().get(0), coordinates[0], coordinates[1], coordinates[2]);

        location.setPitch(getPitch(stringCoordinates));
        location.setYaw(getYaw(stringCoordinates));

        return location;
    }

    public static String[] toStringArray(String point){
        return point.split(",");
    }

    public static double[] toDoubleArray(String[] coordinates){
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);
        double z = Double.parseDouble(coordinates[2]);

        return new double[]{x, y, z};
    }

    public static float getPitch(String[] coordinates){
        if(coordinates.length < 4) return 0F;

        return Float.parseFloat(coordinates[3]);
    }

    public static float getYaw(String[] coordinates){
        if(coordinates.length < 5) return 0F;

        return Float.parseFloat(coordinates[4]);
    }

}
