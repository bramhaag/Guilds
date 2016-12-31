package me.bramhaag.guilds.database.databases.mysql;

public class Query {

    public static final String CREATE_TABLE_GUILDS =          "CREATE IF NOT EXISTS guilds (" +
                                                              "name VARCHAR(255) NOT NULL, " +
                                                              "PRIMARY KEY (name)";
    public static final String CREATE_TABLE_MEMBERS =         "CREATE IF NOT EXISTS guild_members ( " +
                                                              "uuid VARCHAR(36) NOT NULL, " +
                                                              "guild VARCHAR(255) NOT NULL" +
                                                              "role INTEGER NOT NULL, " +
                                                              "PRIMARY KEY (uuid)";
    public static final String CREATE_TABLE_INVITED_MEMBERS = "CREATE IF NOT EXISTS invited_members ( " +
                                                              "uuid VARCHAR(36) NOT NULL, " +
                                                              "guild VARCHAR(255) NOT NULL";

    public static final String CREATE_GUILD =                 "INSERT INTO guilds (name) VALUES(?)";
    public static final String REMOVE_GUILD =                 "DELETE FROM guilds WHERE name=?";

    public static final String ADD_MEMBER =                   "INSERT INTO guild_members (uuid, guild, role) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE guild=VALUES(guild), role=VALUES(role)";
    public static final String REMOVE_MEMBER =                "DELETE FROM guild_members WHERE uuid=?";

    public static final String ADD_INVITED_MEMBER =           "INSERT INTO invited_members (uuid, guild) VALUES(?, ?)";
    public static final String REMOVE_INVITED_MEMBER =        "DELETE FROM invited_members WHERE uuid=?";

    public static final String GET_GUILD_MEMBERS =            "SELECT uuid, role FROM guild_members WHERE guild=?";
}
