package frozor.kits;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mage extends PlayerKit {
    public Mage() {
        super("Mage", new String[] {"§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a64 §fARrows", "§3•§fChainmail Helmet", "§3•§fChainmail Chestplate", "§3•§fChainmail Leggings", "§3•§fChainmail Boots"}, new ItemStack(Material.BOW));

        ItemStack flameStaff = new ItemStack(Material.BLAZE_ROD);

        flameStaff.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        flameStaff.addEnchantment(Enchantment.KNOCKBACK, 1);

        flameStaff.getItemMeta().setDisplayName(ChatColor.RED + (ChatColor.BOLD + "Flame Staff"));

        addStartingItems(new ItemStack[]{flameStaff});
    }
}
