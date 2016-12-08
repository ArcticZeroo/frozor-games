package frozor.game;

import frozor.kits.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

public class CastleSiege extends Game implements Listener{
    public CastleSiege(){
        super("Castle Siege", new PlayerKit[]{new Knight(), new Archer(), new Scout(), new FireMage()}, "rivendale");

        getSettings().setPlayerMin(2);
        getSettings().setPlayerMax(3);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        //getServer().getPluginManager().registerEvents(this, this);
    }
}
