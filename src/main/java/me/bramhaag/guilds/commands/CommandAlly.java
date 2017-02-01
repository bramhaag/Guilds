package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.guild.GuildRole;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandAlly extends CommandBase {

    public CommandAlly() {
        super("ally", "Ally commands", "guilds.command.ally", false, null, "<add | remove> <guild>, or chat <guild>", 2, -1);
    }

    @Override
    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        Guild targetGuild = Guild.getGuild(args[1]);
        if(targetGuild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_GUILD_NOT_FOUND);
            return;
        }

        GuildRole role = GuildRole.getRole(guild.getMember(player.getUniqueId()).getRole());

        if(args[0].equals("accept")) {
            if(!role.canAddAlly()) {
                return;
            }

            if(!guild.getPendingAllies().contains(targetGuild.getName())) {
                Message.sendMessage(player, Message.COMMAND_ALLY_GUILD_NOT_PENDING);
                return;
            }

            guild.addAlly(targetGuild);
            targetGuild.addAlly(guild);

            guild.getMembers().forEach(member -> Message.sendMessage(Bukkit.getPlayer(member.getUniqueId()), Message.COMMAND_ALLY_ACCEPTED.replace("guild", targetGuild.getName())));
            guild.getMembers().forEach(member -> Message.sendMessage(Bukkit.getPlayer(member.getUniqueId()), Message.COMMAND_ALLY_ACCEPTED_TARGET.replace("guild", guild.getName())));
        }
        else if(args[0].equalsIgnoreCase("decline")) {
            if(!role.canAddAlly()) {
                return;
            }

            if(!guild.getPendingAllies().contains(targetGuild.getName())) {
                Message.sendMessage(player, Message.COMMAND_ALLY_GUILD_NOT_PENDING);
                return;
            }

            guild.getMembers().stream().filter(member -> GuildRole.getRole(member.getRole()).canAddAlly()).forEach(member -> Message.sendMessage(Bukkit.getPlayer(member.getUniqueId()), Message.COMMAND_ALLY_DECLINED.replace("guild", targetGuild.getName())));
            guild.getMembers().stream().filter(member -> GuildRole.getRole(member.getRole()).canAddAlly()).forEach(member -> Message.sendMessage(Bukkit.getPlayer(member.getUniqueId()), Message.COMMAND_ALLY_DECLINED.replace("guild", guild.getName())));

        }
        if(args[0].equalsIgnoreCase("add")) {
            if(!role.canAddAlly()) {
                return;
            }

            targetGuild.addPendingAlly(guild);
            Message.sendMessage(player, Message.COMMAND_ALLY_SEND);
        }
        else if(args[0].equalsIgnoreCase("remove")) {
            if(!role.canRemoveAlly()) {
                return;
            }

            guild.removeAlly(targetGuild);
            targetGuild.removeAlly(guild);

            guild.sendMessage(Message.COMMAND_ALLY_REMOVED.replace("guild", targetGuild.getName()));
            targetGuild.sendMessage(Message.COMMAND_ALLY_REMOVED_TARGET.replace("guild", guild.getName()));
        }
        else if(args[0].equalsIgnoreCase("chat")) {
            if(!role.useAllyChat()) {
                return;
            }

            String message = String.format("[%s] [%s] %s", guild.getName(), role.getName(), String.join(" ", Arrays.copyOfRange(args, 2, args.length)));

            guild.sendMessage(message);
            targetGuild.sendMessage(message);
        }
    }
}
