package me.bramhaag.guilds.api;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.leaderboard.Leaderboard;
import org.spigotmc.SneakyThrow;

import java.util.ArrayList;

public class GuildsAPI {

    public static Leaderboard createLeaderboard(String name, Leaderboard.LeaderboardType leaderboardType, Leaderboard.SortType sortType) {
        Leaderboard leaderboard = Main.getInstance().getLeaderboardHandler().getLeaderboards().stream().filter(l -> l.getName().equals(name) && l.getLeaderboardType() == leaderboardType).findFirst().orElse(null);

        if(leaderboard == null) {
            leaderboard = new Leaderboard(name, leaderboardType, sortType, new ArrayList<>());
            Main.getInstance().getDatabaseProvider().createLeaderboard(leaderboard, (result, exception) -> {
                if (!result && exception != null) {
                    SneakyThrow.sneaky(exception);
                }
            });
        }

        return leaderboard;
    }

    public static void removeLeaderboard(String name, Leaderboard.LeaderboardType leaderboardType) {
        Main.getInstance().getDatabaseProvider().removeLeaderboard(Leaderboard.getLeaderboard(name, leaderboardType), (result, exception) -> {
            if(!result && exception != null) {
                SneakyThrow.sneaky(exception);
            }
        });
    }

    public static Leaderboard getLeaderboard(String name, Leaderboard.LeaderboardType leaderboardType) {
        return Main.getInstance().getLeaderboardHandler().getLeaderboard(name, leaderboardType);
    }
}
