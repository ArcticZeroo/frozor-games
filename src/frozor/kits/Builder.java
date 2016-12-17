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

public class Builder extends PlayerKit {
    public Builder() {
        super("Builder",
                new ArrayList<>(Arrays.asList(
                        ChatColor.GRAY + "Enemies trying to kill your king?",
                        ChatColor.GRAY + "Build a wall!",
                        "",
                        ChatColor.WHITE + "You Will Receive:",
                        UtilChat.getKitEquipmentString("Diamond Pickaxe"),
                        UtilChat.getKitEquipmentString("Iron Axe"),
                        UtilChat.getKitEquipmentString("Iron Shovel"),
                        UtilChat.getKitItemString("Brick", 64),
                        UtilChat.getKitItemString("Redstone", 16),
                        UtilChat.getKitEquipmentString("Chainmail Helmet"),
                        UtilChat.getKitEquipmentString("Chainmail Chestplate"),
                        UtilChat.getKitEquipmentString("Chainmail Leggings"),
                        UtilChat.getKitEquipmentString("Chainmail Boots"))),
                new ItemStack(Material.DIAMOND_PICKAXE));

        addStartingItems(
                new ItemStack[]{
                        UtilItem.createUnbreakableItem(Material.DIAMOND_PICKAXE),
                        UtilItem.createUnbreakableItem(Material.IRON_AXE),
                        UtilItem.createUnbreakableItem(Material.IRON_SPADE),
                        UtilItem.setSoulbound(new ItemStack(Material.BRICK, 64)),
                        UtilItem.setSoulbound(new ItemStack(Material.REDSTONE, 16))
                });

        setStartingArmor(ArmorSet.getArmorSet(ArmorSetType.CHAINMAIL));

    }
}
