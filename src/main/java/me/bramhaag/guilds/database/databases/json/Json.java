package me.bramhaag.guilds.database.databases.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;

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
    public boolean createGuild(Guild guild) {
        HashMap<String, Guild> guilds = getGuilds() == null ? new HashMap<>() : getGuilds();
        guilds.put(guild.getName(), guild);

        if(!write(guilds)) {
            return false;
        }

        Main.getInstance().getGuildHandler().addGuild(guild);
        return true;
    }

    @Override
    public boolean removeGuild(String name) {
        HashMap<String, Guild> guilds = getGuilds();

        if(guilds == null || !guilds.keySet().contains(name)) {
            return false;
        }

        guilds.remove(name);
        return write(guilds);
    }

    @Override
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
    }

    @Override
    public boolean updateGuild(Guild guild) {
        HashMap<String, Guild> guilds = getGuilds();
        guilds.put(guild.getName(), guild);

        return write(guilds);
    }

    private boolean write(HashMap<String, Guild> guilds) {
        try (Writer writer = new FileWriter(guildsFile)) {
            gson.toJson(guilds, writer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
