package frozor.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerKit {
    protected String name;
    protected String[] description;
    protected ItemStack displayItem;
    protected ItemStack[] startingItems = {};
    protected ItemStack[] startingArmor = {};

    PlayerKit(String name, String[] description, ItemStack displayItem){
        this.name = name;
        this.description = description;
        this.displayItem = displayItem;
    }

    protected void addStartingItem(ItemStack item){
        startingItems[startingItems.length] = item;
    }

    protected void addStartingItems(ItemStack[] items){
        for(ItemStack item : items){
            addStartingItem(item);
        }
    }

    protected void addStartingArmor(ItemStack item){
        startingArmor[startingArmor.length] = item;
    }

    public String getName(){
        return name;
    }

    public String[] getDescription(){
        return description;
    }

    public ItemStack getDisplayItem(){
        return displayItem;
    }

    protected void giveStartingItems(Player player){
        player.getInventory().setArmorContents(startingItems);
        player.getInventory().setContents(startingArmor);
    }

    public void giveItems(Player player){
        giveStartingItems(player);
    }
}
