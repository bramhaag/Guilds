package me.bramhaag.guilds;

import me.bramhaag.guilds.commands.*;
import me.bramhaag.guilds.commands.base.CommandHandler;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.database.databases.Json;
import me.bramhaag.guilds.database.databases.MySql;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * Created by Bram on 22-12-2016.
 */
public class Main extends JavaPlugin {

    private static Main instance;

    private DatabaseProvider database;

    private GuildHandler guildHandler;
    private CommandHandler commandHandler;

    public static final String PREFIX = "[Guilds] ";

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        instance = this;

        switch (getConfig().getString("database.type").toLowerCase()) {
            case "json":
                database = new Json();
                break;
            case "mysql":
                database = new MySql();
                break;
            default:
                database = null;
                break;
        }

        database.initialize();

        guildHandler = new GuildHandler();
        guildHandler.enable();

        commandHandler = new CommandHandler();
        commandHandler.enable();

        getCommand("guild").setExecutor(commandHandler);

        commandHandler.register(new CommandAccept("accept", "null", null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandChat("chat", "null", null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandCreate());
        commandHandler.register(new CommandDelete());
        commandHandler.register(new CommandHelp());
        commandHandler.register(new CommandDemote("demote", "null", null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandInfo());
        commandHandler.register(new CommandInvite("invite", "null", null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandLeave("leave", "null", null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandPromote()); //Not yet implemented
        commandHandler.register(new CommandRole());
    }

    @Override
    public void onDisable() {
        commandHandler.disable();
    }

    public DatabaseProvider getDatabaseProvider() {
        return database;
    }

    public GuildHandler getGuildHandler() {
        return guildHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static Main getInstance() {
        return instance;
    }
}
