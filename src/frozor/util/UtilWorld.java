package frozor.util;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class UtilWorld {
    public static World loadWorld(String worldName){
        System.out.println(String.format("Loading world %s", worldName));
        World world = Bukkit.getServer().createWorld(new WorldCreator(worldName));
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
        ClearWorld(world);
        return world;
    }

    public static void unloadWorld(String worldName){
        unloadWorld(Bukkit.getWorld(worldName));
    }

    public static void unloadWorld(World world){
        System.out.println(String.format("Unloading world %s", world.getName()));
        ClearWorld(world);
        world.setAutoSave(false);
        unloadChunks(world);
        Bukkit.getServer().unloadWorld(world.getName(), false);
    }

    public static World reloadWorld(String worldName){
        System.out.println(String.format("Reloading world %s", worldName));
        if(Bukkit.getServer().getWorld(worldName) == null){
            System.out.println("Bukkit doesn't seem to be able to find the server...");
            return null;
        }
        if(Bukkit.getServer().getWorld(worldName).getPlayers().size() > 0){
            System.out.println("There are still people on the world!");
            return null;
        }

        unloadWorld(worldName);
        return loadWorld(worldName);
    }

    public static void unloadChunks(World world){
        System.out.println(String.format("Unloading chunks for world %s", world.getName()));
        Chunk[] loadedChunks = world.getLoadedChunks();
        for(Chunk chunk : loadedChunks){
            chunk.unload(false, false);
        }
    }

    public static void ClearWorld(World world){
        List<Entity> entities = world.getEntities();
        for(Entity entity : entities){
            if(!(entity instanceof Player)){
                entity.remove();
            }
        }
    }
}
