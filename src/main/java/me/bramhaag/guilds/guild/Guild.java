package me.bramhaag.guilds.guild;

import com.google.gson.annotations.Expose;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Guild {

    @Expose(serialize = false)
    private String name;

    @Expose
    private String prefix;

    @Expose
    private List<GuildMember> members;

    @Expose
    private List<UUID> invitedMembers;

    @Expose
    private List<Guild> allies;

    public Guild(String name) {
        this.name = name;

        this.members = new ArrayList<>();
        this.invitedMembers = new ArrayList<>();
    }

    public Guild(String name, UUID master) {
        this.name = name;

        this.prefix = name.substring(0, Main.getInstance().getConfig().getInt("prefix.max-length") > name.length() ? name.length() : Main.getInstance().getConfig().getInt("prefix.max-length"));
        this.members = new ArrayList<>();
        this.members.add(new GuildMember(master, 0));

        this.invitedMembers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<GuildMember> getMembers() {
        return members;
    }

    public List<UUID> getInvitedMembers() {
        return invitedMembers;
    }

    public GuildMember getGuildMaster() {
        return this.members.stream().filter(member -> member.getRole() == 0).findFirst().orElse(null);
    }

    public void addMember(UUID uuid, GuildRole role) {
        this.members.add(new GuildMember(uuid, role.getLevel()));

        Player player = Bukkit.getPlayer(uuid);
        if(player != null && player.isOnline()) {
            Main.getInstance().getScoreboardHandler().show(player);
        }

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
                    return;
                }

                HashMap<String, Guild> guilds = Main.getInstance().getGuildHandler().getGuilds();
                guilds.remove(this.name);

                Main.getInstance().getGuildHandler().setGuilds(guilds);

                Main.getInstance().getScoreboardHandler().update();
            });
            return;
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

    public void sendMessage(String message) {
        for(GuildMember member : this.members) {
            Player receiver = Bukkit.getPlayer(member.getUniqueId());
            if (receiver == null || !receiver.isOnline()) {
                continue;
            }
            Message.sendMessage(receiver, message);
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void updatePrefix(String prefix) {
        setPrefix(prefix);

        Main.getInstance().getDatabaseProvider().updateGuild(this, ((result, exception) -> {
            if(!result) {
                Main.getInstance().getLogger().log(Level.SEVERE, String.format("An error occurred while updating prefix to '%s' for guild '%s'", prefix, this.name));
                if(exception != null) {
                    exception.printStackTrace();
                }
            }
        }));

        Main.getInstance().getScoreboardHandler().update();
    }

    public List<Guild> getAllies() {
        return allies;
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

    public static boolean areAllies(UUID uuid1, UUID uuid2) {
        Guild guild1 = getGuild(uuid1);
        Guild guild2 = getGuild(uuid2);

        return !(guild1 == null || guild2 == null) && guild1.getAllies().contains(guild2);

    }
}
