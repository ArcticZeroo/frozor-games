package frozor.managers;

import frozor.arcade.Arcade;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamageManager implements Listener{
    private Arcade arcade;
    private NotificationManager deathNotificationManager = new NotificationManager("Death");

    public DamageManager(Arcade arcade){
        this.arcade = arcade;
        this.arcade.RegisterEvents(this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        event.setDeathMessage("");
        event.getEntity().setHealth(20);
        arcade.getPlugin().getServer().broadcastMessage(deathNotificationManager.getMessage(event.getEntity().getDisplayName() + ChatColor.GRAY + " killed by " + event.getEntity().getLastDamageCause().getEntity().getName()));
    }
}
