package frozor.arcade;

import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.PlayerKit;
import frozor.managers.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Arcade implements Listener{
    //private GameManager gameManager;
    private KitManager kitManager;
    private DebugManager debugManager;
    private WaitingLobbyManager waitingLobbyManager;
    private DamageManager damageManager;
    private NotificationManager notificationManager = new NotificationManager("Game");

    private GameState gameState = GameState.LOBBY;

    private Game plugin;

    public Arcade(Game plugin, PlayerKit[] kits){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        debugManager = new DebugManager(this);
        //gameManager = new GameManager(this);
        kitManager = new KitManager(this, kits);
        damageManager = new DamageManager(this);
        waitingLobbyManager = new WaitingLobbyManager(this);
    }

    public Game getPlugin() {
        return plugin;
    }

    public Game getGame(){
        return plugin;
    }

    public void RegisterEvents(Listener listener){
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public GameState getGameState(){
        return gameState;
    }

    public DebugManager getDebugManager(){
        return debugManager;
    }

    public DamageManager getDamageManager() {
        return damageManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public WaitingLobbyManager getWaitingLobbyManager() {
        return waitingLobbyManager;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void setGameState(GameState gameState){
        GameStateChangeEvent event = new GameStateChangeEvent("Game state has been updated", gameState);
        event.callEvent();

        if(event.isCancelled()) return;

        this.gameState = gameState;
    }

    //Event Handlers
    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        //gameState = event.getGameState();
        getDebugManager().print("Game state has been updated to " + event.getGameState());
    }
}
