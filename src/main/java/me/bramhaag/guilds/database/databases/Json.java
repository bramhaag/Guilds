package me.bramhaag.guilds.database.databases;

import com.google.gson.Gson;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;

import java.util.UUID;

/**
 * Created by Bram on 22-12-2016.
 */
public class Json extends DatabaseProvider {

    private Gson gson = new Gson();

    @Override
    public boolean createGuild(String name, UUID master) {
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
