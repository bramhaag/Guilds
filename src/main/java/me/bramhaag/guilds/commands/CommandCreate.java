package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.message.Message;
import org.bukkit.entity.Player;

public class CommandCreate extends CommandBase {

    public CommandCreate() {
        super("create", "Create a guild", "guilds.commands.create", false, new String[] { "c" }, new String[] { "<name>" }, 1, 1);
    }

    @Override
    public void execute(Player player, String[] args) {

        if(Guild.getGuild(player.getUniqueId()) != null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_ALREADY_IN_GUILD);
            return;
        }

        if(Main.getInstance().getDatabaseProvider().createGuild(new Guild(args[0], player.getUniqueId()))) {
            Message.sendMessage(player, Message.COMMAND_CREATE_SUCCESSFUL.replace("{guildname}", args[0]));
        }
        else {
            Message.sendMessage(player, Message.COMMAND_CREATE_ERROR_CREATE);
        }
    }
}
