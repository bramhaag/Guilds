package me.bramhaag.guilds.database.databases.mysql;

import co.aikar.taskchain.TaskChain;
import com.zaxxer.hikari.HikariDataSource;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.DatabaseProvider;

import me.bramhaag.guilds.guild.Guild;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.ResultSet;
import java.util.HashMap;


public class MySql extends DatabaseProvider {
    private HikariDataSource hikari;

    @Override
    public void initialize() {
        ConfigurationSection databaseSection = Main.getInstance().getConfig().getConfigurationSection("database");
        if(databaseSection == null) {
            //TODO probably should disable the plugin
            throw new IllegalStateException("MySQL database configured incorrectly, cannot continue properly");
        }

        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(databaseSection.getInt("pool-size"));

        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", databaseSection.getString("host"));
        hikari.addDataSourceProperty("port", databaseSection.getInt("port"));
        hikari.addDataSourceProperty("databaseName", databaseSection.getString("database"));

        hikari.addDataSourceProperty("user", databaseSection.getString("username"));
        hikari.addDataSourceProperty("password", databaseSection.getString("password"));

        hikari.validate();

        TaskChain<?> chain = Main.newSharedChain("create_guild");
        chain
            .async(() -> updateQuery(Query.CREATE_TABLE_GUILDS))
            .async(() -> updateQuery(Query.CREATE_TABLE_MEMBERS))
            .execute();
    }

    @Override
    public boolean createGuild(Guild guild) {
        return false;
    }

    @Override
    public boolean removeGuild(String name) {
        return false;
    }

    @Override
    public Guild getGuild(String name) {
        return null;
    }

    @Override
    public HashMap<String, Guild> getGuilds() {
        return null;
    }

    @Override
    public boolean updateGuild(Guild guild) {
        return false;
    }

    private void runQuery(String query, String... params) {
        return;
    }

    private ResultSet updateQuery(String query, String... params) {
        return null;
    }
}
