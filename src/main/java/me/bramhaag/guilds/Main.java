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
import me.bramhaag.guilds.guild.GuildHandler;
import me.bramhaag.guilds.leaderboard.LeaderboardHandler;
import me.bramhaag.guilds.listeners.ChatListener;
import me.bramhaag.guilds.listeners.JoinListener;
import me.bramhaag.guilds.listeners.PlayerDamangeListener;
import me.bramhaag.guilds.placeholders.Placeholders;
import me.bramhaag.guilds.scoreboard.GuildScoreboardHandler;
import me.bramhaag.guilds.updater.Updater;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main instance;

    private DatabaseProvider database;

    private GuildHandler           guildHandler;
    private CommandHandler         commandHandler;
    private LeaderboardHandler     leaderboardHandler;
    private GuildScoreboardHandler scoreboardHandler;

    private static long creationTime;

    private static TaskChainFactory taskChainFactory;

    public static String PREFIX;

    String[] rip;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        PREFIX = ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugin-prefix")) + ChatColor.RESET + " ";
        instance = this;

        taskChainFactory = BukkitTaskChainFactory.create(this);

        setDatabaseType();

        guildHandler = new GuildHandler();
        guildHandler.enable();

        commandHandler = new CommandHandler();
        commandHandler.enable();

        leaderboardHandler = new LeaderboardHandler();
        leaderboardHandler.enable();

        //scoreboardHandler is enabled after the guilds are loaded
        scoreboardHandler = new GuildScoreboardHandler();

        initializePlaceholder();

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

        commandHandler.register(new CommandConfirm());
        commandHandler.register(new CommandCancel());

        commandHandler.register(new CommandAdmin());

        commandHandler.register(new CommandReload());

        commandHandler.register(new CommandUpdate());
        commandHandler.register(new CommandHelp());


        if(Main.getInstance().getConfig().getBoolean("chat.enable")) {
            getServer().getPluginManager().registerEvents(new ChatListener(), this);
        }

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamangeListener(), this);

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Failed to submit stats to mcstats.org!");
        }

        try {
            BasicFileAttributes attr = Files.readAttributes(Paths.get(getFile().getAbsolutePath()), BasicFileAttributes.class);
            creationTime = attr.creationTime().toMillis();
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Cannot get plugin file's creation time!");
            e.printStackTrace();

            creationTime = 0;
        }

        if(getConfig().getBoolean("updater.check")) {
            Updater.checkForUpdates((result, exception) -> {
                if (result != null) {
                    getLogger().log(Level.INFO, "A new update for Guilds has been found! Go to " + result + " to download it!");
                }
                else {
                    getLogger().log(Level.INFO, "No updates found!");
                }
            });
        }
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

    public static long getCreationTime() {
        return creationTime / 1000;
    }

    public static Main getInstance() {
        return instance;
    }

    private void initializePlaceholder() {
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            PlaceholderAPI.registerPlaceholder(this, "guild",              event -> Placeholders.getGuild(event.getPlayer()));
            PlaceholderAPI.registerPlaceholder(this, "guild-master",       event -> Placeholders.getGuildMaster(event.getPlayer()));
            PlaceholderAPI.registerPlaceholder(this, "guild-member-count", event -> Placeholders.getGuildmemberCount(event.getPlayer()));
            PlaceholderAPI.registerPlaceholder(this, "guild-prefix",       event -> Placeholders.getGuildPrefix(event.getPlayer()));
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            try {
                new EZPlaceholderHook(this, "guild") {
                    @Override
                    public String onPlaceholderRequest(Player player, String placeholder) {
                        return Placeholders.getGuild(player);
                    }
                }.hook();

                new EZPlaceholderHook(this, "guild-master") {
                    @Override
                    public String onPlaceholderRequest(Player player, String placeholder) {
                        return Placeholders.getGuildMaster(player);
                    }
                }.hook();

                new EZPlaceholderHook(this, "guild-member-count") {
                    private LeaderboardHandler leaderboardHandler;

                    @Override
                    public String onPlaceholderRequest(Player player, String placeholder) {
                        return Placeholders.getGuildmemberCount(player);
                    }
                }.hook();

                new EZPlaceholderHook(this, "guild-prefix") {
                    @Override
                    public String onPlaceholderRequest(Player player, String placeholder) {
                        return Placeholders.getGuildPrefix(player);
                    }
                }.hook();
            } catch (Exception ex) {
                getLogger().log(Level.WARNING, "Error while creating PlaceholderAPI placeholders!");
            }
        }
    }

    public LeaderboardHandler getLeaderboardHandler() {
        return leaderboardHandler;
    }
}
