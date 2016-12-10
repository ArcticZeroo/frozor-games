package frozor.arcade;

import frozor.commands.GameCommands;
import frozor.commands.KitCommands;
import frozor.component.FrozorScoreboard;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.PlayerKit;
import frozor.managers.*;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Arcade implements Listener{
    //private GameManager gameManager;
    private KitManager kitManager;
    private DebugManager debugManager;
    private WaitingLobbyManager waitingLobbyManager;
    private DamageManager damageManager;
    private NotificationManager notificationManager = new NotificationManager("Game");
    private NotificationManager joinNotificationManager = new NotificationManager("Join");
    private FrozorScoreboard scoreboard;

    private GameState gameState = GameState.LOBBY;

    private Game plugin;

    public Arcade(Game plugin, PlayerKit[] kits){
        this.plugin = plugin;
        RegisterEvents(this);

        debugManager = new DebugManager(this);
        //gameManager = new GameManager(this);
        kitManager = new KitManager(this, kits);
        damageManager = new DamageManager(this);
        waitingLobbyManager = new WaitingLobbyManager(this);
        scoreboard = new FrozorScoreboard(this, "arcadeSb");

        plugin.getCommand("kit").setExecutor(new KitCommands(this));
        plugin.getCommand("game").setExecutor(new GameCommands(this));

        joinNotificationManager.setPrefixColor(ChatColor.DARK_GRAY);
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

    public FrozorScoreboard getGameScoreboard() {
        return scoreboard;
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

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event){
        event.setJoinMessage("");
        getPlugin().getServer().broadcastMessage(joinNotificationManager.getMessage(event.getPlayer().getName()));
        kitManager.handlePlayerJoin(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage("");
    }
}
