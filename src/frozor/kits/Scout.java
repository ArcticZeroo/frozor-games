package frozor.kits;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Scout extends PlayerKit {
    private ItemStack[] startingItems = {new ItemStack(Material.WOOD_SWORD)};
    private ItemStack[] startingArmor = {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)};

    public Scout() {
        super("Scout", new String[] {"§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a2 §fSpeed 2 Potion", "§3•§fLeather Helmet", "§3•§fLeather Chestplate", "§3•§fLeather Leggings", "§3•§fLeather Boots"}, new ItemStack(Material.SUGAR));
    }

    public void giveItems(Player player){
        player.getInventory().setArmorContents(startingArmor);
        player.getInventory().setContents(startingItems);
    }
}
