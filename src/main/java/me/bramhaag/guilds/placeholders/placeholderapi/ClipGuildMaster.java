package me.bramhaag.guilds.placeholders.placeholderapi;

import me.bramhaag.guilds.guild.Guild;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ClipGuildMaster extends EZPlaceholderHook {

    public ClipGuildMaster(Plugin plugin) {
        super(plugin, "guild-master");
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return "N/A";
        }

        return Bukkit.getPlayer(guild.getGuildMaster().getUniqueId()).getName();
    }
}
