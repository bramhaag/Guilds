package me.bramhaag.guilds;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import me.bramhaag.guilds.commands.*;
import me.bramhaag.guilds.commands.base.CommandHandler;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.database.databases.json.Json;
import me.bramhaag.guilds.database.databases.mysql.MySql;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildHandler;
import me.bramhaag.guilds.listeners.ChatListener;
import me.bramhaag.guilds.listeners.JoinListener;
import me.bramhaag.guilds.scoreboard.GuildScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    private DatabaseProvider database;

    private GuildHandler guildHandler;
    private CommandHandler commandHandler;
    private GuildScoreboardHandler scoreboardHandler;

    private static TaskChainFactory taskChainFactory;

    public static String PREFIX;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        PREFIX = ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugin-prefix")) + ChatColor.RESET + " ";

        instance = this;

        setDatabaseType();

        taskChainFactory = BukkitTaskChainFactory.create(this);

        guildHandler = new GuildHandler();
        guildHandler.enable();

        commandHandler = new CommandHandler();
        commandHandler.enable();

        //scoreboardHandler is enabled after the guilds are loaded
        scoreboardHandler = new GuildScoreboardHandler();

        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            PlaceholderAPI.registerPlaceholder(this, "guild", event -> {
                Guild guild = Guild.getGuild(event.getPlayer().getUniqueId());
                if (guild == null) {
                    return "N/A";
                }

                return guild.getName();
            });

            PlaceholderAPI.registerPlaceholder(this, "guild-prefix", event -> {
                Guild guild = Guild.getGuild(event.getPlayer().getUniqueId());
                if (guild == null) {
                    return "N/A";
                }

                return guild.getPrefix();
            });

            PlaceholderAPI.registerPlaceholder(this, "guild-master", event -> {
                Guild guild = Guild.getGuild(event.getPlayer().getUniqueId());
                if (guild == null) {
                    return "N/A";
                }

                return Bukkit.getPlayer(guild.getGuildMaster().getUniqueId()).getName();
            });

            PlaceholderAPI.registerPlaceholder(this, "member-count", event -> {
                Guild guild = Guild.getGuild(event.getPlayer().getUniqueId());
                if (guild == null) {
                    return "N/A";
                }

                return String.valueOf(guild.getMembers().size());
            });
        }

        getCommand("guild").setExecutor(commandHandler);

        commandHandler.register(new CommandCreate());
        commandHandler.register(new CommandDelete());

        commandHandler.register(new CommandInvite());
        commandHandler.register(new CommandAccept());
        commandHandler.register(new CommandLeave());

        commandHandler.register(new CommandChat());

        commandHandler.register(new CommandInfo());

        commandHandler.register(new CommandRole());
        commandHandler.register(new CommandPromote());
        commandHandler.register(new CommandDemote());

        commandHandler.register(new CommandPrefix());
        commandHandler.register(new CommandBoot());

        commandHandler.register(new CommandReload());
        commandHandler.register(new CommandHelp());

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        guildHandler.disable();
        commandHandler.disable();
        scoreboardHandler.disable();
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

    public GuildScoreboardHandler getScoreboardHandler() {
        return scoreboardHandler;
    }

    public void setDatabaseType() {
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
