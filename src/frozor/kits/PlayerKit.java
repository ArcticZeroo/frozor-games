package frozor.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerKit {
    private String kitName;
    private String[] kitDescription;
    private ItemStack kitDisplayItem;

    PlayerKit(String name, String[] description, ItemStack kitDisplayItem){
        this.kitName = name;
        this.kitDescription = description;
        this.kitDisplayItem = kitDisplayItem;
    }

    public String getName(){
        return kitName;
    }

    public String[] getDescription(){
        return kitDescription;
    }

    public ItemStack getDisplayItem(){
        return kitDisplayItem;
    }

    public void giveItems(Player player){
        ItemStack[] defaultArmor = {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)};
        player.getInventory().setArmorContents(defaultArmor);

        ItemStack[] defaultItems = {new ItemStack(Material.WOOD_SWORD)};
        player.getInventory().setContents(defaultItems);
    }
}
