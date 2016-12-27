package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandBoot extends CommandBase {

    public CommandBoot() {
        super("boot", "Kick a player from your guild", "guilds.command.boot", false, new String[] { "kick" }, new String[] { "<player>" }, 1, 1);
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

        Player kickedPlayer = Bukkit.getPlayer(args[0]);

        if(kickedPlayer == null || !kickedPlayer.isOnline()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_PLAYER_NOT_FOUND.replace("{player}", args[0]));
            return;
        }

        guild.removeMember(kickedPlayer.getUniqueId());

        Message.sendMessage(kickedPlayer, Message.COMMAND_BOOT_KICKED.replace("{kicker}", player.getName()));
        Message.sendMessage(player, Message.COMMAND_BOOT_SUCCESSFUL);

        for(GuildMember member : guild.getMembers()) {
            Player receiver = Bukkit.getPlayer(member.getUniqueId());
            if (receiver == null || !receiver.isOnline()) {
                continue;
            }

            Message.sendMessage(player, Message.COMMAND_BOOT_PLAYER_KICKED.replace("{player}", player.getName()));
        }
    }
}
