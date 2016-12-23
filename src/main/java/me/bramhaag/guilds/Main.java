package me.bramhaag.guilds;

import me.bramhaag.guilds.commands.*;
import me.bramhaag.guilds.commands.base.CommandHandler;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.GuildHandler;
import org.bukkit.plugin.java.JavaPlugin;

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
        instance = this;

        this.database = null; //TODO get database from config and initialize

        guildHandler = new GuildHandler();
        guildHandler.enable();

        commandHandler = new CommandHandler();
        commandHandler.enable();

        getCommand("guild").setExecutor(commandHandler);

        commandHandler.register(new CommandAccept(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandChat(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandCreate());
        commandHandler.register(new CommandDelete(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandHelp());
        commandHandler.register(new CommandDemote(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandInfo(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandInvite(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandLeave(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandPromote(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandRole());

        this.saveDefaultConfig();
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
