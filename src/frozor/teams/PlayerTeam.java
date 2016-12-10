package frozor.teams;

import groovyjarjarcommonscli.Option;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTeam {
    private String teamName;
    private ChatColor teamColor;
    private Team scoreboardTeam;
    private Location[] teamSpawns;

    public PlayerTeam(String displayName, ChatColor teamColor){
        this.teamName = displayName;
        this.teamColor = teamColor;
    }

    public void setTeamSpawns(Location[] teamSpawns) {
        this.teamSpawns = teamSpawns;
    }

    public Location[] getTeamSpawns() {
        return teamSpawns;
    }

    public String getTeamName() {
        return teamName;
    }

    public ChatColor getTeamColor() {
        return teamColor;
    }

    public void register(Scoreboard scoreboard){
        scoreboardTeam = scoreboard.registerNewTeam(teamName);
        scoreboardTeam.setPrefix(getTeamColor().toString());
    }

    public Team getScoreboardTeam() {
        return scoreboardTeam;
    }
}
