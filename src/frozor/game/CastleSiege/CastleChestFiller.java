package frozor.game.CastleSiege;

import com.sun.org.apache.xpath.internal.operations.Bool;
import frozor.component.DatapointParser;
import frozor.teams.PlayerTeam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CastleChestFiller {
    private CastleSiege castleSiege;
    private Random random = new Random();

    public CastleChestFiller(CastleSiege castleSiege){
        this.castleSiege = castleSiege;
    }

    private ItemStack getRandomItemFromList(List<ItemStack> itemList){
        int index = random.nextInt(itemList.size());
        return itemList.get(index);
    }

    private ItemStack getRandomCategoryItem(CastleChestLootType lootType){
        List<ItemStack> lootList = new ArrayList<>();
        switch (lootType){
            case MATERIALS:
                lootList.add(new ItemStack(Material.SMOOTH_BRICK, random.nextInt(32)+32));
                lootList.add(new ItemStack(Material.SMOOTH_BRICK, random.nextInt(16)+24));
                lootList.add(new ItemStack(Material.BRICK, random.nextInt(32)+32));
                lootList.add(new ItemStack(Material.BRICK, random.nextInt(16)+24));
                lootList.add(new ItemStack(Material.COBBLESTONE, random.nextInt(32)+32));
                lootList.add(new ItemStack(Material.COBBLESTONE, random.nextInt(16)+24));
                lootList.add(new ItemStack(Material.WOOD, random.nextInt(32)+32));
                lootList.add(new ItemStack(Material.WOOD, random.nextInt(16)+24));
                lootList.add(new ItemStack(Material.LOG, random.nextInt(12)+4));
                lootList.add(new ItemStack(Material.LOG, random.nextInt(8)+2));
                lootList.add(new ItemStack(Material.IRON_INGOT, random.nextInt(6)+2));
                lootList.add(new ItemStack(Material.DIAMOND, random.nextInt(3)+1));
                break;
            case FOOD:
                lootList.add(new ItemStack(Material.COOKED_BEEF, random.nextInt(7)+10));
                lootList.add(new ItemStack(Material.COOKED_CHICKEN, random.nextInt(7)+10));
                lootList.add(new ItemStack(Material.COOKED_FISH, random.nextInt(7)+10));
                lootList.add(new ItemStack(Material.COOKED_MUTTON, random.nextInt(7)+10));
                lootList.add(new ItemStack(Material.COOKED_BEEF, random.nextInt(10)+16));
                lootList.add(new ItemStack(Material.COOKED_CHICKEN, random.nextInt(10)+16));
                lootList.add(new ItemStack(Material.COOKED_FISH, random.nextInt(10)+16));
                lootList.add(new ItemStack(Material.COOKED_MUTTON, random.nextInt(10)+16));
                lootList.add(new ItemStack(Material.BAKED_POTATO, random.nextInt(17)+16));
                lootList.add(new ItemStack(Material.BAKED_POTATO, random.nextInt(25)+16));
                lootList.add(new ItemStack(Material.CARROT, random.nextInt(17)+16));
                lootList.add(new ItemStack(Material.CARROT, random.nextInt(25)+16));
                lootList.add(new ItemStack(Material.GOLDEN_CARROT, random.nextInt(3)+1));
                break;
            case TOOLS:
                lootList.add(new ItemStack(Material.IRON_PICKAXE));
                lootList.add(new ItemStack(Material.IRON_PICKAXE));
                lootList.add(new ItemStack(Material.IRON_PICKAXE));
                lootList.add(new ItemStack(Material.IRON_AXE));
                lootList.add(new ItemStack(Material.IRON_AXE));
                lootList.add(new ItemStack(Material.IRON_AXE));
                lootList.add(new ItemStack(Material.IRON_SPADE));
                lootList.add(new ItemStack(Material.IRON_SPADE));
                lootList.add(new ItemStack(Material.IRON_SPADE));
                lootList.add(new ItemStack(Material.DIAMOND_SPADE));
                lootList.add(new ItemStack(Material.DIAMOND_AXE));
                lootList.add(new ItemStack(Material.DIAMOND_PICKAXE));
                break;
            case AMMO:
                lootList.add(new ItemStack(Material.TNT, random.nextInt(8)+8));
                lootList.add(new ItemStack(Material.TNT, random.nextInt(16)+8));
                lootList.add(new ItemStack(Material.TNT, random.nextInt(24)+8));
                lootList.add(new ItemStack(Material.FLINT_AND_STEEL));
                lootList.add(new ItemStack(Material.FLINT_AND_STEEL));
                lootList.add(new ItemStack(Material.ARROW, random.nextInt(17)+16));
                lootList.add(new ItemStack(Material.EXP_BOTTLE, random.nextInt(16)+8));
                break;
            case WEAPONS:
                lootList.add(new ItemStack(Material.STONE_SWORD));
                lootList.add(new ItemStack(Material.STONE_SWORD));
                lootList.add(new ItemStack(Material.IRON_SWORD));
                lootList.add(new ItemStack(Material.IRON_SWORD));
                lootList.add(new ItemStack(Material.IRON_SWORD));
                lootList.add(new ItemStack(Material.IRON_SWORD));
                lootList.add(new ItemStack(Material.DIAMOND_SWORD));
                lootList.add(new ItemStack(Material.BOW));
                lootList.add(new ItemStack(Material.BOW));
                break;
            case ARMOR:
                lootList.add(new ItemStack(Material.CHAINMAIL_HELMET));
                lootList.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                lootList.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                lootList.add(new ItemStack(Material.CHAINMAIL_BOOTS));
                lootList.add(new ItemStack(Material.CHAINMAIL_HELMET));
                lootList.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                lootList.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                lootList.add(new ItemStack(Material.CHAINMAIL_BOOTS));
                lootList.add(new ItemStack(Material.IRON_HELMET));
                lootList.add(new ItemStack(Material.IRON_CHESTPLATE));
                lootList.add(new ItemStack(Material.IRON_LEGGINGS));
                lootList.add(new ItemStack(Material.IRON_BOOTS));
                lootList.add(new ItemStack(Material.IRON_HELMET));
                lootList.add(new ItemStack(Material.IRON_CHESTPLATE));
                lootList.add(new ItemStack(Material.IRON_LEGGINGS));
                lootList.add(new ItemStack(Material.IRON_BOOTS));
                lootList.add(new ItemStack(Material.DIAMOND_HELMET));
                lootList.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
                lootList.add(new ItemStack(Material.DIAMOND_LEGGINGS));
                lootList.add(new ItemStack(Material.DIAMOND_BOOTS));
                break;
        }

        return getRandomItemFromList(lootList);
    }

    private ItemStack getRandomChestItem(){
        int categoryStartWeight = 101;
        double RandomCategoryDouble = random.nextInt(categoryStartWeight);

        categoryStartWeight--;
        if(RandomCategoryDouble == categoryStartWeight) return new ItemStack(Material.GOLDEN_APPLE);

        if(RandomCategoryDouble <= (double) categoryStartWeight*0.25){
            return getRandomCategoryItem(CastleChestLootType.FOOD);
        }

        if(RandomCategoryDouble <= (double) categoryStartWeight*0.45){
            return getRandomCategoryItem(CastleChestLootType.MATERIALS);
        }

        if(RandomCategoryDouble <= (double) categoryStartWeight*0.60){
            return getRandomCategoryItem(CastleChestLootType.TOOLS);
        }

        if(RandomCategoryDouble <= (double) categoryStartWeight*0.75){
            return getRandomCategoryItem(CastleChestLootType.AMMO);
        }

        if(RandomCategoryDouble <= (double) categoryStartWeight*0.85){
            return getRandomCategoryItem(CastleChestLootType.WEAPONS);
        }

        return getRandomCategoryItem(CastleChestLootType.ARMOR);
    }

    private ItemStack[] getRandomAirSlots(int bound){
        int AirCount = random.nextInt(bound);

        ItemStack[] air = new ItemStack[AirCount];
        for(int i = 0; i < AirCount; i++){
            air[i] = new ItemStack(Material.AIR);
        }

        return air;
    }


    private ItemStack[] getRandomChestLoot(){
        int LootCount = random.nextInt(6)+5;
        List<ItemStack> chestLoot = new ArrayList<>();

        int maxAir = (int) Math.floor((27 - LootCount) / LootCount);

        for(int i = 0; i < LootCount; i++){
            chestLoot.add(getRandomChestItem());

            ItemStack[] airSlots = getRandomAirSlots(maxAir+1);
            Collections.addAll(chestLoot, airSlots);
        }

        ItemStack[] finalLoot = new ItemStack[chestLoot.size()];
        finalLoot = chestLoot.toArray(finalLoot);
        return finalLoot;
    }

    public void fillTeamChests(String teamName){
        List<String> configChestStrings = castleSiege.getMapConfig().getStringList("chests." + teamName);

        if(configChestStrings == null){
            castleSiege.getArcade().getDebugManager().print("Unable to fill chests for team  " + teamName + " because their locations are invalid.");
            return;
        }

        for(String configChestString : configChestStrings){
            Location chestLocation = DatapointParser.parse(configChestString);

            Chest chest = (Chest) chestLocation.getBlock().getState();

            chest.getInventory().setContents(getRandomChestLoot());
        }
    }

    public void fillChests(){
        for(PlayerTeam team : castleSiege.getArcade().getTeamManager().getTeams().values()){
            fillTeamChests(team.getTeamName());
        }
    }
}
