package me.bramhaag.guilds.database;

import me.bramhaag.guilds.guild.Guild;

import java.util.UUID;

/**
 * Created by Bram on 22-12-2016.
 */
public abstract class DatabaseProvider {

    public abstract boolean createGuild(String name, UUID master);
    public abstract boolean removeGuild(int id);

    public abstract Guild getGuild();

    public abstract boolean updateGuild(Guild guild);
}
