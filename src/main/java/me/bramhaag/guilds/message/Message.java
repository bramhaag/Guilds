package me.bramhaag.guilds.message;

import me.bramhaag.guilds.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public enum Message {

    COMMAND_ERROR_CONSOLE,
    COMMAND_ERROR_ARGS,
    COMMAND_ERROR_PERMISSION,
    COMMAND_ERROR_NOT_FOUND,
    COMMAND_ERROR_NO_GUILD,
    COMMAND_ERROR_NOT_GUILDMASTER,
    COMMAND_ERROR_INVALID_NUMBER,
    COMMAND_ERROR_ALREADY_IN_GUILD,
    COMMAND_ERROR_PLAYER_NOT_FOUND,
    COMMAND_ERROR_PLAYER_NOT_IN_GUILD,
    COMMAND_ERROR_INVALID_ROLE,
    COMMAND_ERROR_GUILD_NOT_FOUND,

    COMMAND_HELP_MESSAGE,
    COMMAND_HELP_NEXT_PAGE,

    COMMAND_ROLE_PLAYERS,

    COMMAND_CREATE_SUCCESSFUL,
    COMMAND_CREATE_ERROR,
    COMMAND_CREATE_REQUIREMENTS,
    COMMAND_CREATE_GUILD_NAME_TAKEN,

    COMMAND_DELETE_SUCCESSFUL,
    COMMAND_DELETE_ERROR,

    COMMAND_INFO_HEADER,
    COMMAND_INFO_NAME,
    COMMAND_INFO_MASTER,
    COMMAND_INFO_MEMBER_COUNT,
    COMMAND_INFO_RANK,

    COMMAND_PROMOTE_PROMOTED,
    COMMAND_PROMOTE_SUCCESSFUL,
    COMMAND_PROMOTE_CANNOT_PROMOTE,

    COMMAND_DEMOTE_DEMOTED,
    COMMAND_DEMOTE_SUCCESSFUL,
    COMMAND_DEMOTE_CANNOT_DEMOTE,

    COMMAND_CHAT_MESSAGE,

    COMMAND_ACCEPT_NOT_INVITED,
    COMMAND_ACCEPT_GUILD_FULL,
    COMMAND_ACCEPT_SUCCESSFUL,
    COMMAND_ACCEPT_PLAYER_JOINED,

    COMMAND_INVITE_MESSAGE,
    COMMAND_INVITE_SUCCESSFUL,

    COMMAND_LEAVE_SUCCESSFUL,
    COMMAND_LEAVE_PLAYER_LEFT,

    COMMAND_BOOT_SUCCESSFUL,
    COMMAND_BOOT_KICKED,
    COMMAND_BOOT_PLAYER_KICKED,

    COMMAND_PREFIX_REQUIREMENTS,
    COMMAND_PREFIX_SUCCESSFUL,

    EVENT_JOIN_PENDING_INVITES;

    public static void sendMessage(CommandSender sender, Message message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.PREFIX + Main.getInstance().getConfig().getString(getPath(message))));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.PREFIX + message));
    }

    public String replace(String... strings) {
        if(strings.length % 2 != 0) {
            throw new IllegalArgumentException("Amount of keys and values do not match!");
        }

        String message = Main.getInstance().getConfig().getString(getPath(this));

        if(message == null) {
            return null;
        }

        for(int i = 0; i < strings.length / 2; i++) {
            message = message.replace(strings[i * 2], strings[(i * 2) + 1]);
        }

        return message;
    }

     private static String getPath(Message message) {
        StringBuilder pathBuilder = new StringBuilder();
        String[] parts = message.name().toLowerCase().split("_");

        pathBuilder.append(parts[0])
                .append(".")
                .append(parts[1])
                .append(".")
                .append(String.join("-", Arrays.copyOfRange(parts, 2, parts.length)));

        return "messages." + pathBuilder.toString();
    }
}
