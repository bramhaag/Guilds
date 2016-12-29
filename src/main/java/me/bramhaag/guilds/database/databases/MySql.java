package me.bramhaag.guilds.database.databases;

import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;

import java.util.HashMap;

public class MySql extends DatabaseProvider {

    @Override
    public void initialize() {

    }

    @Override
    public boolean createGuild(Guild guild) {
        return false;
    }

    @Override
    public boolean removeGuild(String name) {
        return false;
    }

    @Override
    public Guild getGuild(String name) {
        return null;
    }

    @Override
    public HashMap<String, Guild> getGuilds() {
        return null;
    }

    @Override
    public boolean updateGuild(Guild guild) {
        return false;
    }
}
