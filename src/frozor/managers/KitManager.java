package frozor.managers;

import a.j.m.P;
import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.kits.PlayerKit;
import frozor.util.UtilChat;
import frozor.util.UtilItem;
import frozor.util.UtilPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KitManager implements Listener{
    private Arcade arcade;
    //private PlayerKit[] kits;
    private HashMap<String, PlayerKit> kits = new HashMap<>();
    private PlayerKit defaultKit;
    private HashMap<UUID, PlayerKit> selectedKits = new HashMap<>();
    private NotificationManager notificationManager = new NotificationManager("Kit");
    private String inventoryPrefix = "Kit Information - ";

    public KitManager(Arcade arcade, PlayerKit[] kits){
        this.arcade = arcade;

        this.arcade.RegisterEvents(this);

        defaultKit = kits[0];

        for(PlayerKit kit : kits){
            addKit(kit);
        }
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void addKit(PlayerKit kit){
        kits.put(kit.getName().toLowerCase(), kit);
    }

    public void setDefaultKit(PlayerKit defaultKit){
        if(kits.containsValue(defaultKit)){
            this.defaultKit = defaultKit;
        }
    }

    public void selectKit(UUID uuid, PlayerKit kit){
        selectedKits.put(uuid, kit);
    }

    public void changeKit(Player player, PlayerKit kit){
        selectKit(player.getUniqueId(), kit);

        if(arcade.getGameState().compareTo(GameState.START) < 0){
            //If the game has not started yet
            player.sendMessage(getNotificationManager().getMessage(String.format("You equipped %s kit.", ChatColor.YELLOW + kit.getName() + ChatColor.GRAY)));
        }else{
            player.sendMessage(getNotificationManager().getMessage(String.format("You equipped %s kit. It will be equipped when you respawn.", ChatColor.YELLOW + kit.getName() + ChatColor.GRAY)));
        }
    }

    public void changeKit(Player player, String kitName){
        if(!kits.containsKey(kitName)) return;
        changeKit(player, kits.get(kitName));
    }

    public void giveKit(UUID uuid){
        if(selectedKits.containsKey(uuid)){
            PlayerKit selectedKit = selectedKits.get(uuid);
            Player player = Bukkit.getPlayer(uuid);

            UtilPlayer.cleanPlayer(player);
            selectedKit.giveItems(player);
        }
    }

    public void giveKit(Player player){
        if(selectedKits.containsKey(player.getUniqueId())){
            PlayerKit selectedKit = selectedKits.get(player.getUniqueId());

            UtilPlayer.cleanPlayer(player);
            selectedKit.giveItems(player);
        }
    }

    public void assignPlayerKits(){
        arcade.getDebugManager().print("Assigning player kits...");
        for(UUID uuid : selectedKits.keySet()){
            giveKit(uuid);
        }
    }

    public HashMap<UUID, PlayerKit> getSelectedKits() {
        return selectedKits;
    }

    public HashMap<String, PlayerKit> getKits() {
        return kits;
    }

    public void handlePlayerJoin(Player player){
        if(defaultKit != null){
            selectKit(player.getUniqueId(), defaultKit);
            player.sendMessage(notificationManager.getMessage("Equipped default kit " + ChatColor.YELLOW + defaultKit.getName() + ChatColor.GRAY + "."));
        }
    }

    public boolean sendKitList(Player player){
        player.sendMessage(arcade.getKitManager().getNotificationManager().getMessage("Available Kits: "));

        for(PlayerKit kit : arcade.getKitManager().getKits().values()){
            TextComponent kitMessage = new TextComponent(arcade.getKitManager().getNotificationManager().getPrefixColor() + "> ");
            TextComponent viewComponent = UtilChat.createHoverCommandComponent(ChatColor.AQUA + "[View] ", ChatColor.AQUA + "Click to view details", "/kit view " + kit.getName().toLowerCase());
            TextComponent selectComponent = UtilChat.createHoverCommandComponent(ChatColor.GREEN + "[Select] ", ChatColor.GREEN + "Click to select " + kit.getName(), "/kit " + kit.getName().toLowerCase());

            kitMessage.addExtra(viewComponent);
            kitMessage.addExtra(selectComponent);
            kitMessage.addExtra(ChatColor.GRAY + kit.getName());
            UtilChat.SendComponent(player, kitMessage);
        }

        return true;
    }

    public boolean sendKitInventory(Player player, PlayerKit kit){
        //Create the inventory and its contents
        Inventory kitInventory = arcade.getPlugin().getServer().createInventory(null, InventoryType.HOPPER, inventoryPrefix + kit.getName());
        List<ItemStack> kitInventoryContents = new ArrayList<>();

        //Create the cancel viewing buttons
        ItemStack cancelSelectKit = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta cancelSelectKitMeta = cancelSelectKit.getItemMeta();
        cancelSelectKitMeta.setDisplayName(ChatColor.RED + ("Close Kit Information"));
        cancelSelectKit.setItemMeta(cancelSelectKitMeta);

        for(int i = 0; i < 2; i++){
            kitInventoryContents.add(cancelSelectKit);
        }

        //Create Kit's Display Item
        ItemStack kitItem = kit.getDisplayItem();
        ItemMeta itemMeta = kitItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + (ChatColor.BOLD + kit.getName()));

        List<String> kitDescription = kit.getDescription();

        //Enchant the display item and add to lore if the kit is selected
        if(getSelectedKit(player) == kit){
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            kitDescription.add("");
            kitDescription.add(ChatColor.GREEN + "Selected");
        }

        itemMeta.setLore(new ArrayList<String>());
        itemMeta.setLore(kitDescription);

        kitItem.setItemMeta(itemMeta);

        //Add the display item to the inventory
        kitInventoryContents.add(kitItem);

        //Create the select buttons
        ItemStack selectKit = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta selectKitMeta = selectKit.getItemMeta();
        selectKitMeta.setDisplayName(ChatColor.GREEN + ("Select Kit " + kit.getName()));
        selectKit.setItemMeta(selectKitMeta);

        //Add the select buttons to the inventory
        for(int i = 0; i < 2; i++){
            kitInventoryContents.add(selectKit);
        }

        //Initialize the inventory contents as an ItemStack[]
        //Set the inventory's contents
        ItemStack[] inventoryContents = new ItemStack[5];
        kitInventory.setContents(kitInventoryContents.toArray(inventoryContents));

        arcade.getDebugManager().print("Opening an inventory");
        arcade.getDebugManager().print(kitInventory.getType().toString());

        //Open the inventory
        player.openInventory(kitInventory);

        arcade.getDebugManager().print("Opened the inventory");

        //Return true so I can use this to return in a command
        return true;
    }

    public PlayerKit getSelectedKit(Player player){
        if(!selectedKits.containsKey(player.getUniqueId())) return null;

        return selectedKits.get(player.getUniqueId());
    }

    @EventHandler
    public void onKitMenuItemClick(InventoryClickEvent event){
        if(event.getInventory().getSize() == 5 && event.getInventory().getType() == InventoryType.HOPPER && event.getInventory().getName().startsWith(inventoryPrefix)){
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            if(event.getSlot() < 2) {
                player.closeInventory();
            }else if(event.getSlot() > 2){
                player.closeInventory();

                String kitName = ChatColor.stripColor(event.getInventory().getItem(2).getItemMeta().getDisplayName()).toLowerCase();

                changeKit(player, kitName);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        //Only handling events in waiting lobby, so that things don't break.
        if(arcade.getGameState() != GameState.TIMER && arcade.getGameState() != GameState.LOBBY ) return;

        selectedKits.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.START){
            assignPlayerKits();
        }
    }
}
