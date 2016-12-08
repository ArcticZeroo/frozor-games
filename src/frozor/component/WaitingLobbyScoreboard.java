package frozor.component;

import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.events.WaitingTimeChangeEvent;
import frozor.managers.WaitingLobbyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scoreboard.*;

import java.util.Set;

public class WaitingLobbyScoreboard implements Listener{
    private ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private Scoreboard scoreboard;
    private Objective scoreboardObjective;
    private Team scoreboardTeam;

    private WaitingLobbyManager waitingLobbyManager;

    private int nextScoreboardSlot = -1;

    private int getNextScoreboardSlot(){
       return nextScoreboardSlot--;
    }

    public void addScoreboardRow(String text){
        Score score = scoreboardObjective.getScore(text);
        score.setScore(1);
        score.setScore(getNextScoreboardSlot());
    }

    public String getOnlineString(){
        return waitingLobbyManager.getArcade().getPlugin().getServer().getOnlinePlayers().size() + "/" + waitingLobbyManager.getPlayerMax();
    }

    private void updatePlayerCount(){
        scoreboardTeam.setPrefix(getOnlineString());
    }

    public void setWaiting(){
        scoreboardObjective.setDisplayName(ChatColor.AQUA + (ChatColor.BOLD + "Waiting for players..."));
    }

    public WaitingLobbyScoreboard(WaitingLobbyManager waitingLobbyManager){
        this.waitingLobbyManager = waitingLobbyManager;
        scoreboard = scoreboardManager.getNewScoreboard();
        scoreboardObjective = scoreboard.registerNewObjective("waitingBoard", "dummy");
        scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        setWaiting();

        addScoreboardRow(ChatColor.WHITE.toString());
        addScoreboardRow(ChatColor.GOLD + (ChatColor.BOLD + "Game"));
        addScoreboardRow(waitingLobbyManager.getArcade().getGame().getTitle());
        addScoreboardRow(ChatColor.BOLD.toString());
        /*addScoreboardRow(ChatColor.GREEN + (ChatColor.BOLD + "Min Players"));
        addScoreboardRow(Integer.toString(waitingLobbyManager.getPlayerMin()));
        addScoreboardRow("§a");
        addScoreboardRow(ChatColor.DARK_GREEN + (ChatColor.BOLD + "Max Players"));
        addScoreboardRow(Integer.toString(waitingLobbyManager.getPlayerMax()));
        addScoreboardRow("§c");*/

        addScoreboardRow(ChatColor.GREEN + (ChatColor.BOLD + "Players"));
        scoreboardTeam = scoreboard.registerNewTeam("sbPlayerCount");
        scoreboardTeam.addEntry(ChatColor.AQUA.toString());
        scoreboardObjective.getScore(ChatColor.AQUA.toString()).setScore(getNextScoreboardSlot());
        updatePlayerCount();

        waitingLobbyManager.getArcade().RegisterEvents(this);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @EventHandler
    public void onWaitingTimeChange(WaitingTimeChangeEvent event){
        //waitingLobbyManager.getArcade().getDebugManager().print(event.getMessage());
        scoreboardObjective.setDisplayName(String.format(ChatColor.AQUA + (ChatColor.BOLD + "Starting in %d seconds..."), event.getTime()));
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.LOBBY){
            setWaiting();
        }else if(event.getGameState() == GameState.TIMER){
            scoreboardObjective.setDisplayName(String.format(ChatColor.AQUA + (ChatColor.BOLD + "Starting in %d seconds..."), waitingLobbyManager.getWaitingTimer().getStartTime()));
        }
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event){
        updatePlayerCount();
    }
}
