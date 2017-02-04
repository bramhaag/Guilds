package me.bramhaag.guilds.placeholders;

import me.bramhaag.guilds.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Placeholders {

    public static String getGuild(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return "N/A";
        }

        return guild.getName();
    }

    public static String getGuildMaster(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return "N/A";
        }

        return Bukkit.getPlayer(guild.getGuildMaster().getUniqueId()).getName();
    }

    public static String getGuildmemberCount(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return "N/A";
        }

        return String.valueOf(guild.getMembers().size());
    }

    public static String getGuildPrefix(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return "N/A";
        }

        return guild.getPrefix();
    }
}
