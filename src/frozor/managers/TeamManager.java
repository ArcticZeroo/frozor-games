package frozor.managers;

import frozor.arcade.Arcade;
import frozor.teams.PlayerTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TeamManager {
    private Arcade arcade;
    private HashMap<String, PlayerTeam> teams;

    public TeamManager(Arcade arcade, PlayerTeam[] teams){
        this.arcade = arcade;
        for(PlayerTeam team : teams){
            this.teams.put(team.getTeamName(), team);
        }
    }

    public HashMap<String, PlayerTeam> getTeams() {
        return teams;
    }

    public void assignTeams(){
        for(Player player : arcade.getPlugin().getServer().getOnlinePlayers()){
            PlayerTeam smallestTeam = null;

            for(PlayerTeam team : teams.values()){
               if(smallestTeam == null){
                   smallestTeam = team;
                   continue;
               }

               if(team.getScoreboardTeam().getSize() < smallestTeam.getScoreboardTeam().getSize()){
                   smallestTeam = team;
               }
            }

            smallestTeam.getScoreboardTeam().addEntry(player.getName());
        }
    }
}
