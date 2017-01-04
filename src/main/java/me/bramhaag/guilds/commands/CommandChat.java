package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandChat extends CommandBase {

    public CommandChat() {
        super("chat", "Send a message to your guild members", "guilds.command.chat", false, new String[] { "c" }, new String[] { "<message>" }, 1, -1);
    }

    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());

        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        String message = String.join(" ", args);
        guild.sendMessage(Message.COMMAND_CHAT_MESSAGE.replace("{role}", guild.getMember(player.getUniqueId()).getRole().name(), "{player}", player.getName(), "{message}", message));
    }
}
