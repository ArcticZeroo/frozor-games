package frozor.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerKit {
    protected String name;
    protected String[] description;
    protected ItemStack displayItem;
    protected List<ItemStack> startingItems = new ArrayList<>();
    protected ItemStack[] startingArmor;

    PlayerKit(String name, String[] description, ItemStack displayItem){
        this.name = name;
        this.description = description;
        this.displayItem = displayItem;
    }

    public void addStartingItem(ItemStack item){
        startingItems.add(item);
    }

    public void addStartingItems(ItemStack[] items){
        for(ItemStack item : items){
            addStartingItem(item);
        }
    }

    public void setStartingArmor(ItemStack[] items){
        startingArmor = items;
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
        for(ItemStack item : startingItems){
            player.getInventory().addItem(item);
        }

        if(startingArmor != null){
            player.getInventory().setArmorContents(startingArmor);
        }
    }

    public void giveItems(Player player){
        giveStartingItems(player);
    }
}
