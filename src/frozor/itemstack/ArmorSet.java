package frozor.itemstack;

import frozor.util.UtilItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorSet {
    public static ItemStack[] getArmorSet(ArmorSetType type){
        switch(type){
            case LEATHER:
                return new ItemStack[]{UtilItem.createUnbreakableItem(Material.LEATHER_BOOTS), UtilItem.createUnbreakableItem(Material.LEATHER_LEGGINGS), UtilItem.createUnbreakableItem(Material.LEATHER_CHESTPLATE), UtilItem.createUnbreakableItem(Material.LEATHER_HELMET)};
            case CHAINMAIL:
                return new ItemStack[]{UtilItem.createUnbreakableItem(Material.CHAINMAIL_BOOTS), UtilItem.createUnbreakableItem(Material.CHAINMAIL_LEGGINGS), UtilItem.createUnbreakableItem(Material.CHAINMAIL_CHESTPLATE), UtilItem.createUnbreakableItem(Material.CHAINMAIL_HELMET)};
            case GOLD:
                return new ItemStack[]{UtilItem.createUnbreakableItem(Material.GOLD_BOOTS), UtilItem.createUnbreakableItem(Material.GOLD_LEGGINGS), UtilItem.createUnbreakableItem(Material.GOLD_CHESTPLATE), UtilItem.createUnbreakableItem(Material.GOLD_HELMET)};
            case IRON:
                return new ItemStack[]{UtilItem.createUnbreakableItem(Material.IRON_BOOTS), UtilItem.createUnbreakableItem(Material.IRON_LEGGINGS), UtilItem.createUnbreakableItem(Material.IRON_CHESTPLATE), UtilItem.createUnbreakableItem(Material.IRON_HELMET)};
            case DIAMOND:
                return new ItemStack[]{UtilItem.createUnbreakableItem(Material.DIAMOND_BOOTS), UtilItem.createUnbreakableItem(Material.DIAMOND_LEGGINGS), UtilItem.createUnbreakableItem(Material.DIAMOND_CHESTPLATE), UtilItem.createUnbreakableItem(Material.DIAMOND_HELMET)};
            default:
                return new ItemStack[]{};
        }
    }
}
