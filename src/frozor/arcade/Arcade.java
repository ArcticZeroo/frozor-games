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
import frozor.teams.PlayerTeam;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.scoreboard.Objective;

import java.util.List;

public class Arcade implements Listener{
    //private GameManager game`Manager;
    private TeamManager teamManager;
    private KitManager kitManager;
    private DebugManager debugManager;
    private WaitingLobbyManager waitingLobbyManager;
    private DamageManager damageManager;
    private NotificationManager notificationManager = new NotificationManager("Game");
    private NotificationManager joinNotificationManager = new NotificationManager("Join");
    private FrozorScoreboard scoreboard;
    private ChatManager chatManager;

    private GameState gameState = GameState.LOBBY;

    private Game plugin;

    public Arcade(Game plugin, PlayerKit[] kits, PlayerTeam[] teams){
        this.plugin = plugin;
        RegisterEvents(this);

        scoreboard = new FrozorScoreboard(this, "arcadeSb");
        debugManager = new DebugManager(this);
        //gameManager = new GameManager(this);
        teamManager = new TeamManager(this, teams);
        kitManager = new KitManager(this, kits);
        damageManager = new DamageManager(this);
        waitingLobbyManager = new WaitingLobbyManager(this);
        chatManager = new ChatManager(this);

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

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public void setGameState(GameState gameState){
        getDebugManager().print("Updating game state to " + gameState.toString());

        GameStateChangeEvent event = new GameStateChangeEvent("Game state has been updated", gameState);
        event.callEvent();

        if(event.isCancelled()) return;

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
            teamManager.teleportPlayers();
        }else if(event.getGameState() == GameState.TIMER){
            teamManager.assignTeams();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
        getPlugin().getServer().broadcastMessage(joinNotificationManager.getMessage(event.getPlayer().getName()));
        kitManager.handlePlayerJoin(event.getPlayer());
        event.getPlayer().setScoreboard(getGameScoreboard().getScoreboard());

        if(getGameState() == GameState.TIMER){
            teamManager.assignPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage("");
    }


    private void addLapis(EnchantingInventory inventory){
        Dye lapisDye = new Dye();
        lapisDye.setColor(DyeColor.BLUE);
        ItemStack lapis = lapisDye.toItemStack();
        lapis.setAmount(5);

        inventory.setItem(1, lapis);
    }

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event){
        EnchantingInventory inventory = (EnchantingInventory) event.getInventory();
        addLapis(inventory);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){
        if(getGameState() != GameState.PLAYING && event.getInventory().getType() != InventoryType.HOPPER) event.setCancelled(true);
        if(event.getInventory().getType() == InventoryType.ENCHANTING){
            EnchantingInventory inventory = (EnchantingInventory) event.getInventory();
            addLapis(inventory);
        }
    }

    @EventHandler
    public void onLapisClick(InventoryClickEvent event){
        if(event.getInventory().getType() == InventoryType.ENCHANTING){
            if(event.getCursor().getType() == Material.INK_SACK){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEnchantmentTableClose(InventoryCloseEvent event){
        if(event.getInventory().getType() == InventoryType.ENCHANTING){
            event.getInventory().remove(Material.INK_SACK);
        }
    }
}
