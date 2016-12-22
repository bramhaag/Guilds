package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import org.bukkit.command.CommandSender;

/**
 * Created by Bram on 22-12-2016.
 */
public class CommandHelp extends CommandBase {

    public CommandHelp() {
        super("help", "View all commands", "guilds.command.help", true, null, null, -1, -1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //sender.sendMessage("kittens"); whew it actually works
    }
}
