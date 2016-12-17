package frozor.tasks;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.util.UtilWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitingLobbyTeleportTask extends BukkitRunnable{
    private Arcade arcade;

    public WaitingLobbyTeleportTask(Arcade arcade){
        this.arcade = arcade;
    }

    @Override
    public void run() {
        UtilWorld.unloadWorld(arcade.getGame().getGameWorld());

        for(Player player : arcade.getGame().getServer().getOnlinePlayers()){
            arcade.getKitManager().sendEquippedKitMessage(player, arcade.getKitManager().getSelectedKit(player));
        }

        arcade.setGameState(GameState.LOBBY);

        if(arcade.getWaitingLobbyManager().canStartTimer()){
            arcade.getWaitingLobbyManager().getWaitingTimer().startTimer();
        }
    }
}
