package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.message.Message;
import org.bukkit.command.CommandSender;

public class CommandHelp extends CommandBase {

    private final int MAX_PAGE_SIZE = 6;

    public CommandHelp() {
        super("help", "View all commands", "guilds.command.help", true, null, null, 0, 1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        int page = 0;

        if(args.length > 0) {
            try {
                page = Integer.valueOf(args[0]);
            } catch (NumberFormatException ex) {
                Message.sendMessage(sender, Message.COMMAND_ERROR_INVALID_NUMBER);
            }
        }

        for(int i = 0; i < MAX_PAGE_SIZE; i++) {

            int index = (page * 6) + i;
            if(index > Main.getInstance().getCommandHandler().getCommands().size() - 1) {
                //Fail silently
                break;
            }

            CommandBase command = Main.getInstance().getCommandHandler().getCommands().get(index);

            Message.sendMessage(sender, Message.COMMAND_HELP_MESSAGE.replace("{command}", command.getName(), "{arguments}", String.join(" ", command.getArguments()), "{description}", command.getDescription()));
        }

        Message.sendMessage(sender, Message.COMMAND_HELP_NEXT_PAGE);
    }
}
