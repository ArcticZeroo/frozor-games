package frozor.events;

import frozor.enums.GameState;

public class GameStateChangeEvent extends CustomEvent {
    private GameState gameState;

    public GameStateChangeEvent(String message, GameState newState) {
        super(message);
        gameState = newState;
    }

    public GameState getGameState(){
        return gameState;
    }
}
