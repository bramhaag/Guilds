package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;

/**
 * Created by Bram on 22-12-2016.
 */
public class CommandDelete extends CommandBase {

    public CommandDelete(String name, String description, String permission, boolean allowConsole, String[] aliases, String[] arguments, int minimumArguments, int maximumArguments) {
        super(name, description, permission, allowConsole, aliases, arguments, minimumArguments, maximumArguments);
    }
}
