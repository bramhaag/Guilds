package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;

public class CommandLeave extends CommandBase {

    public CommandLeave(String name, String description, String permission, boolean allowConsole, String[] aliases, String[] arguments, int minimumArguments, int maximumArguments) {
        super(name, description, permission, allowConsole, aliases, arguments, minimumArguments, maximumArguments);
    }
}
