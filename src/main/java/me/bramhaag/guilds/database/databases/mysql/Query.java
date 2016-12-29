package me.bramhaag.guilds.database.databases.mysql;

public class Query {

    public static final String CREATE_TABLE_GUILDS = "CREATE IF NOT EXISTS guilds (" +
                                                     "name VARCHAR(255) NOT NULL, " +
                                                     "PRIMARY KEY (name)";
    public static final String CREATE_TABLE_MEMBERS = "CREATE IF NOT EXISTS guild_members ( " +
                                                      "uuid VARCHAR(36) NOT NULL, " +
                                                      "role INTEGER NOT NULL, " +
                                                      "PRIMARY KEY (uuid)";

}
