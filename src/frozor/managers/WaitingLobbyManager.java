package frozor.managers;

import frozor.arcade.Arcade;
import frozor.component.WaitingLobbyScoreboard;
import frozor.enums.GameState;
import frozor.tasks.WaitingTimer;
import frozor.util.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WaitingLobbyManager implements Listener{
    private Arcade arcade;

    private final int minTimer = 60;
    private final int maxTimer = 20;

    private WaitingTimer waitingTimer;
    private WaitingLobbyScoreboard waitingLobbyScoreboard;

    public WaitingLobbyManager(Arcade arcade) {
        this.arcade = arcade;

        arcade.RegisterEvents(this);
        arcade.getDebugManager().print(String.format("Player Min: %s, Player Max: %s", getPlayerMin(), getPlayerMax()));

        waitingTimer = new WaitingTimer(arcade, minTimer);
        waitingLobbyScoreboard = new WaitingLobbyScoreboard(this);

        arcade.getDamageManager().setAllowPlayerDamage(false);
    }

    public int getPlayerMin(){
        return arcade.getGame().getSettings().getPlayerMin();
    }

    public int getPlayerMax(){
        return arcade.getGame().getSettings().getPlayerMax();
    }

    public WaitingTimer getWaitingTimer(){
        return waitingTimer;
    }

    public Arcade getArcade() {
        return arcade;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //Only handling events in waiting lobby, so that things don't break.
        if(arcade.getGameState() != GameState.TIMER && arcade.getGameState() != GameState.LOBBY ) return;

        event.getPlayer().setScoreboard(waitingLobbyScoreboard.getScoreboard());
        UtilPlayer.cleanPlayer(event.getPlayer());
        event.getPlayer().teleport(arcade.getGame().getSpawnLocation());

        int onlineCount = Bukkit.getServer().getOnlinePlayers().size();

        if(onlineCount >= getPlayerMin() && arcade.getGameState() == GameState.LOBBY){
            //If there are at least minimum players, and the timer hasn't started yet
            waitingTimer.startTimer();
            arcade.setGameState(GameState.TIMER);

        }else if(onlineCount == getPlayerMax()){
            //If players is max, check if the timer can be shortened and do so if it can
            if(waitingTimer.getTime() > maxTimer){
                waitingTimer.setTime(maxTimer);
            }
        }else if(onlineCount > getPlayerMax()){
            event.getPlayer().kickPlayer("This game is currently full.");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        //Only handling events in waiting lobby, so that things don't break.
        if(arcade.getGameState() != GameState.TIMER && arcade.getGameState() != GameState.LOBBY ) return;

        if(arcade.getGameState() == GameState.TIMER){
            if(Bukkit.getServer().getOnlinePlayers().size() < getPlayerMin()){
                waitingTimer.reset();
                waitingLobbyScoreboard.setWaiting();
                arcade.setGameState(GameState.LOBBY);
            }
        }
    }

    //Prevent Block Breaking
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event){
        if(arcade.getGameState() != GameState.PLAYING && event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
        }
    }

    //Keep the cookie in the chest
    @EventHandler
    public void onChestItemInteract(InventoryClickEvent event){
        if(arcade.getGameState() == GameState.TIMER || arcade.getGameState() == GameState.LOBBY){
            if(event.getInventory().getName().equalsIgnoreCase("§9§lParkour Completion Chest")) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event){
        if(arcade.getGameState() != GameState.PLAYING){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER) return;

        if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
            if(arcade.getGameState() == GameState.PLAYING) return;
            event.setCancelled(true);
        }
    }
}
