package frozor.commands;

import frozor.arcade.Arcade;
import frozor.kits.PlayerKit;
import frozor.managers.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        arcade.getDebugManager().print("Kit command!");
        arcade.getDebugManager().print(label);

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
                    String kitName = args[0].toLowerCase();
                    if(kitManager.getKits().containsKey(kitName)){
                        PlayerKit newKit = kitManager.getKits().get(kitName);
                        kitManager.selectKit(player.getUniqueId(), newKit);
                        player.sendMessage(kitManager.getNotificationManager().getMessage(String.format("You equipped %s kit.", ChatColor.YELLOW + newKit.getName() + ChatColor.GRAY)));
                    }else{
                        player.sendMessage(kitManager.getNotificationManager().getError("That kit doesn't exist!"));
                    }
                }

                break;
        }

        return true;
    }
}
