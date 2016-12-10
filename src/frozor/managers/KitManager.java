package frozor.managers;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.kits.PlayerKit;
import frozor.util.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class KitManager implements Listener{
    private Arcade arcade;
    //private PlayerKit[] kits;
    private HashMap<String, PlayerKit> kits = new HashMap<>();
    private PlayerKit defaultKit;
    private HashMap<UUID, PlayerKit> selectedKits = new HashMap<>();
    private NotificationManager notificationManager = new NotificationManager("Kit");

    public KitManager(Arcade arcade, PlayerKit[] kits){
        this.arcade = arcade;

        this.arcade.RegisterEvents(this);

        defaultKit = kits[0];

        for(PlayerKit kit : kits){
            addKit(kit);
        }
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void addKit(PlayerKit kit){
        kits.put(kit.getName().toLowerCase(), kit);
    }

    public void setDefaultKit(PlayerKit defaultKit){
        if(kits.containsValue(defaultKit)){
            this.defaultKit = defaultKit;
        }
    }

    public void selectKit(UUID uuid, PlayerKit kit){
        selectedKits.put(uuid, kit);
    }

    public void giveKit(UUID uuid){
        if(selectedKits.containsKey(uuid)){
            PlayerKit selectedKit = selectedKits.get(uuid);
            Player player = Bukkit.getPlayer(uuid);

            UtilPlayer.cleanPlayer(player);
            selectedKit.giveItems(player);
        }
    }

    public void giveKit(Player player){
        if(selectedKits.containsKey(player.getUniqueId())){
            PlayerKit selectedKit = selectedKits.get(player.getUniqueId());

            UtilPlayer.cleanPlayer(player);
            selectedKit.giveItems(player);
        }
    }

    public void assignPlayerKits(){
        arcade.getDebugManager().print("Assigning player kits...");
        for(UUID uuid : selectedKits.keySet()){
            giveKit(uuid);
        }
    }

    public HashMap<UUID, PlayerKit> getSelectedKits() {
        return selectedKits;
    }

    public HashMap<String, PlayerKit> getKits() {
        return kits;
    }

    public void handlePlayerJoin(Player player){
        if(defaultKit != null){
            selectKit(player.getUniqueId(), defaultKit);
            player.sendMessage(notificationManager.getMessage("Equipped default kit " + ChatColor.YELLOW + defaultKit.getName() + ChatColor.GRAY + "."));
        }
    }
    
    public void handleKitCommand(){

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        //Only handling events in waiting lobby, so that things don't break.
        if(arcade.getGameState() != GameState.TIMER && arcade.getGameState() != GameState.LOBBY ) return;

        selectedKits.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.START){
            assignPlayerKits();
        }
    }

    @EventHandler(priority =  EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent event){
        giveKit(event.getEntity());
    }
}
