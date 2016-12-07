package frozor.managers;

import frozor.arcade.Arcade;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TeamManager {
    private Arcade arcade;
    private HashMap<String, Team> teams;

    public TeamManager(Arcade arcade, HashMap<String, ChatColor> teams){
        this.arcade = arcade;
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    public void assignTeams(){
        for(Player player : arcade.getPlugin().getServer().getOnlinePlayers()){
            Team smallestTeam = null;
            for(Team team : teams.values()){
               if(smallestTeam == null){
                   smallestTeam = team;
                   continue;
               }

               if(team.getSize() < smallestTeam.getSize()){
                   smallestTeam = team;
               }
            }

            smallestTeam.addEntry(player.getName());
        }
    }
}
