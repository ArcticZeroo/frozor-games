package frozor.kits;

import frozor.perk.KitPerk;
import frozor.perk.PerkType;
import frozor.util.UtilKit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerKit {
    protected String name;
    protected List<String> description;
    protected ItemStack displayItem;
    protected List<ItemStack> startingItems = new ArrayList<>();
    protected ItemStack[] startingArmor;
    protected HashMap<PerkType, KitPerk> kitPerks;

    PlayerKit(String name, List<String> description, ItemStack displayItem){
        this.name = name;
        this.description = description;
        this.displayItem = displayItem;
        this.kitPerks = new HashMap<>();
    }

    PlayerKit(String name, List<String> description, ItemStack displayItem, List<KitPerk> kitPerks){
        this.name = name;
        this.description = description;
        this.displayItem = displayItem;
        this.kitPerks = UtilKit.createPerkMap(kitPerks);
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

    public List<String> getDescription(){
        return description;
    }

    public ItemStack getDisplayItem(){
        return displayItem;
    }

    protected void giveStartingItems(Player player){
        ItemStack[] kitItems = new ItemStack[this.startingItems.size()];
        kitItems = this.startingItems.toArray(kitItems);

        for(ItemStack item : kitItems){
            player.getInventory().addItem(item);
        }

        if(startingArmor != null){
            player.getInventory().setArmorContents(startingArmor);
        }
    }

    public void giveItems(Player player){
        giveStartingItems(player);
    }

    public HashMap<PerkType, KitPerk> getKitPerks() {
        return kitPerks;
    }

    public boolean hasPerk(PerkType perkType){
        return kitPerks.containsKey(perkType);
    }

    public KitPerk getPerk(PerkType perkType){
        return kitPerks.get(perkType);
    }
}
