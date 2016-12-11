package frozor.kits;


import frozor.util.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FireMage extends PlayerKit {
    public FireMage() {
        super("FireMage", new String[] {"§7Light the enemy ablaze!", "", "§fYou Will Receive:", "§3•§Wood Sword with §aFire Aspect I", "§3•§fLeather Helmet", "§3•§fLeather Chestplate", "§3•§fLeather Leggings", "§3•§fLeather Boots"}, new ItemStack(Material.BLAZE_ROD));

        ItemStack flameStaff = new ItemStack(Material.WOOD_SWORD);

        flameStaff.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        flameStaff.addEnchantment(Enchantment.KNOCKBACK, 1);

        flameStaff.getItemMeta().setDisplayName(ChatColor.RED + (ChatColor.BOLD + "Flame Staff"));

        addStartingItems(new ItemStack[]{flameStaff});

        ItemStack[] armorSet = ArmorSet.getArmorSet(ArmorSetType.LEATHER);
        for(ItemStack armorPiece : armorSet){
            UtilItem.ColorLeather(armorPiece, 255, 64, 0);
        }

        setStartingArmor(armorSet);
    }
}
