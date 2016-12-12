package frozor.util;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class UtilItem {
    public static void ColorLeather(ItemStack itemStack, int red, int blue, int green){
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        itemMeta.setColor(Color.fromRGB(red, blue, green));
        itemStack.setItemMeta(itemMeta);
    }

    public static ItemStack createUnbreakableItem(Material material){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack addEnchantmentGlow(ItemStack item){
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        return item;
    }
}
