package me.bramhaag.guilds.database.databases;

import me.bramhaag.guilds.database.Database;
import me.bramhaag.guilds.guild.Guild;

/**
 * Created by Bram on 22-12-2016.
 */
public class MySql extends Database {

    @Override
    public boolean createGuild() {
        return false;
    }

    @Override
    public boolean removeGuild() {
        return false;
    }

    @Override
    public Guild getGuild() {
        return null;
    }

    @Override
    public boolean updateGuild(Guild guild) {
        return false;
    }
}
