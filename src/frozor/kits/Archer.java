package frozor.kits;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Archer extends PlayerKit {
    private ItemStack[] startingItems = {new ItemStack(Material.WOOD_SWORD), new ItemStack(Material.BOW)};
    private ItemStack[] startingArmor = {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_CHESTPLATE
    ), new ItemStack(Material.CHAINMAIL_BOOTS)};

    public Archer() {
        super("Archer", new String[] {"§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a64 §fARrows", "§3•§fChainmail Helmet", "§3•§fChainmail Chestplate", "§3•§fChainmail Leggings", "§3•§fChainmail Boots"}, new ItemStack(Material.BOW));
    }

    public void giveItems(Player player){
        ItemStack arrows = new ItemStack(Material.ARROW);
        arrows.setAmount(64);

        startingItems[startingItems.length] = arrows;

        player.getInventory().setArmorContents(startingArmor);
        player.getInventory().setContents(startingItems);
    }
}
