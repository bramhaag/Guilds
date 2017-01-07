package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.guild.GuildRole;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandDemote extends CommandBase {

    public CommandDemote() {
        super("demote", "Demote a member of your guild", "guilds.command.demote", false, new String[] { "rankdown" }, new String[] { "<player> [new role]" }, 1, 2);
    }

    @Override
    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        GuildRole role = guild.getMember(player.getUniqueId()).getRole();
        if(!role.canDemote()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_ROLE_NO_PERMISSION);
        }

        Player demotedPlayer = Bukkit.getPlayer(args[0]);

        if(demotedPlayer == null || !demotedPlayer.isOnline()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_PLAYER_NOT_FOUND.replace("{player}", args[0]));
            return;
        }

        GuildMember demotedMember = guild.getMember(demotedPlayer.getUniqueId());
        if(demotedMember == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_PLAYER_NOT_IN_GUILD.replace("{player}", demotedPlayer.getName()));
            return;
        }

        int currentLevel = demotedMember.getRole().getLevel();

        if(currentLevel <= 1) {
            Message.sendMessage(player, Message.COMMAND_DEMOTE_CANNOT_DEMOTE);
            return;
        }

        GuildRole demotedRole;

        if(args.length == 2) {
            try {
                demotedRole = GuildRole.valueOf(args[1]);
            }
            catch (IllegalArgumentException ex) {
                Message.sendMessage(player, Message.COMMAND_ERROR_INVALID_ROLE.replace("{input}", args[1]));
                return;
            }

            if(role.getLevel() < demotedMember.getRole().getLevel()) {
                Message.sendMessage(player, Message.COMMAND_ERROR_ROLE_NO_PERMISSION);
                return;
            }

            if(currentLevel > demotedRole.getLevel()) {
                Message.sendMessage(player, Message.COMMAND_DEMOTE_NOT_DEMOTION);
                return;
            }
        }
        else {
            demotedRole = GuildRole.getRole(currentLevel - 1);
        }

        Message.sendMessage(demotedPlayer, Message.COMMAND_DEMOTE_DEMOTED.replace("{old-rank}", demotedMember.getRole().name(), "{new-rank}", demotedRole.name()));
        Message.sendMessage(player, Message.COMMAND_DEMOTE_SUCCESSFUL.replace("{player}", demotedPlayer.getName(), "{old-rank}", demotedMember.getRole().name(), "{new-rank}", demotedRole.name()));
        demotedMember.setRole(demotedRole);
    }
}
