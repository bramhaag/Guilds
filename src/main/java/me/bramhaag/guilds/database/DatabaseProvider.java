package me.bramhaag.guilds.database;

import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.leaderboard.Leaderboard;

import java.util.HashMap;
import java.util.List;

public abstract class DatabaseProvider {
    public abstract void initialize();

    public abstract void createGuild(Guild guild, Callback<Boolean, Exception> callback);
    public abstract void removeGuild(Guild guild, Callback<Boolean, Exception> callback);

    public abstract void getGuilds(Callback<HashMap<String, Guild>, Exception> callback);

    public abstract void updateGuild(Guild guild, Callback<Boolean, Exception> callback);

    public abstract void createLeaderboard(String name, Leaderboard.LeaderboardType leaderboardType, Leaderboard.SortType sortType, Callback<Leaderboard, Exception> callback);
    public abstract void removeLeaderboard(String name, Leaderboard.LeaderboardType leaderboardType, Callback<Boolean, Exception> callback);
    public abstract void getLeaderboards(Callback<List<Leaderboard>, Exception> callback);
}
