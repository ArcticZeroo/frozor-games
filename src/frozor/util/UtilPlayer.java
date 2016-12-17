package frozor.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.Collection;

public class UtilPlayer {
    public static void cleanPlayer(Player player, GameMode gameMode){
        player.setGameMode(gameMode);
        player.setHealth(20D);
        player.setFoodLevel(20);
        player.setSaturation(1.0F);
        player.getInventory().clear();
        player.setExp(0F);
        player.setLevel(0);
        player.setFireTicks(0);

        player.setVelocity(new Vector(0,0,0));

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        Collection<PotionEffect> ActiveEffects = player.getActivePotionEffects();
        if(ActiveEffects.size() > 0){
            for(PotionEffect effect : ActiveEffects){
                player.removePotionEffect(effect.getType());
            }
        }
    }

    public static void cleanPlayer(Player player){
        cleanPlayer(player, GameMode.ADVENTURE);
    }
}
