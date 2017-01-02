package me.bramhaag.guilds.listeners;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.guild.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if(Guild.getGuild(player.getUniqueId()) == null) {
            return;
        }

        Main.getInstance().getScoreboardHandler().hide(player);
    }
}
