package frozor.tasks;

import frozor.arcade.Arcade;
import frozor.events.CustomPlayerSpawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawnTask extends BukkitRunnable{
    private Arcade arcade;
    private Player player;

    public PlayerRespawnTask(Arcade arcade, Player player){
        this.arcade = arcade;
        this.player = player;
    }

    @Override
    public void run() {
        arcade.getDebugManager().print("Respawning player " + player.getName());
        player.teleport(arcade.getTeamManager().getTeamSpawn(player));
        arcade.getKitManager().giveKit(player);
        new CustomPlayerSpawnEvent(player).callEvent();
    }
}
