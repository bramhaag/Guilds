package me.bramhaag.guilds.commands.base;

import me.bramhaag.guilds.IHandler;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bram on 22-12-2016.
 */
public class CommandHandler implements CommandExecutor, IHandler {

    private List<CommandBase> commands;

    @Override
    public void enable() {
        commands = new ArrayList<>();
    }

    @Override
    public void disable() {
        commands.clear();
        commands = null;
    }

    public void register(CommandBase command) {
        commands.add(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!cmd.getName().equalsIgnoreCase("guild")) {
            return true;
        }

        if(args.length == 0) {
            getCommand("help").execute(sender, args);
            return true;
        }

        for(CommandBase command : commands) {
            if (!command.getName().equalsIgnoreCase(args[0]) && !command.getAliases().contains(args[0].toLowerCase())) {
                continue;
            }

            if(!command.allowConsole() && !(sender instanceof Player)) {
                Message.sendMessage(sender, Message.COMMAND_ERROR_CONSOLE);
                return true;
            }

            if(!sender.hasPermission(command.getPermission())) {
                Message.sendMessage(sender, Message.COMMAND_ERROR_PERMISSION);
                return true;
            }

            if(command.getMinimumArguments() != -1 && command.getMinimumArguments() < args.length || command.getMaximumArguments() != -1 && command.getMaximumArguments() > args.length) {
                Message.sendMessage(sender, Message.COMMAND_ERROR_ARGS);
                return true;
            }

            if (command.allowConsole()) {
                command.execute(sender, args);
                return true;
            }
            else {
                command.execute((Player) sender, args);
                return true;
            }
        }

        Message.sendMessage(sender, Message.COMMAND_ERROR_NOT_FOUND);
        return true;
    }

    public List<CommandBase> getCommands() {
        return commands;
    }

    public CommandBase getCommand(String name) {
        return commands.stream().filter(command -> command.getName().equals(name)).findFirst().get();
    }
}
