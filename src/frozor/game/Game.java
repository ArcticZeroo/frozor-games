package frozor.game;

import frozor.arcade.Arcade;
import frozor.kits.PlayerKit;
import frozor.managers.NotificationManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Game extends JavaPlugin{
    protected Arcade arcade;
    protected String title;
    protected PlayerKit[] kits;
    protected NotificationManager notificationManager;
    protected GameSettings settings = new GameSettings();

    public Game(String title, PlayerKit[] kits){
        this.title = title;
        this.kits = kits;

        notificationManager = new NotificationManager(title);
    }

    public void onEnable(){
        arcade = new Arcade(this, kits);
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
}
