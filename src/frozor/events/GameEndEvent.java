package frozor.events;

import frozor.teams.PlayerTeam;

import java.util.List;

public class GameEndEvent extends CustomEvent {
    private PlayerTeam winningTeam;
    private List<PlayerTeam> losingTeams;

    public GameEndEvent(String message, PlayerTeam winningTeam, List<PlayerTeam> losingTeams) {
        super(message);
        this.winningTeam = winningTeam;
        this.losingTeams = losingTeams;
    }

    public PlayerTeam getWinningTeam() {
        return winningTeam;
    }

    public List<PlayerTeam> getLosingTeams() {
        return losingTeams;
    }
}
