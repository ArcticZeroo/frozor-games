package frozor.component;

import frozor.arcade.Arcade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class FrozorScoreboard {
    private Arcade arcade;
    private Scoreboard scoreboard;
    private Objective objective;
    private ChatColor[] scoreNames = {ChatColor.AQUA, ChatColor.BLACK, ChatColor.BLUE, ChatColor.DARK_AQUA, ChatColor.DARK_GRAY, ChatColor.DARK_GREEN, ChatColor.DARK_PURPLE, ChatColor.DARK_RED, ChatColor.GOLD, ChatColor.GRAY, ChatColor.GREEN, ChatColor.LIGHT_PURPLE, ChatColor.RED, ChatColor.WHITE, ChatColor.YELLOW, ChatColor.BOLD};
    private Team[] teams = new Team[16];

    public FrozorScoreboard(Arcade arcade, String scoreboardName){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective(scoreboardName, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(scoreboardName);

        for(int i = -1; i >= teams.length*-1; i--){
            teams[i] = scoreboard.registerNewTeam("fsbTeam-"+i);

            String scoreName = scoreNames[i].toString();
            teams[i].addEntry(scoreName);
            objective.getScore(scoreName).setScore(i);
        }
    }

    public Objective getObjective() {
        return objective;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setLine(int index, String text){
        teams[index].setPrefix(text);
    }
}
