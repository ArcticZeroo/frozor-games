package frozor.game;

import frozor.arcade.Arcade;
import frozor.kits.PlayerKit;
import org.bukkit.plugin.java.JavaPlugin;

public class Game extends JavaPlugin{
    protected Arcade arcade;
    protected String title;
    protected PlayerKit[] kits;

    public Game(String title, PlayerKit[] kits){
        this.title = title;
        this.kits = kits;
    }

    public String getTitle(){
        return title;
    }

    public PlayerKit[] getKits() {
        return kits;
    }

    public void onEnable(){
        arcade = new Arcade(this, kits);
    }
}
