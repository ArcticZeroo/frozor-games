package frozor.arcade;

import clojure.lang.Obj;
import frozor.commands.GameCommands;
import frozor.commands.KitCommands;
import frozor.component.FrozorScoreboard;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.PlayerKit;
import frozor.managers.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Objective;

import java.util.List;

public class Arcade implements Listener{
    //private GameManager game`Manager;
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

        scoreboard = new FrozorScoreboard(this, "arcadeSb");
        debugManager = new DebugManager(this);
        //gameManager = new GameManager(this);
        kitManager = new KitManager(this, kits);
        damageManager = new DamageManager(this);
        waitingLobbyManager = new WaitingLobbyManager(this);

        plugin.getCommand("kit").setExecutor(new KitCommands(this));
        plugin.getCommand("game").setExecutor(new GameCommands(this));

        joinNotificationManager.setPrefixColor(ChatColor.DARK_GRAY);

        List<Entity> entities = getPlugin().getGameWorld().getEntities();
        for(Entity entity : entities){
            if(!(entity instanceof Player)){
                entity.remove();
            }
        }
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
        getDebugManager().print("Updating game state to " + gameState.toString());

        GameStateChangeEvent event = new GameStateChangeEvent("Game state has been updated", gameState);
        event.callEvent();

        if(event.isCancelled()){
            getDebugManager().print("Game state event was cancelled");
            return;
        }

        this.gameState = gameState;
    }

    //Event Handlers
    @EventHandler
    public void onNameTagDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.SQUID || event.getEntityType() == EntityType.ARMOR_STAND){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.START){
            getGame().onStart();
        }
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
