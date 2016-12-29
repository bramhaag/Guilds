package me.bramhaag.guilds;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import me.bramhaag.guilds.commands.*;
import me.bramhaag.guilds.commands.base.CommandHandler;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.database.databases.json.Json;
import me.bramhaag.guilds.database.databases.mysql.MySql;
import me.bramhaag.guilds.guild.GuildHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    private DatabaseProvider database;

    private GuildHandler guildHandler;
    private CommandHandler commandHandler;

    public static TaskChainFactory taskChainFactory;

    public static final String PREFIX = "[Guilds] ";

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        instance = this;

        taskChainFactory = BukkitTaskChainFactory.create(this);

        switch (getConfig().getString("database.type").toLowerCase()) {
            case "json":
                database = new Json();
                break;
            case "mysql":
                database = new MySql();
                break;
            default:
                database = new Json();
                break;
        }

        database.initialize();

        guildHandler = new GuildHandler();
        guildHandler.enable();

        commandHandler = new CommandHandler();
        commandHandler.enable();

        getCommand("guild").setExecutor(commandHandler);

        commandHandler.register(new CommandAccept());
        commandHandler.register(new CommandBoot());
        commandHandler.register(new CommandChat());
        commandHandler.register(new CommandCreate());
        commandHandler.register(new CommandDelete());
        commandHandler.register(new CommandHelp());
        commandHandler.register(new CommandDemote());
        commandHandler.register(new CommandInfo());
        commandHandler.register(new CommandInvite());
        commandHandler.register(new CommandLeave());
        commandHandler.register(new CommandPromote());
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

    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }

    public static <T> TaskChain<T> newSharedChain(String name) {
        return taskChainFactory.newSharedChain(name);
    }

    public static Main getInstance() {
        return instance;
    }
}
