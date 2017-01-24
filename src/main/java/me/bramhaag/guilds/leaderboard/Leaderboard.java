package me.bramhaag.guilds.leaderboard;

import com.google.common.collect.Lists;
import me.bramhaag.guilds.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Leaderboard {
    private String name;
    private SortType sortType;

    private List<Score> scores;

    public Leaderboard(String name, SortType sortType) {
        this.name = name;
        this.sortType = sortType;

        scores = new ArrayList<>();
    }

    /**
     * Get leaderboard's name
     * @return leaderboard's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get leaderboard's sort type
     * @return leaderboard's sort type ({@link SortType#ASCENDING} or {@link SortType#DESCENDING})
     */
    public SortType getSortType() {
        return sortType;
    }

    /**
     * Add a score to the leaderboard
     * @param owner Owner of the score ({@link UUID} or {@link Guild#getName()}})
     * @param value Value of the score (e.g time in seconds it took the owner to complete something)
     */
    public void addScore(String owner, long value) {
        scores.stream().filter(score -> score.getOwner().equals(owner)).forEach(score -> scores.remove(score));

        if(scores.size() == 0) {
            scores.add(0, new Score(owner, value));
            return;
        }

        for(int i = 0; i < scores.size(); i++) {
            if(value < scores.get(i).getValue()) {
                scores.add(i, new Score(owner, value));
            }
        }
    }

    /**
     * Get the score of a player/guild
     * @param owner Player/Guild's score
     * @return The score of {@code owner}
     */
    public Score getScore(String owner) {
        return scores.stream().filter(score -> score.getOwner().equals(owner)).findFirst().orElse(null);
    }

    protected List<Score> getSortedScores() {
        if(sortType == SortType.ASCENDING) {
            return scores;
        }

        return Lists.reverse(scores);
    }

    public void showLeaderboard(CommandSender sender) {
        if(scores.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "This scoreboard is empty!");
        }

        String obj = scores.get(0).getOwner();
        if(obj.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")) {
            showPlayerLeaderboard(sender);
        }
        else {
            showGuildLeaderboard(sender);
        }
    }

    public void showPlayerLeaderboard(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "Leaderboard " + name);
        for(int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);

            sender.sendMessage(String.format("%d. %s - %d", i + 1, Bukkit.getPlayer(UUID.fromString(score.getOwner())), score.getValue()));
        }
    }

    public void showGuildLeaderboard(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "Leaderboard " + name);
        for(int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);

            sender.sendMessage(String.format("%d. %s - %d", i + 1, score.getOwner(), score.getValue()));
        }
    }

    public enum SortType {
        ASCENDING,
        DESCENDING
    }

}
