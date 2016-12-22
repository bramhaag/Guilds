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

    COMMAND_HELP_ERROR_NUMBER,
    COMMAND_HELP_NEXT_PAGE;

    public static void sendMessage(CommandSender sender, Message message) {
        sender.sendMessage(Main.PREFIX + Main.getInstance().getConfig().getString("message." + message.name().toLowerCase().replace('_', '-')));
    }
}
