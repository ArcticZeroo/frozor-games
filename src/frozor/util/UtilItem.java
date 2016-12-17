package frozor.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static Boolean isSword(ItemStack item){
        return (item.getType() == Material.WOOD_SWORD || item.getType() == Material.STONE_SWORD || item.getType() == Material.GOLD_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD);
    }

    public static ItemStack setSoulbound(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new ArrayList<>(Arrays.asList(new String[]{ChatColor.BLUE+"Soulbound"})));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
