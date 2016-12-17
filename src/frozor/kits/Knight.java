package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.perk.KitPerk;
import frozor.perk.PerkType;
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
import java.util.Collection;
import java.util.Collections;

public class Knight extends PlayerKit {
    public Knight() {
        super("Knight",
                new ArrayList<>(Arrays.asList(
                        ChatColor.GRAY + "Starts with more gear,",
                        ChatColor.GRAY + "but moves slowly.",
                        "",
                        ChatColor.WHITE + "You Will Receive:",
                        UtilChat.getKitEquipmentString("Stone Sword"),
                        UtilChat.getKitEquipmentString("Iron Helmet"),
                        UtilChat.getKitEquipmentString("Iron Chestplate"),
                        UtilChat.getKitEquipmentString("Iron Leggings"),
                        UtilChat.getKitEquipmentString("Iron Boots"),
                        UtilChat.getKitEquipmentString(ChatColor.GREEN + "+1 " + ChatColor.WHITE + "Damage with swords"),
                        UtilChat.getKitNegativeString("Permanent Slowness 1"))),
                new ItemStack(Material.IRON_SWORD),
                Collections.singletonList(new KitPerk(PerkType.SWORD_DAMAGE, 1, true)));

        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.STONE_SWORD)});
        setStartingArmor(ArmorSet.getArmorSet(ArmorSetType.IRON));
    }

    public void giveItems(Player player){
        super.giveItems(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 0, true));
    }
}
