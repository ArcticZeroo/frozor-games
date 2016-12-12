package frozor.managers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import frozor.arcade.Arcade;
import frozor.component.DatapointParser;
import frozor.teams.PlayerTeam;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class TeamManager {
    private Arcade arcade;
    private HashMap<String, PlayerTeam> teams = new HashMap<>();
    private HashMap<UUID, String> players = new HashMap<>();
    private NotificationManager notificationManager = new NotificationManager("Team");

    public TeamManager(Arcade arcade, PlayerTeam[] teams){
        this.arcade = arcade;

        arcade.getDebugManager().print("Initializing TeamManager");
        arcade.getDebugManager().print("There are " + teams.length + " teams to add.");

        for(PlayerTeam team : teams){
            arcade.getDebugManager().print("Adding team " + team.getTeamName());

            List<String> spawnStrings = arcade.getGame().getMapConfig().getStringList("spawns."+team.getTeamName());
            if(spawnStrings == null){
                arcade.getDebugManager().print("Team " + team.getTeamName() + " has no valid spawns, continuing.");
                continue;
            }

            List<Location> teamSpawns = new ArrayList<>();

            for(String spawnString : spawnStrings){
                teamSpawns.add(DatapointParser.parse(spawnString));
            }

            Location[] teamSpawnsArray = new Location[teamSpawns.size()];

            teamSpawnsArray = teamSpawns.toArray(teamSpawnsArray);

            team.setTeamSpawns(teamSpawnsArray);
            team.register(arcade.getGameScoreboard().getScoreboard());
            this.teams.put(team.getTeamName(), team);

            arcade.getDebugManager().print("Added team " + team.getTeamName());
        }

        arcade.getDebugManager().print("Added " + this.teams.size() + " teams.");
    }

    public HashMap<String, PlayerTeam> getTeams() {
        return teams;
    }

    public void assignPlayer(Player player){
        arcade.getDebugManager().print("Assigning " + player.getName() + " to a team...");

        PlayerTeam smallestTeam = null;

        for(PlayerTeam team : getTeams().values()){
            if(smallestTeam == null){
                smallestTeam = team;
                continue;
            }

            if(team == null){
                arcade.getDebugManager().print("I'm not sure what happened, but there's a null team.");
                continue;
            }

            if(team.getScoreboardTeam().getSize() < smallestTeam.getScoreboardTeam().getSize()){
                smallestTeam = team;
            }
        }

        if(smallestTeam == null){
            return;
        }

        smallestTeam.getScoreboardTeam().addEntry(player.getName());
        players.put(player.getUniqueId(), smallestTeam.getTeamName());
        player.sendMessage(notificationManager.getMessage("You have joined team " + smallestTeam.getTeamColor() + smallestTeam.getTeamName()));

        arcade.getDebugManager().print("Assigned " + player.getName() + " to team  " + smallestTeam.getTeamName());
    }

    public void assignTeams(){
        for(Player player : arcade.getPlugin().getServer().getOnlinePlayers()){
            if(players.containsKey(player.getUniqueId())) continue;
            assignPlayer(player);
        }
    }

    public Location getTeamSpawn(Player player){
        arcade.getDebugManager().print("Getting a team spawn for "  + player.getName());

        if(!players.containsKey(player.getUniqueId())) assignPlayer(player);

        String playerTeamName = players.get(player.getUniqueId());

        PlayerTeam playerTeam = teams.get(playerTeamName);

        Location[] playerTeamSpawns = playerTeam.getTeamSpawns();

        Random random = new Random();

        Location playerTeamSpawn = playerTeamSpawns[random.nextInt(playerTeamSpawns.length)];

        arcade.getDebugManager().print("Found a spawn: " + playerTeamSpawn.toString());

        return playerTeamSpawn;
    }

    public void teleportPlayers(){
        for(Player player : arcade.getPlugin().getServer().getOnlinePlayers()){
            player.teleport(getTeamSpawn(player));
        }
    }

    public PlayerTeam getPlayerTeam(Player player){
        if(!players.containsKey(player.getUniqueId())) return null;

        return teams.get(players.get(player.getUniqueId()));
    }

    public Boolean isFriendlyTeam(Player player1, Player player2){
        PlayerTeam playerTeam = getPlayerTeam(player1);

        return (playerTeam.getScoreboardTeam().hasEntry(player2.getName()));
    }
}
