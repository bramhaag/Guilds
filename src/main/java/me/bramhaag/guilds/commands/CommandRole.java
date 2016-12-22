package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.GuildRole;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Bram on 22-12-2016.
 */
public class CommandRole extends CommandBase {

    public CommandRole() {
        super("role", "View all players with the specified role", "guilds.command.role", true, null, new String[] { "<" + String.join(" | ", Stream.of(GuildRole.values()).map(Enum::name).collect(Collectors.toList())) + ">" }, 1, 1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
