package me.bramhaag.guilds.leaderboard;

public class Score<T> {

    private T owner;
    private long value;

    public Score(T owner, long value) {
        this.owner = owner;
        this.value = value;
    }

    public T getOwner() {
        return owner;
    }

    public long getValue() {
        return value;
    }
}
