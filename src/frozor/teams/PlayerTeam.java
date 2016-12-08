package frozor.teams;

import groovyjarjarcommonscli.Option;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTeam {
    protected String teamName;
    protected ChatColor teamColor;
    protected Team scoreboardTeam;

    public PlayerTeam(String displayName, ChatColor teamColor){
        this.teamName = displayName;
        this.teamColor = teamColor;
    }

    public String getTeamName() {
        return teamName;
    }

    public ChatColor getTeamColor() {
        return teamColor;
    }

    public void register(Scoreboard scoreboard){
        scoreboardTeam = scoreboard.registerNewTeam(teamName);
        scoreboardTeam.setPrefix(ChatColor.BLUE.toString());
    }

    public Team getScoreboardTeam() {
        return scoreboardTeam;
    }
}
