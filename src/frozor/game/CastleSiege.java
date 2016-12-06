package frozor.game;

import frozor.kits.Archer;
import frozor.kits.Knight;
import frozor.kits.PlayerKit;
import frozor.kits.Scout;
import org.bukkit.event.Listener;

public class CastleSiege extends Game implements Listener{
    public CastleSiege(){
        super("Castle Siege", new PlayerKit[]{new Knight(), new Archer(), new Scout()});
    }

    @Override
    public void onEnable() {
        super.onEnable();
        //getServer().getPluginManager().registerEvents(this, this);
    }
}
