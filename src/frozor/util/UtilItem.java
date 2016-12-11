package frozor.util;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class UtilItem {
    public static void ColorLeather(ItemStack itemStack, int red, int blue, int green){
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        itemMeta.setColor(Color.fromRGB(red, blue, green));
        itemStack.setItemMeta(itemMeta);
    }
}
