package frozor.commands;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.kits.PlayerKit;
import frozor.managers.KitManager;
import frozor.util.UtilChat;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KitCommands implements CommandExecutor{
    private Arcade arcade;

    public KitCommands(Arcade arcade){
        this.arcade = arcade;
    }


    private boolean handleFailedCommand(Player player, String message){
        player.sendMessage(message);
        return false;
    }

    private boolean sendKitDoesNotExist(Player player){
        player.sendMessage(arcade.getKitManager().getNotificationManager().getError("That kit doesn't exist!"));
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        switch(command.getName().toLowerCase()){
            case "kit":
                KitManager kitManager = arcade.getKitManager();

                if(args.length < 1){
                    if(kitManager.getSelectedKits().containsKey(player.getUniqueId())){
                        player.sendMessage(kitManager.getNotificationManager().getMessage("Your equipped kit is " + ChatColor.YELLOW + kitManager.getSelectedKits().get(player.getUniqueId()).getName()));
                    }else{
                        player.sendMessage(kitManager.getNotificationManager().getMessage("You don't have a kit equipped!"));
                    }
                }else{
                    switch(args[0].toLowerCase()){
                        case "view":
                            if(args.length < 2){
                                arcade.getKitManager().sendKitList(player);
                            }else{
                                String kitName = args[1].toLowerCase();
                                if(kitManager.getKits().containsKey(kitName)){
                                    arcade.getKitManager().sendKitInventory(player, kitManager.getKits().get(kitName));
                                }else{
                                    sendKitDoesNotExist(player);
                                }
                            }

                            break;
                        case "list":
                            arcade.getKitManager().sendKitList(player);
                            break;
                        default:
                            String kitName = args[0].toLowerCase();
                            if(kitManager.getKits().containsKey(kitName)){
                                kitManager.changeKit(player, kitName);
                            }else{
                                sendKitDoesNotExist(player);
                            }
                    }
                }

                break;
        }

        return true;
    }
}
