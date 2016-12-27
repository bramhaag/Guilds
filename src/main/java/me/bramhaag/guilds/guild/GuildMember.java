package me.bramhaag.guilds.guild;

import java.util.UUID;

public class GuildMember {

    private UUID uuid;
    private GuildRole role;

    public GuildMember(UUID uuid, GuildRole role) {
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public GuildRole getRole() {
        return role;
    }

    public void setRole(GuildRole role) {
        this.role = role;
    }
}
