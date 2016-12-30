package me.bramhaag.guilds.database.databases.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.*;

public class Json extends DatabaseProvider {

    private Gson gson;
    private File guildsFile;

    @Override
    public void initialize() {
        guildsFile = new File(Main.getInstance().getDataFolder(), "guilds.json");
        gson = new GsonBuilder()
                .registerTypeAdapter(new HashMap<String, Guild>().getClass(), new GuildMapDeserializer())
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

        write(guilds);

        Main.getInstance().getGuildHandler().addGuild(guild);
    }

    @Override
    public void removeGuild(Guild guild, Callback<Boolean, Exception> callback) {
        HashMap<String, Guild> guilds = getGuilds();

        if(guilds == null || !guilds.keySet().contains(guild.getName())) {
            return;
        }

        guilds.remove(guild.getName());
        write(guilds);
    }

    @Override
    public void getGuilds(Callback<HashMap<String, Guild>, Exception> callback) {
        new BukkitRunnable() {
            @Override
            public void run() {
                JsonReader reader;

                try {
                    reader = new JsonReader(new FileReader(guildsFile));
                }
                catch (Exception ex) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            callback.call(null, ex);
                        }
                    }.runTask(Main.getInstance());
                    return;
                }

                HashMap<String, Guild> guilds = gson.fromJson(reader, new HashMap<String, Guild>().getClass());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        callback.call(guilds, null);
                    }
                }.runTask(Main.getInstance());
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    @Override
    public void updateGuild(Guild guild, Callback<Boolean, Exception> callback) {
        HashMap<String, Guild> guilds = getGuilds();
        guilds.put(guild.getName(), guild);

        write(guilds);
    }

    private void write(HashMap<String, Guild> guilds) {
        try (Writer writer = new FileWriter(guildsFile)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    gson.toJson(guilds, writer);
                }
            }.runTaskAsynchronously(Main.getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Guild> getGuilds() {
        return Main.getInstance().getGuildHandler().getGuilds();
    }

   /* @Override
    public Guild getGuild(String name) {
        Map.Entry<String, Guild> guildEntry = getGuilds().entrySet().stream().filter(entry -> entry.getKey().equals(name)).findFirst().orElse(null);
        return guildEntry == null ? null : guildEntry.getValue();
    }

    @Override
    public HashMap<String, Guild> getGuilds() {
        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader(guildsFile));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

            return null;
        }

        return gson.fromJson(reader, new HashMap<String, Guild>().getClass());
    }*/
}
