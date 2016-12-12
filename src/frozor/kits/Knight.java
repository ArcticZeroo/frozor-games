package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.util.UtilItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends PlayerKit {
    public Knight() {
        super("Knight", new ArrayList<>(Arrays.asList("§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a64 §fARrows", "§3•§fChainmail Helmet", "§3•§fChainmail Chestplate", "§3•§fChainmail Leggings", "§3•§fChainmail Boots")), new ItemStack(Material.IRON_SWORD));

        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.STONE_SWORD)});
        setStartingArmor(ArmorSet.getArmorSet(ArmorSetType.IRON));
    }

    public void giveItems(Player player){
        super.giveItems(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 0, true));
    }
}
