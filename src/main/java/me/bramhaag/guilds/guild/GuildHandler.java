package me.bramhaag.guilds.guild;

import me.bramhaag.guilds.IHandler;
import me.bramhaag.guilds.Main;

import java.util.ArrayList;
import java.util.List;

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
        Main.getInstance().getDatabaseProvider().getGuilds().values().forEach(this::addGuild);
    }

    public void addGuild(Guild guild) {
        guilds.add(guild);
    }

    public List<Guild> getGuilds() {
        return guilds;
    }
}
