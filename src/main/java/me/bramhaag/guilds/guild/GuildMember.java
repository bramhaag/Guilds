package me.bramhaag.guilds.guild;

import java.util.UUID;

/**
 * Created by Bram on 22-12-2016.
 */
public class GuildMember {

    private UUID uuid;
    private GuildRole role;

    public GuildMember(UUID uuid, GuildRole role) {
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public GuildRole getRole() {
        return role;
    }
}
