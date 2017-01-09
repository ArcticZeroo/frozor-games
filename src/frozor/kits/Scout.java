package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.util.UtilChat;
import frozor.util.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class Scout extends PlayerKit {
    public Scout() {
        super("Scout",
                new ArrayList<>(Arrays.asList(
                        ChatColor.GRAY + "Faster than a speeding",
                        ChatColor.GRAY + "bullet, but kind of",
                        ChatColor.GRAY + "scrawny.",
                        "",
                        ChatColor.WHITE + "You Will Receive:",
                        UtilChat.getKitEquipmentString("Wood Sword"),
                        UtilChat.getKitItemString("Ender Pearl", 2),
                        UtilChat.getKitEquipmentString("Blue Leather Helmet"),
                        UtilChat.getKitEquipmentString("Blue Leather Chestplate"),
                        UtilChat.getKitEquipmentString("Blue Leather Leggings"),
                        UtilChat.getKitEquipmentString("Blue Leather Boots"),
                        UtilChat.getKitEquipmentString("Permanent Speed I"),
                        UtilChat.getKitNegativeString("Permanent Weakness I"))),

                new ItemStack(Material.SUGAR));
        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.WOOD_SWORD), UtilItem.setSoulbound(new ItemStack(Material.ENDER_PEARL, 2))});

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
