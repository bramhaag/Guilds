package me.bramhaag.guilds.guild;

import me.bramhaag.guilds.Main;

import java.util.*;

public class Guild {

    private int id;
    private String name;

    private List<GuildMember> members;
    private List<UUID> invitedMembers;

    public Guild(String name, UUID master) {
        this.id = getFreeId();

        this.name = name;

        this.members = new ArrayList<>();
        this.members.add(new GuildMember(master, GuildRole.MASTER));

        this.invitedMembers = new ArrayList<>();
    }

    public int getId() {
        return id;
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

        return true;
    }

    public boolean removeMember(UUID uuid) {
        GuildMember member = getMember(uuid);
        if(member == null)
            return false;

        this.members.remove(member);

        return true;
    }

    public void inviteMember(UUID uuid) {
        invitedMembers.add(uuid);
    }

    public void removeInvitedPlayer(UUID uuid) {
        invitedMembers.remove(uuid);
    }

    public GuildMember getMember(UUID uuid) {
        return members.stream().filter(member -> member.getUuid() == uuid).findFirst().orElse(null);
    }

    public static Guild getGuild(UUID uuid) {
        return Main.getInstance().getGuildHandler().getGuilds().stream().filter(guild -> guild.getMembers().stream().anyMatch(member -> member.getUuid().equals(uuid))).findFirst().orElse(null);
    }

    public static Guild getGuild(int id) {
        return Main.getInstance().getGuildHandler().getGuilds().stream().filter(guild -> guild.getId() == id).findFirst().orElse(null);
    }

    private static int getFreeId() {
        if(Main.getInstance().getDatabaseProvider().getGuilds() == null) {
            return 0;
        }

        List<Integer> ids = new ArrayList<>(Main.getInstance().getDatabaseProvider().getGuilds().keySet());

        if(ids.size() == 0) {
            return 0;
        }

        Collections.sort(ids);

        for(int i = 0; i < ids.size(); i++) {
            if(ids.get(i) != i) {
                return i;
            }
        }

        return ids.get(ids.size() - 1) + 1;
    }
}
