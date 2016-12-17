package frozor.managers;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.kits.PlayerKit;
import frozor.perk.KitPerk;
import frozor.perk.PerkType;
import frozor.tasks.PlayerRespawnTask;
import frozor.teams.PlayerTeam;
import frozor.util.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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

    private void modifyDamage(EntityDamageEvent event, double modifier){
        event.setDamage(event.getDamage() * modifier);
    }

    private void modifyDamage(EntityDamageEvent event, double modifier, boolean isStatic){
        if(!isStatic) {
            modifyDamage(event, modifier);
            return;
        }

        event.setDamage(event.getDamage() + modifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER) return;
        if(!allowPlayerDamage){
            event.setCancelled(true);
        }

        if(arcade.getGameState() != GameState.PLAYING) return;

        //Perk Handling - Damage Resistance
        Player player = (Player) event.getEntity();
        PlayerKit playerKit = arcade.getKitManager().getSelectedKit(player);
        if(playerKit.getKitPerks().size() == 0) return;

        EntityDamageEvent.DamageCause damageCause = event.getCause();

        KitPerk perk = null;

        //All Damage
        if(playerKit.hasPerk(PerkType.DAMAGE_RESISTANCE)){
            perk = playerKit.getPerk(PerkType.DAMAGE_RESISTANCE);
        }
        //FALL DAMAGE
        else if(damageCause == EntityDamageEvent.DamageCause.FALL){
            if (playerKit.hasPerk(PerkType.FALL_RESISTANCE)) {
                perk =  playerKit.getPerk(PerkType.FALL_RESISTANCE);
            }

        //FIRE DAMAGE
        } else if(damageCause == EntityDamageEvent.DamageCause.FIRE_TICK || damageCause == EntityDamageEvent.DamageCause.FIRE){
            if (playerKit.hasPerk(PerkType.FIRE_RESISTANCE)) {
                perk =  playerKit.getPerk(PerkType.FIRE_RESISTANCE);
            }

        //EXPLOSION DAMAGE
        } else if(damageCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || damageCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
            if (playerKit.hasPerk(PerkType.EXPLOSION_RESISTANCE)) {
                perk =  playerKit.getPerk(PerkType.EXPLOSION_RESISTANCE);
            }
        }else{
            return;
        }

        if(perk != null){
            arcade.getDebugManager().print(String.format("Changing damage received due to Perk %s, which has a modifier of %.2f (Static: %b)", perk.getPerkType().toString(), perk.getModifier(), perk.isStatic()));
            modifyDamage(event, perk.getModifier(), perk.isStatic());
        }
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event){
        if(arcade.getGameState() != GameState.PLAYING) return;
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        Player target = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if(arcade.getTeamManager().isFriendlyTeam(target, damager)){
            event.setCancelled(true);
            return;
        }

        //Perk Handling - Damage Dealing Modifiers
        PlayerKit playerKit = arcade.getKitManager().getSelectedKit(damager);
        if(playerKit.getKitPerks().size() == 0) return;

        KitPerk perk = null;
        if(playerKit.hasPerk(PerkType.DAMAGE)){
            perk = playerKit.getPerk(PerkType.DAMAGE);
        }else if(playerKit.hasPerk(PerkType.SWORD_DAMAGE)){
            if(UtilItem.isSword(damager.getItemInHand())){
                perk = playerKit.getPerk(PerkType.SWORD_DAMAGE);
            }
        }else{
            return;
        }

        if(perk != null){
            arcade.getDebugManager().print(String.format("Changing damage dealt due to Perk %s, which has a modifier of %.2f (Static: %b)", perk.getPerkType().toString(), perk.getModifier(), perk.isStatic()));
            modifyDamage(event, perk.getModifier(), perk.isStatic());
        }
    }

    private String getNameWithTeamColor(Player player){
        PlayerTeam playerTeam = arcade.getTeamManager().getPlayerTeam(player);
        return playerTeam.getTeamColor() + player.getName();
    }

    public void respawnPlayer(Player player){
        player.setHealth(20);

        player.setTicksLived(1);

        new PlayerRespawnTask(arcade, player).runTaskLater(arcade.getPlugin(), 1L);
    }
    
    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onPlayerDeath(final PlayerDeathEvent event){
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
            case FALLING_BLOCK:
                killer = "Falling Block";
                break;
            case STARVATION:
                killer = "Starvation";
                break;
            case SUFFOCATION:
                killer = "Suffocation";
                break;
            case FIRE_TICK:
            case FIRE:
                killer = "Fire";
                break;
            case VOID:
                killer = "Void";
                break;
            case MAGIC:
                killer = "Magic";
                break;
            case PROJECTILE:
                killer = "Projectile";
                break;

        }

        event.setDeathMessage(notificationManager.getMessage(getNameWithTeamColor(player) + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + killer + ChatColor.GRAY + "."));

        event.setKeepInventory(false);
        event.setKeepLevel(false);

        List<ItemStack> toRemove = new ArrayList<>();
        for(ItemStack item : event.getDrops()){
            if(item == null || item.getType() == Material.AIR) continue;
            if(item.getItemMeta().spigot().isUnbreakable()){
                toRemove.add(item);
            }else{
                List<String> itemLore = item.getItemMeta().getLore();
                if(itemLore == null || itemLore.size() == 0) continue;
                if(itemLore.contains(ChatColor.BLUE+"Soulbound")) toRemove.add(item);
            }
        }
        event.getDrops().removeAll(toRemove);

        respawnPlayer(event.getEntity());
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.PLAYING){
            setAllowPlayerDamage(arcade.getGame().getSettings().isPlayerDamageAllowed());
        }else if(event.getGameState() == GameState.END){
            setAllowPlayerDamage(false);
        }
    }
}
