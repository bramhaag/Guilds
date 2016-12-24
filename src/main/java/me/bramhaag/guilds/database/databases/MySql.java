package me.bramhaag.guilds.database.databases;

import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;

import java.util.HashMap;
import java.util.UUID;

public class MySql extends DatabaseProvider {

    @Override
    public void initialize() {

    }

    @Override
    public boolean createGuild(Guild guild) {
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
    public HashMap<Integer, Guild> getGuilds() {
        return null;
    }

    @Override
    public boolean updateGuild(Guild guild) {
        return false;
    }
}
