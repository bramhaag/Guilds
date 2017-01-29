package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.api.GuildsAPI;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.leaderboard.Leaderboard;
import org.bukkit.entity.Player;

public class CommandTestLeaderboard extends CommandBase {

    public CommandTestLeaderboard() {
        super("test", "", "", false, null, null, -1, -1);
    }

    public void execute(Player player, String[] args) {
        Leaderboard leaderboard1 = GuildsAPI.createLeaderboard(args[0], Leaderboard.LeaderboardType.valueOf(args[1]), Leaderboard.SortType.valueOf(args[2]));
        player.sendMessage("created");

        /*Leaderboard leaderboard2 = GuildsAPI.getLeaderboard(args[0], Leaderboard.LeaderboardType.valueOf(args[1]));

        player.sendMessage("---1---");
        player.sendMessage("Name: "  + leaderboard1.getName());
        player.sendMessage("Type: "  + leaderboard1.getLeaderboardType().name());
        player.sendMessage("Order: " + leaderboard1.getSortType().name());

        player.sendMessage("---2---");
        player.sendMessage("Name: "  + leaderboard2.getName());
        player.sendMessage("Type: "  + leaderboard2.getLeaderboardType().name());
        player.sendMessage("Order: " + leaderboard2.getSortType().name());*/
    }
}
