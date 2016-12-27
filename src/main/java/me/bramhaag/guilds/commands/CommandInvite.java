package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandInvite extends CommandBase {

    public CommandInvite() {
        super("invite", "Invite a player to your guild", "guilds.comamnd.invite", false, null, new String[] { "<player>" }, 1, 1);
    }

    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        if(guild.getGuildMaster().getUniqueId() != player.getUniqueId()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NOT_GUILDMASTER);
            return;
        }

        Player invitedPlayer = Bukkit.getPlayer(args[0]);

        if(invitedPlayer == null || !invitedPlayer.isOnline()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_PLAYER_NOT_FOUND.replace("{player}", args[0]));
            return;
        }

        guild.inviteMember(invitedPlayer.getUniqueId());

        Message.sendMessage(invitedPlayer, Message.COMMAND_INVITE_MESSAGE);
        Message.sendMessage(player, Message.COMMAND_INVITE_SUCCESSFUL);
    }
}
