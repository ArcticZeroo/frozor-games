package frozor.commands;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.managers.NotificationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommands implements CommandExecutor{
    private Arcade arcade;
    private NotificationManager notificationManager = new NotificationManager("Game");

    public GameCommands(Arcade arcade){
        this.arcade = arcade;
    }

    private boolean handleFailedCommand(Player player, String message){
        player.sendMessage(notificationManager.getError(message));
        return true;
    }

    private boolean handleNotOp(Player player){
        return handleFailedCommand(player, "You do not have permission to run this command.");
    }

    private boolean sendGameStateMessage(Player player, String input){
        player.sendMessage(notificationManager.getMessage("Game state has been updated to " + ChatColor.YELLOW + input));
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(!player.isOp()) return handleNotOp(player);

        switch(command.getName().toLowerCase()){
            case "game":
                if(args.length < 1) return handleFailedCommand(player, "You must specify a sub-command.");
                String subCommand = args[0];

                switch(subCommand.toLowerCase()){
                    case "start":
                        if(arcade.getGameState().compareTo(GameState.START) >= 0) return handleFailedCommand(player, "The game has already started.");

                        if(args.length < 2) return handleFailedCommand(player, "You must specify a time to set the timer to.");
                        try{
                            int newTime = Integer.parseInt(args[1]);

                            arcade.getWaitingLobbyManager().getWaitingTimer().setTime(newTime);
                            arcade.getGame().getServer().broadcastMessage(ChatColor.AQUA + (ChatColor.BOLD + player.getName() + " started the game."));

                            if(!arcade.getWaitingLobbyManager().getWaitingTimer().isActive()){
                                arcade.getWaitingLobbyManager().getWaitingTimer().startTimer();
                            }

                            return true;
                        }catch(Exception e){
                            return handleFailedCommand(player, "Invalid input.");
                        }
                    case "state":
                        if(args.length < 2){
                            player.sendMessage(notificationManager.getMessage("The current Game State is " + ChatColor.YELLOW + arcade.getGameState().toString()));
                            return true;
                        }
                        String state = args[1].toUpperCase();

                        switch(state){
                            case "LOBBY":
                                arcade.setGameState(GameState.LOBBY);
                                break;
                            case "TIMER":
                                arcade.setGameState(GameState.TIMER);
                                break;
                            case "START":
                                arcade.setGameState(GameState.START);
                                break;
                            case "PLAYING":
                                arcade.setGameState(GameState.PLAYING);
                                break;
                            case "END":
                                arcade.setGameState(GameState.END);
                                break;
                            default:
                                //Tells them the GameState doesn't exist and returns if their input is invalid
                                return handleFailedCommand(player, "That GameState doesn't exist!");
                        }

                        //If it didn't return, that means the GameState has to exist!
                        return sendGameStateMessage(player, state);
                    default:
                        return handleFailedCommand(player, "Invalid sub-command.");
                }
        }

        return handleFailedCommand(player, "An unexpected error occurred.");
    }
}
