package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.message.Message;
import me.bramhaag.guilds.util.ConfirmAction;
import org.bukkit.entity.Player;

public class CommandLeave extends CommandBase {

    public CommandLeave() {
        super("leave", "Leave your current guild", "guilds.command.leave", false, null, null, 0, 0);
    }

    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        if(guild.getGuildMaster().getUniqueId().equals(player.getUniqueId())) {
            Message.sendMessage(player, Message.COMMAND_LEAVE_WARNING_GUILDMASTER);
        }
        else {
            Message.sendMessage(player, Message.COMMAND_LEAVE_WARNING);
        }

        Main.getInstance().getCommandHandler().addAction(player, new ConfirmAction() {
            @Override
            public void accept() {
                guild.removeMember(player.getUniqueId());
                Message.sendMessage(player, Message.COMMAND_LEAVE_SUCCESSFUL);

                Main.getInstance().getCommandHandler().removeAction(player);
            }

            @Override
            public void decline() {
                Message.sendMessage(player, Message.COMMAND_LEAVE_CANCELLED);

                Main.getInstance().getCommandHandler().removeAction(player);
            }
        });

        guild.sendMessage(Message.COMMAND_LEAVE_PLAYER_LEFT.replace("{player}", player.getName()));
    }
}
