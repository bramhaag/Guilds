package me.bramhaag.guilds.commands;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.base.CommandBase;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildMember;
import me.bramhaag.guilds.guild.GuildRole;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Bram on 22-12-2016.
 */
public class CommandPromote extends CommandBase {

    public CommandPromote() {
        super("promote", "Promote a member of your guild", "guilds.command.promote", false, new String[] { "rankup" }, new String[] { "<player> [role]" }, 1, 2);
    }

    @Override
    public void execute(Player player, String[] args) {
        Player promotedPlayer = Bukkit.getPlayer(args[0]);

        if(promotedPlayer == null || !promotedPlayer.isOnline()) {
            Message.sendMessage(player, Message.COMMAND_PROMOTE_PLAYER_NOT_FOUND.replace("{player}", args[0]));
            return;
        }

        //TODO checks for player
        GuildMember promotedMember = Main.getInstance().getDatabaseProvider().getGuild(Guild.getGuild(player.getUniqueId()).getId()).getMember(promotedPlayer.getUniqueId());
        int currentLevel = promotedMember.getRole().getLevel();

        if(currentLevel <= 1) {
            Message.sendMessage(player, Message.COMMAND_PROMOTE_CANNOT_PROMOTE);
            return;
        }

        promotedMember.setRole(GuildRole.getRole(currentLevel - 1));
    }
}
