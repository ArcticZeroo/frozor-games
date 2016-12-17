package frozor.tasks;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.util.UtilPlayer;
import frozor.util.UtilWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEndTask extends BukkitRunnable{
    private Arcade arcade;

    public GameEndTask(Arcade arcade){
        this.arcade = arcade;
    }

    @Override
    public void run() {
        arcade.setGameState(GameState.PREPARE);
        arcade.getTeamManager().clearTeamEntries();

        arcade.getWaitingLobbyManager().makeWaitingScoreboard();

        for(Player player : arcade.getPlugin().getServer().getOnlinePlayers()){
            UtilPlayer.cleanPlayer(player);
            player.teleport(arcade.getGame().getSpawnLocation());
        }

        UtilWorld.unloadWorld(arcade.getGame().getGameWorld());
        new WaitingLobbyTeleportTask(arcade).runTaskLater(arcade.getPlugin(), 20L);
    }
}
