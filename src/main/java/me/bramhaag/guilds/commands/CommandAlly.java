package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildRole;
import me.bramhaag.guilds.message.Message;
import org.bukkit.entity.Player;

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

        if(args[0].equalsIgnoreCase("add")) {
            if(!role.canAddAlly()) {
                return;
            }
        }
        else if(args[0].equalsIgnoreCase("remove")) {
            if(!role.canRemoveAlly()) {
                return;
            }
        }
        else if(args[0].equalsIgnoreCase("chat")) {
            if(!role.useAllyChat()) {
                return;
            }
        }
    }
}
