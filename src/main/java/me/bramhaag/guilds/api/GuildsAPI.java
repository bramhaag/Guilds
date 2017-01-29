package me.bramhaag.guilds.api;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.leaderboard.Leaderboard;
import org.spigotmc.SneakyThrow;

import java.util.ArrayList;

public class GuildsAPI {

    public static Leaderboard createLeaderboard(String name, Leaderboard.LeaderboardType leaderboardType, Leaderboard.SortType sortType) {
        Leaderboard leaderboard = Main.getInstance().getLeaderboardHandler().getLeaderboards().stream().filter(l -> l.getName().equals(name) && l.getLeaderboardType() == leaderboardType).findFirst().orElse(null);

        if(leaderboard == null) {
            Main.getInstance().getDatabaseProvider().createLeaderboard(name, leaderboardType, sortType, (result, exception) -> {
                if (result == null && exception != null) {
                    SneakyThrow.sneaky(exception);
                }
            });

            return new Leaderboard(name, leaderboardType, sortType, new ArrayList<>());
        }

        return leaderboard;
    }

    public static void removeLeaderboard(String name, Leaderboard.LeaderboardType leaderboardType) {
        Main.getInstance().getDatabaseProvider().removeLeaderboard(name, leaderboardType, (result, exception) -> {
            if(result == null && exception != null) {
                SneakyThrow.sneaky(exception);
            }
        });
    }
}
