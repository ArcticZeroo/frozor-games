package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.util.UtilChat;
import frozor.util.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class FireMage extends PlayerKit {
    public FireMage() {
        super("FireMage",
                new ArrayList<>(Arrays.asList(
                        ChatColor.GRAY + "Burn your enemies alive.",
                        "",
                        ChatColor.WHITE + "You Will Receive:",
                        UtilChat.getKitEquipmentString("Flame Sword"),
                        UtilChat.getKitEquipmentString("Red Leather Helmet"),
                        UtilChat.getKitEquipmentString("Red Leather Chestplate"),
                        UtilChat.getKitEquipmentString("Red Leather Leggings"),
                        UtilChat.getKitEquipmentString("Red Leather Boots"))),
                new ItemStack(Material.BLAZE_ROD));

        ItemStack flameStaff = UtilItem.createUnbreakableItem(Material.WOOD_SWORD);

        flameStaff.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        flameStaff.addEnchantment(Enchantment.KNOCKBACK, 1);

        ItemMeta flameStaffMeta = flameStaff.getItemMeta();
        flameStaffMeta.setDisplayName(ChatColor.RED + "Flame Sword");
        flameStaff.setItemMeta(flameStaffMeta);

        addStartingItems(new ItemStack[]{flameStaff});

        ItemStack[] armorSet = ArmorSet.getArmorSet(ArmorSetType.LEATHER);
        for(ItemStack armorPiece : armorSet){
            UtilItem.ColorLeather(armorPiece, 255, 64, 0);
        }

        setStartingArmor(armorSet);
    }
}
