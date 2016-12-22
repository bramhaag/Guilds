package me.bramhaag.guilds.guild;

import java.util.Arrays;

/**
 * Created by Bram on 22-12-2016.
 */
public enum GuildRole {
    MASTER(0),
    OFFICER(1),
    VETERAN(2),
    MEMBER(3);

    int level;

    GuildRole(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static GuildRole getRole(int level) {
        return Arrays.stream(GuildRole.values()).filter(role -> role.getLevel() == level).findFirst().get();
    }
}
