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

public class Scout extends PlayerKit {
    public Scout() {
        super("Scout", new ArrayList<>(Arrays.asList("§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a64 §fARrows", "§3•§fChainmail Helmet", "§3•§fChainmail Chestplate", "§3•§fChainmail Leggings", "§3•§fChainmail Boots")), new ItemStack(Material.SUGAR));

        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.WOOD_SWORD), new ItemStack(Material.ENDER_PEARL, 2)});

        ItemStack[] armorSet = ArmorSet.getArmorSet(ArmorSetType.LEATHER);
        for(ItemStack armorPiece : armorSet){
            UtilItem.ColorLeather(armorPiece, 66, 193, 242);
        }

        setStartingArmor(armorSet);
    }

    public void giveItems(Player player){
        super.giveItems(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 999999999, 0, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 0, true));
    }
}
