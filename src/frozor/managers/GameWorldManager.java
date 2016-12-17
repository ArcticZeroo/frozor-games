package frozor.managers;

import frozor.arcade.Arcade;
import frozor.util.UtilFile;
import frozor.util.UtilWorld;
import net.minecraft.server.v1_8_R3.RegionFileCache;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class GameWorldManager {
    private Arcade arcade;

    public GameWorldManager(Arcade arcade){
        this.arcade = arcade;
    }

    public World loadMap(String mapName){
        String path = Bukkit.getWorldContainer().getAbsolutePath() + File.separator + "map" + File.separator + mapName;
        arcade.getDebugManager().print("Loading a world at " + path);
        arcade.getDebugManager().print(Bukkit.getWorldContainer().getPath());
        File loadFile = new File(path);
        if(!loadFile.exists()) {
            arcade.getDebugManager().print("No existo: " + path);
            return null;
        }
        if(!loadFile.isDirectory()){
            arcade.getDebugManager().print("No directorio: " + path);
            return null;
        }

        File mapFile = new File(Bukkit.getWorldContainer() + File.separator + mapName);
        if(mapFile.exists()){
            mapFile.delete();
        }

        try {
            arcade.getDebugManager().print("Copying directory...");
            FileUtils.copyDirectoryToDirectory(loadFile, Bukkit.getWorldContainer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        arcade.getDebugManager().print("Copied!");

        World world = UtilWorld.loadWorld(mapName);

        return world;
    }

    public void unloadMap(final World world){
        final File mapFolder = world.getWorldFolder();
        if(!mapFolder.exists()){
            arcade.getDebugManager().print("no existo");
            return;
        }

        UtilWorld.ClearWorld(world);
        UtilWorld.unloadWorld(world);

        Bukkit.getScheduler().runTaskLater(arcade.getGame(), new Runnable() {
            @Override
            public void run() {
                RegionFileCache.a();
                Bukkit.getScheduler().runTaskLater(arcade.getGame(), new Runnable() {
                    @Override
                    public void run() {
                        deleteWorldFolder(mapFolder);
                /*for(World world : Bukkit.getWorlds()){
                    if(world.getName().equals("lobby")) continue;
                    if(world.getLoadedChunks().length > 0){
                        for(Chunk chunk : world.getLoadedChunks()){
                            chunk.unload(false, false);
                        }
                    }
                }*/
                    }
                }, 40L);
            }
        }, 40L);
    }

    public void deleteWorldFolder(World world){
        deleteWorldFolder(world.getWorldFolder());
    }

    public void deleteWorldFolder(File file){
        UtilFile.deleteDirectory(file);
    }
}
