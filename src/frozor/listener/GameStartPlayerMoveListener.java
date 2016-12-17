package frozor.listener;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameStartPlayerMoveListener implements Listener{
    private Arcade arcade;

    public GameStartPlayerMoveListener(Arcade arcade){
        this.arcade = arcade;
        arcade.RegisterEvents(this);
    }

    private void destroy(){
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.PLAYING){
            destroy();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(arcade.getGameState() == GameState.START){
            if(event.getTo().distance(event.getFrom()) > 0){
                Location from = event.getFrom();
                Location to = event.getTo();

                if((Math.abs(to.getX() - from.getX()) > 0) || (Math.abs(to.getZ() - from.getZ()) > 0)){
                    event.getPlayer().teleport(event.getFrom());
                }

            }
        }
    }
}
