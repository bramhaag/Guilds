package me.bramhaag.guilds.database.databases.json;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.leaderboard.Leaderboard;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO handle exceptions
public class Json extends DatabaseProvider {

    private Gson gson;
    private File guildsFile;
    private File leaderboardsFile;

    @Override
    public void initialize() {
        guildsFile = new File(Main.getInstance().getDataFolder(), "guilds.json");
        leaderboardsFile = new File(Main.getInstance().getDataFolder(), "leaderboards.json");

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

        if(!leaderboardsFile.exists()) {
            try {
                if(!leaderboardsFile.createNewFile()) {
                    throw new IOException("Something went wrong when creating the leaderboard storage file!");
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
            .asyncFirst(() -> write(guildsFile, guilds))
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
            .asyncFirst(() -> write(guildsFile, guilds))
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
            .asyncFirst(() -> write(guildsFile, guilds))
            .syncLast(successful -> callback.call(successful, null))
        .execute();
    }

    @Override
    public void createLeaderboard(Leaderboard leaderboard, Callback<Boolean, Exception> callback) {
        List<Leaderboard> leaderboards = getLeaderboards() == null ? new ArrayList<>() : getLeaderboards();
        leaderboards.add(leaderboard);

        //write(leaderboardsFile, leaderboards);

        Main.newChain()
                .asyncFirst(() -> {
                    System.out.println(leaderboards.size());
                    return write(leaderboardsFile, leaderboards);
                })
                .syncLast(successful -> callback.call(successful, null))
            .execute((exception, task) -> {
                if(exception != null) {
                    callback.call(false, exception);
                }
            });

        //Writes 2x because of this
        Main.getInstance().getLeaderboardHandler().addLeaderboard(leaderboard);
    }

    @Override
    public void removeLeaderboard(Leaderboard leaderboard, Callback<Boolean, Exception> callback) {
        List<Leaderboard> leaderboards = getLeaderboards();

        if(leaderboard == null || !leaderboards.contains(leaderboard)) {
            return;
        }

        leaderboards.remove(leaderboard);

        Main.newChain()
                .asyncFirst(() -> write(guildsFile, leaderboards))
                .syncLast(successful -> callback.call(successful, null))
            .execute();
    }

    @Override
    public void getLeaderboards(Callback<List<Leaderboard>, Exception> callback) {
        Main.newChain()
                .asyncFirst(() -> {
                    JsonReader reader;
                    try {
                        reader = new JsonReader(new FileReader(leaderboardsFile));
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                        return null;
                    }

                    return gson.fromJson(reader, new TypeToken<ArrayList<Leaderboard>>(){}.getType());
                })
                .syncLast(leaderboards -> callback.call((ArrayList<Leaderboard>) leaderboards, null))
            .execute();
    }

    @Override
    public void updateLeaderboard(Leaderboard leaderboard, Callback<Boolean, Exception> callback) {
        List<Leaderboard> leaderboards = getLeaderboards();
        leaderboards.remove(leaderboards.stream().filter(l -> l.getName().equals(leaderboard.getName()) && l.getLeaderboardType() == leaderboard.getLeaderboardType()).findFirst().orElse(null));
        leaderboards.add(leaderboard);

        Main.newChain()
                .asyncFirst(() -> write(leaderboardsFile, leaderboards))
                .syncLast(successful -> callback.call(successful, null))
            .execute();
    }

    private boolean write(File file, Object toWrite) {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(toWrite, writer);
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

    private List<Leaderboard> getLeaderboards() {
        return Main.getInstance().getLeaderboardHandler().getLeaderboards();
    }
}
