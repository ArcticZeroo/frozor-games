package frozor.commands;

import frozor.arcade.Arcade;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
    private Arcade arcade;

    public Commands(Arcade arcade){
        this.arcade = arcade;
    }

    private boolean handleFailedCommand(Player player, String message){
        player.sendMessage(message);
        return false;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        switch(command.getName().toLowerCase()){
            case "game":
                if(args.length < 2) return false;
                String subCommand = args[1];

                switch(subCommand.toLowerCase()){
                    case "start":
                        if(args.length < 3) return false;
                        try{
                            int newTime = Integer.parseInt(args[2]);

                            return true;
                        }catch(Exception e){
                            return false;
                        }
                }

                break;

            case "kit":
                if(args.length < 2) return handleFailedCommand(player, arcade.getKitManager().getNotificationManager().getError("You did not specify a kit!"));



                break;

        }

        return false;
    }
}
