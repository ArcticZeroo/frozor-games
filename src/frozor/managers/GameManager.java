package frozor.managers;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.CustomPlayerSpawnEvent;
import frozor.events.GameEndEvent;
import frozor.events.GameStateChangeEvent;
import frozor.listener.GameStartPlayerMoveListener;
import frozor.tasks.GameEndTask;
import frozor.tasks.GameStartTimer;
import frozor.util.UtilWorld;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameManager implements Listener{
    private Arcade arcade;

    public GameManager(Arcade arcade){
        this.arcade = arcade;
        arcade.RegisterEvents(this);
    }

    private void setAllGamemode(GameMode gamemode){
        for(Player player : arcade.getGame().getServer().getOnlinePlayers()){
            player.setGameMode(gamemode);
        }
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.START){
            arcade.getGame().setGameWorld(UtilWorld.loadWorld(arcade.getGame().getGameWorldName()));

            arcade.getGame().onStart();
            arcade.getTeamManager().teleportPlayers();
            arcade.getKitManager().assignPlayerKits();

            new GameStartPlayerMoveListener(arcade);
            new GameStartTimer(arcade, 6).startTimer();
        }else if(event.getGameState() == GameState.TIMER){
            arcade.getTeamManager().assignTeams();
        }else if(event.getGameState() == GameState.PLAYING){
            setAllGamemode(arcade.getGame().getSettings().getGameMode());
            for(Player player : arcade.getGame().getServer().getOnlinePlayers()){
                player.setTicksLived(1);
                new CustomPlayerSpawnEvent(player).callEvent();
            }
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent endEvent){
        arcade.setGameState(GameState.END);

        if(endEvent.getWinningTeam() != null){
            arcade.getPlugin().getServer().broadcastMessage("");
            arcade.getPlugin().getServer().broadcastMessage(endEvent.getWinningTeam().getTeamColor() + endEvent.getWinningTeam().getTeamName() + " Team won the game!");
            arcade.getPlugin().getServer().broadcastMessage("");
        }

        new GameEndTask(arcade).runTaskLater(arcade.getPlugin(), 6*20L);
    }
}
