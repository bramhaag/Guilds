package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.message.Message;
import org.bukkit.entity.Player;

/**
 * Created by Bram on 22-12-2016.
 */
public class CommandDelete extends CommandBase {

    public CommandDelete() {
        super("delete", "delete your current guild", "guilds.command.delete", false, new String[] { "remove" }, null, 0, 0);
    }

    @Override
    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());

        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        if(guild.getGuildMaster().getUuid() != player.getUniqueId()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NOT_GUILDMASTER);
            return;
        }

        if(Main.getInstance().getDatabaseProvider().removeGuild(guild.getId())) {
            Message.sendMessage(player, Message.COMMAND_DELETE_SUCCESSFUL.replace("{guild}", guild.getName()));
            return;
        }
        else {
            Message.sendMessage(player, Message.COMMAND_DELETE_ERROR);
            return;
        }
    }
}
