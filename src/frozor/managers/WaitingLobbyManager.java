package frozor.managers;

import frozor.arcade.Arcade;
import frozor.component.FrozorScoreboard;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.events.WaitingTimeChangeEvent;
import frozor.tasks.WaitingTimer;
import frozor.util.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WaitingLobbyManager implements Listener{
    private Arcade arcade;

    private final int minTimer = 60;
    private final int maxTimer = 20;

    private WaitingTimer waitingTimer;

    public WaitingLobbyManager(Arcade arcade) {
        this.arcade = arcade;

        arcade.RegisterEvents(this);
        arcade.getDebugManager().print(String.format("Player Min: %s, Player Max: %s", getPlayerMin(), getPlayerMax()));

        waitingTimer = new WaitingTimer(arcade, minTimer);

        arcade.getDamageManager().setAllowPlayerDamage(false);

        makeWaitingScoreboard();
    }

    public void makeWaitingScoreboard(){
        FrozorScoreboard scoreboard = arcade.getGameScoreboard();
        scoreboard.setNewSidebarObjective("waitSb");
        scoreboard.setDisplayName(ChatColor.AQUA + (ChatColor.BOLD + "Waiting for players"));
        scoreboard.setBlankLine(0);
        scoreboard.setLine(1, ChatColor.GOLD + (ChatColor.BOLD + "Game"));
        scoreboard.setLine(2, arcade.getGame().getTitle());
        scoreboard.setBlankLine(3);
        scoreboard.setLine(4, ChatColor.GREEN + (ChatColor.BOLD + "Players"));
        updatePlayerCount();
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

    public String getOnlineString(){
        return arcade.getPlugin().getServer().getOnlinePlayers().size() + "/" + getPlayerMax();
    }

    public void updatePlayerCount(){
        arcade.getGameScoreboard().setLine(5, getOnlineString());
    }

    public void setScoreboardWaiting(){
        arcade.getGameScoreboard().setDisplayName(ChatColor.AQUA + (ChatColor.BOLD + "Waiting for players"));
    }

    public boolean canStartTimer(){
        //The timer can be started if:
        // - The timer has not started
        // - Players are in the lobby
        // - Player count is currently above or equal to the min
        return (arcade.getGameState() == GameState.LOBBY && arcade.getGame().getServer().getOnlinePlayers().size() >= getPlayerMin() && !getWaitingTimer().isActive());
    }

    public boolean canShortenTimer(){
        //The timer can be shortened if:
        // - The timer is already running (GameState.TIMER & isActive())
        // - Players exceeds or is equal to the maximum amount * 0.75
        // - The timer has not already gone past the value it would if it was shortened
        return ((arcade.getGameState() == GameState.TIMER) && (getWaitingTimer().isActive()) && (arcade.getGame().getServer().getOnlinePlayers().size() >= Math.floor(getPlayerMax()*0.75)) && (getWaitingTimer().getTime() > maxTimer));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //Only handling events in waiting lobby, so that things don't break.
        if(arcade.getGameState() != GameState.TIMER && arcade.getGameState() != GameState.LOBBY ) return;
        if(event.getPlayer() == null) return;

        //Clean and tp player
        UtilPlayer.cleanPlayer(event.getPlayer());
        event.getPlayer().teleport(arcade.getGame().getSpawnLocation());

        //Scoreboard magic
        updatePlayerCount();

        //Deal with timer bull
        int onlineCount = Bukkit.getServer().getOnlinePlayers().size();

        if(canStartTimer()){
            waitingTimer.startTimer();
        }else if(canShortenTimer()){
            waitingTimer.setTime(maxTimer);
        }else if(onlineCount > getPlayerMax()){
            event.getPlayer().kickPlayer(ChatColor.RED + (ChatColor.BOLD + "This game is currently full."));
        }
    }

    @EventHandler
    public void onWaitingTimeChange(WaitingTimeChangeEvent event){
        arcade.getGameScoreboard().setDisplayName(String.format(ChatColor.AQUA + (ChatColor.BOLD + "Starting in %d seconds"), event.getTime()));
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.LOBBY){
            setScoreboardWaiting();
            if(waitingTimer != null){
                if(waitingTimer.isActive()){
                    waitingTimer.cancel();
                }
            }
            waitingTimer = new WaitingTimer(arcade, minTimer);
        }else if(event.getGameState() == GameState.TIMER){
            arcade.getGameScoreboard().setDisplayName(String.format(ChatColor.AQUA + (ChatColor.BOLD + "Starting in %d seconds"), getWaitingTimer().getTime()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        //Only handling events in waiting lobby, so that things don't break.
        if(arcade.getGameState() != GameState.TIMER && arcade.getGameState() != GameState.LOBBY ) return;

        if(arcade.getGameState() == GameState.TIMER){
            if(Bukkit.getServer().getOnlinePlayers().size() < getPlayerMin()){
                waitingTimer.cancel();
                waitingTimer.reset();
                setScoreboardWaiting();
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
        //If the game has not yet started
        if(arcade.getGameState().compareTo(GameState.START) < 0){
            if(event.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;
            if(event.getInventory().getType() == InventoryType.CHEST) event.setCancelled(true);
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
