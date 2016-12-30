package me.bramhaag.guilds.database.databases.mysql;

import com.zaxxer.hikari.HikariDataSource;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;
import me.bramhaag.guilds.database.DatabaseProvider;

import me.bramhaag.guilds.guild.Guild;
import org.bukkit.configuration.ConfigurationSection;

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

        Main.newChain()
            .async(() -> updateQuery(Query.CREATE_TABLE_GUILDS))
            .async(() -> updateQuery(Query.CREATE_TABLE_MEMBERS))
            .execute();
    }

    @Override
    public void createGuild(Guild guild, Callback<Boolean, Exception> callback) {

    }

    @Override
    public void removeGuild(Guild guild, Callback<Boolean, Exception> callback) {

    }

    @Override
    public void getGuilds(Callback<HashMap<String, Guild>, Exception> callback) {

    }

    @Override
    public void updateGuild(Guild guild, Callback<Boolean, Exception> callback) {

    }

    private void updateQuery(String query) {
        return;
    }
}
