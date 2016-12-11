package frozor.events;

public class ChestRefillTimeChangeEvent extends CustomEvent{
    private int time;
    public ChestRefillTimeChangeEvent(int time) {
        super("");
        this.time = time;
    }

    public int getTime(){
        return time;
    }
}
