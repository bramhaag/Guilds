package me.bramhaag.guilds.guild;

import java.util.Arrays;

public enum GuildRole {
    MASTER(0, true, true, true, true, true, true, true),
    OFFICER(1, true, true, true, true, true, false, false),
    VETERAN(2, true, true, false, false, false, false, false),
    MEMBER(3, true, false, false, false, false, false, false);

    private int level;

    private boolean chat;
    private boolean invite;
    private boolean kick;
    private boolean promote;
    private boolean demote;
    private boolean changeMaster;
    private boolean remove;

    GuildRole(int level, boolean chat, boolean invite, boolean kick, boolean promote, boolean demote, boolean changeMaster, boolean removeGuild) {
        this.level = level;

        this.chat = chat;
        this.invite = invite;
        this.kick = kick;
        this.promote = promote;
        this.demote = demote;
        this.changeMaster = changeMaster;
        this.remove = removeGuild;
    }

    public int getLevel() {
        return level;
    }

    public static GuildRole getRole(int level) {
        return Arrays.stream(GuildRole.values()).filter(role -> role.getLevel() == level).findFirst().orElse(null);
    }

    public boolean canChat() {
        return chat;
    }

    public boolean canInvite() {
        return invite;
    }

    public boolean canKick() {
        return kick;
    }

    public boolean canPromote() {
        return promote;
    }

    public boolean canDemote() {
        return demote;
    }

    public boolean canChangeMaster() {
        return changeMaster;
    }

    public boolean canRemoveGuild() {
        return remove;
    }
}
