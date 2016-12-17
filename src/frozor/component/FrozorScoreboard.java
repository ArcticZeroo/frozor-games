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
    }

    public Objective getObjective() {
        return objective;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    private String getScoreName(int i){
        return scoreNames[i].toString();
    }

    private void createTeam(int i){
        teams[i] = scoreboard.registerNewTeam("fsbTeam-"+i);

        String scoreName = getScoreName(i);
        teams[i].addEntry(scoreName);
        objective.getScore(scoreName).setScore(-1-i);
    }

    public void setBlankLine(int index){
        setLine(index, "");
    }

    public void setLine(int index, String text){
        if(teams[index] == null){
            createTeam(index);
        }

        teams[index].setPrefix(text);
    }

    public void clearSidebar(){
        getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }

    private void unregisterTeams(){
        for(int i = 0; i < teams.length; i++){
            if(teams[i] == null) continue;

            teams[i].unregister();
        }
    }

    private void clearOldObjective(){
        clearSidebar();
        objective.unregister();
        unregisterTeams();
        teams = new Team[16];
    }

    private Objective makeNewSidebarObjective(Objective newSidebarObjective){
        clearOldObjective();
        objective = newSidebarObjective;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(newSidebarObjective.getDisplayName());
        return objective;
    }

    public Objective setNewSidebarObjective(String objectiveName){
        makeNewSidebarObjective(scoreboard.registerNewObjective(objectiveName, "dummy"));
        return objective;
    }

    public Objective setNewSidebarObjective(String objectiveName, String objectiveType){
        makeNewSidebarObjective(scoreboard.registerNewObjective(objectiveName, objectiveType));
        return objective;
    }

    public void setDisplayName(String displayName){
        getObjective().setDisplayName(displayName);
    }
}
