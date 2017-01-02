package me.bramhaag.guilds.listeners;

import me.bramhaag.guilds.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.getInstance().getScoreboardHandler().show(e.getPlayer());
    }
}
