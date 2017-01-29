package me.bramhaag.guilds.leaderboard;

import com.google.common.collect.Lists;

import java.util.List;

public class Leaderboard {

    private String name;
    private LeaderboardType leaderboardType;
    private SortType sortType;

    private List<Score> scores;

    public Leaderboard(String name, LeaderboardType leaderboardType, SortType sortType, List<Score> scores) {
        this.name = name;
        this.leaderboardType = leaderboardType;
        this.sortType = sortType;
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public LeaderboardType getLeaderboardType() {
        return leaderboardType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public List<Score> getScores() {
        return scores;
    }

    public List<Score> getSortedScores() {
        return sortType == SortType.ASCENDING ? scores : Lists.reverse(scores);
    }

    public void addScore(Score score) {
        scores.stream().filter(s -> s.getOwner().equals(score.getOwner())).forEach(s -> scores.remove(score));

        for(int i = 0; i < scores.size(); i++) {
            Score s = scores.get(i);

            if(score.getValue() < s.getValue()) {
                scores.add(i, score);
                break;
            }
        }
    }

    public void removeScore(int position) {
        scores.remove(position);
    }

    public void removeScore(String owner) {
        removeScore(getScore(owner));
    }

    public void removeScore(Score score) {
        scores.remove(score);
    }

    public Score getScore(String owner) {
        return scores.stream().filter(s -> s.getOwner().equals(owner)).findFirst().orElse(null);
    }

    public enum LeaderboardType {
        PLAYER(0),
        GUILD(1);

        private int value;

        LeaderboardType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum SortType {
        ASCENDING(0),
        DESCENDING(1);

        private int value;

        SortType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }


    }
}
