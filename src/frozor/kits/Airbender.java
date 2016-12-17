package frozor.kits;


import frozor.perk.KitPerk;
import frozor.perk.PerkType;
import frozor.util.UtilChat;
import frozor.util.UtilItem;
import frozor.util.UtilKit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Airbender extends PlayerKit {
    public Airbender() {
        super("Airbender",
                new ArrayList<>(Arrays.asList(
                        ChatColor.GRAY + "Fly through the skies!",
                        "",
                        ChatColor.WHITE + "You Will Receive:",
                        UtilChat.getKitEquipmentString("Air Staff"),
                        UtilChat.getKitEquipmentString("Gold Boots"),
                        UtilChat.getKitItemString("Fall Damage", 0))),
                new ItemStack(Material.STICK),
                Collections.singletonList(new KitPerk(PerkType.FALL_RESISTANCE, 0))
                );

        ItemStack airStaff = new ItemStack(Material.GOLD_SWORD);

        ItemMeta airStaffItemMeta = airStaff.getItemMeta();
        airStaffItemMeta.setDisplayName(ChatColor.WHITE + "Air Staff");
        airStaff.setItemMeta(airStaffItemMeta);

        addStartingItems(new ItemStack[]{UtilItem.setSoulbound(airStaff)});

        ItemStack[] armorSet = {UtilItem.createUnbreakableItem(Material.GOLD_BOOTS)};

        setStartingArmor(armorSet);
    }
}
