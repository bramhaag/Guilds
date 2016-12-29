package me.bramhaag.guilds.guild;

import com.google.gson.GsonBuilder;
import me.bramhaag.guilds.IHandler;
import me.bramhaag.guilds.Main;

import java.util.ArrayList;
import java.util.HashMap;
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
        HashMap<String, Guild> guilds = Main.getInstance().getDatabaseProvider().getGuilds();
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(guilds));
        if(guilds == null) {
            return;
        }

        guilds.values().forEach(this::addGuild);
    }

    public void addGuild(Guild guild) {
        guilds.add(guild);
    }

    public List<Guild> getGuilds() {
        return guilds;
    }
}
