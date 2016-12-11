package frozor.managers;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.teams.PlayerTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamageManager implements Listener{
    private Arcade arcade;
    private NotificationManager notificationManager = new NotificationManager("Death");

    private boolean allowPlayerDamage = true;

    public DamageManager(Arcade arcade){
        this.arcade = arcade;
        this.arcade.RegisterEvents(this);
    }

    public void setAllowPlayerDamage(boolean allowPlayerDamage) {
        this.allowPlayerDamage = allowPlayerDamage;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER) return;
        if(!allowPlayerDamage){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        if(arcade.getTeamManager().isFriendlyTeam((Player) event.getEntity(), (Player) event.getDamager())) event.setCancelled(true);

    }

    private String getNameWithTeamColor(Player player){
        PlayerTeam playerTeam = arcade.getTeamManager().getPlayerTeam(player);
        return playerTeam.getTeamColor() + player.getName();
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        String killer = "Unknown";

        switch(event.getEntity().getLastDamageCause().getCause()){
            case ENTITY_ATTACK:
                killer = getNameWithTeamColor(player.getKiller());
                break;
            case BLOCK_EXPLOSION:
            case ENTITY_EXPLOSION:
                killer = "Explosion";
                break;
            case FALL:
                killer = "Fall";
                break;
            case DROWNING:
                killer = "Drowning";
                break;
        }
        event.setDeathMessage(notificationManager.getMessage(getNameWithTeamColor(player) + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + killer + ChatColor.GRAY + "."));

        event.getEntity().setHealth(20);

        event.getEntity().teleport(arcade.getTeamManager().getTeamSpawn(event.getEntity()));
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.PLAYING){
            setAllowPlayerDamage(arcade.getGame().getSettings().isPlayerDamageAllowed());
        }
    }
}
