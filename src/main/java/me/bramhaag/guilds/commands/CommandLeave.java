package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandLeave extends CommandBase {

    public CommandLeave() {
        super("leave", "Leave your current guild", "guilds.commands.leave", false, null, null, 0, 0);
    }

    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        guild.removeMember(player.getUniqueId());
        Message.sendMessage(player, Message.COMMAND_LEAVE_SUCCESSFUL);

        for(GuildMember member : guild.getMembers()) {
            Player receiver = Bukkit.getPlayer(member.getUniqueId());
            if (receiver == null || !receiver.isOnline()) {
                continue;
            }

            Message.sendMessage(receiver, Message.COMMAND_LEAVE_PLAYER_LEFT.replace("{player}", player.getName()));
        }
    }
}
