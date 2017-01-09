package frozor.arcade;

import a.j.m.P;
import frozor.commands.GameCommands;
import frozor.commands.KitCommands;
import frozor.component.FrozorScoreboard;
import frozor.enums.GameState;
import frozor.events.CustomPlayerSpawnEvent;
import frozor.events.GameEndEvent;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.PlayerKit;
import frozor.managers.*;
import frozor.teams.PlayerTeam;
import kotlin.Suppress;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

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
    private GameManager gameManager;
    private GameWorldManager gameWorldManager;

    private GameState gameState = GameState.LOBBY;

    private Game plugin;

    public Arcade(Game plugin, PlayerKit[] kits, PlayerTeam[] teams){
        this.plugin = plugin;
        RegisterEvents(this);

        scoreboard = new FrozorScoreboard(this, "arcadeSb");
        debugManager = new DebugManager(this);
        gameWorldManager = new GameWorldManager(this);
        gameManager = new GameManager(this);
        teamManager = new TeamManager(this, teams);
        kitManager = new KitManager(this, kits);
        damageManager = new DamageManager(this);
        waitingLobbyManager = new WaitingLobbyManager(this);
        chatManager = new ChatManager(this);

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

    public void CallEvent(Event event){
        getGame().getServer().getPluginManager().callEvent(event);
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

    public ChatManager getChatManager() {
        return chatManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public GameWorldManager getGameWorldManager() {
        return gameWorldManager;
    }

    public void setGameState(GameState gameState){
        getDebugManager().print("Updating game state to " + gameState.toString());

        GameStateChangeEvent event = new GameStateChangeEvent("Game state has been updated", gameState);
        event.callEvent();

        if(event.isCancelled()) return;

        this.gameState = gameState;
    }

    //Event Handlers
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onNameTagDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.SILVERFISH || event.getEntityType() == EntityType.ARMOR_STAND){
            Entity toDamage;
            if(event.getEntityType() == EntityType.SILVERFISH){
                toDamage = event.getEntity().getVehicle();
            }else{
                toDamage = event.getEntity().getVehicle().getVehicle();
            }

            CallEvent(new EntityDamageEvent(toDamage, event.getCause(), event.getDamage()));

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
        getPlugin().getServer().broadcastMessage(joinNotificationManager.getMessage(event.getPlayer().getName()));
        if(getGameState().compareTo(GameState.START) < 0){
            //game has not yet started
            kitManager.handlePlayerJoin(event.getPlayer());

            if(getGameState() == GameState.TIMER){
                teamManager.assignPlayer(event.getPlayer());
            }
        }else{
            //game has started
            if(getGameState() != GameState.END){
                teamManager.assignPlayer(event.getPlayer());
                kitManager.handlePlayerJoin(event.getPlayer());
                event.getPlayer().teleport(teamManager.getTeamSpawn(event.getPlayer()));
                kitManager.giveKit(event.getPlayer());

                if(getGameState() == GameState.PLAYING){
                    new CustomPlayerSpawnEvent(event.getPlayer()).callEvent();
                }
            }
        }

        event.getPlayer().setScoreboard(getGameScoreboard().getScoreboard());
    }

    public void checkTeamSize(PlayerTeam team){
        if(getGameState() == GameState.PLAYING && team.getScoreboardTeam().getSize() == 0){
            getGame().getServer().broadcastMessage(ChatColor.AQUA + (ChatColor.BOLD + "Game ending, not enough Players!"));
            setGameState(GameState.END);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage("");
        kitManager.getSelectedKits().remove(event.getPlayer().getUniqueId());

        PlayerTeam playerTeam = teamManager.getPlayerTeam(event.getPlayer());
        if(playerTeam != null && playerTeam.getScoreboardTeam() != null){
            playerTeam.getScoreboardTeam().removeEntry(event.getPlayer().getName());
        }

        checkTeamSize(playerTeam);
    }


    private void addLapis(EnchantingInventory inventory){
        Dye lapisDye = new Dye();
        lapisDye.setColor(DyeColor.BLUE);
        ItemStack lapis = lapisDye.toItemStack();
        lapis.setAmount(5);

        inventory.setItem(1, lapis);
    }

    //Stop Weather I guess
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        event.setCancelled(true);
    }

    //Prevent kit items from dropping
    @EventHandler
    public void onItemDrop(ItemSpawnEvent event){
        ItemStack item = event.getEntity().getItemStack();
        if(item.getItemMeta().spigot().isUnbreakable()){
            event.setCancelled(true);
        }else{
            List<String> itemLore = item.getItemMeta().getLore();
            if(itemLore == null || itemLore.size() == 0) return;
            if(itemLore.contains(ChatColor.BLUE+"Soulbound")) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event){
        EnchantingInventory inventory = (EnchantingInventory) event.getInventory();
        addLapis(inventory);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){
        if(getGameState() != GameState.PLAYING){
            //For Kit Inventories
            //For Parkour Chests
            if(event.getInventory().getType() != InventoryType.HOPPER && !event.getPlayer().getLocation().getWorld().getName().equals("lobby")){
                event.setCancelled(true);
            }
        }
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
