package me.bramhaag.guilds.database;

import com.google.gson.Gson;
import me.bramhaag.guilds.guild.Guild;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class DatabaseProvider {
    public abstract void initialize();

    public abstract boolean createGuild(Guild guild);
    public abstract boolean removeGuild(String name);

    public abstract Guild getGuild(String name);
    public abstract HashMap<String, Guild> getGuilds();

    public abstract boolean updateGuild(Guild guild);
}
