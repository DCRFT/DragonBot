package pl.dcbot.Managers;

import com.zaxxer.hikari.HikariDataSource;
import pl.dcbot.DragonBot;
import pl.dcbot.Utils.ErrorUtils.ErrorReason;
import pl.dcbot.Utils.ErrorUtils.ErrorUtil;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseManager {

    public static final DragonBot plugin = DragonBot.getInstance();

    private static HikariDataSource ds;
    private static DatabaseManager instance = null;

    private static Connection connection = null;
    public static final String host = ConfigManager.getDatabaseFile().getString("host");
    public static final String database = ConfigManager.getDatabaseFile().getString("database");
    public static final String username = ConfigManager.getDatabaseFile().getString("user");
    public static final String password = ConfigManager.getDatabaseFile().getString("password");
    public static final int port = ConfigManager.getDatabaseFile().getInt("port");
    public static final String table = ConfigManager.getDatabaseFile().getString("table");

    private RowSetFactory factory;

    public static synchronized DatabaseManager get() {
        return instance == null ? instance = new DatabaseManager() : instance;
    }

    public static void openConnection() {
        try {

            if (getConnection() == null || getConnection().isClosed()) {

                ds = new HikariDataSource();
                ds.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
                ds.setUsername(username);
                ds.setPassword(password);
                ds.addDataSourceProperty("useSSL", "false");
                ds.addDataSourceProperty("verifyServerCertificate", "false");
                ds.addDataSourceProperty("characterEncoding","UTF-8");
                ds.addDataSourceProperty("useUnicode","true");
                ds.setConnectionInitSql("SET NAMES utf8mb4");
                ds.setMaximumPoolSize(4);

                synchronized(plugin) {
                    if (getConnection() == null || getConnection().isClosed()) {
                        connection = ds.getConnection();
                    }
                }
            }
        } catch (SQLException e) {
            ErrorUtil.logError(ErrorReason.DATABASE);
            e.printStackTrace();
        }
    }
    public static void closeConnection() {
                try {
                    if (getConnection() == null || getConnection().isClosed()) {
                        getConnection().close();
                        ds.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    ErrorUtil.logError(ErrorReason.DATABASE);
                }
    }

    private CachedRowSet createCachedRowSet() throws SQLException {
        if (factory == null) {
            factory = RowSetProvider.newFactory();
        }
        return factory.createCachedRowSet();
    }


    public void executeStatement(String sql, Object... parameters) {
        executeStatement(sql, false, parameters);
    }

    public ResultSet executeResultStatement(String sql, Object... parameters) {
        return executeStatement(sql, true, parameters);
    }

    private synchronized ResultSet executeStatement(String sql, boolean result, Object... parameters) {
        try (Connection connection = ds.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            if (result) {
                CachedRowSet results = createCachedRowSet();
                results.populate(statement.executeQuery());
                return results;
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorUtil.logError(ErrorReason.DATABASE);
        } catch (NullPointerException e) {
            e.printStackTrace();
            ErrorUtil.logError(ErrorReason.DATABASE);
        }
        return null;
    }


    public static Connection getConnection() {
        try {
            if(connection != null && !connection.isClosed() && ds.isRunning()) {
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorUtil.logError(ErrorReason.DATABASE);
        }
        return null;
    }
}
