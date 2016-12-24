package me.bramhaag.guilds.database;

import com.google.gson.Gson;
import me.bramhaag.guilds.guild.Guild;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class DatabaseProvider {

    protected Gson gson;

    public abstract void initialize();

    public abstract boolean createGuild(Guild guild);
    public abstract boolean removeGuild(int id);

    public abstract Guild getGuild(int id);
    public abstract HashMap<Integer, Guild> getGuilds();

    public abstract boolean updateGuild(Guild guild);

    public Gson getGson() {
        return gson;
    }
}
