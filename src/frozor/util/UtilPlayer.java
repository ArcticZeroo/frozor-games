package frozor.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class UtilPlayer {
    public static void cleanPlayer(Player player){
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20D);
        player.setFoodLevel(20);
        player.setSaturation(1.0F);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[]{});
        Collection<PotionEffect> ActiveEffects = player.getActivePotionEffects();
        if(ActiveEffects.size() > 0){
            for(PotionEffect effect : ActiveEffects){
                player.removePotionEffect(effect.getType());
            }
        }
    }
}
