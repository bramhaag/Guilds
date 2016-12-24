package me.bramhaag.guilds.database.databases;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.util.GuildMapDeserializer;

import java.io.*;
import java.util.*;

/**
 * Created by Bram on 22-12-2016.
 */
public class Json extends DatabaseProvider {

    private File guildsFile;

    @Override
    public void initialize() {
        guildsFile = new File(Main.getInstance().getDataFolder(), "guilds.json");
        gson = new GsonBuilder()
                .registerTypeAdapter(new HashMap<Integer, Guild>().getClass(), new GuildMapDeserializer())
                .setPrettyPrinting()
                .create();

        if(!guildsFile.exists()) {
            try {
                guildsFile.createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean createGuild(Guild guild) {
        HashMap<Integer, Guild> guilds = getGuilds() == null ? new HashMap<>() : getGuilds();
        try (Writer writer = new FileWriter(guildsFile)) {
            guilds.put(guild.getId(), guild);
            gson.toJson(guilds, writer);
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        Main.getInstance().getGuildHandler().addGuild(guild);
        return true;
    }

    @Override
    public boolean removeGuild(int id) {
        return false;
    }

    @Override
    public Guild getGuild(int id) {
        return getGuilds().entrySet().stream().filter(entry -> entry.getKey() == id).findFirst().orElse(null).getValue();
    }

    @Override
    public HashMap<Integer, Guild> getGuilds() {
        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader(guildsFile));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

            return null;
        }

        return gson.fromJson(reader, new HashMap<Integer, Guild>().getClass());;
    }

    @Override
    public boolean updateGuild(Guild guild) {
        return false;
    }
}
