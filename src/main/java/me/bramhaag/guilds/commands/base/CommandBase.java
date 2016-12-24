package me.bramhaag.guilds.commands.base;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandBase {

    private String name;
    private String description;
    private String permission;

    private boolean allowConsole;

    private List<String> aliases;
    private List<String> arguments;

    private int minimumArguments;
    private int maximumArguments;

    public CommandBase(String name, String description, String permission, boolean allowConsole, String[] aliases, String[] arguments, int minimumArguments, int maximumArguments) {
        this.name = name;
        this.description = description;
        this.permission = permission;

        this.allowConsole = allowConsole;

        this.aliases = aliases == null ? new ArrayList<>() : Arrays.asList(aliases);
        this.aliases = arguments == null ? new ArrayList<>() : Arrays.asList(arguments);

        this.minimumArguments = minimumArguments;
        this.maximumArguments = maximumArguments;
    }

    public void execute(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
    public void execute(Player sender, String[] args) {
        throw new NotImplementedException();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public boolean allowConsole() {
        return allowConsole;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public int getMinimumArguments() {
        return minimumArguments;
    }

    public int getMaximumArguments() {
        return maximumArguments;
    }
}
