package me.bramhaag.guilds.leaderboard;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class Leaderboard<T> {
    private String name;
    private SortType sortType;

    private List<Score<T>> scores;

    public Leaderboard(String name, SortType sortType) {
        this.name = name;
        this.sortType = sortType;

        scores = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void addScore(T owner, long value) {
        scores.stream().filter(score -> score.getOwner().equals(owner)).forEach(score -> scores.remove(score));

        for(int i = 0; i < scores.size(); i++) {
            if(value < scores.get(i).getValue()) {
                scores.add(i, new Score<>(owner, value));
            }
        }
    }

    public Score<T> getScore(T owner) {
        return scores.stream().filter(score -> score.getOwner().equals(owner)).findFirst().orElse(null);
    }

    protected List<Score<T>> getSortedScores() {
        if(sortType == SortType.ASCENDING) {
            return scores;
        }

        return Lists.reverse(scores);
    }

    public abstract void showLeaderboard(CommandSender sender);

    public enum SortType {
        ASCENDING,
        DESCENDING
    }

}
