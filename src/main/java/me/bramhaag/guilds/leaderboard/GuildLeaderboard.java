package me.bramhaag.guilds.leaderboard;

import me.bramhaag.guilds.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class GuildLeaderboard extends Leaderboard<Guild> {

    public GuildLeaderboard(String name, SortType sortType) {
        super(name, sortType);
    }

    @Override
    public void showLeaderboard(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Showing leaderboard for: " + getName());

        List<Score<Guild>> sortedScores = getSortedScores();

        for (int i = 0; i < sortedScores.size(); i++) {
            sender.sendMessage(String.format("%s. %s - %s", (i + 1), sortedScores.get(i).getOwner().getName(), sortedScores.get(i).getValue()));
        }
    }
}
