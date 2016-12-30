package me.bramhaag.guilds.guild;

import me.bramhaag.guilds.IHandler;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;

import java.util.HashMap;
import java.util.logging.Level;

public class GuildHandler implements IHandler {

    private HashMap<String, Guild> guilds;

    @Override
    public void enable() {
        guilds = new HashMap<>();

        initialize();
    }

    @Override
    public void disable() {
        guilds.clear();
        guilds = null;
    }

    public void initialize() {
        Main.getInstance().getDatabaseProvider().getGuilds(((result, exception) -> {
            if(result == null && exception != null) {
                Main.getInstance().getLogger().log(Level.SEVERE, "An error occurred while loading guilds");
                exception.printStackTrace();
                return;
            }

            guilds = result;
        }));

        if(guilds == null) {
            return;
        }

        guilds.values().forEach(this::addGuild);
    }

    public void addGuild(Guild guild) {
        guilds.put(guild.getName(), guild);
    }

    public void setGuilds(HashMap<String, Guild> guilds) {
        this.guilds = guilds;
    }

    public HashMap<String, Guild> getGuilds() {
        return guilds;
    }
}
