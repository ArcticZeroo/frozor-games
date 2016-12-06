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
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class KitManager implements Listener{
    private Arcade arcade;
    private PlayerKit[] kits;
    private PlayerKit defaultKit;
    private HashMap<UUID, PlayerKit> selectedKits = new HashMap<>();
    private NotificationManager notificationManager = new NotificationManager("Kit");

    public KitManager(Arcade arcade, PlayerKit[] kits){
        this.arcade = arcade;

        this.arcade.RegisterEvents(this);

        defaultKit = kits[0];
    }

    public void setDefaultKit(PlayerKit defaultKit){
        this.defaultKit = defaultKit;
    }

    public void selectKit(UUID uuid, PlayerKit kit){
        selectedKits.put(uuid, kit);
    }

    public void assignPlayerKits(){
        arcade.getDebugManager().print("Assigning player kits...");
        for(UUID uuid : selectedKits.keySet()){
            PlayerKit selectedKit = selectedKits.get(uuid);

            arcade.getDebugManager().print("Assigning " + selectedKit.getName() + " to " + uuid.toString());

            Player player = Bukkit.getPlayer(uuid);

            UtilPlayer.cleanPlayer(player);
            selectedKit.giveItems(player);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        if(defaultKit != null){
            arcade.getDebugManager().print("Assigning default kit " + defaultKit.getName() + " to joined player " + event.getPlayer().getName());
            selectKit(event.getPlayer().getUniqueId(), defaultKit);
            event.getPlayer().sendMessage(notificationManager.getMessage("Equipped default kit " + ChatColor.YELLOW + defaultKit.getName() + ChatColor.GRAY + " ."));
        }else{
            arcade.getDebugManager().print("default kit is null!");
        }
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
}
