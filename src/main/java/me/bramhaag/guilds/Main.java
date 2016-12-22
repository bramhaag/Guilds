package me.bramhaag.guilds;

import me.bramhaag.guilds.commands.*;
import me.bramhaag.guilds.commands.base.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Bram on 22-12-2016.
 */
public class Main extends JavaPlugin {

    private static Main instance;
    private CommandHandler commandHandler;

    public static final String PREFIX = "[Guilds] ";

    @Override
    public void onEnable() {
        instance = this;

        commandHandler = new CommandHandler();
        commandHandler.enable();

        getCommand("guild").setExecutor(commandHandler);
        commandHandler.register(new CommandAccept(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandAccept(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandChat(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandCreate(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandDelete(null, null, null, false, null, null, -1, -1)); //Not yet implemented
        commandHandler.register(new CommandCreate(null, null, null, false, null, null, -1, -1)); //Not yet implemented
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

    public static Main getInstance() {
        return instance;
    }
}
