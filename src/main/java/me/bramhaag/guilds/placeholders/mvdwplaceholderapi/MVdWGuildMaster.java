package me.bramhaag.guilds.placeholders.mvdwplaceholderapi;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.bramhaag.guilds.guild.Guild;
import org.bukkit.Bukkit;

public class MVdWGuildMaster implements PlaceholderReplacer {
    @Override
    public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
        Guild guild = Guild.getGuild(event.getPlayer().getUniqueId());
        if (guild == null) {
            return "N/A";
        }

        return Bukkit.getPlayer(guild.getGuildMaster().getUniqueId()).getName();
    }
}
