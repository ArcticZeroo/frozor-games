package frozor.commands;

import frozor.arcade.Arcade;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
    public Commands(Arcade arcade){

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return false;

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

        }

        return false;
    }
}
