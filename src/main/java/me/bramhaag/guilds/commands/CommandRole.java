package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.guild.GuildRole;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
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
    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());

        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ROLE_ERROR_NO_GUILD);
            return;
        }

        GuildRole role = GuildRole.valueOf(args[0].toUpperCase());

        if(role == null) {
            Message.sendMessage(player, Message.COMMAND_ROLE_ERROR_ROLE_NOT_FOUND);
            return;
        }

        List<GuildMember> members = guild.getMembers().stream().filter(member -> member.getRole() == role).collect(Collectors.toList());
        members.forEach(member -> Message.sendMessage(player, Message.COMMAND_ROLE_PLAYERS.replace("{name}", Bukkit.getPlayer(member.getUuid()).getName(), "{role}", role.toString())));
    }
}
