package me.bramhaag.guilds;

import me.bramhaag.guilds.commands.CommandHelp;
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
        commandHandler.register(new CommandHelp());

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
