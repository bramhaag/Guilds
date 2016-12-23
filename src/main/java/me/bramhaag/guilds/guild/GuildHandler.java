package me.bramhaag.guilds.guild;

import me.bramhaag.guilds.IHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bram on 23-12-2016.
 */
public class GuildHandler implements IHandler {

    private List<Guild> guilds;

    @Override
    public void enable() {
        guilds = new ArrayList<>();

        initialize();
    }

    @Override
    public void disable() {
        guilds.clear();
        guilds = null;
    }

    public void initialize() {
        //TODO implement
    }

    public void addGuild() {
        //TODO implement
    }

    public List<Guild> getGuilds() {
        return guilds;
    }
}
