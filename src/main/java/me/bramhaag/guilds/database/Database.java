package me.bramhaag.guilds.database;

import me.bramhaag.guilds.guild.Guild;

/**
 * Created by Bram on 22-12-2016.
 */
public abstract class Database {

    public abstract boolean createGuild();
    public abstract boolean removeGuild();

    public abstract Guild getGuild();

    public abstract boolean updateGuild(Guild guild);
}
