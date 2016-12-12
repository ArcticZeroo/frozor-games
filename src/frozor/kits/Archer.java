package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.util.UtilChat;
import frozor.util.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Archer extends PlayerKit {
    public Archer() {
        super("Archer", new ArrayList<>(Arrays.asList(ChatColor.GRAY + "Pierce the heart of your enemies!", "", ChatColor.WHITE + "You Will Receive:", UtilChat.getKitEquipmentString("Wood Sword"), UtilChat.getKitEquipmentString("Bow"), UtilChat.getKitItemString("Arrows", 64), UtilChat.getKitEquipmentString("Chainmail Helmet"), UtilChat.getKitEquipmentString("Chainmail Chestplate"), UtilChat.getKitEquipmentString("Chainmail Leggings"), UtilChat.getKitEquipmentString("Chainmail Boots"))), new ItemStack(Material.BOW));

        ItemStack arrows = new ItemStack(Material.ARROW);
        arrows.setAmount(64);

        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.WOOD_SWORD), UtilItem.createUnbreakableItem(Material.BOW), arrows});
        setStartingArmor(ArmorSet.getArmorSet(ArmorSetType.CHAINMAIL));
    }
}
