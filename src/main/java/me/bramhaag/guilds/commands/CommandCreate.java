package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.database.Callback;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.message.Message;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.regex.Pattern;

public class CommandCreate extends CommandBase {

    public CommandCreate() {
        super("create", "Create a guild", "guilds.commands.create", false, new String[] { "c" }, new String[] { "<name>" }, 1, 1);
    }

    @Override
    public void execute(Player player, String[] args) {

        if(Guild.getGuild(player.getUniqueId()) != null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_ALREADY_IN_GUILD);
            return;
        }

        int minLength = Main.getInstance().getConfig().getInt("name.min-length");
        int maxLength = Main.getInstance().getConfig().getInt("name.max-length");
        String regex = Main.getInstance().getConfig().getString("name.regex");

        if(args[0].length() < minLength || args[0].length() > maxLength || !args[0].matches(regex)) {
            Message.sendMessage(player, Message.COMMAND_CREATE_ERROR_REQUIREMENTS);
            return;
        }

        Main.getInstance().getGuildHandler().getGuilds().keySet().forEach(name -> name.equalsIgnoreCase(args[0]));

        for (String name : Main.getInstance().getGuildHandler().getGuilds().keySet()) {
            if(name.equalsIgnoreCase(args[0])) {
                Message.sendMessage(player, Message.COMMAND_CREATE_ERROR_GUILD_NAME_TAKEN);
                return;
            }
        }

        Main.getInstance().getDatabaseProvider().createGuild(new Guild(args[0], player.getUniqueId()), ((result, exception) -> {
            if(result) {
                Message.sendMessage(player, Message.COMMAND_CREATE_SUCCESSFUL.replace("{guildname}", args[0]));
            }
            else {
                Message.sendMessage(player, Message.COMMAND_CREATE_ERROR_CREATE);

                Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while player '%s' was trying to create guild '%s'", player.getName(), args[0]));
                if(exception != null) {
                    exception.printStackTrace();
                }
            }
        }));
    }
}
