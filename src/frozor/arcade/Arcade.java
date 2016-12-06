package frozor.arcade;

import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.PlayerKit;
import frozor.managers.DebugManager;
import frozor.managers.GameManager;
import frozor.managers.KitManager;
import frozor.managers.WaitingLobbyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Arcade implements Listener{
    //private GameManager gameManager;
    private KitManager kitManager;
    private DebugManager debugManager;
    private WaitingLobbyManager waitingLobbyManager;

    private GameState gameState = GameState.LOBBY;

    private Game plugin;

    public Arcade(Game plugin, PlayerKit[] kits){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        debugManager = new DebugManager(this);
        //gameManager = new GameManager(this);
        kitManager = new KitManager(this, kits);
        waitingLobbyManager = new WaitingLobbyManager(this, 2, 3);
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

    public void setGameState(GameState gameState){
        GameStateChangeEvent event = new GameStateChangeEvent("Game state has been updated", gameState);
        event.callEvent();

        if(event.isCancelled()) return;

        this.gameState = gameState;
    }

    //Event Handlers

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        gameState = event.getGameState();
    }

}
