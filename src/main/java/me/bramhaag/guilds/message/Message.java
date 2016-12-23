package me.bramhaag.guilds.message;

import me.bramhaag.guilds.Main;
import org.bukkit.command.CommandSender;

/**
 * Created by Bram on 22-12-2016.
 */
public enum Message {

    COMMAND_ERROR_CONSOLE,
    COMMAND_ERROR_ARGS,
    COMMAND_ERROR_PERMISSION,
    COMMAND_ERROR_NOT_FOUND,
    COMMAND_ERROR_NO_GUILD,
    COMMAND_ERROR_NOT_GUILDMASTER,

    COMMAND_HELP_MESSAGE,
    COMMAND_HELP_ERROR_NUMBER,
    COMMAND_HELP_NEXT_PAGE,

    COMMAND_ROLE_ERROR_NO_GUILD,
    COMMAND_ROLE_ERROR_ROLE_NOT_FOUND,
    COMMAND_ROLE_PLAYERS,

    COMMAND_CREATE_ERROR_IN_GUILD,
    COMMAND_CREATE_SUCCESSFUL,
    COMMAND_CREATE_ERROR_CREATE,

    COMMAND_DELETE_SUCCESSFUL,
    COMMAND_DELETE_ERROR,

    COMMAND_INFO_HEADER,
    COMMAND_INFO_NAME,
    COMMAND_INFO_MASTER,
    COMMAND_INFO_MEMBER_COUNT,
    COMMAND_INFO_RANK;

    public static void sendMessage(CommandSender sender, Message message) {
        sender.sendMessage(Main.PREFIX + Main.getInstance().getConfig().getString("message." + message.name().toLowerCase().replace('_', '-')));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(Main.PREFIX + message);
    }

    public String replace(String... strings) {
        //String[] keys = new String[strings.length / 2];
        //String[] values = new String[strings.length / 2];

        String message =  Main.getInstance().getConfig().getString("message." + this.name().toLowerCase().replace('_', '-'));

        for(int i = 0; i < strings.length / 2; i++) {
            //keys[i] = strings[i * 2];
            //values[i] = strings[(i * 2) + 1];

            message.replace(strings[i * 2], strings[(i * 2) + 1]);
        }

        /*for(int i = 0; i < keys.length; i++) {
            message.replace(keys[i], values[i]);
        }*/

        return message;
    }
}
