package me.bramhaag.guilds.api.events;

import me.bramhaag.guilds.api.events.base.GuildEvent;
import me.bramhaag.guilds.guild.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class GuildLeaveEvent extends GuildEvent {

    public GuildLeaveEvent(Player player, Guild guild) {
        super(player, guild);
    }
}
