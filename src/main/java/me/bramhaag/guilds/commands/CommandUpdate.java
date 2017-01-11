package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.message.Message;
import me.bramhaag.guilds.updater.Updater;
import org.bukkit.command.CommandSender;

public class CommandUpdate extends CommandBase {

    public CommandUpdate() {
        super("update", "Update to the most recent version of Guilds", "guilds.command.update", true, null, null, 0, 0);
    }

    public void execute(CommandSender sender, String[] args) {
        Updater.checkForUpdates((result, exception) -> {
            if (result) {
                Message.sendMessage(sender, Message.COMMAND_UPDATE_FOUND);

                Updater.downloadUpdate((downloadResult, downloadException) -> {
                    if(downloadResult) {
                        Message.sendMessage(sender, Message.COMMAND_UPDATE_SUCCESSFUL);
                        return;
                    }
                    else {
                        Message.sendMessage(sender, Message.COMMAND_UPDATE_ERROR);
                    }
                });
            }
        });
    }
}
