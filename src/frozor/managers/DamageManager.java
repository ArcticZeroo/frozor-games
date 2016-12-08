package frozor.managers;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamageManager implements Listener{
    private Arcade arcade;
    private NotificationManager deathNotificationManager = new NotificationManager("Death");

    private boolean allowPlayerDamage = true;

    public DamageManager(Arcade arcade){
        this.arcade = arcade;
        this.arcade.RegisterEvents(this);
    }

    public void setAllowPlayerDamage(boolean allowPlayerDamage) {
        this.allowPlayerDamage = allowPlayerDamage;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER) return;
        if(!allowPlayerDamage){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event){
        event.setDeathMessage("");
        event.getEntity().setHealth(20);
        arcade.getPlugin().getServer().broadcastMessage(deathNotificationManager.getMessage(event.getEntity().getDisplayName() + ChatColor.GRAY + " killed by " + event.getEntity().getLastDamageCause().getEntity().getName()));
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.PLAYING){
            setAllowPlayerDamage(arcade.getGame().getSettings().isPlayerDamageAllowed());
        }
    }
}
