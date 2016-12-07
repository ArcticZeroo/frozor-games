package frozor.kits;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Knight extends PlayerKit {
    public Knight() {
        super("Knight", new String[] {"§7Hits hard, moves slow.", "", "§fYou Will Receive:", "§3•§Stone Sword", "§3•§fIron Helmet", "§3•§fIron Chestplate", "§3•§fIron Leggings", "§3•§fIron Boots", "§3•§fPermanent §aSlowness 1"}, new ItemStack(Material.IRON_SWORD));

        addStartingItems(new ItemStack[]{new ItemStack(Material.STONE_SWORD)});
        setStartingArmor(ArmorSet.getArmorSet(ArmorSetType.IRON));
    }

    public void giveItems(Player player){
        giveStartingItems(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 0, true));
    }
}
