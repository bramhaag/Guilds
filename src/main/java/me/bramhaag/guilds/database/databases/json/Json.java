package me.bramhaag.guilds.database.databases.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;
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
    public void createGuild(Guild guild, Callback<Boolean, Exception> callback) {
        HashMap<String, Guild> guilds = getGuilds() == null ? new HashMap<>() : getGuilds();
        guilds.put(guild.getName(), guild);

        Main.newChain()
            .asyncFirst(() -> write(guilds))
            .syncLast((successful) -> callback.call(successful, null))
        .execute();

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
            .syncLast((successful) -> callback.call(successful, null))
        .execute();
    }

    @Override
    public void getGuilds(Callback<HashMap<String, Guild>, Exception> callback) {
        Main.newChain()
            .asyncFirst(this::getGuilds)
            .syncLast(guilds -> callback.call(guilds, null))
        .execute();
    }

    @Override
    public void updateGuild(Guild guild, Callback<Boolean, Exception> callback) {
        HashMap<String, Guild> guilds = getGuilds();
        guilds.put(guild.getName(), guild);

        Main.newChain()
            .asyncFirst(() -> write(guilds))
            .syncLast((successful) -> callback.call(successful, null))
        .execute();
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
