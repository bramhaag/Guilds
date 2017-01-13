package me.bramhaag.guilds.placeholders.placeholderapi;

import me.bramhaag.guilds.guild.Guild;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ClipGuildMemberCount extends EZPlaceholderHook {

    public ClipGuildMemberCount(Plugin plugin) {
        super(plugin, "guild-member-count");
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return "N/A";
        }

        return String.valueOf(guild.getMembers().size());
    }
}
