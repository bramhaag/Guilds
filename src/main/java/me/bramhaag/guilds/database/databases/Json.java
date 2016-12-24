package me.bramhaag.guilds.database.databases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bram on 22-12-2016.
 */
public class Json extends DatabaseProvider {

    private File guildsFile;
    private Gson gson;

    @Override
    public void initialize() {
        guildsFile = new File(Main.getInstance().getDataFolder(), "guilds.json");
        gson = new GsonBuilder()
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
    public boolean createGuild(String name, UUID master) {
        Guild guild = new Guild(name, master);

        try (Writer writer = new FileWriter(guildsFile.getAbsolutePath())) {
            List<Guild> guilds = getGuilds() == null ? new ArrayList<>() : getGuilds();

            guilds.add(guild);
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
        return getGuilds().stream().filter(guild -> guild.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Guild> getGuilds() {
        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader(guildsFile.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

            return null;
        }

        return gson.fromJson(reader, new TypeToken<List<Guild>>(){}.getType());
    }

    @Override
    public boolean updateGuild(Guild guild) {
        return false;
    }
}
