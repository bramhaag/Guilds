package me.bramhaag.guilds.guild;

import me.bramhaag.guilds.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bram on 22-12-2016.
 */
public class Guild {

    private int id;

    private String name;
    private List<GuildMember> members;

    public Guild(String name, UUID master) {
        this.name = name;

        this.members = new ArrayList<>();
        this.members.add(new GuildMember(master, GuildRole.MASTER));
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

    public GuildMember getGuildMaster() {
        return this.members.stream()
                .filter(member -> member.getRole() == GuildRole.MASTER)
                .findFirst()
                .get();
    }

    public boolean addMember(UUID uuid, GuildRole role) {
        this.members.add(new GuildMember(uuid, role));

        return true;
    }

    public boolean removeMember(UUID uuid) {
        if(!isMember(uuid))
            return false;

        this.members.remove(uuid);

        return true;
    }

    public GuildMember getMember(UUID uuid) {
        return members.stream().filter(member -> member.getUuid() == uuid).findFirst().get();
    }

    public boolean isMember(UUID uuid) {

        /*for(UUID member : members.keySet()) {
            if(member == uuid) {
                return true;
            }
        }*/

        return members.stream()
                .anyMatch(member -> member.getUuid() == uuid);
    }

    public static Guild getGuild(UUID uuid) {
        return Main.getInstance().getGuildHandler().getGuilds().stream() .filter(guild -> guild.getMembers().stream().anyMatch(member -> member.getUuid() == uuid)).findFirst().get();
    }
}
