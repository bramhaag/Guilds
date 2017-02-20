package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandVersion extends CommandBase
{
    public CommandVersion() {
        super("version", "Check version", "guilds.command.version", true, null, null, 0, 0);
    }

    public void execute(CommandSender sender, String[] args) {
        PluginDescriptionFile pdf = Main.getInstance().getDescription();
        sender.sendMessage("Guilds v" + pdf.getVersion() + " by " + String.join(" & ", pdf.getAuthors()));
    }
}
