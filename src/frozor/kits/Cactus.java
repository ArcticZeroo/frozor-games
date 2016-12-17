package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.perk.KitPerk;
import frozor.perk.PerkType;
import frozor.util.UtilChat;
import frozor.util.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Cactus extends PlayerKit {
    public Cactus() {
        super("Cactus",
                new ArrayList<>(Arrays.asList(
                        ChatColor.GRAY + "When life gives you lemons,",
                        ChatColor.GRAY + "STAB THE LEMONS!",
                        "",
                        ChatColor.WHITE + "You Will Receive:",
                        UtilChat.getKitEquipmentString("Wood Sword"),
                        UtilChat.getKitEquipmentString("Thorns Leather Helmet"),
                        UtilChat.getKitEquipmentString("Thorns Leather Chestplate"),
                        UtilChat.getKitEquipmentString("Thorns Leather Leggings"),
                        UtilChat.getKitEquipmentString("Thorns Leather Boots"),
                        UtilChat.getKitItemString("Damage Taken", -1))),
                new ItemStack(Material.CACTUS),
                Collections.singletonList(new KitPerk(PerkType.DAMAGE_RESISTANCE, -1, true)));

        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.WOOD_SWORD)});

        ItemStack[] armorSet = ArmorSet.getArmorSet(ArmorSetType.LEATHER);
        for(ItemStack armorPiece : armorSet){
            UtilItem.ColorLeather(armorPiece, 67, 160, 71);
            armorPiece.addEnchantment(Enchantment.THORNS, 3);
        }

        setStartingArmor(armorSet);
    }

    public void giveItems(Player player){
        super.giveItems(player);
    }
}
