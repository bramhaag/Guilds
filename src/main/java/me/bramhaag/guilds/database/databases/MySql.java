package me.bramhaag.guilds.database.databases;

import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;

import java.util.List;
import java.util.UUID;

/**
 * Created by Bram on 22-12-2016.
 */
public class MySql extends DatabaseProvider {

    @Override
    public void initialize() {

    }

    @Override
    public boolean createGuild(String name, UUID master) {
        return false;
    }

    @Override
    public boolean removeGuild(int id) {
        return false;
    }

    @Override
    public Guild getGuild(int id) {
        return null;
    }

    @Override
    public List<Guild> getGuilds() {
        return null;
    }

    @Override
    public boolean updateGuild(Guild guild) {
        return false;
    }
}
