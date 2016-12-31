package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.message.Message;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CommandDelete extends CommandBase {

    public CommandDelete() {
        super("delete", "delete your current guild", "guilds.command.delete", false, new String[] { "remove" }, null, 0, 0);
    }

    @Override
    public void execute(Player player, String[] args) {
        Guild guild = Guild.getGuild(player.getUniqueId());

        if(guild == null) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
            return;
        }

        if(guild.getGuildMaster().getUniqueId() != player.getUniqueId()) {
            Message.sendMessage(player, Message.COMMAND_ERROR_NOT_GUILDMASTER);
            return;
        }

        Main.getInstance().getDatabaseProvider().removeGuild(guild, ((result, exception) -> {
            if(result) {
                Message.sendMessage(player, Message.COMMAND_DELETE_SUCCESSFUL.replace("{guildname}", args[0]));
            }
            else {
                Message.sendMessage(player, Message.COMMAND_DELETE_ERROR);

                Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while player '%s' was trying to delete guild '%s'", player.getName(), guild.getName()));
                if(exception != null) {
                    exception.printStackTrace();
                }
            }
        }));
    }
}
