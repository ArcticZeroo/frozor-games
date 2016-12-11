package frozor.kits;


import frozor.util.UtilItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Scout extends PlayerKit {
    public Scout() {
        super("Scout", new String[] {"§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a2 §fSpeed 2 Potion", "§3•§fLeather Helmet", "§3•§fLeather Chestplate", "§3•§fLeather Leggings", "§3•§fLeather Boots", "§3•§fPermanent §aWeakness 1"}, new ItemStack(Material.SUGAR));

        addStartingItems(new ItemStack[]{new ItemStack(Material.WOOD_SWORD), new ItemStack(Material.ENDER_PEARL, 2)});

        ItemStack[] armorSet = ArmorSet.getArmorSet(ArmorSetType.LEATHER);
        for(ItemStack armorPiece : armorSet){
            UtilItem.ColorLeather(armorPiece, 66, 193, 242);
        }

        setStartingArmor(armorSet);
    }

    public void giveItems(Player player){
        giveStartingItems(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 999999999, 0, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 0, true));
    }
}
