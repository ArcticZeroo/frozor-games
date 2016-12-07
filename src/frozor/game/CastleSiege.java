package frozor.game;

import frozor.kits.*;
import org.bukkit.event.Listener;

public class CastleSiege extends Game implements Listener{
    public CastleSiege(){
        super("Castle Siege", new PlayerKit[]{new Knight(), new Archer(), new Scout(), new FireMage()});
    }

    @Override
    public void onEnable() {
        super.onEnable();
        //getServer().getPluginManager().registerEvents(this, this);
    }
}
