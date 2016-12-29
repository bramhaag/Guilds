package me.bramhaag.guilds.database.databases.mysql;

import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariDataSource;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.DatabaseProvider;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.util.Callback;
import org.bukkit.configuration.ConfigurationSection;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MySql extends DatabaseProvider {
    private HikariDataSource hikari;

    @Override
    public void initialize() {
        ConfigurationSection databaseSection = Main.getInstance().getConfig().getConfigurationSection("database");
        if(databaseSection == null) {
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

        runUpdateAsync(Query.CREATE_TABLE_GUILDS, null, new Callback<Integer>() {
            @Override
            public void onQueryComplete(Integer result) {
                return;
            }

            @Override
            public void onQueryError(Exception ex) {
                ex.printStackTrace();
            }
        });

        runUpdateAsync(Query.CREATE_TABLE_MEMBERS, null, new Callback<Integer>() {
            @Override
            public void onQueryComplete(Integer result) {
                return;
            }

            @Override
            public void onQueryError(Exception ex) {
                ex.printStackTrace();
            }
        });
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

    private void runQueryAsync(String query, Object[] parameters, Callback<ResultSet> callback) {
        Connection connection = null;
        PreparedStatement preparedStatement;

        try {
            connection = hikari.getConnection();
            preparedStatement = connection.prepareStatement(query);

            if(parameters != null) {
                for(int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }

            ResultSet rs = preparedStatement.executeQuery();
            CachedRowSet cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(rs);

            callback.onQueryComplete(cachedRowSet);

            rs.close();
        }
        catch (SQLException ex) {
            callback.onQueryError(ex);
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    callback.onQueryError(ex);
                }
            }
        }
    }

    private void runUpdateAsync(String query, Object[] parameters, Callback<Integer> callback) {
        Connection connection = null;
        PreparedStatement preparedStatement;

        try {
            connection = hikari.getConnection();
            preparedStatement = connection.prepareStatement(query);

            if(parameters != null) {
                for(int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }

            int result = preparedStatement.executeUpdate();
            callback.onQueryComplete(result);

        }
        catch (SQLException ex) {
            callback.onQueryError(ex);
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    callback.onQueryError(ex);
                }
            }
        }
    }
}
