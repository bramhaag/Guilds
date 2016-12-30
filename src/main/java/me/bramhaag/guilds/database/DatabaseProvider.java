package me.bramhaag.guilds.database;

import me.bramhaag.guilds.guild.Guild;

import java.util.HashMap;

public abstract class DatabaseProvider {
    public abstract void initialize();

    public abstract void createGuild(Guild guild, Callback<Boolean, Exception> callback);
    public abstract void removeGuild(Guild guild, Callback<Boolean, Exception> callback);

    public abstract void getGuilds(Callback<HashMap<String, Guild>, Exception> callback);

    public abstract void updateGuild(Guild guild, Callback<Boolean, Exception> callback);
}
