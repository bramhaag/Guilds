package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.api.GuildsAPI;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.leaderboard.Leaderboard;
import me.bramhaag.guilds.leaderboard.Score;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandTestLeaderboard extends CommandBase {

    public CommandTestLeaderboard() {
        super("test", "", "", false, null, null, -1, -1);
    }

    public void execute(Player player, String[] args) {
        Leaderboard leaderboard = GuildsAPI.createLeaderboard(new Leaderboard(args[0], Leaderboard.LeaderboardType.valueOf(args[1]), Leaderboard.SortType.valueOf(args[2])) {
            @Override
            public void show(CommandSender sender) {
                sender.sendMessage(ChatColor.AQUA + "Leaderboard " + getName());
                for(int i = 0; i < getSortedScores().size(); i++) {
                    Score score = getSortedScores().get(i);
                    sender.sendMessage(String.format("%d. %s - %d", i + 1, Bukkit.getPlayer(UUID.fromString(score.getOwner())), score.getValue()));
                }
            }
        });

        //Leaderboard leaderboard = GuildsAPI.createLeaderboard(args[0], Leaderboard.LeaderboardType.valueOf(args[1]), Leaderboard.SortType.valueOf(args[2]));

        player.sendMessage("created");

        leaderboard.addScore(new Score(player.getUniqueId().toString(), 50));
        leaderboard.addScore(new Score(player.getUniqueId().toString(), 500));
        leaderboard.addScore(new Score("testtest", 5005));
        leaderboard.addScore(new Score("teddsttest", 1));

        leaderboard.show(player);

        player.sendMessage("---1---");
        player.sendMessage("Name: "  + leaderboard.getName());
        player.sendMessage("Type: "  + leaderboard.getLeaderboardType().name());
        player.sendMessage("Order: " + leaderboard.getSortType().name());
    }
}
