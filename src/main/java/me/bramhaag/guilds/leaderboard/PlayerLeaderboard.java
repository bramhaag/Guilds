package me.bramhaag.guilds.leaderboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class PlayerLeaderboard extends Leaderboard<UUID> {

    public PlayerLeaderboard(String name, SortType sortType) {
        super(name, sortType);
    }

    @Override
    public void showLeaderboard(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Showing leaderboard for: " + getName());

        List<Score<UUID>> sortedScores = getSortedScores();

        for (int i = 0; i < sortedScores.size(); i++) {
            sender.sendMessage(String.format("%s. %s - %s", (i + 1), Bukkit.getPlayer(sortedScores.get(i).getOwner()), sortedScores.get(i).getValue()));
        }
    }
}
