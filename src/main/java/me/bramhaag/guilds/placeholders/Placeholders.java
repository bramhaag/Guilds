package me.bramhaag.guilds.placeholders;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Placeholders {

    private static final String DEFAULT = Main.getInstance().getConfig().getString("placeholders.default");

    public static String getGuild(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return DEFAULT;
        }

        return guild.getName();
    }

    public static String getGuildMaster(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return DEFAULT;
        }

        return Bukkit.getPlayer(guild.getGuildMaster().getUniqueId()).getName();
    }

    public static String getGuildMemberCount(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return DEFAULT;
        }

        return String.valueOf(guild.getMembers().size());
    }

    public static String getGuildPrefix(Player player) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return DEFAULT;
        }

        return guild.getPrefix();
    }
}
