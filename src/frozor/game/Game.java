package frozor.game;

import frozor.arcade.Arcade;
import frozor.component.DatapointParser;
import frozor.component.CustomConfig;
import frozor.kits.PlayerKit;
import frozor.managers.NotificationManager;
import frozor.teams.PlayerTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Game extends JavaPlugin{
    protected Arcade arcade;
    protected String title;
    protected PlayerKit[] kits;
    protected PlayerTeam[] teams;
    protected NotificationManager notificationManager;
    protected GameSettings settings = new GameSettings();

    protected CustomConfig customConfig = new CustomConfig(this, "map.yml");
    protected FileConfiguration mapConfig = customConfig.getConfig();

    private String gameWorldName;
    protected Location spawnLocation;

    public Game(String title, PlayerKit[] kits, PlayerTeam[] teams, String gameWorldName){
        this.title = title;
        this.kits = kits;
        this.teams = teams;

        notificationManager = new NotificationManager(title);

        //int[] spawnCoords = DatapointParser.toIntArray(mapConfig.getString("spawns.Lobby"));

        //getServer().getWorlds().get(0).setSpawnLocation(spawnCoords[0], spawnCoords[1], spawnCoords[2]);

        this.gameWorldName = gameWorldName;
    }

    public void onEnable(){
        arcade = new Arcade(this, kits);

        String lobbySpawn = mapConfig.getString("spawns.Lobby");

        spawnLocation = DatapointParser.parse(lobbySpawn);
    }


    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public String getTitle(){
        return title;
    }

    public PlayerKit[] getKits() {
        return kits;
    }

    public GameSettings getSettings() {
        return settings;
    }

    public FileConfiguration getMapConfig() {
        return mapConfig;
    }
}
