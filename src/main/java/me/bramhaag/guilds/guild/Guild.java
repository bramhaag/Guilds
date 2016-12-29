package me.bramhaag.guilds.guild;

import com.google.gson.annotations.Expose;
import me.bramhaag.guilds.Main;

import java.util.*;

public class Guild {

    @Expose(serialize = false)
    private String name;

    @Expose
    private List<GuildMember> members;

    @Expose
    private List<UUID> invitedMembers;

    public Guild(String name, UUID master) {
        this.name = name;

        this.members = new ArrayList<>();
        this.members.add(new GuildMember(master, GuildRole.MASTER));

        this.invitedMembers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<GuildMember> getMembers() {
        return members;
    }

    public List<UUID> getInvitedMembers() {
        return invitedMembers;
    }

    public GuildMember getGuildMaster() {
        return this.members.stream().filter(member -> member.getRole() == GuildRole.MASTER).findFirst().orElse(null);
    }

    public boolean addMember(UUID uuid, GuildRole role) {
        this.members.add(new GuildMember(uuid, role));

        return Main.getInstance().getDatabaseProvider().updateGuild(this);
    }

    public boolean removeMember(UUID uuid) {
        GuildMember member = getMember(uuid);
        if(member == null)
            return false;

        if(member == getGuildMaster()) {
            return Main.getInstance().getDatabaseProvider().removeGuild(this.name);
        }

        this.members.remove(member);

        return Main.getInstance().getDatabaseProvider().updateGuild(this);
    }

    public void inviteMember(UUID uuid) {
        invitedMembers.add(uuid);
        Main.getInstance().getDatabaseProvider().updateGuild(this);
    }

    public void removeInvitedPlayer(UUID uuid) {
        invitedMembers.remove(uuid);
        Main.getInstance().getDatabaseProvider().updateGuild(this);
    }

    public GuildMember getMember(UUID uuid) {
        return members.stream().filter(member -> member.getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    public static Guild getGuild(UUID uuid) {
        return Main.getInstance().getGuildHandler().getGuilds().stream().filter(guild -> guild.getMembers().stream().anyMatch(member -> member.getUniqueId().equals(uuid))).findFirst().orElse(null);
    }

    public static Guild getGuild(String name) {
        return Main.getInstance().getGuildHandler().getGuilds().stream().filter(guild -> guild.getName().equals(name)).findFirst().orElse(null);
    }
}
