package frozor.kits;


import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.util.UtilItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Builder extends PlayerKit {
    public Builder() {
        super("Builder", new String[] {"§7Run at the speed of light!", "", "§fYou Will Receive:", "§3•§Wood Sword", "§3•§a64 §fARrows", "§3•§fChainmail Helmet", "§3•§fChainmail Chestplate", "§3•§fChainmail Leggings", "§3•§fChainmail Boots"}, new ItemStack(Material.DIAMOND_PICKAXE));

        addStartingItems(new ItemStack[]{UtilItem.createUnbreakableItem(Material.IRON_PICKAXE), UtilItem.createUnbreakableItem(Material.IRON_AXE), new ItemStack(Material.BRICK, 64)});
        setStartingArmor(ArmorSet.getArmorSet(ArmorSetType.CHAINMAIL));
    }

    public void giveItems(Player player){
        super.giveItems(player);

    }
}
