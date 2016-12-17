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

public class Medic extends PlayerKit {
    public Medic() {
        super("Medic",
                new ArrayList<>(Arrays.asList(
                        ChatColor.GRAY + "Heroes never die!",
                        "",
                        ChatColor.WHITE + "You Will Receive:",
                        UtilChat.getKitEquipmentString("Wood Sword"),
                        UtilChat.getKitItemString("Healing II Splash Potion", 2),
                        UtilChat.getKitItemString("Regen I Splash Potion", 2),
                        UtilChat.getKitEquipmentString("Pink Leather Helmet"),
                        UtilChat.getKitEquipmentString("Pink Leather Chestplate"),
                        UtilChat.getKitEquipmentString("Pink Leather Leggings"),
                        UtilChat.getKitEquipmentString("Pink Leather Boots"),
                        UtilChat.getKitEquipmentString("Permanent Regeneration I"))),
                new ItemStack(Material.POTION, 1, (short) 16421));

        addStartingItems(new ItemStack[]{
                UtilItem.createUnbreakableItem(Material.WOOD_SWORD),
                //Healing II
                UtilItem.setSoulbound(new ItemStack(Material.POTION, 2, (short) 16421)),
                //Regen I
                UtilItem.setSoulbound(new ItemStack(Material.POTION, 2, (short) 16421))
        });

        ItemStack[] armorSet = ArmorSet.getArmorSet(ArmorSetType.LEATHER);
        for(ItemStack armorPiece : armorSet){
            UtilItem.ColorLeather(armorPiece, 245, 191, 209);
        }

        setStartingArmor(armorSet);
    }

    public void giveItems(Player player){
        super.giveItems(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999999, 0, true));
    }
}
