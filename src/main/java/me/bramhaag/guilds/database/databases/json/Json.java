package me.bramhaag.guilds.database.databases.json;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.__old.leaderboard.Leaderboard;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO handle exceptions
public class Json extends DatabaseProvider {

    private Gson gson;
    private File guildsFile;

    @Override
    public void initialize() {
        guildsFile = new File(Main.getInstance().getDataFolder(), "guilds.json");
        gson = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<Map<String, Guild>>() { }.getType(), new GuildMapDeserializer())
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
        .create();

        if(!guildsFile.exists()) {
            try {
                if(!guildsFile.createNewFile()) {
                    throw new IOException("Something went wrong when creating the guild storage file!");
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void createGuild(Guild guild, Callback<Boolean, Exception> callback) {
        HashMap<String, Guild> guilds = getGuilds() == null ? new HashMap<>() : getGuilds();
        guilds.put(guild.getName(), guild);

        Main.newChain()
            .asyncFirst(() -> write(guilds))
            .syncLast(successful -> callback.call(successful, null))
        .execute((exception, task) -> {
            if(exception != null) {
                callback.call(false, exception);
            }
        });

        Main.getInstance().getGuildHandler().addGuild(guild);
    }

    @Override
    public void removeGuild(Guild guild, Callback<Boolean, Exception> callback) {
        HashMap<String, Guild> guilds = getGuilds();

        if(guilds == null || !guilds.keySet().contains(guild.getName())) {
            return;
        }

        guilds.remove(guild.getName());

        Main.newChain()
            .asyncFirst(() -> write(guilds))
            .syncLast(successful -> callback.call(successful, null))
        .execute();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getGuilds(Callback<HashMap<String, Guild>, Exception> callback) {
        Main.newChain()
            .asyncFirst(() -> {
                JsonReader reader;
                try {
                    reader = new JsonReader(new FileReader(guildsFile));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    return null;
                }

                return gson.fromJson(reader, new TypeToken<Map<String, Guild>>() { }.getType());
            })
            .syncLast(guilds -> callback.call((HashMap<String, Guild>)guilds, null))
        .execute();
    }

    @Override
    public void updateGuild(Guild guild, Callback<Boolean, Exception> callback) {
        HashMap<String, Guild> guilds = getGuilds();
        guilds.put(guild.getName(), guild);

        Main.newChain()
            .asyncFirst(() -> write(guilds))
            .syncLast(successful -> callback.call(successful, null))
        .execute();
    }

    @Override
    public void createLeaderboard(String name, me.bramhaag.guilds.leaderboard.Leaderboard.LeaderboardType leaderboardType, me.bramhaag.guilds.leaderboard.Leaderboard.SortType sortType, Callback<me.bramhaag.guilds.leaderboard.Leaderboard, Exception> callback) {

    }

    @Override
    public void removeLeaderboard(String name, me.bramhaag.guilds.leaderboard.Leaderboard.LeaderboardType leaderboardType, Callback<Boolean, Exception> callback) {

    }

    @Override
    public void getLeaderboards(Callback<List<me.bramhaag.guilds.leaderboard.Leaderboard>, Exception> callback) {

    }

    private boolean write(HashMap<String, Guild> guilds) {
        try (Writer writer = new FileWriter(guildsFile)) {
            gson.toJson(guilds, writer);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HashMap<String, Guild> getGuilds() {
        return Main.getInstance().getGuildHandler().getGuilds();
    }
}
