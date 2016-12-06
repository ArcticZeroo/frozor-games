package frozor.kits;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Knight extends PlayerKit {
    private ItemStack[] startingItems = {new ItemStack(Material.STONE_SWORD)};
    private ItemStack[] startingArmor = {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)};

    public Knight() {
        super("Knight", new String[] {"§7Hits hard, moves slow.", "", "§fYou Will Receive:", "§3•§Stone Sword", "§3•§fIron Helmet", "§3•§fIron Chestplate", "§3•§fIron Leggings", "§3•§fIron Boots", "§3•§fPermanent §aSlowness 2"}, new ItemStack(Material.IRON_SWORD));
    }

    public void giveItems(Player player){
        player.getInventory().setArmorContents(startingArmor);
        player.getInventory().setContents(startingItems);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 1, true));
    }
}
