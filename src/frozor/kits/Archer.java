package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.util.UtilItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Archer extends PlayerKit {
    public Archer() {
        super("Archer", new String[] {"§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a64 §fARrows", "§3•§fChainmail Helmet", "§3•§fChainmail Chestplate", "§3•§fChainmail Leggings", "§3•§fChainmail Boots"}, new ItemStack(Material.BOW));

        ItemStack arrows = new ItemStack(Material.ARROW);
        arrows.setAmount(64);

        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.WOOD_SWORD), UtilItem.createUnbreakableItem(Material.BOW), arrows});
        setStartingArmor(ArmorSet.getArmorSet(ArmorSetType.CHAINMAIL));
    }
}
