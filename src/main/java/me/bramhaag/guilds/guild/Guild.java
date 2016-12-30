package me.bramhaag.guilds.guild;

import com.google.gson.annotations.Expose;
import me.bramhaag.guilds.Main;

import java.util.*;
import java.util.logging.Level;

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

    public void addMember(UUID uuid, GuildRole role) {
        this.members.add(new GuildMember(uuid, role));

        Main.getInstance().getDatabaseProvider().updateGuild(this, (result, exception) -> {
            if(!result) {
                Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while adding a member with an UUID of '%s' to guild '%s'", uuid, this.name));

                if(exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void removeMember(UUID uuid) {
        GuildMember member = getMember(uuid);
        if(member == null)
            return;

        if(member == getGuildMaster()) {
            Main.getInstance().getDatabaseProvider().removeGuild(this, (result, exception) -> {
                if(!result) {
                    Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while removing guild '%s'", this.name));
                    if(exception != null) {
                        exception.printStackTrace();
                    }
                }
            });
        }

        this.members.remove(member);
        Main.getInstance().getDatabaseProvider().updateGuild(this, ((result, exception) -> {
            if(!result) {
                Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while removing a member with the UUID of '%s' from guild '%s'", uuid, this.name));
                if(exception != null) {
                    exception.printStackTrace();
                }
            }
        }));
    }

    public void inviteMember(UUID uuid) {
        invitedMembers.add(uuid);
        Main.getInstance().getDatabaseProvider().updateGuild(this, ((result, exception) -> {
            if(!result) {
                Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while inviting a member with the UUID of '%s' to guild '%s'", uuid, this.name));
                if(exception != null) {
                    exception.printStackTrace();
                }
            }
        }));
    }

    public void removeInvitedPlayer(UUID uuid) {
        invitedMembers.remove(uuid);
        Main.getInstance().getDatabaseProvider().updateGuild(this, ((result, exception) -> {
            if(!result) {
                Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while removing an invited member member with the UUID of '%s' to guild '%s'", uuid, this.name));
                if(exception != null) {
                    exception.printStackTrace();
                }
            }
        }));
    }

    public GuildMember getMember(UUID uuid) {
        return members.stream().filter(member -> member.getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    public static Guild getGuild(UUID uuid) {
        return Main.getInstance().getGuildHandler().getGuilds().values().stream().filter(guild -> guild.getMembers().stream().anyMatch(member -> member.getUniqueId().equals(uuid))).findFirst().orElse(null);
    }

    public static Guild getGuild(String name) {
        return Main.getInstance().getGuildHandler().getGuilds().values().stream().filter(guild -> guild.getName().equals(name)).findFirst().orElse(null);
    }
}
