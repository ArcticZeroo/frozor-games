package frozor.events;

public class WaitingTimeChangeEvent extends CustomEvent{
    private int time;
    public WaitingTimeChangeEvent(String message, int time) {
        super(message);
        this.time = time;
    }

    public int getTime(){
        return time;
    }
}
