package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.guild.GuildRole;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandPromote extends CommandBase {

    public CommandPromote() {
        super("promote", "Promote a member of your guild", "guilds.command.promote", false, new String[] { "rankup" }, new String[] { "<player> [new role]" }, 1, 2);
    }

    @Override
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

        Player promotedPlayer = Bukkit.getPlayer(args[0]);

        if(promotedPlayer == null || !promotedPlayer.isOnline()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_PLAYER_NOT_FOUND.replace("{player}", args[0]));
            return;
        }

        GuildMember promotedMember = guild.getMember(promotedPlayer.getUniqueId());
        if(promotedMember == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_PLAYER_NOT_IN_GUILD.replace("{player}", promotedPlayer.getName()));
            return;
        }

        int currentLevel = promotedMember.getRole().getLevel();

        if(currentLevel <= 1) {
            Message.sendMessage(player, Message.COMMAND_PROMOTE_CANNOT_PROMOTE);
            return;
        }

        GuildRole role;

        if(args.length == 2) {
            try {
                role = GuildRole.valueOf(args[1]);
            }
            catch (IllegalArgumentException ex) {
                Message.sendMessage(player, Message.COMMAND_ERROR_INVALID_ROLE.replace("{input}", args[1]));
                return;
            }

            if(role.getLevel() > currentLevel) {
                Message.sendMessage(player, Message.COMMAND_ERROR_INVALID_ROLE.replace("{input}", args[1]));
                return;
            }
        }
        else {
            role = GuildRole.getRole(currentLevel - 1);
        }

        Message.sendMessage(promotedPlayer, Message.COMMAND_PROMOTE_PROMOTED.replace("{old-role}", promotedMember.getRole().name(), "{new-role}", role.name()));
        Message.sendMessage(player, Message.COMMAND_PROMOTE_SUCCESSFUL.replace("{player}", promotedPlayer.getName(), "{old-role}", promotedMember.getRole().name(), "{new-role}", role.name()));
        promotedMember.setRole(role);
    }
}
