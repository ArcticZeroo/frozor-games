package frozor.managers;

import frozor.arcade.Arcade;
import frozor.util.UtilWorld;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class GameWorldManager {
    private Arcade arcade;

    public GameWorldManager(Arcade arcade){
        this.arcade = arcade;
    }

    public World loadMap(String mapName){
        overwriteMap(mapName);
        return UtilWorld.loadWorld(mapName);
    }

    public void overwriteMap(String mapName){
        String path = Bukkit.getWorldContainer().getAbsolutePath() + File.separator + "map" + File.separator + mapName;
        arcade.getDebugManager().print("Loading a world at " + path);
        arcade.getDebugManager().print(Bukkit.getWorldContainer().getPath());
        File loadFile = new File(path);
        if(!loadFile.exists()) {
            arcade.getDebugManager().print("No existo: " + path);
            return;
        }
        if(!loadFile.isDirectory()){
            arcade.getDebugManager().print("No directorio: " + path);
            return;
        }


        try {
            arcade.getDebugManager().print("Copying directory...");
            FileUtils.copyDirectoryToDirectory(loadFile, Bukkit.getWorldContainer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        arcade.getDebugManager().print("Copied!");
    }
}
